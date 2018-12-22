package guru.springframework.sfgpetclinic.model;

import java.util.Set;

public final class Vet
    extends Person
{
    private static final long serialVersionUID = 1L;

    private Set<VetSpecialty> specialties;
}