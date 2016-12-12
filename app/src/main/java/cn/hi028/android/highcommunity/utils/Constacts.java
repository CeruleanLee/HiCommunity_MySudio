/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.utils;

import com.baidu.location.BDLocation;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.bean.LabelBean;
import cn.hi028.android.highcommunity.bean.UserCenterBean;

/**
 * @功能：通用参数配置、全局变量配置<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/9<br>
 */
public class Constacts {

    public static final String QQ_APPID = "1105014018";
    public static final String QQ_APPKEY = "XVY3pxamWpgvhPJm";
    public static final String WEIXIN_APPID = "wx4ef53eabd1de60a1";
    public static final String WEIXIN_SECRET = "1905bab92f9fca8fbdde3c2c632d8035";
    public static final String WEIBO_APPID = "4045696716";
    public static final String WEIBO_SECRET = "9489249d6e4a90551caa4ac7d94541c9";
    public static final String IMAGEHTTP = "http://028hi.cn/";

    public static String APPVERSION = null;
    public static String APPTOKEN = "AppToken";
    public static String BROADCAST = "com.highcommunity.broadcast";
    /**
     * 服务页面跳转参数
     */
    //缴费
    public static final int SERVICE_PAYMENT = 0x000001;
    //租房
    public static final int SERVICE_TENEMENT = 0x000002;
    //报修
    public static final int SERVICE_REPAIR = 0x000003;
    //公告
    public static final int SERVICE_NOTICE = 0x000004;
    //办事指南
    public static final int SERVICE_GUIDE = 0x000005;
    //调查天地
    public static final int SERVICE_RESEARCH = 0x000006;
    //公告
    public static final int SERVICE_NOTICE_ONE = 0x000007;
    //第三方
    public static final int SERVICE_THIRD = 0x000008;
    //手艺人
    public static final int SERVICE_CARFSMAN = 0x000009;
    //成为手艺人
    public static final int SERVICE_BECOME_CARFSMAN = 0x000011;
    //成为手艺人
    public static final int SERVICE_SHAKE = 0x000012;

    //公告详情
    public static final int SERVICENOTICE_DETAILS = 0x000012;

    //手艺人
    public static final int SERVICECARFTS_DETAILS = 0x000013;
    //租房详情
    public static final int SERVICE_TENEMENT_DETAIL = 0x000014;
    //预约报修
    public static final int SERVICE_REPAIR_DETAIL_ORDER = 0x000015;
    //报修记录
    public static final int SERVICE_REPAIR_DETAIL_RECORD = 0x000016;//
    //紧急报修
    public static final int SERVICE_REPAIR_DETAIL_JJ = 0x000017;
    //活动详情
    public static final int ACTIVITY_DETAILS = 0x000018;
    //创建活动
    public static final int ACTIVITY_CREATE = 0x000019;
    //缴费详情
    public static final int SERVICEPAYMENT_DETAILS = 0x000020;

    //惠生活-物业直供订单
    public static final int HUILIFE_SUPPORT_ORDER = 0x000021;
    //惠生活-物业直供支付
    public static final int HUILIFE_SUPPORT_PAY = 0x000021;
    //惠生活-众筹详情
    public static final int HUILIFE_CHIPS_DETAIL = 0x000022;
    //惠生活-众筹订单提交
    public static final int HUILIFE_CHIPS_ORDER = 0x000024;
    public static final int HUILIFE_CHIPS_ORDER_PAY = 0x000025;
    //账单缴费
    public static final int PAYMENTBILL_PAY = 0x000023;

