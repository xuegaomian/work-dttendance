package com.coder520.common.task;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/27 14:24
 *@Description:执行定时任务。计算出勤时间，判断是否异常打卡
 */

import com.coder520.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;

public class AttendCheckTask {
    @Autowired
    private AttendService attdService;

    public void checkAttend(){
        /*
         *@Author:XueGaoMian
         *@Date:2017/8/27 15:21
         *@MethordDescription:
         */
        //首先获取今天没打卡的人 给她插入打卡记录 并且设置为异常 缺勤480分钟
        //如果有打卡记录 检查早晚打卡时间 看看考勤是否异常
        attdService.checkAttend();


    }
}
