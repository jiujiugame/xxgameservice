package com.jiujiu.xxgame.controller;

import com.jiujiu.xxgame.model.ChargeResult;
import com.jiujiu.xxgame.model.JiuJiuUser;
import com.jiujiu.xxgame.model.LoginResult;
import com.jiujiu.xxgame.service.GameService;
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

    @Autowired
    private GameService gameService;

    @RequestMapping("/user/index")
    public List<User> getIndexPage(ModelMap model){
        List<User> list = new ArrayList<>();
        list.add(new User("GHSIUDIU276"));
        model.put("list", list);
        return list;
    }

    @RequestMapping("/user/login")
    public LoginResult login(String token){
        return userService.checkLogin(token);
    }

    @RequestMapping("/user/charge")
    public ChargeResult charge(String gameZoneId, String gameRoleId, String productId, String orderId){
        return gameService.charge(gameZoneId, gameRoleId, productId, orderId);
    }

}
