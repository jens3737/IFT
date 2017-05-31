package be.ift.services;

import be.ift.domain.Vraag;

import java.util.List;

/**
 * Created by JAHBD44 on 29/03/2017.
 */
public interface VraagService {
    List<Vraag> getAllVragen();
    List<Vraag> getAllVragenFEvaluatietemplate(Integer Vra_ID_template);
    List<Vraag> getAllVragen(int pageNumber);
    double getAantalPaginas();
    Vraag getVraagByID(Integer vraagID);
}
