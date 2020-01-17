package com.jiujiu.xxgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jiujiu.xxgame.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

	@RequestMapping("/user/index")
    public List<User> getIndexPage(ModelMap model){
        List<User> list=new ArrayList<>();
        System.out.print("你好 home ");
        list.add(new User("GHSIUDIU276","barea"));
        model.put("list", list);
        return list;
    }
	
	@RequestMapping("/user/login")
    public List<User> login(ModelMap model){
		List<User> list=new ArrayList<>();
        System.out.print("你好 home ");
        list.add(new User("GHSIUDIU276","barea"));
        model.put("list", list);
        return list;
    }
}
