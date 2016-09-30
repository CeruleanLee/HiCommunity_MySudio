/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * @功能：<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2016/1/29<br>
 */
public class ScoreBean extends BaseBean {

    String scores;
    String zero_money;
    List<RecodeBean> record;

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getZero_money() {
        return zero_money;
    }

    public void setZero_money(String zero_money) {
        this.zero_money = zero_money;
    }

    public List<RecodeBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecodeBean> record) {
        this.record = record;
    }

    public class RecodeBean {
        String scores;
        String money;
        String create_time;

        public String getScores() {
            return scores;
        }

        public void setScores(String scores) {
            this.scores = scores;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
