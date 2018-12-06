package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Person;

public interface PersonService<T extends Person>
    extends CrudService<T, Long>
{
    T findByLastName(String lastName);
}