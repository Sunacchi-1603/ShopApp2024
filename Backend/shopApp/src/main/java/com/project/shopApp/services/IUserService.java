package com.project.shopApp.services;

import com.project.shopApp.dtos.UserDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.exeptions.InvalidParamException;
import com.project.shopApp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber,String password) throws DataNotFoundException, InvalidParamException;
}
