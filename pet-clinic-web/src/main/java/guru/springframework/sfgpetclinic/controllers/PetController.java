package guru.springframework.sfgpetclinic.controllers;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.validators.PetValidator;

@Controller
@RequestMapping("/owners/{ownerId}")
public final class PetController
{
    protected static final String VIEW_NAME_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";

    private final OwnerService   ownerService;
    private final PetService     petService;
    private final PetTypeService petTypeService;

    public PetController(final PetService petService, final PetTypeService petTypeService, final OwnerService ownerService)
    {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable final Long ownerId)
    {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(final WebDataBinder dataBinder)
    {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void initPetBinder(final WebDataBinder dataBinder)
    {
        dataBinder.setValidator(new PetValidator());
    }

    @ModelAttribute("petTypes")
    public Collection<PetType> populatePetTypes()
    {
        return StreamSupport.stream(petTypeService.findAll().spliterator(), false)
                            .sorted((pt1, pt2) -> pt1.getName().compareTo(pt2.getName()))
                            .collect(toList());
    }

    /**
     * @see #showCreationForm(Owner, Model)
     */
    @PostMapping(path = { "/pets/new" })
    public String processCreationForm(final Owner owner, @Valid final Pet pet, final BindingResult result, final Model model)
    {
        if( pet.isNew() == true && StringUtils.isBlank(pet.getName()) == false )
        {
            final Optional<Pet> lookupResult = owner.getPets().stream()
                                                    .filter(curPet -> curPet.getName().equals(pet.getName()) == true)
                                                    .findFirst();
            if( lookupResult.isPresent() == true )
            {
                result.rejectValue("name", "duplicate", "already exists");
            }
        }

        owner.getPets().add(pet);
        // pet.setOwner(owner); TODO: required?

        if( result.hasErrors() == true )
        {
            model.addAttribute("pet", pet);
            return VIEW_NAME_CREATE_OR_UPDATE_PET_FORM;
        }

        petService.save(pet);

        // return "redirect:/owners/{ownerId}"; // TODO: Why should this be working?
        return "redirect:/owners/" + owner.getId();
    }

    /**
     * @see #showUpdateForm(Long, ModelMap)
     */
    @PostMapping(path = { "/pets/{petId}/edit" })
    public String processUpdateForm(final Owner owner, @Valid final Pet pet, final BindingResult result, final Model model)
    {
        if( result.hasErrors() == true )
        {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);

            return VIEW_NAME_CREATE_OR_UPDATE_PET_FORM;
        }

        // owner.getPets().add(pet); // TODO: Why should this be necessary?

        petService.save(pet);

        // return "redirect:/owners/{ownerId}"; // TODO: Why should this be working?
        return "redirect:/owners/" + owner.getId();
    }

    @InitBinder
    public void setAllowedFields(final WebDataBinder dataBinder)
    {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * @see #processCreationForm(Owner, Pet, BindingResult, Model)
     */
    @GetMapping(path = { "/pets/new" })
    public String showCreationForm(final Owner owner, final Model model)
    {
        final Pet newPet = Pet.builder()
                              .owner(owner)
                              .build();

        model.addAttribute("pet", newPet);

        return VIEW_NAME_CREATE_OR_UPDATE_PET_FORM;
    }

    /**
     * @see #processUpdateForm(Owner, Pet, BindingResult, Model)
     */
    @GetMapping(path = { "/pets/{petId}/edit" })
    public String showUpdateForm(@PathVariable final Long petId, final ModelMap model)
    {
        final Pet existingPet = petService.findById(petId);

        model.put("pet", existingPet);

        return VIEW_NAME_CREATE_OR_UPDATE_PET_FORM;
    }
}