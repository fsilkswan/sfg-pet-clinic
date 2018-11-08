package guru.springframework.sfgpetclinic.model;

public class Person
{
    private String firstName;
    private String lastName;

    // public Person(final String firstName, final String lastName)
    // {
    // this.firstName = firstName;
    // this.lastName = lastName;
    // }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }
}