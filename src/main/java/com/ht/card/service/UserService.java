package com.ht.card.service;

import com.ht.card.entities.UserInfo;
import com.ht.card.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public UserInfo getUserInfo(String id){
        return userMapper.getInfo(id);
    }

    public UserInfo getUserRInfo(String id){
        return userMapper.getRInfo(id);
    }

    public void save(UserInfo userInfo){
        userMapper.insert(userInfo);
    }
}
