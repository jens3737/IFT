package be.ift.controllers;

import be.ift.domain.Persoon;
import be.ift.domain.School;
import be.ift.repositories.SchoolRepository;
import be.ift.services.PersoonService;
import be.ift.services.SchoolService;
import be.ift.services.servicesimpl.SchoolServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.*;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by VXFBD43 on 21/03/2017.
 */


public class SchoolControllerTest {

    private MockMvc mockmvc;

    @Mock
    private SchoolService schoolService;

    @Mock
    private SchoolServiceImpl schoolServiceImpl;

    @InjectMocks
    private SchoolController schoolController;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockmvc = MockMvcBuilders.standaloneSetup(schoolController).build();
    }

    @Test
    public void getAllScholen() throws Exception{
        List<School> scholen = Arrays.asList(
                new School("Sint-vic"),
                new School("Odisee"));

        when(schoolService.getAllScholen()).thenReturn((List) scholen);

        mockmvc.perform(get("/school"))
        .andExpect(status().isOk())
        .andExpect(view().name("schoolcreate"))
        .andExpect(model().attribute("scholenlijst", hasSize(2)));

        verify(schoolService, times(1)).getAllScholen();
    }

    @Test
    public void test_get_by_id() throws Exception {
        when(schoolServiceImpl.getOneSchool(1)).thenReturn(new School());

        mockmvc.perform(get("/school/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("schoolshow"))
                .andExpect(model().attribute(("school"), instanceOf(School.class)));

        verify(schoolServiceImpl, times(1)).getOneSchool(1);
    }


    @Test
    public void SchoolSavedTest() throws Exception {

        String pnaam = "Teststraat";
        String pemail = "Odisee";
        String pnummer = "04123456";
        Persoon persoon = new Persoon();
        persoon.setNaam(pnaam);
        persoon.setEmail(pemail);
        persoon.setGsm(pnummer);


        String adres = "Teststraat";
        String naam = "Odisee";
        String contactnr = "04123456";
        School returnSchool = new School();
        returnSchool.setAdres(adres);
        returnSchool.setNaam(naam);
        returnSchool.setContactpersoon(persoon);
        returnSchool.setContactnummer(contactnr);

        when(schoolService.saveSchool(Matchers.<School>any())).thenReturn(returnSchool);

        mockmvc.perform(post("/schoolopslaan")
                .param("school","returnschool")
                .param("persoonnaam", pnaam)
                .param("persoonmail", pemail)
                .param("persoonnr", pnummer))

                .andExpect(model().attribute("school",instanceOf(School.class)));
    }

}



