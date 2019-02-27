package guru.springframework.sfgpetclinic.model;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private Set<Visit> visits = new HashSet<>();

    @Builder
    public Pet(final Long id,
               /**/ final PetType petType, final LocalDate birthDate, final String name, final Owner owner, final Set<Visit> visits)
    {
        super(id);

        this.petType = petType;
        this.birthDate = birthDate;
        this.name = name;
        this.owner = owner;

        if( visits != null )
        {
            this.visits = visits;
        }
    }

    /**
     * Helper method to add a visit to this pet and also assigns this pet to the visit.
     *
     * @param visit
     *            The visit to add.
     */
    public void addVisit(final Visit visit)
    {
        if( visit == null )
        {
            return;
        }

        getVisits().add(visit);
        visit.setPet(this);
    }

    public List<Visit> getVisitsOrdered()
    {
        return getVisits().stream()
                          .sorted((v1, v2) ->
                          {
                              final int dateCompResult = v1.getDate().compareTo(v2.getDate());
                              if( dateCompResult != 0 )
                              {
                                  return dateCompResult;
                              }

                              return v1.getDescription().compareTo(v2.getDescription());
                          })
                          .collect(toList());
    }
}