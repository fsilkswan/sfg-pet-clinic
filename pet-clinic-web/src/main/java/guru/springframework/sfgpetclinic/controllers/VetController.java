package guru.springframework.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.sfgpetclinic.services.VetService;

@Controller
public class VetController
{
    private final VetService vetService;

    // @Autowired
    public VetController(final VetService vetService)
    {
        this.vetService = vetService;
    }

    @RequestMapping(path = { "/vets", "/vets/index", "/vets/index.html" })
    public String listVets(final Model model)
    {
        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }
}