package com.epam.esm.service;

import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.pojo.TagPOJO;
import com.epam.esm.pojo.UserPOJO;

import java.util.List;

public interface UserService {
    /**
     * This method finds all users from database using DAO
     **/
    List<UserPOJO> findAll();

    /**
     * This method finds concrete User By Id using DAO
     *
     * @param id Tag Id
     * @return Tag
     **/
    UserPOJO find(long id);

    /**
     * This method delete user by transmitted user id
     *
     * @param id user
     **/
    void delete(long id);

    /**
     * This method add new User
     *
     * @param tag User object
     **/
    UserPOJO create(UserPOJO user);

}
