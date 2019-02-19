package guru.springframework.sfgpetclinic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.sfgpetclinic.model.Owner;

public interface OwnerRepository
    extends CrudRepository<Owner, Long>
{
    List<Owner> findAllByLastNameLike(String lastName);

    @Deprecated
    Optional<Owner> findByLastName(String lastName);
}