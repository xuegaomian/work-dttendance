package com.coder520.attend.service;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/24 10:31
 *@Description:
 */

import com.coder520.attend.dao.AttendMapper;
import com.coder520.attend.entity.Attend;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class AttendServiceImpl implements AttendService {
    private Log log= LogFactory.getLog(AttendServiceImpl.class);
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Autowired
    private AttendMapper attendMapper;
    @Override
    public void signAttend(Attend attend) throws Exception {
        try {
            /*
             *@Author:XueGaoMian
             *@Date:2017/8/24 17:19
             *@MethordDescription:
             * 中午十二点之前打卡 都算早晨打卡，如果时间超过九点半，算迟到（异常打卡）
             * 十二点以后都算下午
             * 下午打卡，检查与上午打卡的时间差，18点之前打卡算早退（异常）
             * 不足八小时算异常，并且要将时间差存进去
             */

            attendMapper.insertSelective(attend);
        }catch (Exception e){
            log.error("用户签到异常！");
            throw e;//如果不throw，事务就可能不会回滚
        }


    }
}
