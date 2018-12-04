package guru.springframework.sfgpetclinic.services.map;

import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;

@Service
public final class VetMapService
    extends AbstractMapService<Vet, Long>
    implements VetService
{
    @Override
    public Vet save(final Vet entity)
    {
        if( entity == null )
        {
            return null;
        }

        return save(entity.getId(), entity);
    }
}