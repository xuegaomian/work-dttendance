package com.coder520.attend.vo;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/26 14:12
 *@Description:封装页面传进来的条件
 */

import com.coder520.common.page.PageQueryBean;

public class QueryCondition extends PageQueryBean {
    private String rangeDate;

    private String attendStatus;

    private long userId;
    //查询条件，几月几号到几月几号
    private String startDate;

    private String endDate;

    public String getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(String attendStatus) {
        this.attendStatus = attendStatus;
    }

    public String getRangeDate() {
        return rangeDate;
    }

    public void setRangeDate(String rangeDate) {
        this.rangeDate = rangeDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
