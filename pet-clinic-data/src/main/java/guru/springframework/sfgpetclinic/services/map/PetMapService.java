package guru.springframework.sfgpetclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;

@Service
@Profile(value = { "default", "map" })
public final class PetMapService
    extends AbstractMapService<Pet>
    implements PetService
{
}