package com.coder520.attend.service;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/24 10:31
 *@Description:
 */

import com.coder520.attend.dao.AttendMapper;
import com.coder520.attend.entity.Attend;
import com.coder520.common.util.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AttendServiceImpl implements AttendService {
    private Log log= LogFactory.getLog(AttendServiceImpl.class);
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Autowired
    private AttendMapper attendMapper;
    /*中午十二点 判定上下午*/
    private static int NOON_HOUR=12;
    private static int NOON_MUNITE=00;

    @Override
    public void signAttend(Attend attend) throws Exception {
        /*
             *@Author:XueGaoMian
             *@Date:2017/8/24 17:19
             *@MethordDescription:
             * 中午十二点之前打卡 都算早晨打卡，如果时间超过九点半，算迟到（异常打卡）
             * 十二点以后都算下午
             * 下午打卡，检查与上午打卡的时间差，18点之前打卡算早退（异常）
             * 不足八小时算异常，并且要将时间差存进去
             */
        try {
            Date today=new Date();
            //设置打卡日期
            attend.setAttendDate(today);
            //设置打卡时间是周几
            attend.setAttendWeek((byte) DateUtils.getTodayWeek());
            //查询此人今天是否已经打过卡？
            Attend todayRecord=attendMapper.selectTodaySignRecord(attend.getUserId());
            //获取当天的十二点
            Date noon= DateUtils.getDate(NOON_HOUR,NOON_MUNITE);

            if(todayRecord==null){
                //此人今天打卡记录还不存在
                if(today.compareTo(noon)<=0){//中午之前打卡  判定为上午打卡

                    attend.setAttendMorning(today);


                }else {
                    attend.setAttendEvening(today);
                }
                attendMapper.insertSelective(attend);
            }else {
                //此人今天已经打过卡
                if(today.compareTo(noon)<=0){//中午之前打卡  判定为上午打卡

                    return;


                }else {
                    todayRecord.setAttendEvening(today);
                    attendMapper.updateByPrimaryKeySelective(todayRecord);
                }
            }

        }catch (Exception e){
            log.error("用户签到异常！");
            throw e;//如果不throw，事务就可能不会回滚
        }


    }
}
