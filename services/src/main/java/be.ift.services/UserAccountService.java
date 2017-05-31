package be.ift.services;

import be.ift.domain.Begeleider;
import be.ift.domain.Rol;
import be.ift.domain.UserAccount;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public interface UserAccountService {


    UserAccount getOneUserAccount(Integer id);
    UserAccount saveUserAccount(UserAccount userAccount);

    Begeleider getBegeleiderByEmail(String email);
    Rol getRolByEmail(String email);
//    double getAantalPaginas();
    double aantalUsers();
//    List<UserAccount> getAllUserAccounts(int pageNumber);
    UserAccount findOneByEmail(String email);



}
