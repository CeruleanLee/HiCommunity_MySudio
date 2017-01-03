package cn.hi028.android.highcommunity.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee_yting on 2016/12/2 0002.
 * 说明：新版直供商品详情Bean
 */
public class NewSupplyGoodsDetailBean implements Parcelable {


    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : {"id":"1","name":"东北大米","mid":"0","pic":["img/logo.jpg"],"intro":"正宗东北大米,好吃又实惠","sale":"20","detail":"正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠","type":"0","limitNum":"5","sid":"1","tel":"02884828769","delivery":"9:00 - 21:00","supply":"嗨社区","attr":[{"attr_name":"产地","attr_val":"东北"},{"attr_name":"保质期","attr_val":"3年"},{"attr_name":"适用人群","attr_val":"老少皆宜"}],"standard":[{"id":"1","name":"5公斤/袋","price":"35.99","old_price":"50.00","storage":"100"},{"id":"2","name":"10公斤/袋","price":"50.00","old_price":"75.00","storage":"50"}],"cartNum":null,"comment":[{"uid":"1","head_pic":"upload/head_pic/201608/201608051720247328.jpg","nick":"super man2","time":"1479696128","content":"便宜又实惠"}],"recommend":[{"id":"5","label":"限时抢购","cover_pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","sale":"50","price":"15.00"},{"id":"1","label":"热卖","cover_pic":"img/logo.jpg","name":"东北大米","sale":"20","price":"35.99"}]}
     */

