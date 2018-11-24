package guru.springframework.sfgpetclinic.services.map;

import java.util.Optional;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

public final class OwnerMapService
    extends AbstractMapService<Owner, Long>
    implements OwnerService
{
    @Override
    public Owner findByLastName(final String lastName)
    {
        final Optional<Owner> lastNameLookupResult = map.values()
                                                        .stream()
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