package guru.springframework.sfgpetclinic.services.map;

import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.VetSpecialty;
import guru.springframework.sfgpetclinic.services.VetSpecialtyService;

@Service
public final class VetSpecialtyMapService
    extends AbstractMapService<VetSpecialty>
    implements VetSpecialtyService
{
}