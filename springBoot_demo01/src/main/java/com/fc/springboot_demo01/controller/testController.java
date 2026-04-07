package com.fc.springboot_demo01.controller;

import com.fc.springboot_demo01.bean.user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class testController {

//    @RequestMapping(value = "/fun1",method = RequestMethod.GET)
    @GetMapping("/fun1")    //是上面一行的简写
    @ResponseBody   //返回值会直接写入到 HTTP 响应体中，而不会被解析为跳转路径
    public String fun1() {
        return "This is fun1";
    }

    @GetMapping("/fun2")
    @ResponseBody
    public String fun2(@RequestParam("name") String name) {
        return "This is fun2. name = " + name;
    }

    @GetMapping("/fun3/{id}")
    @ResponseBody
    public String fun3(@PathVariable(value = "id",required = false) String id) {
        return "This is fun3. id = " + id;
    }

    @PostMapping("/fun4")
    @ResponseBody
    public String fun4(@ModelAttribute user user) {
        return "This is fun4. user = " + user;
    }

    @PostMapping("/fun5")
    @ResponseBody
    public String fun5(@RequestBody user user) {
        // 打印接收到的用户数据
        System.out.println("接收到的用户数据：" + user);

        // 返回响应结果给前端
        return "提交成功！\n" +
                "用户名：" + user.getName() + "\n" +
                "性别：" + user.getGender() + "\n" +
                "爱好：" + String.join(",", user.getHobbies()) + "\n" +
                "国家：" + user.getCountry();
    }



}
