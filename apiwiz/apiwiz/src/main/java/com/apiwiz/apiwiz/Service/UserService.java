package com.apiwiz.apiwiz.Service;

import com.apiwiz.apiwiz.Dtos.LoginDto;
import com.apiwiz.apiwiz.Model.UserEntity;
import com.apiwiz.apiwiz.Dtos.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface  UserService {

    UserEntity getByEmailId(String emailId);


    Object getBySymbol(String symbol,String start_date,String end_date) throws JsonProcessingException;
}
