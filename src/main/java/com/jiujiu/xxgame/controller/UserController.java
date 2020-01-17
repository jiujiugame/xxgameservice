package com.jiujiu.xxgame.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.jiujiu.xxgame.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

	@RequestMapping("/user/index")
    public List<User> getIndexPage(ModelMap model){
        List<User> list=new ArrayList<>();
        list.add(new User("GHSIUDIU276","barea"));
        model.put("list", list);
        return list;
    }
	
	@PostMapping("/user/login")
    public List<User> login(String token){
		List<User> list=new ArrayList<>();
        System.out.print("你好 home ");
        list.add(new User("GHSIUDIU276","barea"));
        return list;
    }
}
