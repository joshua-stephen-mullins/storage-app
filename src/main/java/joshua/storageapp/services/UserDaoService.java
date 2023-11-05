package joshua.storageapp.services;

import org.springframework.stereotype.Service;

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
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
    }

    public void saveUser(User user){
        userDao.save(user);
    }

    public User getUserByUsername(String username){
        return userDao.findByUsername(username);
    }
    
}
