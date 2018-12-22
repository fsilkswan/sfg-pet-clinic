package guru.springframework.sfgpetclinic.model;

public final class PetType
    extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }
}