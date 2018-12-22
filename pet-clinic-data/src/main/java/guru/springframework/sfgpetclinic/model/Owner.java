package guru.springframework.sfgpetclinic.model;

import java.util.Set;

public final class Owner
    extends Person
{
    private static final long serialVersionUID = 1L;

    private String   address;
    private String   city;
    private Set<Pet> pets;
    private String   telephone;

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