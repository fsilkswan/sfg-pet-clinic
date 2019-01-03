package guru.springframework.sfgpetclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.VetSpecialty;
import guru.springframework.sfgpetclinic.services.VetSpecialtyService;

@Service
@Profile(value = { "default", "map" })
public final class VetSpecialtyMapService
    extends AbstractMapService<VetSpecialty>
    implements VetSpecialtyService
{
}