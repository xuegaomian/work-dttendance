package com.coder520.common.util;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/24 17:02
 *@Description:日期和时间的工具类
 */

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static Calendar calendar=Calendar.getInstance();

    /*
     *@Author:XueGaoMian
     *@Date:2017/8/24 17:15
     *@MethordDescription:用于得到今天是星期几
     */
    public static int getTodayWeek(){

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

    public static Date getDate(int hour,int munite){
        /*
         *@Author:XueGaoMian
         *@Date:2017/8/25 12:23
         *@MethordDescription:获取当天的某个自己指定的时间
         */
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,munite);
        return calendar.getTime();
    }

    /*public static void main(String[] args) {
        System.out.println(DateUtils.getDate(5,200));
    }*/
}
