package guru.springframework.sfgpetclinic.controllers;

import static guru.springframework.sfgpetclinic.controllers.OwnerController.SQL_WILDCARD;
import static guru.springframework.sfgpetclinic.controllers.OwnerController.VIEW_NAME_CREATE_OR_UPDATE_OWNER_FORM;
import static guru.springframework.sfgpetclinic.controllers.OwnerController.VIEW_NAME_FIND_OWNERS_FORM;
import static guru.springframework.sfgpetclinic.controllers.OwnerController.VIEW_NAME_OWNERS_LIST;
import static guru.springframework.sfgpetclinic.controllers.OwnerController.VIEW_NAME_OWNER_DETAILS;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@ExtendWith(MockitoExtension.class)
public final class OwnerControllerTest
{
    @InjectMocks
    private OwnerController cut;

    private MockMvc mockMvc;

    private Set<Owner> owners;

    @Mock
    private OwnerService ownerServiceMock;

    @BeforeEach
    void beforeEach()
        throws Exception
    {
        final Owner owner1 = Owner.builder().build();
        owner1.setId(1L);

        final Owner owner2 = Owner.builder().build();
        owner2.setId(2L);

        final Owner owner3 = Owner.builder().build();
        owner3.setId(3L);

        owners = new HashSet<>();
        owners.add(owner1);
        owners.add(owner2);
        owners.add(owner3);

        mockMvc = MockMvcBuilders.standaloneSetup(cut).build();
    }

    @Test
    void testProcessCreationForm()
        throws Exception
    {
        final Owner savedOwner = Owner.builder().build();
        savedOwner.setId(10L);

        when(ownerServiceMock.save(any())).thenReturn(savedOwner);

        mockMvc.perform(post("/owners/new"))
               .andExpect(status().isFound())
               .andExpect(view().name(is(equalTo("redirect:/owners/10"))));

        verify(ownerServiceMock, times(1)).save(any());
    }

    @Test
    void testProcessFindFormReturnAll()
        throws Exception
    {
        final List<Owner> allOwnersList = owners.stream().collect(toList());

        when(ownerServiceMock.findAllByLastNameLike(anyString())).thenReturn(allOwnersList);

        mockMvc.perform(get("/owners"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo(VIEW_NAME_OWNERS_LIST))))
               .andExpect(model().attribute("owners", hasSize(equalTo(3))));

        verify(ownerServiceMock, times(1)).findAllByLastNameLike(eq(SQL_WILDCARD));
    }

    @Test
    void testProcessFindFormReturnMany()
        throws Exception
    {
        final String searchString = "many";
        final List<Owner> manyOwnersList = owners.stream().filter(currOwner -> currOwner.getId() != 1L).collect(toList());

        when(ownerServiceMock.findAllByLastNameLike(anyString())).thenReturn(manyOwnersList);

        mockMvc.perform(get("/owners")
                                      .param("lastName", searchString))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo(VIEW_NAME_OWNERS_LIST))))
               .andExpect(model().attribute("owners", hasSize(equalTo(2))));

        verify(ownerServiceMock, times(1)).findAllByLastNameLike(eq(SQL_WILDCARD + searchString + SQL_WILDCARD));
    }

    @Test
    void testProcessFindFormReturnOne()
        throws Exception
    {
        final String searchString = "one";
        final Owner owner = owners.stream().filter(currOwner -> currOwner.getId() == 1L).findFirst().get();

        when(ownerServiceMock.findAllByLastNameLike(anyString())).thenReturn(asList(owner));

        mockMvc.perform(get("/owners")
                                      .param("lastName", searchString))
               .andExpect(status().isFound())
               .andExpect(view().name(is(equalTo("redirect:/owners/1"))));

        verify(ownerServiceMock, times(1)).findAllByLastNameLike(eq(SQL_WILDCARD + searchString + SQL_WILDCARD));
    }

    @Test
    void testProcessUpdatedForm()
        throws Exception
    {
        final Owner owner = owners.stream().filter(currOwner -> currOwner.getId() == 1L).findFirst().get();

        when(ownerServiceMock.save(any())).thenReturn(owner);

        mockMvc.perform(post("/owners/1/edit"))
               .andExpect(status().isFound())
               .andExpect(view().name(is(equalTo("redirect:/owners/1"))));

        final ArgumentCaptor<Owner> ownerToBeSavedCaptor = ArgumentCaptor.forClass(Owner.class);

        verify(ownerServiceMock, times(1)).save(ownerToBeSavedCaptor.capture());
        assertThat(ownerToBeSavedCaptor.getValue().getId(), is(equalTo(1L)));
    }

    @Test
    void testShowCreationForm()
        throws Exception
    {
        mockMvc.perform(get("/owners/new"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo(VIEW_NAME_CREATE_OR_UPDATE_OWNER_FORM))))
               .andExpect(model().attributeExists("owner"));

        verifyZeroInteractions(ownerServiceMock);
    }

    @Test
    void testShowFindForm()
        throws Exception
    {
        mockMvc.perform(get("/owners/find"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo(VIEW_NAME_FIND_OWNERS_FORM))))
               .andExpect(model().attributeExists("owner"));

        verifyZeroInteractions(ownerServiceMock);
    }

    @Test
    void testShowOwnerById()
        throws Exception
    {
        final Owner owner = owners.stream().filter(currOwner -> Long.valueOf(1L).equals(currOwner.getId())).findFirst().get();
        when(ownerServiceMock.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1"))
               .andExpect(status().isOk())
               .andExpect(view().name(VIEW_NAME_OWNER_DETAILS))
               .andExpect(model().attribute("owner", is(not(nullValue()))));

        verify(ownerServiceMock, times(1)).findById(eq(1L));
    }

    @Test
    void testShowUpdatedForm()
        throws Exception
    {
        final Owner owner = owners.stream().filter(currOwner -> currOwner.getId() == 1L).findFirst().get();
        when(ownerServiceMock.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1/edit"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo(VIEW_NAME_CREATE_OR_UPDATE_OWNER_FORM))))
               .andExpect(model().attribute("owner", is(sameInstance(owner))));

        verify(ownerServiceMock, times(1)).findById(eq(1L));
    }
}