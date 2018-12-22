package guru.springframework.sfgpetclinic.model;

import java.time.LocalDate;

public final class Visit
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private LocalDate date;
    private String    description;
    private Pet       pet;

    public LocalDate getDate()
    {
        return date;
    }

    public String getDescription()
    {
        return description;
    }

    public Pet getPet()
    {
        return pet;
    }

    public void setDate(final LocalDate date)
    {
        this.date = date;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    public void setPet(final Pet pet)
    {
        this.pet = pet;
    }
}
