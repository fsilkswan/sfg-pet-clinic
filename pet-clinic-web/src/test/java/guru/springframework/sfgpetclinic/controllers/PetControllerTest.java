package guru.springframework.sfgpetclinic.controllers;

import static guru.springframework.sfgpetclinic.controllers.PetController.VIEW_NAME_CREATE_OR_UPDATE_PET_FORM;
import static java.time.Month.OCTOBER;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
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
import guru.springframework.sfgpetclinic.model.Pet;
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

    private Owner owner;

    @Mock
    private OwnerService ownerServiceMock;

    @Mock
    private PetService petServiceMock;

    private Set<PetType> petTypes;

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
        owner = Owner.builder()
                     .id(1L)
                     .build();

        final PetType dog = PetType.builder()
                                   .id(1L)
                                   .name("Dog")
                                   .build();

        final PetType cat = PetType.builder()
                                   .id(2L)
                                   .name("Cat")
                                   .build();

        petTypes = asList(dog, cat).stream()
                                   .collect(toSet());

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
        when(petTypeServiceMock.findAll()).thenReturn(petTypes);

        final Collection<PetType> foundPetTypes = cut.populatePetTypes();

        assertThat(foundPetTypes, hasSize(equalTo(2)));
        verify(petTypeServiceMock, times(1)).findAll();
        verifyZeroInteractions(petServiceMock, ownerServiceMock);
    }

    @Test
    void testProcessCreationForm()
        throws Exception
    {
        when(ownerServiceMock.findById(anyLong())).thenReturn(owner);
        when(petTypeServiceMock.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new")
                                                  .param("name", "Malibu")
                                                  .param("petType", "Cat")
                                                  .param("birthDate", "2014-10-15"))
               .andExpect(status().isFound())
               .andExpect(view().name(is(equalTo("redirect:/owners/1"))));

        verify(ownerServiceMock, times(1)).findById(eq(1L));
        verify(petTypeServiceMock, times(1)).findAll();
        verify(petServiceMock, times(1)).save(any());
    }

    @Test
    void testProcessUpdateForm()
        throws Exception
    {
        when(ownerServiceMock.findById(anyLong())).thenReturn(owner);
        when(petTypeServiceMock.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/3/edit")
                                                     .param("name", "Sunrise")
                                                     .param("petType", "Cat")
                                                     .param("birthDate", "2014-08-27"))
               .andExpect(status().isFound())
               .andExpect(view().name(is(equalTo("redirect:/owners/1"))));

        verify(ownerServiceMock, times(1)).findById(eq(1L));
        verify(petTypeServiceMock, times(1)).findAll();
        verify(petServiceMock, times(1)).save(any());
    }

    @Test
    void testShowCreationForm()
        throws Exception
    {
        when(ownerServiceMock.findById(anyLong())).thenReturn(owner);
        when(petTypeServiceMock.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/new"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo(VIEW_NAME_CREATE_OR_UPDATE_PET_FORM))))
               .andExpect(model().attributeExists("owner"))
               .andExpect(model().attributeExists("pet"))
               .andExpect(model().attributeExists("petTypes"));

        verify(ownerServiceMock, times(1)).findById(eq(1L));
        verify(petTypeServiceMock, times(1)).findAll();
        verifyZeroInteractions(petServiceMock);
    }

    @Test
    void testShowUpdateForm()
        throws Exception
    {
        final Pet petToUpdate = Pet.builder()
                                   .id(3L)
                                   .name("Malibu")
                                   .petType(petTypes.stream().filter(currPetType -> currPetType.getId() == 2L).findFirst().get())
                                   .birthDate(LocalDate.of(2014, OCTOBER, 14))
                                   .build();

        when(ownerServiceMock.findById(anyLong())).thenReturn(owner);
        when(petTypeServiceMock.findAll()).thenReturn(petTypes);
        when(petServiceMock.findById(anyLong())).thenReturn(petToUpdate);

        mockMvc.perform(get("/owners/1/pets/3/edit"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo(VIEW_NAME_CREATE_OR_UPDATE_PET_FORM))))
               .andExpect(model().attributeExists("owner"))
               .andExpect(model().attributeExists("pet"))
               .andExpect(model().attributeExists("petTypes"));

        verify(ownerServiceMock, times(1)).findById(eq(1L));
        verify(petTypeServiceMock, times(1)).findAll();
        verify(petServiceMock, times(1)).findById(eq(3L));
    }
}