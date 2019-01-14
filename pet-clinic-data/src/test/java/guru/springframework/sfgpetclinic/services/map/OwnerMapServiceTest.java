package guru.springframework.sfgpetclinic.services.map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Owner;

public final class OwnerMapServiceTest
{
    private static final String OWNER_FIRST_NAME = "John";
    private static final String OWNER_LAST_NAME  = "Doe";

    private OwnerMapService cut;

    @BeforeEach
    void beforeEach()
        throws Exception
    {
        cut = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        final Owner owner = Owner.builder().lastName(OWNER_LAST_NAME).firstName(OWNER_FIRST_NAME).build();
        owner.setId(1L);
        cut.save(owner);
    }

    @Test
    void testDelete()
    {
        cut.delete(cut.findById(1L));

        final Set<Owner> foundOwners = cut.findAll();
        assertThat(foundOwners, is(not(nullValue())));
        assertThat(foundOwners, hasSize(equalTo(0)));
    }

    @Test
    void testDeleteById()
    {
        cut.deleteById(1L);

        final Set<Owner> foundOwners = cut.findAll();
        assertThat(foundOwners, is(not(nullValue())));
        assertThat(foundOwners, hasSize(equalTo(0)));
    }

    @Test
    void testFindAll()
    {
        final Set<Owner> foundOwners = cut.findAll();
        assertThat(foundOwners, is(not(nullValue())));
        assertThat(foundOwners, hasSize(equalTo(1)));

        final Owner owner = foundOwners.iterator().next();
        assertThat(owner.getId(), is(equalTo(1L)));
        assertThat(owner.getLastName(), is(equalTo(OWNER_LAST_NAME)));
        assertThat(owner.getFirstName(), is(equalTo(OWNER_FIRST_NAME)));
    }

    @Test
    void testFindById()
    {
        final Owner foundOwner = cut.findById(1L);
        assertThat(foundOwner, is(not(nullValue())));
        assertThat(foundOwner.getLastName(), is(equalTo(OWNER_LAST_NAME)));
        assertThat(foundOwner.getFirstName(), is(equalTo(OWNER_FIRST_NAME)));
    }

    @Test
    void testFindByLastName()
    {
        final Owner foundOwner = cut.findByLastName(OWNER_LAST_NAME);
        assertThat(foundOwner, is(not(nullValue())));
        assertThat(foundOwner.getId(), is(equalTo(1L)));
    }

    @Test
    void testFindByLastNameReturnsNullForUnknownName()
    {
        final Owner foundOwner = cut.findByLastName("unknown");
        assertThat(foundOwner, is(nullValue()));
    }

    @Test
    void testSaveOwnerWithIdAlreadySet()
    {
        final Owner owner = Owner.builder().build();
        owner.setId(3L);
        final Owner savedOwner = cut.save(owner);

        assertThat(savedOwner, is(not(nullValue())));
        assertThat(savedOwner.getId(), is(equalTo(3L)));
    }

    @Test
    void testSaveOwnerWithIdMissing()
    {
        final Owner owner = Owner.builder().build();
        final Owner savedOwner = cut.save(owner);

        assertThat(savedOwner, is(not(nullValue())));
        assertThat(savedOwner.getId(), is(equalTo(2L)));
    }
}