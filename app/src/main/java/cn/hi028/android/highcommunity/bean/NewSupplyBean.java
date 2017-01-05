package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * Created by Lee_yting on 2016/11/28 0028.
 * 说明：
 */
public class NewSupplyBean {


    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : {"category":[{"id":"1","name":"柴米油盐","goods":[{"id":"1","name":"东北大米","label":"热卖","cover_pic":"img/logo.jpg","price":"35.00"},{"id":"2","name":"金龙鱼食用油","label":"新品","cover_pic":"img/logo.jpg","price":"49.00"},{"id":"3","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","label":"促销","cover_pic":"img/logo.jpg","price":"5.00"}]}],"purchase":[{"id":"4","name":"【中华特色馆】西安馆 陕西苹果 红富士 红苹果 苹果 5斤装/箱","cover_pic":"img/logo.jpg","remainTime":"43293","price":"28.00","old_price":"47.00","storage":"100","percent":"25%"},{"id":"5","name":"赣南脐橙优级果5斤装 果径80-85mm","cover_pic":"img/logo.jpg","remainTime":"80043293","price":"15.00","old_price":"25.00","storage":"200","percent":"25%"}],"merchant":[{"id":"11","logo":"upload/head-pic/php_avatar1_20160720171340_716_F0QFXMOL.jpg"},{"id":"13","logo":"upload/head-pic/php_avatar1_20160728102258_702_2QAG4QS6.jpg"},{"id":"14","logo":"upload/head-pic/php_avatar1_20160728152135_271_6RX7MEAW.jpg"},{"id":"1","logo":"upload/head-pic/php_avatar1_20160728152052_557_OGOXV6VS.jpg"},{"id":"16","logo":"upload/head-pic/php_avatar1_20160728095140_790_B621Y9SB.jpg"},{"id":"12","logo":"upload/head-pic/php_avatar1_20160720171319_145_1WF6BLSZ.jpg"},{"id":"17","logo":"upload/head-pic/php_avatar1_20160728100242_822_KUH72AAN.jpg"},{"id":"10","logo":"upload/head-pic/php_avatar1_20160720170251_919_775V27TU.jpg"},{"id":"9","logo":"upload/head-pic/php_avatar1_20160719170730_598_GCFJPSRB.jpg"}]}
     */

    private boolean success;
    private int code;
    private String msg;
    private NewSupplyDataEntity data;

