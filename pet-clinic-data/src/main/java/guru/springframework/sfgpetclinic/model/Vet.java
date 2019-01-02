package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vets")
public final class Vet
    extends Person
{
    private static final long serialVersionUID = 1L;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vets_specialties_map",
        /**/ joinColumns = @JoinColumn(name = "vet_id"),
        /**/ inverseJoinColumns = @JoinColumn(name = "vet_specialty_id"))
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