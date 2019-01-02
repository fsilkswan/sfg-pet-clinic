package guru.springframework.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@Service
@Profile(value = { "springdatajpa" })
public class PetTypesSDJpaService
    implements PetTypeService
{
    private final PetTypeRepository petTypeRepository;

    public PetTypesSDJpaService(final PetTypeRepository petTypeRepository)
    {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public void delete(final PetType petType)
    {
        petTypeRepository.delete(petType);
    }

    @Override
    public void deleteById(final Long id)
    {
        petTypeRepository.deleteById(id);
    }

    @Override
    public Set<PetType> findAll()
    {
        final Set<PetType> petTypes = new HashSet<>();
        petTypeRepository.findAll()
                         .forEach(petTypes::add);

        return petTypes;
    }

    @Override
    public PetType findById(final Long id)
    {
        return petTypeRepository.findById(id)
                                .orElse(null);
    }

    @Override
    public PetType save(final PetType petType)
    {
        return petTypeRepository.save(petType);
    }
}