    //左侧菜单-用户信息
    public static final int MENU_LEFT_USERINFO = 0x000024;
    //左侧菜单-购物车
    public static final int MENU_LEFT_GDCAR = 0x000025;
    //左侧菜单-话题
    public static final int MENU_LEFT_TOPIC = 0x000026;
    //左侧菜单-收藏
    public static final int MENU_LEFT_COLLECTION = 0x000027;
    //左侧菜单-钱包
    public static final int MENU_LEFT_WALLET = 0x000028;
    //左侧菜单-物业账单
    public static final int MENU_LEFT_BILL = 0x000029;
    //左侧菜单-订单
    public static final int MENU_LEFT_ORDER = 0x000030;
    //左侧菜单-众筹
    public static final int MENU_LEFT_ZHONGCOU = 0x000031;
    //左侧菜单-手艺
    public static final int MENU_LEFT_CARFTS = 0x000032;
    //左侧菜单-设置
    public static final int MENU_LEFT_SETTING = 0x000033;
    //左侧菜单2级页面-编辑个人资料
    public static final int MENU_LEFTSECOND_PERSONAL = 0x000034;
    //左侧菜单2级页面-设置/修改密码
    public static final int MENU_LEFTSECOND_SETTINGMODIFYPSD = 0x000035;
    //左侧菜单2级页面-设置/关于我们
    public static final int MENU_LEFTSECOND_SETTINGABOUTUS = 0x000036;
    //左侧菜单2级页面-设置/联系我们
    public static final int MENU_LEFTSECOND_SETTINGCONSTACT = 0x000037;
    //主页面新消息(合并到左侧一级页面)
    public static final int MENU_LEFT_MESSAGECENTER = 0x000038;
    //账单缴费-已完成
    public static final int MENU_LEFTSECOND_PAYFINISH = 0x000039;
    //订单-详情
    public static final int MENU_LEFTSECOND_ORDER_DETAIL = 0x000038;
    //订单-评论
    public static final int MENU_LEFTTHIRD_ORDER_COMMENT = 0x000035;
    //搜索类型
    public static final String SEARCH_TYPE = "search_type";
    public static final String SEARCH_RESULT = "search_result";
    public static final int SEARCH_TYPE_VALLAGE = 0x000041;
    public static final int SEARCH_TYPE_GROUP = 0x000042;
    public static final int SEARCH_TYPE_CARFTS = 0x000043;
    //用户认证
    public static final int SERVICE_SECOND_PERSONALAUTH = 0x000040;
    //订单-详情
    public static final int MENU_LEFTSECOND_CHIP_ORDER_DETAIL = 0x000041;
    public static final int MENU_THIRD_AUTOINFO = 0x000042;
    //与我相关和系统消息
    public static final int MENU_MYMESSAGE = 0x000043;
    public static final int MENU_SYSMESSAGE = 0x000044;
    /**
     * 全局数据保存
     **/

    public static String mCurrentVid = null;
    public static List<LabelBean> mCustomLabel = new ArrayList<LabelBean>();
    public static UserCenterBean mUserCenter = null;
    public static BDLocation location = null;

    public static List<LabelBean> getLocalLabel() {
        List<LabelBean> mList = new ArrayList<LabelBean>();//mCustomLabel
        for (LabelBean label : mCustomLabel) {
            if (label.isClicked() == true) {
                label.setClicked(false);
            }
            if (label.isDel() == true) {
                label.setDel(false);
            }
        }
        mList.addAll(mCustomLabel);
        LabelBean mlabel1 = new LabelBean();
        mlabel1.setId("1");
        mlabel1.setLabel_name("宠物");
        mlabel1.setPic("drawable://" + R.mipmap.img_label_chongwu);
        mList.add(mlabel1);
        LabelBean mlabel2 = new LabelBean();
        mlabel2.setId("2");
        mlabel2.setLabel_name("运动");
        mlabel2.setPic("drawable://" + R.mipmap.img_label_yundong);
        mList.add(mlabel2);
        LabelBean mlabel3 = new LabelBean();
        mlabel3.setId("3");
        mlabel3.setLabel_name("宝妈");
        mlabel3.setPic("drawable://" + R.mipmap.img_label_baoma);
        mList.add(mlabel3);
        LabelBean mlabel4 = new LabelBean();
        mlabel4.setId("4");
        mlabel4.setLabel_name("三缺一");
        mlabel4.setPic("drawable://" + R.mipmap.img_label_majiang);
        mList.add(mlabel4);
        LabelBean mlabel5 = new LabelBean();
        mlabel5.setId("5");
        mlabel5.setLabel_name("吐槽");
        mlabel5.setPic("drawable://" + R.mipmap.img_label_tocao);
        mList.add(mlabel5);
        LabelBean mlabel6 = new LabelBean();
        mlabel6.setId("6");
        mlabel6.setLabel_name("交友");
        mlabel6.setPic("drawable://" + R.mipmap.img_label_jiaoyou);
        mList.add(mlabel6);
        LabelBean mlabel7 = new LabelBean();
        mlabel7.setId("7");
        mlabel7.setLabel_name("帮帮忙");
        mlabel7.setPic("drawable://" + R.mipmap.img_label_help);
        mList.add(mlabel7);
        LabelBean mlabel8 = new LabelBean();
        mlabel8.setId("8");
        mlabel8.setLabel_name("跳蚤市场");
        mlabel8.setPic("drawable://" + R.mipmap.img_label_market);
        mList.add(mlabel8);
        LabelBean mlabel9 = new LabelBean();
        mlabel9.setId("-1");
        mlabel9.setLabel_name("创建");
        mlabel9.setPic("drawable://" + R.mipmap.img_label_create);
        mList.add(mlabel9);
        LabelBean mlabel10 = new LabelBean();
        mlabel10.setId("-2");
        mlabel10.setLabel_name("删除");
        mlabel10.setPic("drawable://" + R.mipmap.img_label_delete);
        mList.add(mlabel10);
        return mList;
    }

}
