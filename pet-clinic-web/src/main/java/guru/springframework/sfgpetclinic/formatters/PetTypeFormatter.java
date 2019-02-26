package guru.springframework.sfgpetclinic.formatters;

import static org.apache.logging.log4j.util.Strings.dquote;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'PetType'.<br>
 * Starting from Spring 3.0, Formatters have come as an improvement in comparison to legacy PropertyEditors.<br>
 * See the following links for more details:<br>
 * - The Spring ref doc: http://static.springsource.org/spring/docs/current/spring-framework-reference/html/validation.html#format-Formatter-SPI
 * - A nice blog entry from Gordon Dickens: http://gordondickens.com/wordpress/2010/09/30/using-spring-3-0-custom-type-converter/
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 */
@Component
public class PetTypeFormatter
    implements Formatter<PetType>
{
    private final PetTypeService petTypeService;

    public PetTypeFormatter(final PetTypeService petTypeRepository)
    {
        this.petTypeService = petTypeRepository;
    }

    @Override
    public PetType parse(final String text, final Locale locale)
        throws ParseException
    {
        final Optional<PetType> queryResult = StreamSupport.stream(petTypeService.findAll().spliterator(), false)
                                                           .filter(currPetType -> currPetType.getName().equalsIgnoreCase(text) == true)
                                                           .findFirst();
        if( queryResult.isPresent() == true )
        {
            return queryResult.get();
        }

        throw new ParseException("pet type not found: " + dquote(text), 0);
    }

    @Override
    public String print(final PetType petType, final Locale locale)
    {
        return petType.getName();
    }
}