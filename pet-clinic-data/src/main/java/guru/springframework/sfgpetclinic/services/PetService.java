package guru.springframework.sfgpetclinic.services;

import java.util.Set;

import guru.springframework.sfgpetclinic.model.Pet;

public interface PetService
{
    Set<Pet> findAll();

    Pet findById(Long id);

    Pet save(Pet pet);
}