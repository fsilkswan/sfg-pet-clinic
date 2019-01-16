package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;

@ExtendWith(MockitoExtension.class)
public final class OwnerSDJpaServiceTest
{
    private static Owner owner;

    private static final Long   OWNER_ID        = 1L;
    private static final String OWNER_LAST_NAME = "Smith";

    @BeforeAll
    public static void beforeAll()
        throws Exception
    {
        owner = Owner.builder().lastName(OWNER_LAST_NAME).build();
        owner.setId(OWNER_ID);
    }

    @InjectMocks
    private OwnerSDJpaService cut;

    @Mock
    private OwnerRepository ownerRepositoryMock;
    @Mock
    private PetRepository   petRepositoryMock;

    @Mock
    private PetTypeRepository petTypeRepositoryMock;

    @Test
    public void testDelete()
        throws Exception
    {
        cut.delete(owner);

        verify(ownerRepositoryMock, times(1)).delete(same(owner));
    }

    @Test
    public void testDeleteById()
        throws Exception
    {
        cut.deleteById(OWNER_ID);

        verify(ownerRepositoryMock, times(1)).deleteById(eq(OWNER_ID));
    }

    @Test
    public void testFindAll()
        throws Exception
    {
        final Owner owner2 = Owner.builder().build();
        owner2.setId(2L);

        final Set<Owner> owners = new HashSet<>();
        owners.add(owner);
        owners.add(owner2);

        when(ownerRepositoryMock.findAll()).thenReturn(owners);

        final Set<Owner> foundOwners = cut.findAll();
        assertThat(foundOwners, is(not(nullValue())));
        assertThat(foundOwners, hasSize(equalTo(2)));

        verify(ownerRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testFindById()
        throws Exception
    {
        when(ownerRepositoryMock.findById(anyLong())).thenReturn(Optional.of(owner));

        final Owner foundOwner = cut.findById(OWNER_ID);
        assertThat(foundOwner, is(not(nullValue())));
        assertThat(foundOwner.getId(), is(equalTo(OWNER_ID)));
        assertThat(foundOwner.getLastName(), is(equalTo(OWNER_LAST_NAME)));

        verify(ownerRepositoryMock, times(1)).findById(eq(OWNER_ID));
    }

    @Test
    public void testFindByIdReturnsNullForUnknownId()
        throws Exception
    {
        when(ownerRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        final Owner foundOwner = cut.findById(OWNER_ID);
        assertThat(foundOwner, is(nullValue()));

        verify(ownerRepositoryMock, times(1)).findById(eq(OWNER_ID));
    }

    @Test
    public void testFindByLastName()
        throws Exception
    {
        when(ownerRepositoryMock.findByLastName(anyString())).thenReturn(Optional.of(owner));

        final Owner foundOwner = cut.findByLastName(OWNER_LAST_NAME);
        assertThat(foundOwner, is(not(nullValue())));
        assertThat(foundOwner.getLastName(), is(equalTo(OWNER_LAST_NAME)));

        verify(ownerRepositoryMock, times(1)).findByLastName(eq(OWNER_LAST_NAME));
    }

    @Test
    public void testFindByLastNameReturnsNullForUnknownLastName()
        throws Exception
    {
        when(ownerRepositoryMock.findByLastName(anyString())).thenReturn(Optional.empty());

        final Owner foundOwner = cut.findByLastName(OWNER_LAST_NAME);
        assertThat(foundOwner, is(nullValue()));

        verify(ownerRepositoryMock, times(1)).findByLastName(eq(OWNER_LAST_NAME));
    }

    @Test
    public void testSave()
        throws Exception
    {
        final Owner ownerToSave = Owner.builder().build();

        when(ownerRepositoryMock.save(any())).thenReturn(owner);

        final Owner savedOwner = cut.save(ownerToSave);
        assertThat(savedOwner, is(not(nullValue())));
        assertThat(savedOwner, is(sameInstance(owner)));
        assertThat(savedOwner.getId(), is(equalTo(OWNER_ID)));

        verify(ownerRepositoryMock, times(1)).save(same(ownerToSave));
    }
}