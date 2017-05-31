package be.ift.repositories;

import be.ift.domain.Persoon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by VXFBD43 on 14/03/2017.
 */
@Repository
public interface PersoonRepository extends CrudRepository<Persoon, Integer> {


}
