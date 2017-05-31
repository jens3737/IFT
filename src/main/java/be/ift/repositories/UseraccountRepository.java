package be.ift.repositories;

import be.ift.domain.Begeleider;
import be.ift.domain.Rol;
import be.ift.domain.UserAccount;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UseraccountRepository extends CrudRepository<UserAccount, Integer> {

    @Query(value = "Select b FROM UserAccount u, Begeleider b WHERE (u.email = ?1) AND (u.rol.naam = 'ROLE_BEGELEIDER' OR u.rol.naam = 'ROLE_STAGECOORDINATOR') AND (u.persoon.id = b.persoon.id)")
    Begeleider findBegeleiderByEmail(String email);

    @Query(value = "Select u.rol FROM UserAccount u WHERE (u.email = ?1)")
    Rol findRolByEmail(String email);

    UserAccount findByEmail(String email);
}
