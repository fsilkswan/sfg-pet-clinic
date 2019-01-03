package guru.springframework.sfgpetclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@Service
@Profile(value = { "default", "map" })
public final class PetTypeMapService
    extends AbstractMapService<PetType>
    implements PetTypeService
{
}