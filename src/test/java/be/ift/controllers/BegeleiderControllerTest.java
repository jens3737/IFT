package be.ift.controllers;

import be.ift.domain.*;
import be.ift.services.*;
import be.ift.services.servicesimpl.*;
import org.junit.*;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

/**
 * Created by VXFBD43 on 22/03/2017.
 */
public class BegeleiderControllerTest {


    private MockMvc mockmvc;

    @Mock
    private BegeleiderService begeleiderService;
    @Mock
    private BegeleiderServiceImpl begeleiderServiceImpl;
    @Mock
    private CategorieService categorieS;

    @InjectMocks
    private SchoolController schoolController;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockmvc = MockMvcBuilders.standaloneSetup(schoolController).build();
    }

    @Test
    public void viewBegeleidersTest() throws Exception{
        List<Categorie> categorieen = Arrays.asList(
                new Categorie("Java"),
                new Categorie("Sisharp"));

        when(categorieS.getAllCategories()).thenReturn(categorieen);

        Persoon persoon1 = new Persoon();
        Persoon persoon2 = new Persoon();

        List<Begeleider> begeleiders = Arrays.asList(
                new Begeleider(persoon1, "tests evrithing", categorieen.get(0)),
                new Begeleider(persoon2, "thest nothing", categorieen.get(1))
        );

        double aPaginas = 0;
        when(begeleiderServiceImpl.getAantalPaginas()).thenReturn(aPaginas);


    }
}