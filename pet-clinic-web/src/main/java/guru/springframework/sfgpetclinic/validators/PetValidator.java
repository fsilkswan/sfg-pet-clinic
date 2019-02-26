package guru.springframework.sfgpetclinic.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import guru.springframework.sfgpetclinic.model.Pet;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public final class PetValidator
    implements Validator
{
    private static final String REQUIRED = "required";

    @Override
    public boolean supports(final Class<?> clazz)
    {
        /* This Validator validates *just* Pet instances! */
        return Pet.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors)
    {
        final Pet pet = (Pet)target;

        // Name validation:
        if( StringUtils.isBlank(pet.getName()) == true )
        {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }

        // Pet type validation:
        if( pet.isNew() == true && pet.getPetType() == null )
        {
            errors.rejectValue("petType", REQUIRED, REQUIRED);
        }

        // Birth date validation:
        if( pet.getBirthDate() == null )
        {
            errors.rejectValue("birthDate", REQUIRED, REQUIRED);
        }
    }
}