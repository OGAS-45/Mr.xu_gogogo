package com.mzy.hdum.service;

import org.springframework.stereotype.Service;
import com.mzy.hdum.model.User;
import java.util.List;

public interface UserService {

    public List<User> findByName(String userName);
    public List<User> queryPage(Integer startRows);

    public int getRowCount() ;

    public User insertUser(User user) ;

    public List<User> ListUser();
    public int Update(User user);

    public int delete(int userId);

}

