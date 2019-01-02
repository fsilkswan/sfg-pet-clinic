package guru.springframework.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import guru.springframework.sfgpetclinic.services.VetService;

@Service
@Profile(value = { "springdatajpa" })
public class VetSDJpaService
    implements VetService
{
    private final VetRepository vetRepository;

    public VetSDJpaService(final VetRepository vetRepository)
    {
        this.vetRepository = vetRepository;
    }

    @Override
    public void delete(final Vet vet)
    {
        vetRepository.delete(vet);
    }

    @Override
    public void deleteById(final Long id)
    {
        vetRepository.deleteById(id);
    }

    @Override
    public Set<Vet> findAll()
    {
        final Set<Vet> vets = new HashSet<>();
        vetRepository.findAll()
                     .forEach(vets::add);

        return vets;
    }

    @Override
    public Vet findById(final Long id)
    {
        return vetRepository.findById(id).orElse(null);
    }

    @Override
    public Vet findByLastName(final String lastName)
    {
        return vetRepository.findByLastName(lastName)
                            .orElse(null);
    }

    @Override
    public Vet save(final Vet vet)
    {
        return vetRepository.save(vet);
    }
}