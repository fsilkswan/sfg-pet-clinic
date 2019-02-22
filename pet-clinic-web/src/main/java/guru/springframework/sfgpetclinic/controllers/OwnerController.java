package guru.springframework.sfgpetclinic.controllers;

import static org.apache.logging.log4j.util.Strings.isBlank;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@RequestMapping("/owners")
@Controller
public class OwnerController
{
    protected final static String SQL_WILDCARD                          = "%";
    protected static final String VIEW_NAME_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    protected static final String VIEW_NAME_FIND_OWNERS_FORM            = "owners/findOwners";
    protected static final String VIEW_NAME_OWNER_DETAILS               = "owners/ownerDetails";
    protected static final String VIEW_NAME_OWNERS_LIST                 = "owners/ownersList";

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService)
    {
        this.ownerService = ownerService;
    }

    @PostMapping(path = { "/new" })
    public String processCreationForm(@Valid final Owner owner, final BindingResult result)
    {
        if( result.hasErrors() == true )
        {
            return VIEW_NAME_CREATE_OR_UPDATE_OWNER_FORM;
        }

        final Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
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
            return VIEW_NAME_FIND_OWNERS_FORM;
        }

        if( serviceResult.size() == 1 )
        {
            // Found exactly one owner:
            owner = serviceResult.get(0);
            return "redirect:/owners/" + owner.getId();
        }

        // Found multiple owners:
        model.addAttribute("owners", serviceResult);
        return VIEW_NAME_OWNERS_LIST;
    }

    @PostMapping(path = { "/{ownerId}/edit" })
    public String processUpdateForm(@PathVariable final Long ownerId, @Valid final Owner owner, final BindingResult result)
    {
        if( result.hasErrors() == true )
        {
            return VIEW_NAME_CREATE_OR_UPDATE_OWNER_FORM;
        }

        owner.setId(ownerId); // This is required because the "id" property is not allowed to be passed from forms. See setAllowedFields method.
        final Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }

    @InitBinder
    public void setAllowedFields(final WebDataBinder dataBinder)
    {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping(path = { "/new" })
    public String showCreationForm(final Model model)
    {
        model.addAttribute("owner", Owner.builder().build());

        return VIEW_NAME_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @GetMapping(path = { "/find" })
    public String showFindForm(final Model model)
    {
        model.addAttribute("owner", Owner.builder().build());

        return VIEW_NAME_FIND_OWNERS_FORM;
    }

    @GetMapping(path = { "/{ownerId}" })
    public ModelAndView showOwnerById(@PathVariable final Long ownerId)
    {
        final Owner owner = ownerService.findById(ownerId);

        final ModelAndView mav = new ModelAndView(VIEW_NAME_OWNER_DETAILS);
        mav.addObject(/* "owner", */ owner);

        return mav;
    }

    @GetMapping(path = { "/{ownerId}/edit" })
    public String showUpdateForm(@PathVariable final Long ownerId, final Model model)
    {
        final Owner owner = ownerService.findById(ownerId);
        model.addAttribute("owner", owner);

        return VIEW_NAME_CREATE_OR_UPDATE_OWNER_FORM;
    }
}