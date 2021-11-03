package com.mzy.hdum.service.impl;

import com.mzy.hdum.mapper.UserMapper;
import com.mzy.hdum.model.User;
import com.mzy.hdum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findByName(String userName) {
        return userMapper.findUserByName(userName);
    }
    @Override
    public List<User> queryPage(Integer startRows) {
        return userMapper.queryPage(startRows);
    }
    @Override
    public int getRowCount() {
        return userMapper.getRowCount();
    }
    @Override
    public User insertUser(User user) {
        userMapper.insertUser(user);
        return user;
    }
    @Override
    public List<User> ListUser(){
        return userMapper.ListUser();
    }
    @Override
    public int Update(User user){
        return userMapper.Update(user);
    }
    @Override
    public int delete(int userId){
        return userMapper.delete(userId);
    }

}