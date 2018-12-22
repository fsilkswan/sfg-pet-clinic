package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

public final class Vet
    extends Person
{
    private static final long serialVersionUID = 1L;

    private Set<VetSpecialty> specialties = new HashSet<>();

    public Set<VetSpecialty> getSpecialties()
    {
        return specialties;
    }

    public void setSpecialties(final Set<VetSpecialty> specialties)
    {
        this.specialties = specialties;
    }
}