package guru.springframework.sfgpetclinic.services.map;

import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.model.VetSpecialty;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.VetSpecialtyService;

@Service
public final class VetMapService
    extends AbstractPersonMapService<Vet>
    implements VetService
{
    private final VetSpecialtyService vetSpecialtyService;

    public VetMapService(final VetSpecialtyService vetSpecialtyService)
    {
        this.vetSpecialtyService = vetSpecialtyService;
    }

    @Override
    public Vet save(final Vet vet)
    {
        if( vet == null )
        {
            return null;
        }

        final Set<VetSpecialty> specialties = vet.getSpecialties();
        if( 0 < specialties.size() )
        {
            specialties.forEach(specialty ->
            {
                if( specialty.getId() == null )
                {
                    final VetSpecialty savedSpecialty = vetSpecialtyService.save(specialty);
                    specialty.setId(savedSpecialty.getId());
                }
            });
        }

        return super.save(vet);
    }
}