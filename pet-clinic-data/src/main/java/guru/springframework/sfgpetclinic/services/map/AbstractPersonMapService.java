package guru.springframework.sfgpetclinic.services.map;

import java.util.Optional;

import guru.springframework.sfgpetclinic.model.Person;
import guru.springframework.sfgpetclinic.services.PersonService;

public abstract class AbstractPersonMapService<T extends Person>
    extends AbstractMapService<T>
    implements PersonService<T>
{
    @Override
    public final T findByLastName(final String lastName)
    {
        final Optional<T> lastNameLookupResult = findAll().stream()
                                                          .filter(entry -> entry.getLastName().equalsIgnoreCase(lastName))
                                                          .findFirst();

        return lastNameLookupResult.orElse(null);
    }
}