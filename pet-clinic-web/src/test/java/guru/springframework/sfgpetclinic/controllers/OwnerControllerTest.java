package guru.springframework.sfgpetclinic.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
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
    void testFindOwners()
        throws Exception
    {
        mockMvc.perform(get("/owners/find"))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("notimplemented"))))
               .andExpect(model().size(0));

        verifyZeroInteractions(ownerServiceMock);
    }

    @Test
    void testListOwnersWithBasePath()
        throws Exception
    {
        testListOwners("");
    }

    @Test
    void testListOwnersWithIndexHtmlPath()
        throws Exception
    {
        testListOwners("/index.html");
    }

    @Test
    void testListOwnersWithIndexPath()
        throws Exception
    {
        testListOwners("/index");
    }

    @Test
    void testListOwnersWithSlahsPath()
        throws Exception
    {
        testListOwners("/");
    }

    @Test
    void testShowOwnerById()
        throws Exception
    {
        final Owner owner = owners.stream().filter(currOwner -> Long.valueOf(1L).equals(currOwner.getId())).findFirst().get();
        when(ownerServiceMock.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/ownerDetails"))
               .andExpect(model().attribute("owner", is(not(nullValue()))));

        verify(ownerServiceMock, times(1)).findById(eq(1L));
    }

    private void testListOwners(final String path)
        throws Exception
    {
        when(ownerServiceMock.findAll()).thenReturn(owners);

        mockMvc.perform(get("/owners" + path))
               .andExpect(status().isOk())
               .andExpect(view().name(is(equalTo("owners/index"))))
               .andExpect(model().attribute("owners", hasSize(equalTo(3))));

        verify(ownerServiceMock, times(1)).findAll();
    }
}