package guru.springframework.sfgpetclinic.model;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public final class Owner
    extends Person
{
    private static final long serialVersionUID = 1L;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    @Column(name = "telephone")
    private String telephone;

    @Builder
    public Owner(final Long id,
                 /**/ final String firstName, final String lastName,
                 /**/ final String address, final String city, final String telephone, final Set<Pet> pets)
    {
        super(id, firstName, lastName);

        this.address = address;
        this.city = city;
        this.telephone = telephone;

        if( pets != null )
        {
            this.pets = pets;
        }
    }

    public List<Pet> getPetsOrdered()
    {
        return getPets().stream()
                        .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                        .collect(toList());
    }
}