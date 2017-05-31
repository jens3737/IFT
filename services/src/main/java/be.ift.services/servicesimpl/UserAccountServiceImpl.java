package be.ift.services.servicesimpl;

import be.ift.domain.Begeleider;
import be.ift.domain.Rol;
import be.ift.domain.UserAccount;
import be.ift.repositories.UseraccountRepository;
import be.ift.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    public UseraccountRepository useraccountRepository;

    @Override
    public UserAccount getOneUserAccount(Integer id) {
        return useraccountRepository.findOne(id);
    }

    @Override
    public UserAccount saveUserAccount(UserAccount userAccount) {
        return useraccountRepository.save(userAccount);
    }

    @Override
    public Begeleider getBegeleiderByEmail(String email){
        return useraccountRepository.findBegeleiderByEmail(email);
    }

    @Override
    public Rol getRolByEmail(String email){
        return useraccountRepository.findRolByEmail(email);
    }

    @Override
    public double aantalUsers() {
        return useraccountRepository.count();
    }

    @Override
    public UserAccount findOneByEmail(String email) {
        return this.useraccountRepository.findByEmail(email);
    }
}
