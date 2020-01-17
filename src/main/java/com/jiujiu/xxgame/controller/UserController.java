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
	

    public LoginResult login(String token, String uid){
        userService.checkLogin(token, uid);
        LoginResult result = new LoginResult();
        result.setBoard("[]");
        result.setS2c_code(200);
        result.setNewUid("FHA-ASKJH-ASJ");
        result.setToken("0d87425f-44b1-4e22-b85b-482fcb6e3048");
        return result;
    }

}
