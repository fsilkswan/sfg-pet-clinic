package guru.springframework.sfgpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.sfgpetclinic.model.VetSpecialty;

public interface VetSpecialtyRepository
    extends CrudRepository<VetSpecialty, Long>
{
}