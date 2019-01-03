package guru.springframework.sfgpetclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;

@Service
@Profile(value = { "default", "map" })
public class VisitMapService
    extends AbstractMapService<Visit>
    implements VisitService
{
    @Override
    public Visit save(final Visit visit)
    {
        if( visit == null )
        {
            return null;
        }

        final Pet pet = visit.getPet();
        if( pet == null || pet.getId() == null || pet.getOwner() == null || pet.getOwner().getId() == null )
        {
            throw new RuntimeException("Invalid Visit!");
        }

        return super.save(visit);
    }
}