package be.ift.services;

import be.ift.domain.Rol;

import java.util.List;

/**
 * Created by JAHBD44 on 20/03/2017.
 */
public interface RolService {

    /*READ*/
    Rol getOneRol(Integer ID);

    List<Rol> getAllRollen(int pageNumber);

    double getAantalPaginas();

    List<Rol> getAllRollen();

    /*CREATE*/
    Rol saveRol(Rol rol);


}
