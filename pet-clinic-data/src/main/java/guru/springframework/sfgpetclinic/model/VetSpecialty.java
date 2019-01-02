package guru.springframework.sfgpetclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vet_specialties")
public final class VetSpecialty
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Column(name = "description")
    private String description;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }
}