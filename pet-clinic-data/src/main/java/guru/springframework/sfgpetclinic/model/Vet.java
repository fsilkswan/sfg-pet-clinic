package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
    private Set<VetSpecialty> specialties;

    @Builder
    public Vet(final Long id,
               /**/final String firstName, final String lastName,
               /**/ final Set<VetSpecialty> specialties)
    {
        super(id, firstName, lastName);

        if( specialties == null )
        {
            this.specialties = new HashSet<>();
        }
        else
        {
            this.specialties = specialties;
        }
    }
}