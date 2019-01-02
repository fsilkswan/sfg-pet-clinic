package guru.springframework.sfgpetclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "visits")
public final class Visit
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

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
