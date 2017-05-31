package be.ift.services;

import be.ift.domain.Schaal;

import java.util.List;

/**
 * Created by JAHBD44 on 29/03/2017.
 */
public interface SchaalService {

    List<Schaal> getAllSchalen();

    Schaal getSchaalByID(int schaalID);
}
