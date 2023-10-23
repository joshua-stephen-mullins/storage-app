package joshua.storageapp.services;

import org.springframework.stereotype.Service;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import joshua.storageapp.models.User;
import joshua.storageapp.repositories.UserRepository;

@Service
public class UserDaoService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userDao;

    public UserDaoService(UserRepository userDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser (User user){
        System.out.println("************ " + user.getUsername());
        System.out.println("************ " + user.getPassword());
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setCreated(new Date());
        user.setIsAdmin(false);
        userDao.save(user);
    }
    
}
