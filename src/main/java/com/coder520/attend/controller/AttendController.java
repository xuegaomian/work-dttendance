package com.coder520.attend.controller;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/24 9:56
 *@Description:
 */

import com.coder520.attend.entity.Attend;
import com.coder520.attend.service.AttendService;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.user.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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

    @RequiresPermissions("attend:attendList")
    @RequestMapping("/attendList")
    @ResponseBody
    public PageQueryBean listAttend(QueryCondition condition, HttpSession session){
        /*
         *@Author:XueGaoMian
         *@Date:2017/8/26 14:16
         *@MethordDescription:查询打卡信息（分页）可以根据日期查询
         */
        //从session中获取用户信息
        User user= (User) session.getAttribute("userinfo");
        //从页面获取到的起始日期和终止日期，在分割之后存进condition中
        String rangeDate []=condition.getRangeDate().split("/");
        condition.setStartDate(rangeDate[0]);
        condition.setEndDate(rangeDate[1]);
        condition.setUserId(user.getId());

        PageQueryBean result=attendService.listAttend(condition);
        return result;
    }
}
