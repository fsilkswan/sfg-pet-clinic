package guru.springframework.sfgpetclinic.services.map;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@Service
@Profile(value = { "default", "map" })
public final class OwnerMapService
    extends AbstractPersonMapService<Owner>
    implements OwnerService
{
    private final PetService     petService;
    private final PetTypeService petTypeService;

    public OwnerMapService(final PetTypeService petTypeService, final PetService petService)
    {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public final Owner save(final Owner owner)
    {
        if( owner == null )
        {
            return null;
        }

        final Set<Pet> pets = owner.getPets();
        if( pets != null )
        {
            pets.forEach(pet ->
            {
                final PetType petType = pet.getPetType();
                if( petType == null )
                {
                    throw new RuntimeException("PetType is required!");
                }

                pet.setPetType(petTypeService.save(petType));

                if( pet.getId() == null )
                {
                    final Pet savedPetd = petService.save(pet);
                    pet.setId(savedPetd.getId());
                }
            });
        }

        return super.save(owner);

    }
}