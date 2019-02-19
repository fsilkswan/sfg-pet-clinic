package guru.springframework.sfgpetclinic.services;

import java.util.List;

import guru.springframework.sfgpetclinic.model.Owner;

public interface OwnerService
    extends PersonService<Owner>
{
    List<Owner> findAllByLastNameLike(String lastName);
}