package com.coder520.attend.service;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/24 10:31
 *@Description:
 */

import com.coder520.attend.dao.AttendMapper;
import com.coder520.attend.entity.Attend;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.common.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AttendServiceImpl implements AttendService {
    private Log log= LogFactory.getLog(AttendServiceImpl.class);
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Autowired
    private AttendMapper attendMapper;
    /*中午十二点 判定上下午*/
    private static int NOON_HOUR=12;
    private static int NOON_MUNITE=00;
    /**
     * 早晚上班时间判定
     */
    private static final int MORNING_HOUR = 9;
    private static final int MORNING_MINUTE = 30;
    private static final int EVENING_HOUR = 18;
    private static final int EVENING_MINUTE = 30;

    /**
     * 缺勤一整天
     */
    private static final Integer ABSENCE_DAY =480 ;
    /**
     * 考勤异常状态
     */
    private static final Byte ATTEND_STATUS_ABNORMAL = 2;
    private static final Byte ATTEND_STATUS_NORMAL = 1;


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

    @Override
    public PageQueryBean listAttend(QueryCondition condition) {
        // 根据条件查询count分页数据
        int count=attendMapper.countByConditon(condition);
        PageQueryBean pageresult=new PageQueryBean();
        //如果有记录 采取查询分页数据 如果没有记录，就没有必要去查询分页记录
        if (count>0){
            //分页信息类中设置总行数
            pageresult.setTotalRows(count);
            pageresult.setCurrentPage(condition.getCurrentPage());
            pageresult.setPageSize(condition.getPageSize());
            List<Attend> attendList=attendMapper.selectAttendPage(condition);
            pageresult.setItems(attendList);
        }

        return pageresult;
    }
    @Override
    @Transactional
    public void checkAttend() {
        //查询缺勤用户ID 插入打卡记录  并且设置为异常 缺勤480分钟
        List<Long> userIdList=attendMapper.selectTodayAbsence();
        //如果有缺勤的用户
        if (CollectionUtils.isNotEmpty(userIdList)){
            //将他们的打卡记录插入日期  是否异常 星期 缺勤时间
            List<Attend> attendList=new ArrayList<Attend>();
            for (Long userId:userIdList ) {
                Attend attend=new Attend();
                attend.setUserId(userId);
                attend.setAttendWeek((byte) DateUtils.getTodayWeek());
                attend.setAttendDate(new Date());
                attend.setAbsence(ABSENCE_DAY);
                attend.getAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendList.add(attend);
            }
            //批量插入
            attendMapper.batchInsert(attendList);

        }
        // 检查晚打卡 将下班未打卡记录设置为异常
        List<Attend> absenceList = attendMapper.selectTodayEveningAbsence();
        if(CollectionUtils.isNotEmpty(absenceList)){
            for(Attend attend : absenceList){
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);

                attendMapper.updateByPrimaryKeySelective(attend);
            }
        }
    }

}
