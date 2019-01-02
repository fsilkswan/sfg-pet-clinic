package guru.springframework.sfgpetclinic.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.sfgpetclinic.model.Vet;

public interface VetRepository
    extends CrudRepository<Vet, Long>
{
    Optional<Vet> findByLastName(String lastName);
}