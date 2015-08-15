package ru.trollsmedjan.remedy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.dao.UserRepository;
import ru.trollsmedjan.remedy.model.entity.AccessRights;
import ru.trollsmedjan.remedy.model.entity.User;

/**
 * Created by finnetrolle on 15.08.2015.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createDefaultUser(String name) throws RemedyServiceLayerException {
        return createUser(name, new AccessRights());
    }

    @Override
    public User createUser(String name, AccessRights accessRights) throws RemedyServiceLayerException {
        User user = userRepository.findOne(name);
        if (user != null) {
            throw new RemedyServiceLayerException("User with this name is already exists");
        }
        user = new User();
        user.setAccessRights(accessRights);
        user.setUsername(name);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(String username) {
        return userRepository.findOne(username);
    }

    @Override
    public User updateUser(String username, AccessRights accessRights) throws RemedyDataLayerException {
        User user = userRepository.findOne(username);
        if (user == null) {
            throw new RemedyDataLayerException();
        }
        user.setAccessRights(accessRights);
        userRepository.save(user);
        return user;
    }
}
