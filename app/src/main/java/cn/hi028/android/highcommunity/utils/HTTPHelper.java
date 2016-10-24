/***************************************************************************
 * Copyright (c) by raythinks.com, Inc. All Rights Reserved
 **************************************************************************/

package cn.hi028.android.highcommunity.utils;

import android.text.TextUtils;
import android.util.Log;

import com.don.tools.BpiHttpClient;
import com.don.tools.BpiHttpHandler;
import com.don.tools.Debug;
import com.don.tools.DongConstants;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import net.duohuo.dhroid.util.ImageUtil;
import net.duohuo.dhroid.util.LogUtil;
import net.duohuo.dhroid.util.MD5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.hi028.android.highcommunity.HighCommunityApplication;
import cn.hi028.android.highcommunity.activity.alliance.AllianceOrder;
import cn.hi028.android.highcommunity.bean.ActiveBean;
import cn.hi028.android.highcommunity.bean.ActivityDetailBean;
import cn.hi028.android.highcommunity.bean.AddressBean;
import cn.hi028.android.highcommunity.bean.AddressModifyBean;
import cn.hi028.android.highcommunity.bean.AliParamBean;
import cn.hi028.android.highcommunity.bean.AllTicketBean;
import cn.hi028.android.highcommunity.bean.AllianceOrderBean;
import cn.hi028.android.highcommunity.bean.AlliancePayBean;
import cn.hi028.android.highcommunity.bean.Autonomous.AutoDetail_QuestionVotedBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_DoorBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InitBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_InquiryDetailBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionDetailBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_NameListBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_NoticeListBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_QuestionDeatailBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_ReportDetailBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SuperViseBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_UnitBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteList_Vote;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_VoteResultBean;
import cn.hi028.android.highcommunity.bean.BillBean;
import cn.hi028.android.highcommunity.bean.BillSimpleBean;
import cn.hi028.android.highcommunity.bean.CarftsBean;
import cn.hi028.android.highcommunity.bean.CarftsDetailBean;
import cn.hi028.android.highcommunity.bean.CertifiBean;
import cn.hi028.android.highcommunity.bean.ChipOrderDetailBean;
import cn.hi028.android.highcommunity.bean.ChipsBean;
import cn.hi028.android.highcommunity.bean.ChipsOrderBean;
import cn.hi028.android.highcommunity.bean.ChipsOrderResulBean;
import cn.hi028.android.highcommunity.bean.CitysBean;
import cn.hi028.android.highcommunity.bean.CommunityBean;
import cn.hi028.android.highcommunity.bean.CommunityListBean;
import cn.hi028.android.highcommunity.bean.CommunityMsgBean;
import cn.hi028.android.highcommunity.bean.CountyBean;
import cn.hi028.android.highcommunity.bean.CouponBean;
import cn.hi028.android.highcommunity.bean.DistrictBean;
import cn.hi028.android.highcommunity.bean.GdCarBean;
import cn.hi028.android.highcommunity.bean.GoodsOrderBean;
import cn.hi028.android.highcommunity.bean.GroupBean;
import cn.hi028.android.highcommunity.bean.GroupDetailsBean;
import cn.hi028.android.highcommunity.bean.HSuppGdDefBean;
import cn.hi028.android.highcommunity.bean.HuiOrderBean;
import cn.hi028.android.highcommunity.bean.HuiSupportBean;
import cn.hi028.android.highcommunity.bean.LabelBean;
import cn.hi028.android.highcommunity.bean.MerchantEvaluationInfoListBean;
import cn.hi028.android.highcommunity.bean.MerchantShopGoodBean;
import cn.hi028.android.highcommunity.bean.MessageDetailsBean;
import cn.hi028.android.highcommunity.bean.NearByShopData;
import cn.hi028.android.highcommunity.bean.NearbyOrderDetailBean;
import cn.hi028.android.highcommunity.bean.NoticeBean;
import cn.hi028.android.highcommunity.bean.NoticeDetailsBean;
import cn.hi028.android.highcommunity.bean.OperateBean;
import cn.hi028.android.highcommunity.bean.OrderBean;
import cn.hi028.android.highcommunity.bean.OrderResult;
import cn.hi028.android.highcommunity.bean.PersonAuthBean;
import cn.hi028.android.highcommunity.bean.PersonalInfoBean;
import cn.hi028.android.highcommunity.bean.RepairBean;
import cn.hi028.android.highcommunity.bean.RepairJJBean;
import cn.hi028.android.highcommunity.bean.RepairJinJBean;
import cn.hi028.android.highcommunity.bean.ScoreBean;
import cn.hi028.android.highcommunity.bean.ShakeBean;
import cn.hi028.android.highcommunity.bean.ShakeUser;
import cn.hi028.android.highcommunity.bean.SubmitOrderBean;
import cn.hi028.android.highcommunity.bean.SystemMessageBean;
import cn.hi028.android.highcommunity.bean.TenementBean;
import cn.hi028.android.highcommunity.bean.TenementHouseBean;
import cn.hi028.android.highcommunity.bean.ThirdServiceBean;
import cn.hi028.android.highcommunity.bean.UserCenterBean;
import cn.hi028.android.highcommunity.bean.UserInfoBean;
import cn.hi028.android.highcommunity.bean.VallageBean;
import cn.hi028.android.highcommunity.bean.VallageSelectBean;
import cn.hi028.android.highcommunity.bean.WechatParamBean;
import cn.hi028.android.highcommunity.bean.WpayBean;
import cn.hi028.android.highcommunity.params.HuiChipsOrderparams;
import it.sauronsoftware.base64.Base64;

/**
 * @功能：<br>
 * @作者： 李凌云<br>
 * @版本：1.0<br>
 * @时间：2015/12/7<br>
 */
public class HTTPHelper {
   static  final String Tag="~~~000 HTTPHelper:";
    public static String HTTPPOSTURL = "http://028hi.cn/api/";
    // public static String HTTPPOSTURL =
    // "http://028hi.cn/api/default/";//http://028hi.cn/api/message/index.html
    public static Gson gson = new Gson();

    /**
     * 登录
     **/
    public static void Login(BpiHttpHandler.IBpiHttpHandler mIbpi,
                             String username, String psd, String type, String third_uid,
                             String nick) {
        String url = HTTPPOSTURL + "default/login.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if ("0".equals(type)) {
            mParamMap.put("username", Base64(username));
            mParamMap.put("password", MD5(psd));
        }
        mParamMap.put("type", type);
        if (!"0".equals(type)) {
            mParamMap.put("nick", nick);
        }
        if (third_uid != null)
            mParamMap.put("third_uid", third_uid);

        Log.d(Tag,"登陆用的数据："+mParamMap.toString());


