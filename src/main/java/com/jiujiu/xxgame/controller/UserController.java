package com.jiujiu.xxgame.controller;

import com.jiujiu.xxgame.model.JiuJiuUser;
import com.jiujiu.xxgame.model.LoginResult;
import com.jiujiu.xxgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.jiujiu.xxgame.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

	@RequestMapping("/user/index")
    public List<User> getIndexPage(ModelMap model){
        List<User> list = new ArrayList<>();
        list.add(new User("GHSIUDIU276"));
        model.put("list", list);
        return list;
    }

    @RequestMapping("/user/login")
    public LoginResult login(String jiujiuToken){
        return userService.checkLogin(jiujiuToken);
    }

}
