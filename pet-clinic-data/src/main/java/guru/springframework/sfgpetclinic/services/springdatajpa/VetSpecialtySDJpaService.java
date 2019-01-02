package guru.springframework.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.VetSpecialty;
import guru.springframework.sfgpetclinic.repositories.VetSpecialtyRepository;
import guru.springframework.sfgpetclinic.services.VetSpecialtyService;

@Service
@Profile(value = { "springdatajpa" })
public class VetSpecialtySDJpaService
    implements VetSpecialtyService
{
    private final VetSpecialtyRepository vetSpecialtyRepository;

    public VetSpecialtySDJpaService(final VetSpecialtyRepository vetSpecialtyRepository)
    {
        this.vetSpecialtyRepository = vetSpecialtyRepository;
    }

    @Override
    public void delete(final VetSpecialty vetSpecialty)
    {
        vetSpecialtyRepository.delete(vetSpecialty);
    }

    @Override
    public void deleteById(final Long id)
    {
        vetSpecialtyRepository.deleteById(id);
    }

    @Override
    public Set<VetSpecialty> findAll()
    {
        final Set<VetSpecialty> vetSpecialties = new HashSet<>();
        vetSpecialtyRepository.findAll()
                              .forEach(vetSpecialties::add);

        return vetSpecialties;
    }

    @Override
    public VetSpecialty findById(final Long id)
    {
        return vetSpecialtyRepository.findById(id)
                                     .orElse(null);
    }

    @Override
    public VetSpecialty save(final VetSpecialty vetSpecialty)
    {
        return vetSpecialtyRepository.save(vetSpecialty);
    }
}