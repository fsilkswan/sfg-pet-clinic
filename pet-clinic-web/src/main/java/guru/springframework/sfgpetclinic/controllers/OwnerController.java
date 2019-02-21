package guru.springframework.sfgpetclinic.controllers;

import static org.apache.logging.log4j.util.Strings.isBlank;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@RequestMapping("/owners")
@Controller
public class OwnerController
{
    protected final static String SQL_WILDCARD = "%";

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService)
    {
        this.ownerService = ownerService;
    }

    @GetMapping(path = { "" })
    public String processFindForm(Owner owner, final BindingResult result, final Model model)
    {
        final String lastNameSearchString;
        if( isBlank(owner.getLastName()) == true )
        {
            // Allow parameterless GET request for "/owners" to return all records:
            lastNameSearchString = SQL_WILDCARD;
        }
        else
        {
            lastNameSearchString = SQL_WILDCARD + owner.getLastName() + SQL_WILDCARD;
        }

        // Find owners by last name:
        final List<Owner> serviceResult = ownerService.findAllByLastNameLike(lastNameSearchString);
        if( serviceResult.isEmpty() == true )
        {
            // No owners found:
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        }

        if( serviceResult.size() == 1 )
        {
            // Found exactly one owner:
            owner = serviceResult.get(0);
            return "redirect:/owners/" + owner.getId();
        }

        // Found multiple owners:
        model.addAttribute("owners", serviceResult);
        return "owners/ownersList";
    }

    @InitBinder
    public void setAllowedFields(final WebDataBinder dataBinder)
    {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping(path = { "/find" })
    public String showFindForm(final Model model)
    {
        model.addAttribute("owner", Owner.builder().build());

        return "owners/findOwners";
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