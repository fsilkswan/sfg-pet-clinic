package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }

    public Set<Pet> getPets()
    {
        return pets;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setAddress(final String address)
    {
        this.address = address;
    }

    public void setCity(final String city)
    {
        this.city = city;
    }

    public void setPets(final Set<Pet> pets)
    {
        this.pets = pets;
    }

    public void setTelephone(final String telephone)
    {
        this.telephone = telephone;
    }
}