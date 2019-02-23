package guru.springframework.sfgpetclinic.controllers;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

/**
 * Unit tests for class: {@linkplain PetController}
 */
@ExtendWith(MockitoExtension.class)
class PetControllerTest
{
    @InjectMocks
    private PetController cut;

    private MockMvc mockMvc;

    @Mock
    private OwnerService ownerServiceMock;

    @Mock
    private PetService petServiceMock;

    @Mock
    private PetTypeService petTypeServiceMock;

    /**
     * Creates the test fixtures.
     *
     * @throws Exception
     *             On errors.
     */
    @BeforeEach
    void beforeEach()
        throws Exception
    {
        mockMvc = MockMvcBuilders.standaloneSetup(cut).build();
    }

    /**
     * Test for method: {@linkplain PetController#findOwner(Long)}
     *
     * @throws Exception
     *             On errors.
     */
    @Test
    void testFindOwner()
        throws Exception
    {
        final Owner owner = Owner.builder().build();
        when(ownerServiceMock.findById(anyLong())).thenReturn(owner);

        final Owner foundOwner = cut.findOwner(1L);

        assertThat(foundOwner, is(sameInstance(owner)));
        verify(ownerServiceMock, times(1)).findById(eq(1L));
        verifyZeroInteractions(petServiceMock, petTypeServiceMock);
    }

    /**
     * Test for method: {@linkplain PetController#populatePetTypes()}
     *
     * @throws Exception
     *             On errors.
     */
    @Test
    void testPopulatePetTypes()
        throws Exception
    {
        final Set<PetType> allPetTypes = asList(PetType.builder().build()).stream().collect(toSet());
        when(petTypeServiceMock.findAll()).thenReturn(allPetTypes);

        final Collection<PetType> foundPetTypes = cut.populatePetTypes();

        assertThat(foundPetTypes, is(sameInstance(allPetTypes)));
        verify(petTypeServiceMock, times(1)).findAll();
        verifyZeroInteractions(petServiceMock, ownerServiceMock);
    }
}