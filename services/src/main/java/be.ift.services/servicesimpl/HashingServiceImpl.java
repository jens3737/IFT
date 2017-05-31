package be.ift.services.servicesimpl;

import be.ift.domain.UserAccount;
import be.ift.services.HashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JHNBD42 on 20/04/2017.
 */
@Service
public class HashingServiceImpl implements HashingService {

//    @Override
//    public String hashString(String stringToHash) {
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            return passwordEncoder.encode(stringToHash);
//    }

    @Autowired
    public PasswordEncoder passwordEncoder;

}
