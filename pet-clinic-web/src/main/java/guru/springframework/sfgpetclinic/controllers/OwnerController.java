package guru.springframework.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@RequestMapping("/owners")
@Controller
public class OwnerController
{
    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService)
    {
        this.ownerService = ownerService;
    }

    @RequestMapping(path = { "/find" })
    public String findOwners()
    {
        return "notimplemented";
    }

    @RequestMapping(path = { "", "/", "/index", "/index.html" })
    public String listOwners(final Model model)
    {
        model.addAttribute("owners", ownerService.findAll());

        return "owners/index";
    }

    @GetMapping(path = { "/{ownerId}" })
    public ModelAndView showOwnerById(@PathVariable final Long ownerId)
    {
        final Owner owner = ownerService.findById(ownerId);

        final ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(/* "owner", */ owner);

        return mav;
    }
}