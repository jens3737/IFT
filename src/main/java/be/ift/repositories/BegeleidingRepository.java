package be.ift.repositories;

import be.ift.domain.Begeleiding;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by VXFBD43 on 20/03/2017.
 */
public interface BegeleidingRepository extends CrudRepository<Begeleiding, Integer> {

    @Query ("select b from Begeleiding b where b.begeleider.id = ?1")
    List<Begeleiding> findAllBegeleidingFBegeleider(Integer BEGL_ID);


}
