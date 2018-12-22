package guru.springframework.sfgpetclinic.model;

import java.time.LocalDate;

public final class Pet
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private LocalDate birthDate;
    private Owner     owner;
    private PetType   petType;

    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    public Owner getOwner()
    {
        return owner;
    }

    public PetType getPetType()
    {
        return petType;
    }

    public void setBirthDate(final LocalDate birthDate)
    {
        this.birthDate = birthDate;
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