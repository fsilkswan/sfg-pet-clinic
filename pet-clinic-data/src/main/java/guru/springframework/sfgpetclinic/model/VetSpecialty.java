package guru.springframework.sfgpetclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vet_specialties")
public final class VetSpecialty
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Column(name = "description")
    private String description;

    @Builder
    public VetSpecialty(final Long id,
                        /**/ final String description)
    {
        super(id);

        this.description = description;
    }
}