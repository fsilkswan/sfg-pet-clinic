package guru.springframework.sfgpetclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pet_types")
public final class PetType
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Builder
    public PetType(final Long id, final String name)
    {
        super(id);

        this.name = name;
    }
}