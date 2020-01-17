package com.jiujiu.xxgame.controller;

import com.jiujiu.xxgame.model.JiuJiuUser;
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
        model.put("list", list);
        return list;
    }
	
	@PostMapping("/user/login")
    public List<User> login(@RequestParam("token") String token, @RequestParam("name") String name){
		List<User> list = new ArrayList<>();
        JiuJiuUser jiuJiuUser = userService.getJiuJiuUser(token);
        if (jiuJiuUser != null) {
            list.add(new User(jiuJiuUser.getUserName(), token,"barea"));
        }
        return list;
    }
}
