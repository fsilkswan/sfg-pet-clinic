package guru.springframework.sfgpetclinic.services;

import java.util.Set;

import guru.springframework.sfgpetclinic.model.Vet;

public interface VetService
{
    Set<Vet> findAll();

    Vet findById(long id);

    Vet save(Vet vet);
}