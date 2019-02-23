package guru.springframework.sfgpetclinic.controllers;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController
{
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

    @ModelAttribute("petTypes")
    public Collection<PetType> populatePetTypes()
    {
        return petTypeService.findAll();
    }

    @InitBinder
    public void setAllowedFields(final WebDataBinder dataBinder)
    {
        dataBinder.setDisallowedFields("id");
    }
}