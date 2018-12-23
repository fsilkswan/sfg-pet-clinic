package guru.springframework.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}