        post(mParamMap, mIbpi, url);
    }

    /**
     * 注册
     **/
    public static void Register(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                String username, String psd, String Identfy) {
        String url = HTTPPOSTURL + "default/register.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("username", Base64(username));
        mParamMap.put("password", MD5(psd));
        mParamMap.put("captcha", Identfy);
        post(mParamMap, mIbpi, url);
    }

    /**
     * token 验证
     **/
    public static void Token(BpiHttpHandler.IBpiHttpHandler mIbpi, String token) {
        String url = HTTPPOSTURL + "default/token.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("token", token);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 在支付界面点击支付请求解析
     *
     * @param result
     * @return
     */
    public static OrderResult ResolveOrderResult(String result) {
        return gson.fromJson(result, OrderResult.class);
    }

    /**
     * 获取消息条数
     *
     * @param mIbpi
     */
    public static void getMsgNum(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        if (HighCommunityUtils.isLogin()) {
            String url = HTTPPOSTURL + "message/tips.html";
            HashMap<String, String> mParamMap = getBaseParamMap();
            post(mParamMap, mIbpi, url);
        }
    }

    /**
     * 分享后回掉
     *
     * @param mIbpi
     */
    public static void shareResult(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        if (HighCommunityUtils.isLogin()) {
            String url = HTTPPOSTURL + "share/index.html";
            HashMap<String, String> mParamMap = getBaseParamMap();
            mParamMap.put("user_id", HighCommunityApplication.mUserInfo.getId()
                    + "");
            post(mParamMap, mIbpi, url);
        }
    }

    /**
     * 忘记密码
     **/
    public static void Forget(BpiHttpHandler.IBpiHttpHandler mIbpi,
                              String username, String psd, String Identfy) {
        String url = HTTPPOSTURL + "default/forget.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("username", Base64(username));
        mParamMap.put("password", MD5(psd));
        mParamMap.put("captcha", Identfy);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 注册 /忘记密码 时获取验证码 type 1=>注册,2=>忘记密码
     **/
    public static void Send(BpiHttpHandler.IBpiHttpHandler mIbpi,
                            String username, String type) {
        String url = HTTPPOSTURL + "default/send.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("username", Base64(username));
        mParamMap.put("type", type);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取信息流
     **/
    public static void GetMessage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                  int userId, int Vid, String time) {
        LogUtil.d("------------CommunityFrag   GetMessage");
        String url = HTTPPOSTURL + "message/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (userId != 0)
            mParamMap.put("uid", userId + "");
        if (!TextUtils.isEmpty(time)) {
            mParamMap.put("time", time);
        }
        mParamMap.put("vid", Vid + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 点击位置跳转该小区发帖
     **/
    public static void GetVillageMessage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                         int userId, String Vid) {
        String url = HTTPPOSTURL + "message/village-message-index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (userId != 0)
            mParamMap.put("uid", userId + "");
        mParamMap.put("vid", Vid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 我的话题主页
     **/
    public static void GetMyMessage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    int userId) {
        String url = HTTPPOSTURL + "message/my-message-index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", userId + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 群消息信息流
     **/
    public static void GetGroupMessage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       int userId, String gid) {
        String url = HTTPPOSTURL + "message/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", userId + "");
        mParamMap.put("vid", gid);
        mParamMap.put("isGroup", gid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 我收藏的话题主页
     **/
    public static void GetMyCollectMessage(
            BpiHttpHandler.IBpiHttpHandler mIbpi, int userId) {
        String url = HTTPPOSTURL + "favorite/my-favorite.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", userId + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 刷新我的话题信息流
     **/
    public static void RefreshMessage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      int type, String time, int userId, int Vid) {
        String url = HTTPPOSTURL + "message/refresh.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (userId != 0)
            mParamMap.put("uid", userId + "");
        mParamMap.put("vid", Vid + "");
        mParamMap.put("type", type + "");
        mParamMap.put("time", time);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 刷新小区信息流
     **/
    public static void RefreshVillageMessage(
            BpiHttpHandler.IBpiHttpHandler mIbpi, int type, String time,
            int userId, String Vid) {
        String url = HTTPPOSTURL + "message/village-message-refresh.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (userId != 0)
            mParamMap.put("uid", userId + "");
        mParamMap.put("vid", Vid);
        mParamMap.put("type", type + "");
        mParamMap.put("time", time);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 刷新我的话题信息流
     **/
    public static void RefreshMyMessage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        int type, String time, int userId) {
        String url = HTTPPOSTURL + "message/my-message-refresh.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (userId != 0)
            mParamMap.put("uid", userId + "");
        mParamMap.put("type", type + "");
        mParamMap.put("time", time);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 刷新群消息信息流
     **/
    public static void RefreshGroupMessage(
            BpiHttpHandler.IBpiHttpHandler mIbpi, int type, String time,
            int userId, String gid) {
        String url = HTTPPOSTURL + "message/refresh.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (userId != 0)
            mParamMap.put("uid", userId + "");
        mParamMap.put("type", type + "");
        mParamMap.put("time", time);
        mParamMap.put("vid", gid);
        mParamMap.put("isGroup", gid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取当前用户报修记录
     *
     * @param mIbpi
     * @param user_id
     */
    public static void GetRepairRecordList(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String user_id) {
        String url = HTTPPOSTURL + "repair/record.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", user_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 提交预约报修信息
     *
     * @param mIbpi
     * @param link_man  联系人
     * @param phoneNum  电话
     * @param address   地址
     * @param make_time 预约时间
     * @param content   描述内容
     * @param image     图片
     */
    public static void OrderRepairMsg(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String link_man, String phoneNum, String address, String make_time,
                                      String content, List<String> image) {
        String url = HTTPPOSTURL + "repair/appointment.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("user_id", HighCommunityApplication.mUserInfo.getId());
        mParamMap.put("vid", HighCommunityApplication.mUserInfo.getV_id());
        mParamMap.put("content", content);
        mParamMap.put("address", address);
        mParamMap.put("make_time", make_time);
        mParamMap.put("tel", phoneNum);
        mParamMap.put("link_man", link_man);
        try {
            for (int i = 0; i < image.size(); i++) {
                mParamMap.put("image" + "[" + i + "]",
                        ImageUtil.getImage(image.get(i)));
            }
        } catch (Exception e) {

        }
        if (image.size() > 0) {
            Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url
                    + "   ling params:" + mParamMap.toString());
        }
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 获取紧急报修记录
     *
     * @param mIbpi
     * @param vid
     */
    public static void GetRepairJJList(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String vid) {
        String url = HTTPPOSTURL + "repair/urgency.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("vid", vid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取订单号
     * <p/>
     * //	 * @param mIbpi
     * //	 * @param vid
     */
    public static void GetOrderNo(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                  String total_price, String goods_info, String merchant_id) {
        String url = HTTPPOSTURL + "nearby/submit-order.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("merchant_id", merchant_id);
        mParamMap.put("total_price", total_price);
        mParamMap.put("goods_info", goods_info);
        LogUtil.d("------GetOrderNo---" + mParamMap.toString());


        post(mParamMap, mIbpi, url);
    }

    /**
     * 点击预约报修判断是否认证,如认证返回认证信息接口
     *
     * @param mIbpi
     * @param user_id
     */
    public static void GetRepairCertification(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String user_id) {
        String url = HTTPPOSTURL + "repair/certification.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", user_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取惠生活物品直供列表
     **/
    public static void GetHuiSupportList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "goods/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 添加直供商品到购物车
     **/
    public static void addHuiSupportCar(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String gid, String user_id) {
        String url = HTTPPOSTURL + "goods/shopping-cart.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("gid", gid);
        mParamMap.put("user_id", user_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取惠生活众筹列表
     **/
    public static void GetHuiChipsList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "raise/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取惠生活众筹详情
     **/
    public static void GetHuiChipsDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                         String rid) {
        String url = HTTPPOSTURL + "raise/detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("rid", rid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取订单列表
     **/
    public static void GetHuiOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String typeURL, String uid) {
        String url = HTTPPOSTURL + typeURL;
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", uid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 摇一摇
     **/
    public static void ShakeData(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "shake/shake.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 摇一摇列表
     **/
    public static void ShakeDataList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "shake/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取我的众筹
     **/
    public static void GetHuiMyChipsOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                          int status) {
        String url = HTTPPOSTURL + "raise/my-raise.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("status", status + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 修改密码
     **/
    public static void modifyPsd(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                 String oldPsd, String newPsd) {
        String url = HTTPPOSTURL + "default/reset.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("old_password", MD5(oldPsd));
        mParamMap.put("new_password", MD5(newPsd));
        mParamMap.put("confirm_password", MD5(newPsd));
        post(mParamMap, mIbpi, url);
    }

    /**
     * 取消直供订单接口
     **/
    public static void cancelOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String order_id) {
        String url = HTTPPOSTURL + "order/cancel.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 确认收货接口
     **/
    public static void reciveOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String order_id) {
        String url = HTTPPOSTURL + "goods/receipt.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 评论直供订单接口
     **/
    public static void commentOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    String content) {
        String url = HTTPPOSTURL + "goods/submit-comment.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("comment", content);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 取消众筹订单接口
     **/
    public static void cancelChipsOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String order_id) {
        String url = HTTPPOSTURL + "raise/cancel.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 确认众筹订单收货接口
     **/
    public static void reciveChipsOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String out_trade_no) {
        String url = HTTPPOSTURL + "raise/confirm.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("out_trade_no", out_trade_no);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取惠生活众筹提交界面详情
     **/
    public static void GetHuiChipsOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String rid, String uid) {
        String url = HTTPPOSTURL + "raise/join.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("rid", rid);
        mParamMap.put("uid", uid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 提交众筹商品订单
     **/
    public static void submintHuiChipsOrder(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String rid, String uid,
            String num, String join_price, String aid) {
        String url = HTTPPOSTURL + "raise/order.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("rid", rid);
        mParamMap.put("uid", uid);
        mParamMap.put("num", num);
        mParamMap.put("join_price", join_price);
        mParamMap.put("aid", aid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 购买获取直供商品信息,零钱包和默认收货地址信息接口
     **/
    public static void GetHuiSuppGoodsMsg(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                          String user_id, String gid) {
        String url = HTTPPOSTURL + "goods/create.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("gid", gid);
        mParamMap.put("user_id", user_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 创建订单
     *
     * @param mIbpi
     * @param order
     */
    public static void submitHuiSuppOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                          String order) {
        LogUtil.d("------创建订单");
        String url = HTTPPOSTURL + "goods/order.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order", order);
        LogUtil.d("------post2");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取订单信息
     *
     * @param mIbpi
     * @param order
     */
    public static void getSuppOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    String order) {
        String url = HTTPPOSTURL + "goods/check.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取订单信息详情
     *
     * @param mIbpi
     * @param order
     */
    public static void getSuppOrderDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                          String order) {
        String url = HTTPPOSTURL + "order/detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order);
        post(mParamMap, mIbpi, url);
    }

    public static void getChipOrderDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                          String order_id) {
        LogUtil.d("获取众筹订单信息详情");
        String url = HTTPPOSTURL + "raise/order-detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order_id);
//		Log.e("renk", url);
//		Log.e("renk", mParamMap.toString());
        post(mParamMap, mIbpi, url);

    }

    /**
     * 获取联盟订单详情
     * //	 * @param mIbpi
     * //	 * @param order_id
     */
    public static void getAlliOrderDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                          String out_trade_no) {
        LogUtil.d("获取联盟订单信息详情");
        String url = HTTPPOSTURL + "nearby/order-detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("out_trade_no", out_trade_no);
        post(mParamMap, mIbpi, url);

    }

    /**
     * 获取众筹订单信息
     *
     * @param mIbpi
     * @param order
     */
    public static void getChipsOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String order) {
        String url = HTTPPOSTURL + "raise/show-pay.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order);
        post(mParamMap, mIbpi, url);
    }

    public static void submitChipsOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String order, String real_pay, String ticket_id, String zero_money) {
        String url = HTTPPOSTURL + "raise/pay.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order);
        mParamMap.put("real_pay", CommonUtils.str2Bi(real_pay));
        mParamMap.put("ticket_id", ticket_id);
        mParamMap.put("zero_money", CommonUtils.str2Bi(zero_money));
        post(mParamMap, mIbpi, url);
    }

    /**
     * 众筹微信支付
     *
     * @param mIbpi
     * @param out_trade_no
     * @param real_pay
     * @param ticket_id
     * @param zero_money
     * @param body
     */
    public static void submitChipsWPayOrder(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String out_trade_no,
            String real_pay, String ticket_id, String zero_money, String body) {
        String url = HTTPPOSTURL + "wx/raise-pay.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("ticket_id", ticket_id);
        mParamMap.put("real_pay", CommonUtils.str2Bi(real_pay));
        mParamMap.put("out_trade_no", out_trade_no);
        mParamMap.put("zero_money", CommonUtils.str2Bi(zero_money));
        mParamMap.put("body", body);
        post(mParamMap, mIbpi, url);
    }

    /**
     * @param mIbpi
     * @param out_trade_no
     * @param real_pay
     * @param ticket_id
     * @param zero_money
     * @param body
     */
    public static void submitBillWPayOrder(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String order_id,
            String out_trade_no, String real_pay, String ticket_id,
            String zero_money, String body) {
        String url = HTTPPOSTURL + "wx/fee-pay.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", order_id);
        mParamMap.put("ticket_id", ticket_id);
        mParamMap.put("real_pay", CommonUtils.str2Bi(real_pay));
        mParamMap.put("out_trade_no", out_trade_no);
        mParamMap.put("zero_money", CommonUtils.str2Bi(zero_money));
        mParamMap.put("body", body);
        post(mParamMap, mIbpi, url);
    }

    public static void submitSuppWPayOrder(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String out_trade_no) {
        String url = HTTPPOSTURL + "wx/goods-pay.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_id", out_trade_no);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取小区城市信息
     *
     * @param mIbpi
     * @param id
     * @param vid
     */
    public static void GetCity(BpiHttpHandler.IBpiHttpHandler mIbpi, String id,
                               String vid) {
        String url = HTTPPOSTURL + "village/change.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("vid", vid);
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取城市
     **/
    public static void GetCityFirst(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    String city_name, String lat, String lng) {
        String url = HTTPPOSTURL + "village/area.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("city_name", city_name);
        mParamMap.put("lng", lng);
        mParamMap.put("lat", lat);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取城市
     **/
    public static void IsSelectVillage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String user_id, int vid) {
        String url = HTTPPOSTURL + "village/sto.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", user_id);
        mParamMap.put("vid", vid + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取租房列表
     **/
    public static void GetTenementList(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String vid) {
        String url = HTTPPOSTURL + "renting/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("vid", vid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取租房详情
     **/
    public static void GetTenementDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                         String id) {
        String url = HTTPPOSTURL + "renting/xq.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取区县
     **/
    public static void GetCouty(BpiHttpHandler.IBpiHttpHandler mIbpi, int code) {
        String url = HTTPPOSTURL + "village/town.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("city_code", code + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 请求可用的优惠券,零钱接口
     **/
    public static void getCoupon(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                 String uid, String fid) {
        String url = HTTPPOSTURL + "fee/payrequest.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        mParamMap.put("fid", fid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 优惠券列表接口 (适用于个人中心,缴费时,购买直供商品时,购买众筹商品时 使用) 优惠券类型(1=>缴费类2=>直供商品,3=>众筹商品)
     **/
    public static void getTicketList(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String use_to, String total_price) {
        String url = HTTPPOSTURL + "ticket/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(use_to))
            mParamMap.put("use_to", use_to);
        if (!TextUtils.isEmpty(total_price))
            mParamMap.put("total_price", CommonUtils.str2Bi(total_price));
        post(mParamMap, mIbpi, url);
    }

    /**
     * 请求可用的优惠券,零钱接口
     **/
    public static void CreateOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String real_pay, String fid, String money, String ticket_id) {
        String url = HTTPPOSTURL + "fee/order.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("real_pay", CommonUtils.str2Bi(real_pay));
        mParamMap.put("fid", fid);
        mParamMap.put("money", CommonUtils.str2Bi(money));
        mParamMap.put("ticket_id", ticket_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 缴费账单 接口
     **/
    public static void getBill(BpiHttpHandler.IBpiHttpHandler mIbpi,
                               String uid, String type, String fid) {
        String url = HTTPPOSTURL + "fee/bill.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        mParamMap.put("type", type);
        if (!TextUtils.isEmpty(fid))
            mParamMap.put("fid", fid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取购物车列表接口
     **/
    public static void getGdCarList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "goods/cart-index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 购物车删除商品
     *
     * @param mIbpi
     * @param cart_ids
     */
    public static void deleteGdCarList(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String cart_ids) {
        String url = HTTPPOSTURL + "goods/cart-remove.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("cart_ids", cart_ids);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取用户收货地址列表
     **/
    public static void getAddressList(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String uid) {
        String url = HTTPPOSTURL + "api/addresses.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 加载修改收货地址界面时需要传递的数据接口
     **/
    public static void getAddressDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String uid) {
        String url = HTTPPOSTURL + "api/show-update-address.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", uid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 加载新增收货地址界面时需传递的城市数据接口
     **/
    public static void getAddressCity(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "api/show-create-address.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 加载新增收货地址界面时需传递的城市数据接口
     **/
    public static void getAuthInfo(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "approve/rove.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", HighCommunityApplication.mUserInfo.getId() + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 新增收货地址界面根据城市得到区县数据接口
     **/
    public static void getAddressDistrist(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                          String city_code) {
        String url = HTTPPOSTURL + "api/get-districts-by-city.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("city_code", city_code);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 新增收货地址界面根据区县得到下属小区数据及模糊搜索小区接口
     **/
    public static void getVillagesByDistrict(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String district_code,
            String lng, String lat, String keyword) {
        String url = HTTPPOSTURL + "api/get-villages-by-district.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("district_code", district_code);
        mParamMap.put("lng", lng);
        mParamMap.put("lat", lat);
        if (!TextUtils.isEmpty(keyword))
            mParamMap.put("keyword", keyword);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 搜索小区
     *
     * @param mIbpi
     * @param cityCode
     * @param keyword
     */
    public static void searchVillages(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String cityCode, String keyword) {
        String url = HTTPPOSTURL + "village/village.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("vill", keyword);
        mParamMap.put("city_code", cityCode);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 搜索群組
     *
     * @param mIbpi
     * @param keyword
     */
    public static void searchGroup(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String keyword) {
        String url = HTTPPOSTURL + "group/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("keyword", keyword);
        mParamMap.put("type", 3 + "");
        mParamMap.put("uid", HighCommunityApplication.mUserInfo.getId() + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 新增收货地址接口
     **/
    public static void CreateAddress(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String real_name, String tel, String city_code,
                                     String district_code, String vid, String building, String unit,
                                     String doorNum, String isDefault, String uid) {
        String url = HTTPPOSTURL + "api/create-address.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("real_name", real_name);
        mParamMap.put("tel", tel);
        mParamMap.put("city_code", city_code);
        mParamMap.put("district_code", district_code);
        mParamMap.put("vid", vid);
        mParamMap.put("building", building);
        mParamMap.put("unit", unit);
        mParamMap.put("doorNum", doorNum);
        mParamMap.put("isDefault", isDefault);
        mParamMap.put("uid", uid);
        LogUtil.d("~~~~新增收货地址传递的参数：" + mParamMap.toString());


        post(mParamMap, mIbpi, url);
    }

    /**
     * 修改收货地址接口
     **/
    public static void ModifyAddress(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String id, String real_name, String tel, String city_code,
                                     String district_code, String vid, String building, String unit,
                                     String doorNum, String isDefault, String uid) {
        String url = HTTPPOSTURL + "api/update-address.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("real_name", real_name);
        mParamMap.put("tel", tel);
        mParamMap.put("id", id);
        mParamMap.put("city_code", city_code);
        mParamMap.put("district_code", district_code);
        mParamMap.put("vid", vid);
        mParamMap.put("building", building);
        mParamMap.put("unit", unit);
        mParamMap.put("doorNum", doorNum);
        mParamMap.put("isDefault", isDefault);
        mParamMap.put("uid", uid);

        post(mParamMap, mIbpi, url);
    }

    /**
     * 删除地址
     **/
    public static void DeleteAddress(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String aid) {
        String url = HTTPPOSTURL + "api/delete-address.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("aid", aid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取小区列表
     **/
    public static void GetVallage(BpiHttpHandler.IBpiHttpHandler mIbpi, int code) {
        String url = HTTPPOSTURL + "village/village.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("city_code", code + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 活动是否操作接口（收藏，举报，删除）
     **/
    public static void GetActivityOpreate(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                          String user_id, String aid) {
        String url = HTTPPOSTURL + "activity/operate.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", user_id);
        mParamMap.put("aid", aid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 删除活动
     **/
    public static void DeleteAct(BpiHttpHandler.IBpiHttpHandler mIbpi, String id) {
        String url = HTTPPOSTURL + "activity/delete.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取群组
     **/
    public static void GetGroup(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                int userId, int type, String keyword) {
        String url = HTTPPOSTURL + "group/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (userId != 0)
            mParamMap.put("uid", userId + "");
        mParamMap.put("type", type + "");
        if (TextUtils.isEmpty(keyword))
            mParamMap.put("keyword", keyword);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 创建群组
     **/
    public static void CreateGroup(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   int userId, String name, String intro, String image) {
        String url = HTTPPOSTURL + "group/create.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("uid", userId + "");
        mParamMap.put("name", name);
        mParamMap.put("intro", intro);
        try {
            mParamMap.put("image", new File(image));
        } catch (FileNotFoundException e) {
        }
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url
                + "   ling params:" + mParamMap.toString());
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 关注群组
     **/
    public static void AttentionGroup(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String uid, String gid) {
        String url = HTTPPOSTURL + "group/attention.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        mParamMap.put("gid", gid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 取消关注群组
     **/
    public static void CancelAttention(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String uid, String gid) {
        String url = HTTPPOSTURL + "group/cancel.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        mParamMap.put("gid", gid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取帖子详情
     **/
    public static void GetMessageDetails(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                         int id) {
        String url = HTTPPOSTURL + "message/details.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 帖子评论和回复
     **/
    public static void CommentMessage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String mid, String from_id, String to_id, String parentId,
                                      String reply_content) {
        String url = HTTPPOSTURL + "message/comments.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("mid", mid);
        mParamMap.put("from_id", from_id);
        if (!TextUtils.isEmpty(to_id))
            mParamMap.put("to_id", to_id);
        if (!TextUtils.isEmpty(parentId))
            mParamMap.put("parentId", parentId);
        mParamMap.put("reply_content", reply_content);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 帖子收藏
     **/
    public static void Favorite(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                String Userid, String mid) {
        String url = HTTPPOSTURL + "favorite/favorite.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(Userid))
            mParamMap.put("user_id", Userid);
        if (!TextUtils.isEmpty(mid))
            mParamMap.put("mid", mid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 活动收藏
     **/
    public static void FavoriteActivity(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String Userid, String mid) {
        String url = HTTPPOSTURL + "favorite/favorite.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(Userid))
            mParamMap.put("user_id", Userid);
        if (!TextUtils.isEmpty(mid))
            mParamMap.put("aid", mid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 帖子举报
     **/
    public static void Report(BpiHttpHandler.IBpiHttpHandler mIbpi,
                              String Userid, String mid, String type) {
        String url = HTTPPOSTURL + "report/report.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", Userid);
        mParamMap.put("mid", mid);
        mParamMap.put("type", type);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 帖子点赞
     **/
    public static void AssistMessage(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String Userid, String mid) {
        String url = HTTPPOSTURL + "message/praise.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", Userid);
        mParamMap.put("id", mid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 信息流页面点击帖子操作时,判断是否已收藏,是否已举报
     **/
    public static void GetPoerate(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                  String Userid, String mid) {
        String url = HTTPPOSTURL + "message/operate.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(Userid))
            mParamMap.put("user_id", Userid);
        mParamMap.put("mid", mid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 取消帖子收藏
     **/
    public static void CancelFavorite(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String Userid, String mid) {
        String url = HTTPPOSTURL + "favorite/cancel.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(Userid))
            mParamMap.put("user_id", Userid);
        if (!TextUtils.isEmpty(mid))
            mParamMap.put("mid", mid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 取消活动收藏
     **/
    public static void CancelActivityCollection(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String Userid, String mid) {
        String url = HTTPPOSTURL + "favorite/cancel.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(Userid))
            mParamMap.put("user_id", Userid);
        if (!TextUtils.isEmpty(mid))
            mParamMap.put("aid", mid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 群组详情接口
     **/
    public static void GroupDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String uid, String gid) {
        String url = HTTPPOSTURL + "group/detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(uid))
            mParamMap.put("uid", uid);
        mParamMap.put("gid", gid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 物业公告/政务公告 列表
     **/
    public static void GetNotice(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                 String type, String vid, String page) {
        String url = HTTPPOSTURL + "notice/check-notices.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("type", type);
        mParamMap.put("vid", vid);
        mParamMap.put("page", page);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取与我相关
     *
     * @param mIbp
     */
    public static void GetRelatedMsg(BpiHttpHandler.IBpiHttpHandler mIbp) {
        String url = HTTPPOSTURL + "message/about-me.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbp, url);
    }
    /**
     * 获取系统消息
     *
     * @param mIbp
     */
    public static void GetSystemMsg(BpiHttpHandler.IBpiHttpHandler mIbp) {
        String url = HTTPPOSTURL + "personal/system.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbp, url);
    }


    /**
     * 解析系统消息
     *
     * @param result
     * @return
     */
    public static List<SystemMessageBean.SystemMsgDataEntity> ResolveSystemMsgDataEntity(String result) {
        List<SystemMessageBean.SystemMsgDataEntity> mList = new ArrayList<SystemMessageBean.SystemMsgDataEntity>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                SystemMessageBean.SystemMsgDataEntity mBean = gson.fromJson(mArray.getString(i),
                        SystemMessageBean.SystemMsgDataEntity.class);
                mList.add(mBean);
            }
            return mList;
        } catch (JSONException e) {
            return null;
        }
    }
    /**
     * 物业/政务 公告详情
     **/
    public static void GetNoticeDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String id) {
        String url = HTTPPOSTURL + "notice/detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 手艺人列表
     **/
    public static void GetCarfsList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "skill/skills.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 附近商家列表
     **/
    public static void GetNearbyShops(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String code, int page) {
        String url = HTTPPOSTURL + "nearby/merchants.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("code", code);
        if (page > 1) {
            mParamMap.put("page", "" + page);
        } else {
            mParamMap.put("page", "1");
        }
        post(mParamMap, mIbpi, url);
    }

    /**
     * 附近商家列表详情
     **/
    public static void GetMerchantShops(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String id) {
        String url = HTTPPOSTURL + "nearby/goods-list.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 单个商品详情
     **/
    public static void GetGoodDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String id) {
        String url = HTTPPOSTURL + "nearby/goods-detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 附近商家详情
     **/
    public static void GetMerchantDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                         String id) {
        String url = HTTPPOSTURL + "nearby/merchant-detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 自治大厅初始化
     **/
    public static void InitAutoAct(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "yinit/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析自治大厅初始化数据
     *
     * @param result
     * @return
     */
    public static Auto_InitBean.Auto_Init_DataEntity ResolveDataEntity(String result) {
        return gson.fromJson(result, Auto_InitBean.Auto_Init_DataEntity.class);
    }

    /**
     * 自治大厅公告列表
     **/
    public static void AutoNoticeList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "ynotice/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析自治大厅公告列表
     *
     * @param result
     * @return
     */
    public static List<Auto_NoticeListBean.NoticeListDataEntity> ResolveAutoNoticeListEntity(String result) {
        List<Auto_NoticeListBean.NoticeListDataEntity> mlist = new ArrayList<Auto_NoticeListBean.NoticeListDataEntity>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                Auto_NoticeListBean.NoticeListDataEntity mBean = gson.fromJson(mArray.getString(i),
                        Auto_NoticeListBean.NoticeListDataEntity.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 自治大厅 选举列表
     *
     * @param mIbpi
     * @param type
     */
    public static void GetAutoVoteList(BpiHttpHandler.IBpiHttpHandler mIbpi, String type) {
        String url = HTTPPOSTURL + "yvote/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("type", type);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析自治大厅 选举列表
     *
     * @param result
     * @return
     */
    public static List<Auto_VoteList_Vote.VoteVVDataEntity> ResolveVoteVVDataEntity(String result) {
        List<Auto_VoteList_Vote.VoteVVDataEntity> mlist = new ArrayList<Auto_VoteList_Vote.VoteVVDataEntity>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                Auto_VoteList_Vote.VoteVVDataEntity mBean = gson.fromJson(mArray.getString(i),
                        Auto_VoteList_Vote.VoteVVDataEntity.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }


    /**
     * 自治大厅名单
     **/
    public static void AutoNamelistList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "ymember/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析自治大厅名单
     *
     * @param result
     * @return
     */
    public static Auto_NameListBean.NameListDataEntity ResolveAutoNamelistListEntity(String result) {
        return gson.fromJson(result, Auto_NameListBean.NameListDataEntity.class);
    }

    /**
     * 获取自治大厅提案列表
     * @param mIbpi
     */
    public static void GetAutoMotionList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "ysuggest/index.html";
        HashMap<String, String> maps = new HashMap<String, String>();
        if (HighCommunityApplication.mUserInfo.getId()!=-1)
            LogUtil.d("------User token------" + HighCommunityApplication.mUserInfo.getToken());
        maps.put("id", HighCommunityApplication.mUserInfo.getId()+"");
        post(maps, mIbpi, url);
    }
    /**
     * 解析自治大厅提案列表
     *
     * @param result
     * @return
     */
    public static List<Auto_MotionBean.MotionDataEntity> ResolveMotionDataEntity(String result) {
        List<Auto_MotionBean.MotionDataEntity> mlist = new ArrayList<Auto_MotionBean.MotionDataEntity>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                Auto_MotionBean.MotionDataEntity mBean = gson.fromJson(mArray.getString(i),
                        Auto_MotionBean.MotionDataEntity.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 获取自治大厅支持提案
     * @param mIbpi
     */
    public static void SupportMotion(BpiHttpHandler.IBpiHttpHandler mIbpi,String id) {
        String url = HTTPPOSTURL + "ysuggest/suggest.html";
        HashMap<String, String> maps = new HashMap<String, String>();
        if (HighCommunityApplication.mUserInfo.getId()!=-1)
            LogUtil.d("------User token------" + HighCommunityApplication.mUserInfo.getToken());
        maps.put("uid", HighCommunityApplication.mUserInfo.getId()+"");
        maps.put("id", id);


        post(maps, mIbpi, url);
    }


    /**
     * 自治大厅 监督列表
     *
     * @param mIbpi
     * @param owner_id
     */
    public static void GetAutoSuperviseList(BpiHttpHandler.IBpiHttpHandler mIbpi, int owner_id) {
        String url = HTTPPOSTURL + "ywatch/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", owner_id+"");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析自治大厅 监督列表
     *
     * @param result
     * @return
     */
    public static List<List<Auto_SuperViseBean.SuperViseDataEntity>> ResolveSuperViseDataEntity(String result) {
        List<List<Auto_SuperViseBean.SuperViseDataEntity>> mlist = new ArrayList<List<Auto_SuperViseBean.SuperViseDataEntity>>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                JSONArray mArray2 = new JSONArray(mArray.get(i).toString());

                Log.d(Tag,"mArray2: "+mArray2.toString());
                Log.d(Tag,"mArray2.lenght: "+mArray2.length());

                List<Auto_SuperViseBean.SuperViseDataEntity> mList_Inner=new ArrayList<Auto_SuperViseBean.SuperViseDataEntity>();
//                List<Auto_SuperViseBean.SuperViseDataEntity> mlistOuterbean=gson.fromJson(mArray2.getString(i),T);
                for (int j = 0; j <mArray2.length() ; j++) {

                    Auto_SuperViseBean.SuperViseDataEntity mBean = gson.fromJson(mArray2.getString(j),
                            Auto_SuperViseBean.SuperViseDataEntity.class);
                    mList_Inner.add(mBean);
                }
                mlist.add(mList_Inner);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 自治大厅   汇报详情
     * @param mIbpi
     * @param id  监督id
     */
    public static void GetReportDetail(BpiHttpHandler.IBpiHttpHandler mIbpi, String id) {
        String url = HTTPPOSTURL + "ywatch/repdetail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }
    /**
     * 解析自治大厅 汇报详情
     *
     * @param result
     * @return
     */
    public static Auto_ReportDetailBean.ReportDetailDataEntity ResolveReportDetailDataEntity(String result) {
        return gson.fromJson(result, Auto_ReportDetailBean.ReportDetailDataEntity.class);
    }


    /**
     * 自治大厅   问询详情
     * @param mIbpi
     * @param id  监督id
     */
    public static void GetInquiryDetail(BpiHttpHandler.IBpiHttpHandler mIbpi, String id) {
        String url = HTTPPOSTURL + "ywatch/condetail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }
    /**
     * 解析自治大厅 问询详情
     * @param result
     * @return
     */
    public static Auto_InquiryDetailBean.InquiryDetailDataEntity ResolveInquiryDetailDataEntity(String result) {
        return gson.fromJson(result, Auto_InquiryDetailBean.InquiryDetailDataEntity.class);
    }


    /**
     * 自治大厅    汇报详情-提交评论
     * @param mIbpi
     * @param watch_id  监督id
     * @param from_id  评论人id
     * @param to_id   被评论人id
     * @param parentId  评论id  对监督评论传0，对评论回复传id
     * @param reply_content  内容
     */
    public static void CommentReportDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String from_id, String to_id,  String watch_id, String parentId,
                                      String reply_content) {
        String url = HTTPPOSTURL + "ywatch/comment.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("from_id", from_id);
        mParamMap.put("watch_id", watch_id);
        if (!TextUtils.isEmpty(to_id))
            mParamMap.put("to_id", to_id);
        if (!TextUtils.isEmpty(parentId))
            mParamMap.put("parent_id", parentId);
        mParamMap.put("reply_content", reply_content);
        Log.d(Tag,"汇报详情-提交评论:参数--->"+url+",from_id"+from_id+",to_id"+to_id+",watch_id"+watch_id+",parent_id"+parentId+",reply_content"+reply_content);
        post(mParamMap, mIbpi, url);
    }


    /**
     * 自治大厅-监督页面-创建汇报
     * @param mIbpi
     * @param uid
     * @param title
     * @param content
     */
    public static void AutoCreatReport(BpiHttpHandler.IBpiHttpHandler mIbpi, String uid,String title,String content) {
        String url = HTTPPOSTURL + "ywatch/report.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        mParamMap.put("title", title);
        mParamMap.put("content", content);
        post(mParamMap, mIbpi, url);
    }
    /** 自治大厅-监督页面-创建询问
     * @param mIbpi
     * @param uid
     * @param content
     */
    public static void AutoCreatInquiry(BpiHttpHandler.IBpiHttpHandler mIbpi, String uid,String content) {
        String url = HTTPPOSTURL + "ywatch/consult.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        mParamMap.put("content", content);
        post(mParamMap, mIbpi, url);
    }


    /**
     * 自治大厅   提案详情
     * @param mIbpi
     * @param id  用户id
     */
    public static void GetMotionDetail(BpiHttpHandler.IBpiHttpHandler mIbpi, String gid,String id) {
        String url = HTTPPOSTURL + "ysuggest/detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("gid", gid);
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }
    /**
     * 解析自治大厅 提案详情
     *
     * @param result
     * @return
     */
    public static Auto_MotionDetailBean.MotionDetailDataEntity ResolveMotionDetailData(String result) {
        List<Auto_MotionDetailBean.MotionDetailDataEntity>  mlist = new ArrayList<Auto_MotionDetailBean.MotionDetailDataEntity>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                Auto_MotionDetailBean.MotionDetailDataEntity mBean = gson.fromJson(mArray.getString(i),
                        Auto_MotionDetailBean.MotionDetailDataEntity.class);
                mlist.add(mBean);
            }
            return mlist.get(0);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 自治大厅   问卷调查详情
     * @param mIbpi
     * @param id  投票id
     */
    public static void GetQuestionDetail(BpiHttpHandler.IBpiHttpHandler mIbpi, String id) {
        String url = HTTPPOSTURL + "yvote/detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }
    /**
     * 解析自治大厅 问卷调查详情
     *
     * @param result
     * @return
     */
    public static Auto_QuestionDeatailBean.QuestionDeatailDataEntity ResolveQuestionDeatailDataEntity(String result) {
        return gson.fromJson(result, Auto_QuestionDeatailBean.QuestionDeatailDataEntity.class);

    }

    /**
     * 自治大厅   问卷调查详情-获取答案选中数组
     * @param mIbpi
     * @param id  投票id
     */
    public static void GetQuestionAnswerArray(BpiHttpHandler.IBpiHttpHandler mIbpi, String id) {
        String url = HTTPPOSTURL + "yvote/view.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("vote_id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析问卷调查详情-获取答案选中数组
     */
    public static AutoDetail_QuestionVotedBean.QuestionVotedDataEntity ResolveQuestionVotedDataEntity(String result) {
        return gson.fromJson(result, AutoDetail_QuestionVotedBean.QuestionVotedDataEntity.class);
    }


    /**
     * 自治大厅-提交投票数据
     **/
    public static void commitAnswers(BpiHttpHandler.IBpiHttpHandler mIbpi, String vote_id,String title) {
        String url = HTTPPOSTURL + "yvote/submit.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("vote_id", vote_id);
        mParamMap.put("title", title);
        post(mParamMap, mIbpi, url);
    }


    /**
     * 自治大厅选举投票结果得票率
     **/
    public static void getVotedData(BpiHttpHandler.IBpiHttpHandler mIbpi,String vote_id) {
        String url = HTTPPOSTURL + "yvote/result.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("vote_id",vote_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析自治选举投票结果得票率
     *
     * @param result
     * @return
     */
    public static List<Auto_VoteResultBean.VoteResultDataEntity> ResolveVoteResultDataEntity(String result) {
        List<Auto_VoteResultBean.VoteResultDataEntity> mlist = new ArrayList<Auto_VoteResultBean.VoteResultDataEntity>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                Auto_VoteResultBean.VoteResultDataEntity mBean = gson.fromJson(mArray.getString(i),
                        Auto_VoteResultBean.VoteResultDataEntity.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }




//    public static void CommentReportDetail2(BpiHttpHandler.IBpiHttpHandler mIbpi,
//                                           String from_id, String to_id,  String watch_id, String parentId,
//                                           String reply_content) {
//        String url = HTTPPOSTURL + "ywatch/comment.html";
//
//        HashMap<String, Object> mParamMap = new HashMap<String, Object>();
////        HashMap<String, String> mParamMap = getBaseParamMap();
//        mParamMap.put("from_id", from_id);
//        mParamMap.put("watch_id", watch_id);
//        if (!TextUtils.isEmpty(to_id))
//            mParamMap.put("to_id", to_id);
//        if (!TextUtils.isEmpty(parentId))
//            mParamMap.put("parentId", parentId);
//        mParamMap.put("reply_content", reply_content);
//        Log.d(Tag,"汇报详情-提交评论:参数--->"+url+",from_id"+from_id+",to_id"+to_id+",watch_id"+watch_id+",parentId"+parentId+",reply_content"+reply_content);
//        post(mParamMap, mIbpi, url);
//    }



    /**
     * 自治大厅-业主认证-获取验证码
     **/
    public static void Auto_Send(BpiHttpHandler.IBpiHttpHandler mIbpi, String tel) {
        String url = HTTPPOSTURL + "yinit/captcha.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
//		mParamMap.put("tel", Base64(tel));
        mParamMap.put("tel", tel);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 自治大厅-提交数据
     **/
//	public static void Auto_Commit(BpiHttpHandler.IBpiHttpHandler mIbpi,
//									 String name, String address, String tel, String title,
//									 String content, String price, String head_pic, String IDCard_A,
//									 String IDCard_B, String IDCard_hand, String certificate_cover,
//									 String certificate_inside) {
    public static void Auto_Commit(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String name, String village_id, String building_id, String unit_id,
                                   String door_id, String tel, String captcha, String IDCard_A,
                                   String IDCard_B, String house_certificate) {
        String url = HTTPPOSTURL + "yinit/submit.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("name", name);
        mParamMap.put("village_id", village_id);
        mParamMap.put("building_id", building_id);
        mParamMap.put("unit_id", unit_id);
        mParamMap.put("door_id", door_id);
        mParamMap.put("tel", tel);
        mParamMap.put("captcha", captcha);
        try {
            mParamMap.put("IDCard", ImageUtil.getImage(IDCard_A));
            mParamMap.put("IDCard_F", ImageUtil.getImage(IDCard_B));
            mParamMap.put("house_certificate", ImageUtil.getImage(house_certificate));
//			if (!TextUtils.isEmpty(certificate_cover))
//				mParamMap.put("certificate_cover",
//						ImageUtil.getImage(certificate_cover));
//			if (!TextUtils.isEmpty(certificate_inside))
//				mParamMap.put("certificate_inside",
//						ImageUtil.getImage(certificate_inside));
        } catch (FileNotFoundException e) {

        }
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url
                + "   ling params:" + mParamMap.toString());
        Log.d(Tag,"上传的认证信息："+mParamMap.toString());
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 自治大厅-获取单元数据(根据楼栋)
     **/
    public static void Auto_GetUnit(BpiHttpHandler.IBpiHttpHandler mIbpi, String building_id) {
        String url = HTTPPOSTURL + "yinit/get-unit.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("building_id", building_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析自治大厅单元数据
     *
     * @param result
     * @return
     */
    public static List<Auto_UnitBean.UnitDataEntity> ResolveUnitDataEntity(String result) {
        List<Auto_UnitBean.UnitDataEntity> mlist = new ArrayList<Auto_UnitBean.UnitDataEntity>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                Auto_UnitBean.UnitDataEntity mBean = gson.fromJson(mArray.getString(i),
                        Auto_UnitBean.UnitDataEntity.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }


    /**
     * 自治大厅-获取单元数据(根据楼栋)
     **/
    public static void Auto_GetDoor(BpiHttpHandler.IBpiHttpHandler mIbpi, String unit_id) {
        String url = HTTPPOSTURL + "yinit/get-door.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("unit_id", unit_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析自治大厅单元数据
     *
     * @param result
     * @return
     */
    public static List<Auto_DoorBean.DoorDataEntity> ResolveDoorDataEntity(String result) {
        List<Auto_DoorBean.DoorDataEntity> mlist = new ArrayList<Auto_DoorBean.DoorDataEntity>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                Auto_DoorBean.DoorDataEntity mBean = gson.fromJson(mArray.getString(i),
                        Auto_DoorBean.DoorDataEntity.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }


    /**
     * 支付请求的接口
     */
    public static void Pay(BpiHttpHandler.IBpiHttpHandler mIbpi,
                           String order_num, int pay_type, String consign, String tel,
                           String address) {
        String url = HTTPPOSTURL + "nearby/submit_order.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_num", order_num);
        mParamMap.put("pay_type", pay_type + "");
        mParamMap.put("consign", consign);
        mParamMap.put("tel", tel);
        mParamMap.put("address", address);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 在支付界面点击支付请求的接口
     *
     * @param mIbpi
     * @param orderNum
     * @param payType
     * @param consign
     * @param tel
     * @param address
     */
    public static void getPayParam(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String orderNum, int payType, String consign, String tel,
                                   String address) {
        String url = HTTPPOSTURL + "nearby/pay.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_num", orderNum);
        mParamMap.put("pay_type", String.valueOf(payType));
        mParamMap.put("consign", consign);
        mParamMap.put("tel", tel);
        mParamMap.put("address", address);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 商家联盟支付请求的接口
     */
    public static void doPay(BpiHttpHandler.IBpiHttpHandler mIbpi,
                             String order_num, int pay_type, String consign, String tel,
                             String address) {
        LogUtil.d("商家联盟支付请求的接口");
        String url = HTTPPOSTURL + "nearby/pay.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_num", order_num);
        mParamMap.put("pay_type", pay_type + "");
        mParamMap.put("consign", consign);
        mParamMap.put("tel", tel);
        mParamMap.put("address", address);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 商家联盟订单详情
     */
    public static void getOderDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String out_trade_no) {
        LogUtil.d("------商家联盟订单详情");
        String url = HTTPPOSTURL + "nearby/order-detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        LogUtil.d("------HashMap<String, String> mParamMap：" + mParamMap.toString());


        mParamMap.put("out_trade_no", out_trade_no);
        LogUtil.d("------HashMap<String, String> mParamMap---2：" + mParamMap.toString());
        post(mParamMap, mIbpi, url);
    }

//	public static void getChipOrderDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
//			String order_id) {
//		LogUtil.d("获取众筹订单信息详情");
//		String url = HTTPPOSTURL + "raise/order-detail.html";
//		HashMap<String, String> mParamMap = getBaseParamMap();
//		mParamMap.put("order_id", order_id);
////		Log.e("renk", url);
////		Log.e("renk", mParamMap.toString());
//		post(mParamMap, mIbpi, url);
//
//	}


    /**
     * 附近商家列表详情
     **/
    public static void GetMerchantEvaluation(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String id, int page) {
        String url = HTTPPOSTURL + "nearby/comments.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        mParamMap.put("page", page + "");
        post(mParamMap, mIbpi, url);
    }

    /**
     * 我的手艺人列表
     **/
    public static void GetMyCarfsList(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "skill/my-skills.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        post(mParamMap, mIbpi, url);
    }

    /**
     * 我的手艺人列表
     **/
    public static void DeleteMyCarfsList(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                         String id) {
        String url = HTTPPOSTURL + "skill/delete.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 手艺人详情
     **/
    public static void GetCarftDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String id) {
        String url = HTTPPOSTURL + "skill/detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取用户自定义标签接口
     **/
    public static void Getlabel(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                String user_id, String token) {
        String url = HTTPPOSTURL + "posting/default_tag.html";
        HashMap<String, String> mParamMap = new HashMap<String, String>();
        mParamMap.put("user_id", user_id);
        mParamMap.put("token", token);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 删除用户自定义标签接口
     **/
    public static void Dellabel(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                String tagid) {
        String url = HTTPPOSTURL + "posting/del.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", tagid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 创建标签
     **/
    public static void Addlabel(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                String user_id, String label_name) {
        String url = HTTPPOSTURL + "posting/tag.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", user_id);
        mParamMap.put("label_name", label_name);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 积分接口
     **/
    public static void getWalletScore(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String uid, String count) {
        String url = HTTPPOSTURL + "personal/score.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        mParamMap.put("count", count);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 积分兑换接口
     **/
    public static void ConvertScore(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    String uid, String scores) {
        String url = HTTPPOSTURL + "personal/convert.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        mParamMap.put("scores", scores);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 用户认证小区选择接口
     **/
    public static void personalAuthSelectVill(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String vill) {
        String url = HTTPPOSTURL + "approve/select.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("vill", vill);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 用户认证接口
     **/
    public static void personalAuth(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    String id, String real_name, String tel, String city_code,
                                    String district_code, String village_id, String unit,
                                    String doorplate, String building, String Village_id) {
        String url = HTTPPOSTURL + "approve/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        mParamMap.put("real_name", real_name);
        mParamMap.put("tel", tel);
        mParamMap.put("unit", unit);
        mParamMap.put("doorplate", doorplate);
        mParamMap.put("building", building);
        mParamMap.put("doorplate", doorplate);
        mParamMap.put("city_code", city_code);
        mParamMap.put("district_code", district_code);
        mParamMap.put("village_id", village_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 我的物业账单接口
     **/
    public static void getTenementBillList(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String id, String status) {
        String url = HTTPPOSTURL + "bill/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", id);
        mParamMap.put("status", status);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 侧边栏首页接口
     **/
    public static void getUserCenter(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String uid) {
        String url = HTTPPOSTURL + "personal/user-center.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 活动列表接口
     **/
    public static void GetActivityList(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String user_id) {
        String url = HTTPPOSTURL + "activity/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(user_id))
            mParamMap.put("uid", user_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 获取我收藏的活动列表接口
     **/
    public static void GetMyCollectActivityList(
            BpiHttpHandler.IBpiHttpHandler mIbpi, String user_id) {
        String url = HTTPPOSTURL + "favorite/my-activity.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (!TextUtils.isEmpty(user_id))
            mParamMap.put("user_id", user_id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 参加活动接口
     **/
    public static void JoinActivity(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    String user_id, String aid) {
        String url = HTTPPOSTURL + "activity/join.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", user_id);
        mParamMap.put("aid", aid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 活动详情接口
     **/
    public static void ActivityDetail(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String user_id, String aid) {
        String url = HTTPPOSTURL + "activity/detail.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", user_id);
        mParamMap.put("aid", aid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 评论活动接口
     **/
    public static void CommentActivity(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String user_id, String aid, String Content, String rid) {
        String url = HTTPPOSTURL + "activity/comment.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", user_id);
        mParamMap.put("aid", aid);
        mParamMap.put("content", Content);
        if (!TextUtils.isEmpty(rid))
            mParamMap.put("rid", rid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 创建活动
     **/
    public static void CreateActivity(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                      String uid, String title, String Content, String phone,
                                      String mWeixin, String qq, String address, String start_time,
                                      String end_time, List<String> image) {
        String url = HTTPPOSTURL + "activity/create.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("uid", uid);
        mParamMap.put("title", title);
        mParamMap.put("content", Content);
        mParamMap.put("phone", phone);
        mParamMap.put("weixin", mWeixin);
        mParamMap.put("qq", qq);
        mParamMap.put("address", address);
        mParamMap.put("start_time", start_time);
        mParamMap.put("end_time", end_time);
        try {
            for (int i = 0; i < image.size(); i++) {
                mParamMap.put("image" + "[" + i + "]",
                        ImageUtil.getImage(image.get(i)));
            }
        } catch (Exception e) {

        }
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url
                + "   ling params:" + mParamMap.toString());
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 帖子删除接口
     **/
    public static void DeleteMsg(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                 String mid) {
        String url = HTTPPOSTURL + "message/delete.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("id", mid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 评论点赞接口
     **/
    public static void AssistComment(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String user_id, String parentId) {
        String url = HTTPPOSTURL + "message/comment-praise.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("user_id", user_id);
        mParamMap.put("parentId", parentId);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 第三方服务接口
     **/
    public static void GetThirdService(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "service/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        LogUtil.d("-------------GetThirdService==" + mParamMap.get("token"));

        post(mParamMap, mIbpi, url);
    }

    public static void GetThirdService2(BpiHttpHandler.IBpiHttpHandler mIbpi) {
        String url = HTTPPOSTURL + "service/index.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        LogUtil.d("-------------GetThirdService==" + mParamMap.get("token"));

        post(mParamMap, mIbpi, url);
    }


    /**
     * 个人资料接口
     **/
    public static void getPersonalInfo(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       String uid) {
        String url = HTTPPOSTURL + "personal/information.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("uid", uid);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 个人资料编辑接口
     **/
    public static void EditPersonalInfo(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                        String uid, String nick, String age, String sex, String sign,
                                        String image) {
        String url = HTTPPOSTURL + "personal/info-edit.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("uid", uid);
        mParamMap.put("nick", nick);
        mParamMap.put("age", age);
        mParamMap.put("sex", sex);
        mParamMap.put("sign", sign);
        try {
            if (!TextUtils.isEmpty(image))
                mParamMap.put("image", ImageUtil.getImage(image));
        } catch (FileNotFoundException e) {

        }
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url
                + "   ling params:" + mParamMap.toString());
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 手艺人申请
     **/
    public static void ApplyForCarft(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                     String name, String address, String tel, String title,
                                     String content, String price, String head_pic, String IDCard_A,
                                     String IDCard_B, String IDCard_hand, String certificate_cover,
                                     String certificate_inside) {
        String url = HTTPPOSTURL + "skill/apply.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("name", name);
        mParamMap.put("address", address);
        mParamMap.put("tel", tel);
        mParamMap.put("title", title);
        mParamMap.put("price", price);
        mParamMap.put("content", content);
        try {
            mParamMap.put("head_pic", ImageUtil.getImage(head_pic));
            mParamMap.put("IDCard_A", ImageUtil.getImage(IDCard_A));
            mParamMap.put("IDCard_B", ImageUtil.getImage(IDCard_B));
            mParamMap.put("IDCard_hand", ImageUtil.getImage(IDCard_hand));
            if (!TextUtils.isEmpty(certificate_cover))
                mParamMap.put("certificate_cover",
                        ImageUtil.getImage(certificate_cover));
            if (!TextUtils.isEmpty(certificate_inside))
                mParamMap.put("certificate_inside",
                        ImageUtil.getImage(certificate_inside));
        } catch (FileNotFoundException e) {

        }
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url
                + "   ling params:" + mParamMap.toString());
        Log.d(Tag,"上传的手艺人数据："+mParamMap.toString());
//        BpiHttpClient.getInstance().post(url, mParamMap,
//                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 用户发帖接口
     **/
    public static void PostingMsg(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                  String label_id, String content, String user_id, String vid,
                                  List<String> image, String gid, String token) {
        String url = HTTPPOSTURL + "posting/index.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("label_id", label_id);
        mParamMap.put("user_id", user_id);
        mParamMap.put("vid", vid);
        mParamMap.put("token", token);
        if (!TextUtils.isEmpty(gid)) {
            mParamMap.put("type", "1");
            mParamMap.put("gid", gid);
        } else {
            mParamMap.put("type", "0");
        }
        mParamMap.put("content", content);
        if (image != null && image.size() > 0)
            try {
                for (int i = 0; i < image.size(); i++) {
                    mParamMap.put("image" + "[" + i + "]",
                            ImageUtil.getImage(image.get(i)));
                }
            } catch (Exception e) {

            }
        Debug.verbose(DongConstants.EDUCATIONHTTPTAG, "URL:" + url
                + "   ling params:" + mParamMap.toString());
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 取消订单接口
     */
    public static void CancelOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String order_num) {
        String url = HTTPPOSTURL + "nearby/order-cancel.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("order_num", order_num);
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 确认收货接口
     */
    public static void ConfirmOrder(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    String order_num) {
        String url = HTTPPOSTURL + "nearby/finish.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("order_num", order_num);
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 联盟订单内付款
     */
    public static void GoPay(BpiHttpHandler.IBpiHttpHandler mIbpi,
                             String order_num) {
        LogUtil.d("联盟订单内付款");
        String url = HTTPPOSTURL + "nearby/to-pay.html";
        RequestParams mParamMap = new RequestParams(getBaseParamMap());
        mParamMap.put("order_num", order_num);
        BpiHttpClient.getInstance().post(url, mParamMap,
                BpiHttpHandler.getInstance(mIbpi));
    }

    /**
     * 解析联盟订单内付款
     */
    public static AlliancePayBean ResolveAllianceGoPay(String result) {
        LogUtil.d("解析联盟订单内付款");
        return gson.fromJson(result, AlliancePayBean.class);
    }

    /**
     * 解析login接口数据
     */
    public static UserInfoBean ResolveLogin(String result) {
        return gson.fromJson(result, UserInfoBean.class);
    }

    /**
     * 解析message 信息流接口数据
     */
    public static CommunityListBean ResolveMessage(String result) {
        return gson.fromJson(result, CommunityListBean.class);
    }

    /**
     * 解析我的收藏message 信息流接口数据
     */
    public static List<CommunityBean> ResolveMyCollectMessage(String result) {
        List<CommunityBean> mlist = new ArrayList<CommunityBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                CommunityBean mBean = gson.fromJson(mArray.getString(i),
                        CommunityBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
        // return gson.fromJson(result, CommunityListBean.class);
    }

    /**
     * 解析message 信息流接口数据
     */
    public static BillBean ResolveBill(String result) {
        return gson.fromJson(result, BillBean.class);
    }

    /**
     * 解析label自定义标签数据
     */
    public static List<LabelBean> ResolveLabels(String result) {
        List<LabelBean> mlist = new ArrayList<LabelBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                LabelBean mBean = gson.fromJson(mArray.getString(i),
                        LabelBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析我的众筹数据
     */
    public static List<ChipsOrderBean> ResolveChipsOrderList(String result) {
        List<ChipsOrderBean> mlist = new ArrayList<ChipsOrderBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                ChipsOrderBean mBean = gson.fromJson(mArray.getString(i),
                        ChipsOrderBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析我的物业账单数据
     */
    public static List<BillSimpleBean> ResolveTenementBillList(String result) {
        List<BillSimpleBean> mlist = new ArrayList<BillSimpleBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                BillSimpleBean mBean = gson.fromJson(mArray.getString(i),
                        BillSimpleBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 我的订单解析
     *
     * @param result
     * @return
     */
    public static List<HuiOrderBean> ResolveOrderList(String result) {
        List<HuiOrderBean> mlist = new ArrayList<HuiOrderBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                HuiOrderBean mBean = gson.fromJson(mArray.getString(i),
                        HuiOrderBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析购物车列表
     */
    public static List<GdCarBean> ResolvGdCarList(String result) {
        List<GdCarBean> mlist = new ArrayList<GdCarBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                GdCarBean mBean = gson.fromJson(mArray.getString(i),
                        GdCarBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析地址列表address数据
     */
    public static List<AddressBean> ResolveAddressList(String result) {
        List<AddressBean> mlist = new ArrayList<AddressBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                AddressBean mBean = gson.fromJson(mArray.getString(i),
                        AddressBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    public static ShakeBean ResolveShakeData(String result) {
        return gson.fromJson(result, ShakeBean.class);
    }

    public static List<ShakeUser> ResolveShakeDataList(String result) {
        List<ShakeUser> mlist = new ArrayList<ShakeUser>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                ShakeUser mBean = gson.fromJson(mArray.getString(i),
                        ShakeUser.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析label自定义标签数据
     */
    public static LabelBean ResolveLabel(String result) {
        return gson.fromJson(result, LabelBean.class);
    }

    /**
     * 解析钱包积分Score数据
     */
    public static ScoreBean ResolveScore(String result) {
        return gson.fromJson(result, ScoreBean.class);
    }

    /**
     * 解析钱包所有优惠券数据
     */
    public static List<AllTicketBean> ResolveTicket(String result) {
        List<AllTicketBean> mlist = new ArrayList<AllTicketBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                AllTicketBean mBean = gson.fromJson(mArray.getString(i),
                        AllTicketBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析个人资料数据
     */
    public static PersonalInfoBean ResolvePersonalInfo(String result) {
        return gson.fromJson(result, PersonalInfoBean.class);
    }

    /**
     * 解析侧边栏首页接口数据
     */
    public static UserCenterBean ResolveUserCenter(String result) {
        return gson.fromJson(result, UserCenterBean.class);
    }

    /**
     * 解析修改地址时获取的address数据
     */
    public static AddressModifyBean ResolveAddressModify(String result) {
        return gson.fromJson(result, AddressModifyBean.class);
    }

    /**
     * 联盟订单列表
     */
    public static void GetAllianceList(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                       int status) {
        LogUtil.d("------联盟订单列表");
        String url = HTTPPOSTURL + "nearby/order-list.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        if (status != AllianceOrder.TAB_ALL_ORDER) {
            mParamMap.put("status", String.valueOf(status));
            LogUtil.d("------联盟订单状态------" + String.valueOf(status));
        }
        post(mParamMap, mIbpi, url);
    }

    /**
     * 评价列表
     */
    public static void GetEvaluate(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                   String id) {
        String url = HTTPPOSTURL + "nearby/load-comment.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_num", id);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 评价
     */
    public static void SendEvaluate(BpiHttpHandler.IBpiHttpHandler mIbpi,
                                    String id, JSONObject obj) {
        String url = HTTPPOSTURL + "nearby/submit-comment.html";
        HashMap<String, String> mParamMap = getBaseParamMap();
        mParamMap.put("order_num", id);
        mParamMap.put("comment_info", "[" + obj.toString() + "]");
        Log.e("renk", mParamMap.toString());
        Log.e("renk", url);
        post(mParamMap, mIbpi, url);
    }

    /**
     * 解析联盟订单列表
     */
    public static List<AllianceOrderBean> ResolveAllianceOrderBean(String result) {
        LogUtil.d("解析联盟订单列表");
        List<AllianceOrderBean> list = new ArrayList<AllianceOrderBean>(0);
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                AllianceOrderBean mBean = gson.fromJson(mArray.getString(i),
                        AllianceOrderBean.class);
                list.add(mBean);
            }
            return list;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析区县列表City list信息
     */
    public static List<CitysBean> ResolveCityList(String result) {
        List<CitysBean> mlist = new ArrayList<CitysBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                CitysBean mBean = gson.fromJson(mArray.getString(i),
                        CitysBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析区县列表district list信息
     */
    public static List<DistrictBean> ResolveDistrict(String result) {
        List<DistrictBean> mlist = new ArrayList<DistrictBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                DistrictBean mBean = gson.fromJson(mArray.getString(i),
                        DistrictBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析修改地址时获取区县信息
     */
    public static List<VallageBean> ResolveVillage(String result) {
        List<VallageBean> mlist = new ArrayList<VallageBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                VallageBean mBean = gson.fromJson(mArray.getString(i),
                        VallageBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    public static CouponBean ResolveCoupon(String result) {
        return gson.fromJson(result, CouponBean.class);
    }

    /**
     * 解析认证信息
     */
    public static PersonAuthBean ResolvePersonAuth(String result) {
        return gson.fromJson(result, PersonAuthBean.class);
    }

    /**
     * 解析message 信息流接口数据
     */
    public static ActivityDetailBean ResolveActivityDetail(String result) {
        return gson.fromJson(result, ActivityDetailBean.class);
    }

    /**
     * 解析messageDetails 帖子详情数据
     */
    public static MessageDetailsBean ResolveMessageDetails(String result) {
        return gson.fromJson(result, MessageDetailsBean.class);
    }

    /**
     * 解析ThirdService 第三方服务数据
     */
    public static ThirdServiceBean ResolveThirdService(String result) {
        return gson.fromJson(result, ThirdServiceBean.class);
    }

    /**
     * 解析messageDetails 群组详情数据
     */
    public static GroupDetailsBean ResolveGridDetails(String result) {
        return gson.fromJson(result, GroupDetailsBean.class);
    }

    /**
     * 解析是否已收藏,是否已举报数据
     */
    public static OperateBean ResolveOperateBean(String result) {
        return gson.fromJson(result, OperateBean.class);
    }

    /**
     * 解析缴费订单数据
     */
    public static OrderBean ResolveOrder(String result) {
        return gson.fromJson(result, OrderBean.class);
    }

    public static WpayBean ResolveWpayBean(String result) {
        JSONObject json = null;
        WpayBean bean = new WpayBean();
        try {
            json = new JSONObject(result);
            bean.setAppid(json.optString("appid"));
            bean.setPartnerid(json.optString("partnerid"));
            bean.setPrepayid(json.optString("prepayid"));
            bean.setNoncestr(json.optString("noncestr"));
            bean.setTimestamp(json.optString("timestamp"));
            bean.setPackages(json.optString("package"));
            bean.setSign(json.optString("sign"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * 解析group 群组接口数据
     */
    public static List<GroupBean> ResolveGroup(String result) {
        List<GroupBean> mlist = new ArrayList<GroupBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                GroupBean mBean = gson.fromJson(mArray.getString(i),
                        GroupBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析group 报修记录解析
     */
    public static List<RepairBean> ResolveRepairRecordList(String result) {
        List<RepairBean> mlist = new ArrayList<RepairBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                RepairBean mBean = gson.fromJson(mArray.getString(i),
                        RepairBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析活动列表数据
     */
    public static List<ActiveBean> ResolveActivitylist(String result) {
        List<ActiveBean> mlist = new ArrayList<ActiveBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                ActiveBean mBean = gson.fromJson(mArray.getString(i),
                        ActiveBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 认证信息解析
     *
     * @param result
     * @return
     */
    public static CertifiBean ResolveCertification(String result) {
        return gson.fromJson(result, CertifiBean.class);
    }

    /**
     * 紧急报修解析
     *
     * @param result
     * @return
     */
    public static List<RepairJJBean> ResolveRepaiJJList(String result) {
        RepairJinJBean data = gson.fromJson(result, RepairJinJBean.class);
        List<RepairJJBean> mlist = new ArrayList<RepairJJBean>();
        for (RepairJJBean bean : data.getService()) {
            bean.setSortLetters("物业联系电话");
            bean.setType(1);
            mlist.add(bean);
        }
        for (RepairJJBean bean : data.getProperty()) {
            bean.setSortLetters("报修维护电话");
            bean.setType(2);
            mlist.add(bean);
        }

        return mlist;
    }

    /**
     * 惠生活物品直供解析
     *
     * @param result
     * @return
     */
    public static List<HuiSupportBean> ResolveHuiSupportList(String result) {
        List<HuiSupportBean> mlist = new ArrayList<HuiSupportBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                HuiSupportBean mBean = gson.fromJson(mArray.getString(i),
                        HuiSupportBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 附近商家列表解析
     *
     * @param result
     * @return
     */
    public static List<NearByShopData> NearByShopList(String result) {
        List<NearByShopData> mlist = new ArrayList<NearByShopData>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                NearByShopData mBean = gson.fromJson(mArray.getString(i),
                        NearByShopData.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 惠生活物品直供点击购买解析
     *
     * @param result
     * @return
     */
    public static HSuppGdDefBean ResolveHuiSuppGoodsMsg(String result) {
        return gson.fromJson(result, HSuppGdDefBean.class);
    }

    /**
     * 惠生活-物品直供订单参数解析
     *
     * @param result
     * @return
     */
    public static GoodsOrderBean ResolveHuiSuppOrder(String result) {
        return gson.fromJson(result, GoodsOrderBean.class);
    }

    /**
     * 惠生活-物品直供订单结果解析
     *
     * @param result
     * @return
     */
    public static HuiOrderBean ResolveHuiOrder(String result) {
        return gson.fromJson(result, HuiOrderBean.class);
    }

    /**
     * 我的众筹订单详情
     *
     * @param result
     * @return
     */
    public static ChipOrderDetailBean ResolveChipOrder(String result) {
        return gson.fromJson(result, ChipOrderDetailBean.class);
    }

    /**
     * 我的众筹订单详情
     *
     * @param result
     * @return
     */
    public static ChipOrderDetailBean ResolveGoodsOrder(String result) {
        return gson.fromJson(result, ChipOrderDetailBean.class);
    }

    /**
     * 惠生活-众筹订单参数解析
     *
     * @param result
     * @return
     */
    public static ChipsOrderBean ResolveChipsOrder(String result) {
        return gson.fromJson(result, ChipsOrderBean.class);
    }

    /**
     * 惠生活-众筹订单结果解析
     *
     * @param result
     * @return
     */
    public static ChipsOrderResulBean ResolveChipsOrderResult(String result) {
        return gson.fromJson(result, ChipsOrderResulBean.class);
    }

    /**
     * 惠生活-物业众筹订单参数解析
     *
     * @param result
     * @return
     */
    public static HuiChipsOrderparams ResolveHuiChipsOrder(String result) {
        return gson.fromJson(result, HuiChipsOrderparams.class);
    }

    /**
     * 惠生活众筹列表解析
     *
     * @param result
     * @return
     */
    public static List<ChipsBean> ResolveHuiChipsList(String result) {
        List<ChipsBean> mlist = new ArrayList<ChipsBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                ChipsBean mBean = gson.fromJson(mArray.getString(i),
                        ChipsBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 惠生活众筹详情解析
     *
     * @param result
     * @return
     */
    public static ChipsBean ResolveHuiChipsDetail(String result) {
        return gson.fromJson(result, ChipsBean.class);
    }

    /**
     * 解析group 城市接口数据解析
     */
    public static VallageSelectBean ResolveCity(String result) {
        return gson.fromJson(result, VallageSelectBean.class);
    }

    /**
     * 解析group 租房列表接口数据解析
     */
    public static TenementBean ResolvTenementList(String result) {
        return gson.fromJson(result, TenementBean.class);
    }

    /**
     * 解析group 租房详情接口数据解析
     */
    public static TenementHouseBean ResolvTenementDetail(String result) {
        return gson.fromJson(result, TenementHouseBean.class);
    }

    /**
     * 解析group 小区接口数据解析
     */
    public static List<VallageBean> ResolveVallage(String result) {
        List<VallageBean> mlist = new ArrayList<VallageBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                VallageBean mBean = gson.fromJson(mArray.getString(i),
                        VallageBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析group 区县接口数据解析
     */
    public static List<CountyBean> ResolveCounty(String result) {
        List<CountyBean> mlist = new ArrayList<CountyBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                CountyBean mBean = gson.fromJson(mArray.getString(i),
                        CountyBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析商家产品双列表
     */
    public static List<MerchantShopGoodBean> GetGoodsList(String result) {
        List<MerchantShopGoodBean> mlist = new ArrayList<MerchantShopGoodBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                MerchantShopGoodBean mBean = gson.fromJson(mArray.getString(i),
                        MerchantShopGoodBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析商家评价
     */
    public static List<MerchantEvaluationInfoListBean> GetEvaluationList(
            String result) {
        List<MerchantEvaluationInfoListBean> mlist = new ArrayList<MerchantEvaluationInfoListBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                MerchantEvaluationInfoListBean mBean = gson.fromJson(
                        mArray.getString(i),
                        MerchantEvaluationInfoListBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析公告notice数据
     */
    public static List<NoticeBean> ResolveNotice(String result) {
        List<NoticeBean> mlist = new ArrayList<NoticeBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                NoticeBean mBean = gson.fromJson(mArray.getString(i),
                        NoticeBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析与我相关数据
     */
    public static List<CommunityMsgBean> ResolveRelated(String result) {
        List<CommunityMsgBean> mlist = new ArrayList<CommunityMsgBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                CommunityMsgBean mBean = gson.fromJson(mArray.getString(i),
                        CommunityMsgBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析NoticeDetails 群组详情数据
     */
    public static NoticeDetailsBean ResolveNoticeDetails(String result) {
        return gson.fromJson(result, NoticeDetailsBean.class);
    }

    /**
     * 解析手艺人数据
     */
    public static List<CarftsBean> ResolveCarftsList(String result) {
        List<CarftsBean> mlist = new ArrayList<CarftsBean>();
        try {
            JSONArray mArray = new JSONArray(result);
            for (int i = 0; i < mArray.length(); i++) {
                CarftsBean mBean = gson.fromJson(mArray.getString(i),
                        CarftsBean.class);
                mlist.add(mBean);
            }
            return mlist;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 解析NoticeDetails 群组详情数据
     */
    public static CarftsDetailBean ResolveCarftsDetails(String result) {
        return gson.fromJson(result, CarftsDetailBean.class);
    }

    /**
     * 解析SubmitOrder返回数据，获取订单号
     */
    public static SubmitOrderBean ResolveSubmitOrder(String result) {
        return gson.fromJson(result, SubmitOrderBean.class);
    }

    /**
     * 解析WechatParam返回数据，获取微信支付参数
     *
     * @param result
     * @return
     */
    public static WechatParamBean ResolveWechatParams(String result) {
        return gson.fromJson(result, WechatParamBean.class);
    }

    /**
     * 解析AliParam返回数据，获取支付宝支付参数
     *
     * @param result
     * @return
     */
    public static AliParamBean ResolveAliParams(String result) {
        return gson.fromJson(result, AliParamBean.class);
    }

    /**
     * 获得基本参数
     **/
    private static HashMap<String, String> getBaseParamMap() {
        HashMap<String, String> maps = new HashMap<String, String>();
        if (!TextUtils.isEmpty((HighCommunityApplication.mUserInfo.getToken())))
            LogUtil.d("------User token------" + HighCommunityApplication.mUserInfo.getToken());
        maps.put("token", HighCommunityApplication.mUserInfo.getToken());
        return maps;
    }



    /**
     * post请求
     **/
    private static void post(HashMap<String, String> params,
                             BpiHttpHandler.IBpiHttpHandler imh, String URL) {
        BpiHttpClient.post(URL, params, imh);
    }
//    /**
//     * post请求
//     **/
//    private static void post(HashMap<String, Object> params,
//                             BpiHttpHandler.IBpiHttpHandler imh, String URL) {
//        BpiHttpClient.post(URL, params, imh);
//    }
    /**
     * 请求
     **/
    private static void post(HashMap<String, String> params) {
        BpiHttpClient.post(HTTPPOSTURL, params);

    }

    public static NearbyOrderDetailBean ResolveNearbyOrderDetail(String result) {
        return gson.fromJson(result, NearbyOrderDetailBean.class);
    }

    /**
     * 获取MD5加密后的字段
     */
    private static String MD5(String flag) {
        try {
            return MD5.encryptMD5(flag);
        } catch (Exception e) {
            return null;
        }
    }

    private static String Base64(String params) {
        // "MTg3ODAyMDk3OTQ="
        return Base64.encode(params);
    }


}

