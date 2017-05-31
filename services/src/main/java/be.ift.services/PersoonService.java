package be.ift.services;

import be.ift.domain.Persoon;

/**
 * Created by VXFBD43 on 14/03/2017.
 */
public interface PersoonService {

    Persoon savePersoon(Persoon persoon);

    Persoon getOnePersoon(Integer id);
}
