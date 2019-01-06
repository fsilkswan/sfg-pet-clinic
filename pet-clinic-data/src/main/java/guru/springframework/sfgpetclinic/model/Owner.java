package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
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
    private Set<Pet> pets;

    @Column(name = "telephone")
    private String telephone;

    @Builder
    public Owner(final String firstName, final String lastName,
                 /**/ final String address, final String city, final String telephone, final Set<Pet> pets)
    {
        super(firstName, lastName);

        this.address = address;
        this.city = city;
        this.telephone = telephone;

        if( pets == null )
        {
            this.pets = new HashSet<>();
        }
        else
        {
            this.pets = pets;
        }
    }
}