package com.coder520.common.util;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/24 17:02
 *@Description:日期和时间的工具类
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /*
     *@Author:XueGaoMian
     *@Date:2017/8/24 17:15
     *@MethordDescription:用于得到今天是星期几
     */
    public static int getTodayWeek(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int week=calendar.get(Calendar.DAY_OF_WEEK)-1;//得到的星期比实际的大一天；
        if(week<1)
            week=7;
        return week;
    }

    /*
     *@Author:XueGaoMian
     *@Date:2017/8/24 17:15
     *@MethordDescription:用于计算最后打卡时间和首次打卡时间的差（单位是分钟）
     */
    public static int  getMunite(Date startDate,Date endDate){
        long start=startDate.getTime();
        long end=endDate.getTime();
        int munite= (int) ((end-start)/(1000*60));
        return munite;
    }
}
