package guru.springframework.sfgpetclinic.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;

/**
 * FIXME: Add unit tests!
 */
@Controller
public final class VisitController
{
    private final PetService   petService;
    private final VisitService visitService;

    public VisitController(final VisitService visitService, final PetService petService)
    {
        this.visitService = visitService;
        this.petService = petService;
    }

    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param petId
     *
     * @return Visit
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") final Long petId, final Model model)
    {
        final Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);

        final Visit visit = new Visit();
        pet.addVisit(visit);

        return visit;
    }

    /**
     * Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
     *
     * @see #showCreationForm(Long, Model)
     */
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processCreationForm(@Valid final Visit visit, final BindingResult result)
    {
        if( result.hasErrors() == true )
        {
            return "pets/createOrUpdateVisitForm";
        }

        visitService.save(visit);

        return "redirect:/owners/{ownerId}"; // TODO: Is this really working?
    }

    @InitBinder
    public void setAllowedFields(final WebDataBinder dataBinder)
    {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
     *
     * @see #processCreationForm(Visit, BindingResult)
     */
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String showCreationForm(@PathVariable("petId") final Long petId, final Model model)
    {
        return "pets/createOrUpdateVisitForm";
    }
}