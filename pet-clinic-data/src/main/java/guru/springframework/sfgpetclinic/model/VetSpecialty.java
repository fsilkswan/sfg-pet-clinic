package guru.springframework.sfgpetclinic.model;

public final class VetSpecialty
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private String            description;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }
}