    @Override
    public String toString() {
        return "NewSupplyBean{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(NewSupplyDataEntity data) {
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

    public NewSupplyDataEntity getData() {
        return data;
    }

    public static class NewSupplyDataEntity {
        /**
         * category : [{"id":"1","name":"柴米油盐","goods":[{"id":"1","name":"东北大米","label":"热卖","cover_pic":"img/logo.jpg","price":"35.00"},{"id":"2","name":"金龙鱼食用油","label":"新品","cover_pic":"img/logo.jpg","price":"49.00"},{"id":"3","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","label":"促销","cover_pic":"img/logo.jpg","price":"5.00"}]}]
         * purchase : [{"id":"4","name":"【中华特色馆】西安馆 陕西苹果 红富士 红苹果 苹果 5斤装/箱","cover_pic":"img/logo.jpg","remainTime":"43293","price":"28.00","old_price":"47.00","storage":"100","percent":"25%"},{"id":"5","name":"赣南脐橙优级果5斤装 果径80-85mm","cover_pic":"img/logo.jpg","remainTime":"80043293","price":"15.00","old_price":"25.00","storage":"200","percent":"25%"}]
         * merchant : [{"id":"11","logo":"upload/head-pic/php_avatar1_20160720171340_716_F0QFXMOL.jpg"},{"id":"13","logo":"upload/head-pic/php_avatar1_20160728102258_702_2QAG4QS6.jpg"},{"id":"14","logo":"upload/head-pic/php_avatar1_20160728152135_271_6RX7MEAW.jpg"},{"id":"1","logo":"upload/head-pic/php_avatar1_20160728152052_557_OGOXV6VS.jpg"},{"id":"16","logo":"upload/head-pic/php_avatar1_20160728095140_790_B621Y9SB.jpg"},{"id":"12","logo":"upload/head-pic/php_avatar1_20160720171319_145_1WF6BLSZ.jpg"},{"id":"17","logo":"upload/head-pic/php_avatar1_20160728100242_822_KUH72AAN.jpg"},{"id":"10","logo":"upload/head-pic/php_avatar1_20160720170251_919_775V27TU.jpg"},{"id":"9","logo":"upload/head-pic/php_avatar1_20160719170730_598_GCFJPSRB.jpg"}]
         */

        private List<CategoryEntity> category;
        private List<PurchaseEntity> purchase;
        private List<MerchantEntity> merchant;

        @Override
        public String toString() {
            return "NewSupplyDataEntity{" +
                    "category=" + category +
                    ", purchase=" + purchase +
                    ", merchant=" + merchant +
                    '}';
        }

        public void setCategory(List<CategoryEntity> category) {
            this.category = category;
        }

        public void setPurchase(List<PurchaseEntity> purchase) {
            this.purchase = purchase;
        }

        public void setMerchant(List<MerchantEntity> merchant) {
            this.merchant = merchant;
        }

        public List<CategoryEntity> getCategory() {
            return category;
        }

        public List<PurchaseEntity> getPurchase() {
            return purchase;
        }

        public List<MerchantEntity> getMerchant() {
            return merchant;
        }

        public static class CategoryEntity {
            /**
             * id : 1
             * name : 柴米油盐
             * goods : [{"id":"1","name":"东北大米","label":"热卖","cover_pic":"img/logo.jpg","price":"35.00"},{"id":"2","name":"金龙鱼食用油","label":"新品","cover_pic":"img/logo.jpg","price":"49.00"},{"id":"3","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","label":"促销","cover_pic":"img/logo.jpg","price":"5.00"}]
             */

            private String id;
            private String name;
            private List<GoodsEntity> goods;

            @Override
            public String toString() {
                return "CategoryEntity{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", goods=" + goods +
                        '}';
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setGoods(List<GoodsEntity> goods) {
                this.goods = goods;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public List<GoodsEntity> getGoods() {
                return goods;
            }

            public static class GoodsEntity {
                /**
                 * id : 1
                 * name : 东北大米
                 * label : 热卖
                 * cover_pic : img/logo.jpg
                 * price : 35.00
                 */

                private String id;
                private String name;
                private String label;
                private String cover_pic;
                private String price;

                @Override
                public String toString() {
                    return "GoodsEntity{" +
                            "id='" + id + '\'' +
                            ", name='" + name + '\'' +
                            ", label='" + label + '\'' +
                            ", cover_pic='" + cover_pic + '\'' +
                            ", price='" + price + '\'' +
                            '}';
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public void setCover_pic(String cover_pic) {
                    this.cover_pic = cover_pic;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public String getLabel() {
                    return label;
                }

                public String getCover_pic() {
                    return cover_pic;
                }

                public String getPrice() {
                    return price;
                }
            }
        }

        public static class PurchaseEntity {
            /**
             * id : 4
             * name : 【中华特色馆】西安馆 陕西苹果 红富士 红苹果 苹果 5斤装/箱
             * cover_pic : img/logo.jpg
             * remainTime : 43293
             * price : 28.00
             * old_price : 47.00
             * storage : 100
             * percent : 25%
             */

            private String id;
            private String name;
            private String cover_pic;
            private String remainTime;
            private String price;
            private String old_price;
            private String storage;
            private String percent;

            @Override
            public String toString() {
                return "PurchaseEntity{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", cover_pic='" + cover_pic + '\'' +
                        ", remainTime='" + remainTime + '\'' +
                        ", price='" + price + '\'' +
                        ", old_price='" + old_price + '\'' +
                        ", storage='" + storage + '\'' +
                        ", percent='" + percent + '\'' +
                        '}';
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setCover_pic(String cover_pic) {
                this.cover_pic = cover_pic;
            }

            public void setRemainTime(String remainTime) {
                this.remainTime = remainTime;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setOld_price(String old_price) {
                this.old_price = old_price;
            }

            public void setStorage(String storage) {
                this.storage = storage;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getCover_pic() {
                return cover_pic;
            }

            public String getRemainTime() {
                return remainTime;
            }

            public String getPrice() {
                return price;
            }

            public String getOld_price() {
                return old_price;
            }

            public String getStorage() {
                return storage;
            }

            public String getPercent() {
                return percent;
            }
        }

        public static class MerchantEntity {
            /**
             * id : 11
             * logo : upload/head-pic/php_avatar1_20160720171340_716_F0QFXMOL.jpg
             */

            private String id;
            private String logo;

            @Override
            public String toString() {
                return "MerchantEntity{" +
                        "id='" + id + '\'' +
                        ", logo='" + logo + '\'' +
                        '}';
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getId() {
                return id;
            }

            public String getLogo() {
                return logo;
            }
        }
    }
}
