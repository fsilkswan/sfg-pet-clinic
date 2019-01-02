package guru.springframework.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.services.PetService;

@Service
@Profile(value = { "springdatajpa" })
public class PetSDJpaService
    implements PetService
{
    private final PetRepository petRepository;

    public PetSDJpaService(final PetRepository petRepository)
    {
        this.petRepository = petRepository;
    }

    @Override
    public void delete(final Pet pet)
    {
        petRepository.delete(pet);
    }

    @Override
    public void deleteById(final Long id)
    {
        petRepository.deleteById(id);
    }

    @Override
    public Set<Pet> findAll()
    {
        final Set<Pet> pets = new HashSet<>();
        petRepository.findAll()
                     .forEach(pets::add);

        return pets;
    }

    @Override
    public Pet findById(final Long id)
    {
        return petRepository.findById(id)
                            .orElse(null);
    }

    @Override
    public Pet save(final Pet pet)
    {
        return petRepository.save(pet);
    }
}