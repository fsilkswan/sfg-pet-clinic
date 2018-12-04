package guru.springframework.sfgpetclinic.services.map;

import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;

@Service
public final class PetMapService
    extends AbstractMapService<Pet, Long>
    implements PetService
{
    @Override
    public Pet save(final Pet entity)
    {
        if( entity == null )
        {
            return null;
        }

        return save(entity.getId(), entity);
    }
}