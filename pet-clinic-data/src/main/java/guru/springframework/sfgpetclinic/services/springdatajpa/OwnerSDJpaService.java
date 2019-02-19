package guru.springframework.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;

@Service
@Profile(value = { "springdatajpa" })
public class OwnerSDJpaService
    implements OwnerService
{
    private final OwnerRepository   ownerRepository;
    private final PetRepository     petRepository;
    private final PetTypeRepository petTypeRepository;

    public OwnerSDJpaService(final OwnerRepository ownerRepository,
                             /**/ final PetRepository petRepository,
                             /**/ final PetTypeRepository petTypeRepository)
    {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public void delete(final Owner owner)
    {
        ownerRepository.delete(owner);
    }

    @Override
    public void deleteById(final Long id)
    {
        ownerRepository.deleteById(id);
    }

    @Override
    public Set<Owner> findAll()
    {
        final Set<Owner> owners = new HashSet<>();
        ownerRepository.findAll()
                       .forEach(owners::add);

        return owners;
    }

    @Override
    public List<Owner> findAllByLastNameLike(final String lastName)
    {
        return ownerRepository.findAllByLastNameLike(lastName);
    }

    @Override
    public Owner findById(final Long id)
    {
        return ownerRepository.findById(id)
                              .orElse(null);
    }

    @Override
    public Owner findByLastName(final String lastName)
    {
        return ownerRepository.findByLastName(lastName)
                              .orElse(null);
    }

    @Override
    public Owner save(final Owner owner)
    {
        return ownerRepository.save(owner);
    }
}