    private boolean success;
    private int code;
    private String msg;
    private SupplyGoodsDetailDataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(SupplyGoodsDetailDataEntity data) {
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

    public SupplyGoodsDetailDataEntity getData() {
        return data;
    }

    public static class SupplyGoodsDetailDataEntity implements Parcelable {
        /**
         * id : 1
         * name : 东北大米
         * mid : 0
         * pic : ["img/logo.jpg"]
         * intro : 正宗东北大米,好吃又实惠
         * sale : 20
         * detail : 正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠正宗东北大米,好吃又实惠
         * type : 0
         * limitNum : 5
         * sid : 1
         * tel : 02884828769
         * delivery : 9:00 - 21:00
         * supply : 嗨社区
         * attr : [{"attr_name":"产地","attr_val":"东北"},{"attr_name":"保质期","attr_val":"3年"},{"attr_name":"适用人群","attr_val":"老少皆宜"}]
         * standard : [{"id":"1","name":"5公斤/袋","price":"35.99","old_price":"50.00","storage":"100"},{"id":"2","name":"10公斤/袋","price":"50.00","old_price":"75.00","storage":"50"}]
         * cartNum : null
         * comment : [{"uid":"1","head_pic":"upload/head_pic/201608/201608051720247328.jpg","nick":"super man2","time":"1479696128","content":"便宜又实惠"}]
         * recommend : [{"id":"5","label":"限时抢购","cover_pic":"img/logo.jpg","name":"赣南脐橙优级果5斤装 果径80-85mm","sale":"50","price":"15.00"},{"id":"1","label":"热卖","cover_pic":"img/logo.jpg","name":"东北大米","sale":"20","price":"35.99"}]
         */

        private String id;
        private String name;
        private String mid;
        private String intro;
        private String sale;
        private String detail;
        private String type;
        private String limitNum;
        private String sid;
        private String remainTime;//倒计时(仅在type=1时显示出来)
        private String tel;
        private String delivery;
        private String supply;
        private String percent;
        private String cartNum;
        private List<String> pic;
        private List<AttrEntity> attr;
        private List<StandardEntity> standard;
        private List<CommentEntity> comment;
        private List<RecommendEntity> recommend;

        @Override
        public String toString() {
            return "SupplyGoodsDetailDataEntity{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", mid='" + mid + '\'' +
                    ", intro='" + intro + '\'' +
                    ", sale='" + sale + '\'' +
                    ", detail='" + detail + '\'' +
                    ", type='" + type + '\'' +
                    ", limitNum='" + limitNum + '\'' +
                    ", sid='" + sid + '\'' +
                    ", remainTime='" + remainTime + '\'' +
                    ", tel='" + tel + '\'' +
                    ", delivery='" + delivery + '\'' +
                    ", supply='" + supply + '\'' +
                    ", percent='" + percent + '\'' +
                    ", cartNum='" + cartNum + '\'' +
                    ", pic=" + pic +
                    ", attr=" + attr +
                    ", standard=" + standard +
                    ", comment=" + comment +
                    ", recommend=" + recommend +
                    '}';
        }

        public String getRemainTime() {
            return remainTime;
        }

        public void setRemainTime(String remainTime) {
            this.remainTime = remainTime;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setSale(String sale) {
            this.sale = sale;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setLimitNum(String limitNum) {
            this.limitNum = limitNum;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setDelivery(String delivery) {
            this.delivery = delivery;
        }

        public void setSupply(String supply) {
            this.supply = supply;
        }

        public void setCartNum(String cartNum) {
            this.cartNum = cartNum;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public void setAttr(List<AttrEntity> attr) {
            this.attr = attr;
        }

        public void setStandard(List<StandardEntity> standard) {
            this.standard = standard;
        }

        public void setComment(List<CommentEntity> comment) {
            this.comment = comment;
        }

        public void setRecommend(List<RecommendEntity> recommend) {
            this.recommend = recommend;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getMid() {
            return mid;
        }

        public String getIntro() {
            return intro;
        }

        public String getSale() {
            return sale;
        }

        public String getDetail() {
            return detail;
        }

        public String getType() {
            return type;
        }

        public String getLimitNum() {
            return limitNum;
        }

        public String getSid() {
            return sid;
        }

        public String getTel() {
            return tel;
        }

        public String getDelivery() {
            return delivery;
        }

        public String getSupply() {
            return supply;
        }

        public String getCartNum() {
            return cartNum;
        }

        public List<String> getPic() {
            return pic;
        }

        public List<AttrEntity> getAttr() {
            return attr;
        }

        public List<StandardEntity> getStandard() {
            return standard;
        }

        public List<CommentEntity> getComment() {
            return comment;
        }

        public List<RecommendEntity> getRecommend() {
            return recommend;
        }

        public static class AttrEntity implements Parcelable {
            /**
             * attr_name : 产地
             * attr_val : 东北
             */

            private String attr_name;
            private String attr_val;

            @Override
            public String toString() {
                return "AttrEntity{" +
                        "attr_name='" + attr_name + '\'' +
                        ", attr_val='" + attr_val + '\'' +
                        '}';
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }

            public void setAttr_val(String attr_val) {
                this.attr_val = attr_val;
            }

            public String getAttr_name() {
                return attr_name;
            }

            public String getAttr_val() {
                return attr_val;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.attr_name);
                dest.writeString(this.attr_val);
            }

            public AttrEntity() {
            }

            protected AttrEntity(Parcel in) {
                this.attr_name = in.readString();
                this.attr_val = in.readString();
            }

            public static final Creator<AttrEntity> CREATOR = new Creator<AttrEntity>() {
                public AttrEntity createFromParcel(Parcel source) {
                    return new AttrEntity(source);
                }

                public AttrEntity[] newArray(int size) {
                    return new AttrEntity[size];
                }
            };
        }

        public static class StandardEntity implements Comparable<StandardEntity>,Parcelable {
            /**
             * id : 1
             * name : 5公斤/袋
             * price : 35.99
             * old_price : 50.00
             * storage : 100
             */

            private String id;
            private String name;
            private String price;
            private String old_price;
            private String storage;

            @Override
            public String toString() {
                return "StandardEntity{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", price='" + price + '\'' +
                        ", old_price='" + old_price + '\'' +
                        ", storage='" + storage + '\'' +
                        '}';
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
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

            @Override
            public int compareTo(StandardEntity another) {
                return (int)(Float.parseFloat(this.getPrice())*100) - (int)(Float.parseFloat(another.getPrice())*100);//先按照年龄排序  ;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeString(this.price);
                dest.writeString(this.old_price);
                dest.writeString(this.storage);
            }

            public StandardEntity() {
            }

            protected StandardEntity(Parcel in) {
                this.id = in.readString();
                this.name = in.readString();
                this.price = in.readString();
                this.old_price = in.readString();
                this.storage = in.readString();
            }

            public static final Creator<StandardEntity> CREATOR = new Creator<StandardEntity>() {
                public StandardEntity createFromParcel(Parcel source) {
                    return new StandardEntity(source);
                }

                public StandardEntity[] newArray(int size) {
                    return new StandardEntity[size];
                }
            };
        }

        public static class CommentEntity implements Parcelable {
            /**
             * uid : 1
             * head_pic : upload/head_pic/201608/201608051720247328.jpg
             * nick : super man2
             * time : 1479696128
             * content : 便宜又实惠
             */

            private String uid;
            private String head_pic;
            private String nick;
            private String time;
            private String content;

            @Override
            public String toString() {
                return "CommentEntity{" +
                        "uid='" + uid + '\'' +
                        ", head_pic='" + head_pic + '\'' +
                        ", nick='" + nick + '\'' +
                        ", time='" + time + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUid() {
                return uid;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public String getNick() {
                return nick;
            }

            public String getTime() {
                return time;
            }

            public String getContent() {
                return content;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.uid);
                dest.writeString(this.head_pic);
                dest.writeString(this.nick);
                dest.writeString(this.time);
                dest.writeString(this.content);
            }

            public CommentEntity() {
            }

            protected CommentEntity(Parcel in) {
                this.uid = in.readString();
                this.head_pic = in.readString();
                this.nick = in.readString();
                this.time = in.readString();
                this.content = in.readString();
            }

            public static final Creator<CommentEntity> CREATOR = new Creator<CommentEntity>() {
                public CommentEntity createFromParcel(Parcel source) {
                    return new CommentEntity(source);
                }

                public CommentEntity[] newArray(int size) {
                    return new CommentEntity[size];
                }
            };
        }

        public static class RecommendEntity implements Parcelable {
            /**
             * id : 5
             * label : 限时抢购
             * cover_pic : img/logo.jpg
             * name : 赣南脐橙优级果5斤装 果径80-85mm
             * sale : 50
             * price : 15.00
             */

            private String id;
            private String label;
            private String cover_pic;
            private String name;
            private String sale;
            private String price;
            private String old_price;

            @Override
            public String toString() {
                return "RecommendEntity{" +
                        "id='" + id + '\'' +
                        ", label='" + label + '\'' +
                        ", cover_pic='" + cover_pic + '\'' +
                        ", name='" + name + '\'' +
                        ", sale='" + sale + '\'' +
                        ", price='" + price + '\'' +
                        ", old_price='" + old_price + '\'' +
                        '}';
            }

            public String getOld_price() {
                return old_price;
            }

            public void setOld_price(String old_price) {
                this.old_price = old_price;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setCover_pic(String cover_pic) {
                this.cover_pic = cover_pic;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setSale(String sale) {
                this.sale = sale;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getId() {
                return id;
            }

            public String getLabel() {
                return label;
            }

            public String getCover_pic() {
                return cover_pic;
            }

            public String getName() {
                return name;
            }

            public String getSale() {
                return sale;
            }

            public String getPrice() {
                return price;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.label);
                dest.writeString(this.cover_pic);
                dest.writeString(this.name);
                dest.writeString(this.sale);
                dest.writeString(this.price);
                dest.writeString(this.old_price);
            }

            public RecommendEntity() {
            }

            protected RecommendEntity(Parcel in) {
                this.id = in.readString();
                this.label = in.readString();
                this.cover_pic = in.readString();
                this.name = in.readString();
                this.sale = in.readString();
                this.price = in.readString();
                this.old_price = in.readString();
            }

            public static final Creator<RecommendEntity> CREATOR = new Creator<RecommendEntity>() {
                public RecommendEntity createFromParcel(Parcel source) {
                    return new RecommendEntity(source);
                }

                public RecommendEntity[] newArray(int size) {
                    return new RecommendEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.mid);
            dest.writeString(this.intro);
            dest.writeString(this.sale);
            dest.writeString(this.detail);
            dest.writeString(this.type);
            dest.writeString(this.limitNum);
            dest.writeString(this.sid);
            dest.writeString(this.remainTime);
            dest.writeString(this.tel);
            dest.writeString(this.delivery);
            dest.writeString(this.supply);
            dest.writeString(this.percent);
            dest.writeString(this.cartNum);
            dest.writeStringList(this.pic);
            dest.writeList(this.attr);
            dest.writeList(this.standard);
            dest.writeList(this.comment);
            dest.writeList(this.recommend);
        }

        public SupplyGoodsDetailDataEntity() {
        }

        protected SupplyGoodsDetailDataEntity(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
            this.mid = in.readString();
            this.intro = in.readString();
            this.sale = in.readString();
            this.detail = in.readString();
            this.type = in.readString();
            this.limitNum = in.readString();
            this.sid = in.readString();
            this.remainTime = in.readString();
            this.tel = in.readString();
            this.delivery = in.readString();
            this.supply = in.readString();
            this.percent = in.readString();
            this.cartNum = in.readString();
            this.pic = in.createStringArrayList();
            this.attr = new ArrayList<AttrEntity>();
            in.readList(this.attr, List.class.getClassLoader());
            this.standard = new ArrayList<StandardEntity>();
            in.readList(this.standard, List.class.getClassLoader());
            this.comment = new ArrayList<CommentEntity>();
            in.readList(this.comment, List.class.getClassLoader());
            this.recommend = new ArrayList<RecommendEntity>();
            in.readList(this.recommend, List.class.getClassLoader());
        }

        public static final Creator<SupplyGoodsDetailDataEntity> CREATOR = new Creator<SupplyGoodsDetailDataEntity>() {
            public SupplyGoodsDetailDataEntity createFromParcel(Parcel source) {
                return new SupplyGoodsDetailDataEntity(source);
            }

            public SupplyGoodsDetailDataEntity[] newArray(int size) {
                return new SupplyGoodsDetailDataEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(success ? (byte) 1 : (byte) 0);
        dest.writeInt(this.code);
        dest.writeString(this.msg);
        dest.writeParcelable(this.data, flags);
    }

    public NewSupplyGoodsDetailBean() {
    }

    protected NewSupplyGoodsDetailBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.code = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(SupplyGoodsDetailDataEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<NewSupplyGoodsDetailBean> CREATOR = new Parcelable.Creator<NewSupplyGoodsDetailBean>() {
        public NewSupplyGoodsDetailBean createFromParcel(Parcel source) {
            return new NewSupplyGoodsDetailBean(source);
        }

        public NewSupplyGoodsDetailBean[] newArray(int size) {
            return new NewSupplyGoodsDetailBean[size];
        }
    };
}
