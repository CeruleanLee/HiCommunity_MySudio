package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * @说明：新版订单支付详情页 购物车跳转支付   普通订单支付
 * @作者： Lee_yting
 * @时间：2016/12/17 0019
 */
public class NewSupplyPaydetailBean {


    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : {"total_fee":225,"goods":[{"merchant":"嗨社区","info":[{"cart_id":"296","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"302","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"306","pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","standard":"20个/盒","price":"15.00","num":"1","little_amount":"15.00"}],"total_amount":75},{"merchant":"麦当劳","info":[{"cart_id":"300","pic":"img/logo.jpg","name":"金龙鱼食用油","standard":"10L/桶","price":"90.00","num":"1","little_amount":"90.00"},{"cart_id":"301","pic":"img/logo.jpg","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","standard":"500g/袋","price":"5.00","num":"1","little_amount":"5.00"},{"cart_id":"303","pic":"img/logo.jpg","name":"金龙鱼食用油","standard":"5L/桶","price":"50.00","num":"1","little_amount":"50.00"},{"cart_id":"304","pic":"img/logo.jpg","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","standard":"500g/袋","price":"5.00","num":"1","little_amount":"5.00"}],"total_amount":150}],"zero_money":"170.89","consign":{"id":"10","name":"王","tel":"13526985241","isDefault":"0","address":"成都市金牛区万达广场"}}
     */

    private boolean success;
    private int code;
    private String msg;
    private SupplyPayDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(SupplyPayDataEntity data) {
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

    public SupplyPayDataEntity getData() {
        return data;
    }

    public static class SupplyPayDataEntity {
        /**
         * total_fee : 225
         * goods : [{"merchant":"嗨社区","info":[{"cart_id":"296","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"302","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"306","pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","standard":"20个/盒","price":"15.00","num":"1","little_amount":"15.00"}],"total_amount":75},{"merchant":"麦当劳","info":[{"cart_id":"300","pic":"img/logo.jpg","name":"金龙鱼食用油","standard":"10L/桶","price":"90.00","num":"1","little_amount":"90.00"},{"cart_id":"301","pic":"img/logo.jpg","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","standard":"500g/袋","price":"5.00","num":"1","little_amount":"5.00"},{"cart_id":"303","pic":"img/logo.jpg","name":"金龙鱼食用油","standard":"5L/桶","price":"50.00","num":"1","little_amount":"50.00"},{"cart_id":"304","pic":"img/logo.jpg","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","standard":"500g/袋","price":"5.00","num":"1","little_amount":"5.00"}],"total_amount":150}]
         * zero_money : 170.89
         * consign : {"id":"10","name":"王","tel":"13526985241","isDefault":"0","address":"成都市金牛区万达广场"}
         */

        private float total_fee;
        private float zero_money;
        private SupplyPayConsignEntity consign;
        private List<SupplyPayGoodsEntity> goods;

        @Override
        public String toString() {
            return "SupplyPayDataEntity{" +
                    "total_fee=" + total_fee +
                    ", zero_money='" + zero_money + '\'' +
                    ", consign=" + consign +
                    ", goods=" + goods +
                    '}';
        }

        public void setTotal_fee(float total_fee) {
            this.total_fee = total_fee;
        }

        public void setZero_money(float zero_money) {
            this.zero_money = zero_money;
        }

        public void setConsign(SupplyPayConsignEntity consign) {
            this.consign = consign;
        }

        public void setGoods(List<SupplyPayGoodsEntity> goods) {
            this.goods = goods;
        }

        public float getTotal_fee() {
            return total_fee;
        }

        public float getZero_money() {
            return zero_money;
        }

        public SupplyPayConsignEntity getConsign() {
            return consign;
        }

        public List<SupplyPayGoodsEntity> getGoods() {
            return goods;
        }

        public static class SupplyPayConsignEntity {
            /**
             * id : 10
             * name : 王
             * tel : 13526985241
             * isDefault : 0
             * address : 成都市金牛区万达广场
             */

            private int id;
            private String name;
            private String tel;
            private String isDefault;
            private String address;

            @Override
            public String toString() {
                return "SupplyPayConsignEntity{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", tel='" + tel + '\'' +
                        ", isDefault='" + isDefault + '\'' +
                        ", address='" + address + '\'' +
                        '}';
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public void setIsDefault(String isDefault) {
                this.isDefault = isDefault;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getTel() {
                return tel;
            }

            public String getIsDefault() {
                return isDefault;
            }

            public String getAddress() {
                return address;
            }
        }

        public static class SupplyPayGoodsEntity {
            /**
             * merchant : 嗨社区
             * info : [{"cart_id":"296","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"302","pic":"img/logo.jpg","name":"东北大米","standard":"5公斤/袋","price":"30.00","num":"1","little_amount":"30.00"},{"cart_id":"306","pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","standard":"20个/盒","price":"15.00","num":"1","little_amount":"15.00"}]
             * total_amount : 75
             */

            private String merchant;
            private int total_amount=-1;
            private List<SupplyPayInfoEntity> info;

            @Override
            public String toString() {
                return "SupplyPayGoodsEntity{" +
                        "merchant='" + merchant + '\'' +
                        ", total_amount=" + total_amount +
                        ", info=" + info +
                        '}';
            }

            public void setMerchant(String merchant) {
                this.merchant = merchant;
            }

            public void setTotal_amount(int total_amount) {
                this.total_amount = total_amount;
            }

            public void setInfo(List<SupplyPayInfoEntity> info) {
                this.info = info;
            }

            public String getMerchant() {
                return merchant;
            }

            public int getTotal_amount() {
                return total_amount;
            }

            public List<SupplyPayInfoEntity> getInfo() {
                return info;
            }

            public static class SupplyPayInfoEntity {
                /**
                 * cart_id : 296
                 * pic : img/logo.jpg
                 * name : 东北大米
                 * standard : 5公斤/袋
                 * price : 30.00
                 * num : 1
                 * little_amount : 30.00
                 */

                private String cart_id;
                private String pic;
                private String name;
                private String standard;
                private String price;
                private String num;
                private String little_amount;

                @Override
                public String toString() {
                    return "SupplyPayInfoEntity{" +
                            "cart_id='" + cart_id + '\'' +
                            ", pic='" + pic + '\'' +
                            ", name='" + name + '\'' +
                            ", standard='" + standard + '\'' +
                            ", price='" + price + '\'' +
                            ", num='" + num + '\'' +
                            ", little_amount='" + little_amount + '\'' +
                            '}';
                }

                public void setCart_id(String cart_id) {
                    this.cart_id = cart_id;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setStandard(String standard) {
                    this.standard = standard;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public void setLittle_amount(String little_amount) {
                    this.little_amount = little_amount;
                }

                public String getCart_id() {
                    return cart_id;
                }

                public String getPic() {
                    return pic;
                }

                public String getName() {
                    return name;
                }

                public String getStandard() {
                    return standard;
                }

                public String getPrice() {
                    return price;
                }

                public String getNum() {
                    return num;
                }

                public String getLittle_amount() {
                    return little_amount;
                }
            }
        }
    }
}
