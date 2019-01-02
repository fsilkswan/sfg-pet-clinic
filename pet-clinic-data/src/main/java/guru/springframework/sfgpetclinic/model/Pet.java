package guru.springframework.sfgpetclinic.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pets")
public final class Pet
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private final Set<Visit> visits = new HashSet<>();

    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    public String getName()
    {
        return name;
    }

    public Owner getOwner()
    {
        return owner;
    }

    public PetType getPetType()
    {
        return petType;
    }

    public Set<Visit> getVisits()
    {
        return visits;
    }

    public void setBirthDate(final LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public void setOwner(final Owner owner)
    {
        this.owner = owner;
    }

    public void setPetType(final PetType petType)
    {
        this.petType = petType;
    }
}