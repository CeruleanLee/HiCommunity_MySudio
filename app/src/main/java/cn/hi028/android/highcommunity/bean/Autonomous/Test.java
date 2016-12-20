package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/20 0020
 */
public class Test {

    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : {"id":"46","order_num":"20161220112847126850","create_time":"1482204527","pay_time":"0","send_time":"0","finish_time":"0","comment_time":"0","status":"待付款","pay_type":"1","old_fee":"11.00","total_fee":"0.01","zero_money":"0.00","ticket_val":"0.00","mark":"微信","consign":"张大山","address":"成都市青羊区万达广场","tel":"13698525225","merchant":"德克士","list":[{"goods_id":"33","pic":"upload/new-goods/cover_pic/cover_pic_201612091720426342.png","name":"按时打算","standard_name":"按时打","goods_price":"11.00","goods_number":"1"}]}
     */

    private boolean success;
    private int code;
    private String msg;
    private DataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 46
         * order_num : 20161220112847126850
         * create_time : 1482204527
         * pay_time : 0
         * send_time : 0
         * finish_time : 0
         * comment_time : 0
         * status : 待付款
         * pay_type : 1
         * old_fee : 11.00
         * total_fee : 0.01
         * zero_money : 0.00
         * ticket_val : 0.00
         * mark : 微信
         * consign : 张大山
         * address : 成都市青羊区万达广场
         * tel : 13698525225
         * merchant : 德克士
         * list : [{"goods_id":"33","pic":"upload/new-goods/cover_pic/cover_pic_201612091720426342.png","name":"按时打算","standard_name":"按时打","goods_price":"11.00","goods_number":"1"}]
         */

        private String id;
        private String order_num;
        private String create_time;
        private String pay_time;
        private String send_time;
        private String finish_time;
        private String comment_time;
        private String status;
        private String pay_type;
        private String old_fee;
        private String total_fee;
        private String zero_money;
        private String ticket_val;
        private String mark;
        private String consign;
        private String address;
        private String tel;
        private String merchant;
        private List<ListEntity> list;

        public void setId(String id) {
            this.id = id;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public void setOld_fee(String old_fee) {
            this.old_fee = old_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public void setZero_money(String zero_money) {
            this.zero_money = zero_money;
        }

        public void setTicket_val(String ticket_val) {
            this.ticket_val = ticket_val;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public void setConsign(String consign) {
            this.consign = consign;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setMerchant(String merchant) {
            this.merchant = merchant;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public String getId() {
            return id;
        }

        public String getOrder_num() {
            return order_num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public String getSend_time() {
            return send_time;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public String getComment_time() {
            return comment_time;
        }

        public String getStatus() {
            return status;
        }

        public String getPay_type() {
            return pay_type;
        }

        public String getOld_fee() {
            return old_fee;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public String getZero_money() {
            return zero_money;
        }

        public String getTicket_val() {
            return ticket_val;
        }

        public String getMark() {
            return mark;
        }

        public String getConsign() {
            return consign;
        }

        public String getAddress() {
            return address;
        }

        public String getTel() {
            return tel;
        }

        public String getMerchant() {
            return merchant;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            /**
             * goods_id : 33
             * pic : upload/new-goods/cover_pic/cover_pic_201612091720426342.png
             * name : 按时打算
             * standard_name : 按时打
             * goods_price : 11.00
             * goods_number : 1
             */

            private String goods_id;
            private String pic;
            private String name;
            private String standard_name;
            private String goods_price;
            private String goods_number;

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setStandard_name(String standard_name) {
                this.standard_name = standard_name;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public void setGoods_number(String goods_number) {
                this.goods_number = goods_number;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public String getPic() {
                return pic;
            }

            public String getName() {
                return name;
            }

            public String getStandard_name() {
                return standard_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public String getGoods_number() {
                return goods_number;
            }
        }
    }
}
