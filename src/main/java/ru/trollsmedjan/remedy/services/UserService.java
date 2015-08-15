package ru.trollsmedjan.remedy.services;

import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.AccessRights;
import ru.trollsmedjan.remedy.model.entity.User;

/**
 * Created by finnetrolle on 15.08.2015.
 */
public interface UserService {

    User createDefaultUser(String name) throws RemedyServiceLayerException;

    User createUser(String name, AccessRights accessRights) throws RemedyServiceLayerException;

    User getUser(String username);

    User updateUser(String username, AccessRights accessRights) throws RemedyDataLayerException;
}
