package com.mzy.hdum.controller;

import com.mzy.hdum.model.User;
import com.mzy.hdum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

        @Autowired
        private UserService userService;

        @RequestMapping(value = "/delete", method = RequestMethod.POST)
        public Integer delete(Integer userId) {
            System.out.println(userId);
            int result = userService.delete(userId);
            return result;
        }

        @RequestMapping(value = "/update", method = RequestMethod.POST)
        @ResponseBody
        public String update(User user) {
            int result = userService.Update(user);
            if (result >= 1) {
                return "修改成功";
            } else {
                return "修改失败";
            }

        }

        @RequestMapping(value = "/insert", method = RequestMethod.POST)
        public User insert(User user) {
            return userService.insertUser(user);
        }

        @RequestMapping("/ListUser")
        @ResponseBody
        public List<User> ListUser() {
            return userService.ListUser();
        }

        @RequestMapping("/ListByName")
        @ResponseBody
        public List<User> ListUserByName(String userName) {
            return userService.findByName(userName);
        }

        /**
         * 分页
         * @return
         */
        @RequestMapping(value="/page")
        @ResponseBody
        public List<User> page(Integer page){
            int pageNow = page == null ? 1 : page;
            int pageSize = 5;
            int startRows = pageSize*(pageNow-1);
            List<User> list = userService.queryPage(startRows);
            return list;
        }

        /**
         * rows
         * @return
         */
        @RequestMapping(value="/rows")
        @ResponseBody
        public int rows(){
            return userService.getRowCount();
        }
    }
