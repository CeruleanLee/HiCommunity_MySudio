package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * Created by Lee_yting on 2016/12/1 0001.
 * 说明：直供商品更多列表
 */
public class SupplyGoodsMoreBean {


    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : {"category":[{"id":"1","name":"柴米油盐"},{"id":"2","name":"水果"}],"goods":[{"id":"3","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","label":"促销","cover_pic":"img/logo.jpg","sale":"35","price":"5.00","old_price":"7.50"},{"id":"2","name":"金龙鱼食用油","label":"新品","cover_pic":"img/logo.jpg","sale":"30","price":"49.00","old_price":"69.00"},{"id":"1","name":"东北大米","label":"热卖","cover_pic":"img/logo.jpg","sale":"20","price":"35.99","old_price":"50.00"}]}
     */

    private boolean success;
    private int code;
    private String msg;
    private SupplyGoodsMoreDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(SupplyGoodsMoreDataEntity data) {
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

    public SupplyGoodsMoreDataEntity getData() {
        return data;
    }

    public static class SupplyGoodsMoreDataEntity {
        /**
         * category : [{"id":"1","name":"柴米油盐"},{"id":"2","name":"水果"}]
         * goods : [{"id":"3","name":"粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料","label":"促销","cover_pic":"img/logo.jpg","sale":"35","price":"5.00","old_price":"7.50"},{"id":"2","name":"金龙鱼食用油","label":"新品","cover_pic":"img/logo.jpg","sale":"30","price":"49.00","old_price":"69.00"},{"id":"1","name":"东北大米","label":"热卖","cover_pic":"img/logo.jpg","sale":"20","price":"35.99","old_price":"50.00"}]
         */

        private List<SupplyMoreCategoryEntity> category;
        private List<SupplyMoreGoodsEntity> goods;

        public void setCategory(List<SupplyMoreCategoryEntity> category) {
            this.category = category;
        }

        public void setGoods(List<SupplyMoreGoodsEntity> goods) {
            this.goods = goods;
        }

        public List<SupplyMoreCategoryEntity> getCategory() {
            return category;
        }

        public List<SupplyMoreGoodsEntity> getGoods() {
            return goods;
        }

        public static class SupplyMoreCategoryEntity {
            /**
             * id : 1
             * name : 柴米油盐
             */

            private String id;
            private String name;

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public static class SupplyMoreGoodsEntity {
            public SupplyMoreGoodsEntity(String id, String name, String label, String cover_pic, String sale, String price, String old_price) {
                this.id = id;
                this.name = name;
                this.label = label;
                this.cover_pic = cover_pic;
                this.sale = sale;
                this.price = price;
                this.old_price = old_price;
            }

            public SupplyMoreGoodsEntity() {
            }

            /**
             * id : 3
             * name : 粤盐 澳洲湖盐雪晶盐无碘250g 无碘盐食用盐调味料
             * label : 促销
             * cover_pic : img/logo.jpg
             * sale : 35
             * price : 5.00
             * old_price : 7.50
             */

            private String id;
            private String name;
            private String label;
            private String cover_pic;
            private String sale;
            private String price;
            private String old_price;

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

            public void setSale(String sale) {
                this.sale = sale;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setOld_price(String old_price) {
                this.old_price = old_price;
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

            public String getSale() {
                return sale;
            }

            public String getPrice() {
                return price;
            }

            public String getOld_price() {
                return old_price;
            }
        }
    }

}
