package guru.springframework.sfgpetclinic.services.map;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@Service
public final class OwnerMapService
    extends AbstractMapService<Owner, Long>
    implements OwnerService
{
    @Override
    public Owner findByLastName(final String lastName)
    {
        final Optional<Owner> lastNameLookupResult = findAll().stream()
                                                              .filter(entry -> entry.getLastName().equals(lastName))
                                                              .findFirst();

        return lastNameLookupResult.orElse(null);
    }

    @Override
    public Owner save(final Owner entity)
    {
        if( entity == null )
        {
            return null;
        }

        return save(entity.getId(), entity);
    }
}