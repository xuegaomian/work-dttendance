package com.coder520.attend.service;/*
 *@Author: XueGaoMian
 *@Date: 2017/8/24 10:30
 *@Description:签到
 */

import com.coder520.attend.entity.Attend;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;

public interface AttendService {
    void signAttend(Attend attend) throws Exception;

    PageQueryBean listAttend(QueryCondition condition);
    void checkAttend();
}
