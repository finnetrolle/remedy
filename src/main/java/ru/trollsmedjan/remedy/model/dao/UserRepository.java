package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.trollsmedjan.remedy.model.entity.User;

/**
 * Created by finnetrolle on 08.08.2015.
 */
public interface UserRepository extends JpaRepository<User, String> {
}
