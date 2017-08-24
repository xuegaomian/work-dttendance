package com.coder520.attend.controller;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/24 9:56
 *@Description:
 */

import com.coder520.attend.entity.Attend;
import com.coder520.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("attend")
public class AttendController {
    @Autowired
    private AttendService attendService;

    /*
         *@Author:XueGaoMian
         *@Date:2017/8/24 9:56
         *@MethordDescription:跳转打卡记录页面
         */
    @RequestMapping()
    public String attend(){

        return "attend";
    }

    /*
     *@Author:XueGaoMian
     *@Date:2017/8/24 10:31
     *@MethordDescription:签到功能  log4j
     */
    @RequestMapping("/sign")
    @ResponseBody
    public String signAttend(@RequestBody Attend attend) throws Exception {
        attendService.signAttend(attend);
        return "success";

    }

}
