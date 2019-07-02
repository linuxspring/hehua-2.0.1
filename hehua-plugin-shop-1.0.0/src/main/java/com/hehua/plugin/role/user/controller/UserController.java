package com.hehua.plugin.role.user.controller;

import com.hehua.plugin.role.user.entity.User;
import com.hehua.plugin.role.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2019/4/23.
 * IntelliJ IDEA 2019 of gzcss
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Description("保存IP封堵接口")
    @RequestMapping(value = "/save.data",method = RequestMethod.GET)
    @ResponseBody
    public String saveAlarm(int index,int size){

        return "sssssssssssssssssss";
    }


    @Description("保存IP封堵接口")
    @RequestMapping(value = "/view.data",method = RequestMethod.GET)
    @ResponseBody
    public String getUser(int index,int size){

        User user =userService.selectById(1);
        return "sssssssssssssssssss";
    }
}
