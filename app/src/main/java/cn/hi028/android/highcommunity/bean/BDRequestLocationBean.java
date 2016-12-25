package cn.hi028.android.highcommunity.bean;

import java.util.List;

/**
 * Created by Lee on 2016/12/15.
 * 说明：百度地图请求返回Bean
 */
public class BDRequestLocationBean {


    /**
     * status : 0
     * result : {"location":{"lng":116.32298699999993,"lat":39.98342407140365},"formatted_address":"北京市海淀区中关村大街27号1101-08室","business":"中关村,人民大学,苏州街","addressComponent":{"country":"中国","country_code":0,"province":"北京市","city":"北京市","district":"海淀区","adcode":"110108","street":"中关村大街","street_number":"27号1101-08室","direction":"附近","distance":"7"},"pois":[{"addr":"北京北京海淀海淀区中关村大街27号（地铁海淀黄庄站A1","cp":"","direction":"内","distance":"0","name":"北京远景国际公寓(中关村店)","poiType":"房地产","point":{"x":116.32294589160055,"y":39.983610361549296},"tag":"房地产","tel":"","uid":"35a08504cb51b1138733049d","zip":""},{"addr":"海淀区中关村北大街27号","cp":" ","direction":"附近","distance":"21","name":"中关村大厦","poiType":"房地产","point":{"x":116.32290995938015,"y":39.98356198726214},"tag":"房地产;写字楼","tel":"","uid":"06d2dffdaef1b7ef88f15d04","zip":""},{"addr":"北京市海淀区中关村大街27号中关村大厦二层","cp":" ","direction":"附近","distance":"5","name":"眉州东坡酒楼(中关村店)","poiType":"美食","point":{"x":116.32293690854546,"y":39.98343759607948},"tag":"美食;中餐厅","tel":"","uid":"2c0bd6c57dbdd3b342ab9a8c","zip":""},{"addr":"北京市海淀区中关村大街29号(海淀黄庄路口)","cp":" ","direction":"东北","distance":"116","name":"海淀医院","poiType":"医疗","point":{"x":116.32234402690887,"y":39.98278799397245},"tag":"医疗;综合医院","tel":"","uid":"fa01e9371a040053774ff1ca","zip":""},{"addr":"中关村大街19号新中关大厦(近丹棱街)","cp":" ","direction":"东南","distance":"179","name":"新中关购物中心","poiType":"购物","point":{"x":116.32172419610698,"y":39.984190850302326},"tag":"购物;购物中心","tel":"","uid":"8d96925c0372e65cc1f1cf7f","zip":""},{"addr":"中关村大街与大泥湾路交叉口东北角","cp":" ","direction":"西","distance":"159","name":"必胜客(希望餐厅)","poiType":"美食","point":{"x":116.32440114652672,"y":39.983230276934485},"tag":"美食;外国餐厅","tel":"","uid":"c85cfc41edd6631cc5effb84","zip":""},{"addr":"中关村大街19号新中关大厦C座11层近地铁十号线海淀黄庄站新中关商场海淀医院人民大学国家图书馆","cp":" ","direction":"东南","distance":"130","name":"北京七喜酒店公寓(海淀黄庄店)","poiType":"房地产","point":{"x":116.32239792523947,"y":39.9842046714025},"tag":"房地产;住宅区","tel":"","uid":"9b285eb82ff8da77077ce8b1","zip":""},{"addr":"北京市海淀区中关村大街28号","cp":" ","direction":"西北","distance":"229","name":"海淀剧院","poiType":"休闲娱乐","point":{"x":116.32476046873072,"y":39.98262213711767},"tag":"休闲娱乐;电影院","tel":"","uid":"edd64ce1a6d799913ee231b3","zip":""},{"addr":"北京市海淀区海淀南路甲2号","cp":" ","direction":"北","distance":"283","name":"东润商厦","poiType":"购物","point":{"x":116.32365555295344,"y":39.981537146848645},"tag":"购物;购物中心","tel":"","uid":"0dc3d82d4ba1b06c74e5b6af","zip":""},{"addr":"中关村大街28-1号海淀文化艺术大厦A座","cp":" ","direction":"西北","distance":"311","name":"北京市海淀区博物馆","poiType":"旅游景点","point":{"x":116.32543419786319,"y":39.98238026181034},"tag":"旅游景点;博物馆","tel":"","uid":"5b25f446a72b99ea112935ad","zip":""}],"poiRegions":[],"sematic_description":"北京远景国际公寓(中关村店)内0米","cityCode":131}
     */

    private int status;
    /**
     * location : {"lng":116.32298699999993,"lat":39.98342407140365}
     * formatted_address : 北京市海淀区中关村大街27号1101-08室
     * business : 中关村,人民大学,苏州街
     * addressComponent : {"country":"中国","country_code":0,"province":"北京市","city":"北京市","district":"海淀区","adcode":"110108","street":"中关村大街","street_number":"27号1101-08室","direction":"附近","distance":"7"}
     * pois : [{"addr":"北京北京海淀海淀区中关村大街27号（地铁海淀黄庄站A1","cp":"","direction":"内","distance":"0","name":"北京远景国际公寓(中关村店)","poiType":"房地产","point":{"x":116.32294589160055,"y":39.983610361549296},"tag":"房地产","tel":"","uid":"35a08504cb51b1138733049d","zip":""},{"addr":"海淀区中关村北大街27号","cp":" ","direction":"附近","distance":"21","name":"中关村大厦","poiType":"房地产","point":{"x":116.32290995938015,"y":39.98356198726214},"tag":"房地产;写字楼","tel":"","uid":"06d2dffdaef1b7ef88f15d04","zip":""},{"addr":"北京市海淀区中关村大街27号中关村大厦二层","cp":" ","direction":"附近","distance":"5","name":"眉州东坡酒楼(中关村店)","poiType":"美食","point":{"x":116.32293690854546,"y":39.98343759607948},"tag":"美食;中餐厅","tel":"","uid":"2c0bd6c57dbdd3b342ab9a8c","zip":""},{"addr":"北京市海淀区中关村大街29号(海淀黄庄路口)","cp":" ","direction":"东北","distance":"116","name":"海淀医院","poiType":"医疗","point":{"x":116.32234402690887,"y":39.98278799397245},"tag":"医疗;综合医院","tel":"","uid":"fa01e9371a040053774ff1ca","zip":""},{"addr":"中关村大街19号新中关大厦(近丹棱街)","cp":" ","direction":"东南","distance":"179","name":"新中关购物中心","poiType":"购物","point":{"x":116.32172419610698,"y":39.984190850302326},"tag":"购物;购物中心","tel":"","uid":"8d96925c0372e65cc1f1cf7f","zip":""},{"addr":"中关村大街与大泥湾路交叉口东北角","cp":" ","direction":"西","distance":"159","name":"必胜客(希望餐厅)","poiType":"美食","point":{"x":116.32440114652672,"y":39.983230276934485},"tag":"美食;外国餐厅","tel":"","uid":"c85cfc41edd6631cc5effb84","zip":""},{"addr":"中关村大街19号新中关大厦C座11层近地铁十号线海淀黄庄站新中关商场海淀医院人民大学国家图书馆","cp":" ","direction":"东南","distance":"130","name":"北京七喜酒店公寓(海淀黄庄店)","poiType":"房地产","point":{"x":116.32239792523947,"y":39.9842046714025},"tag":"房地产;住宅区","tel":"","uid":"9b285eb82ff8da77077ce8b1","zip":""},{"addr":"北京市海淀区中关村大街28号","cp":" ","direction":"西北","distance":"229","name":"海淀剧院","poiType":"休闲娱乐","point":{"x":116.32476046873072,"y":39.98262213711767},"tag":"休闲娱乐;电影院","tel":"","uid":"edd64ce1a6d799913ee231b3","zip":""},{"addr":"北京市海淀区海淀南路甲2号","cp":" ","direction":"北","distance":"283","name":"东润商厦","poiType":"购物","point":{"x":116.32365555295344,"y":39.981537146848645},"tag":"购物;购物中心","tel":"","uid":"0dc3d82d4ba1b06c74e5b6af","zip":""},{"addr":"中关村大街28-1号海淀文化艺术大厦A座","cp":" ","direction":"西北","distance":"311","name":"北京市海淀区博物馆","poiType":"旅游景点","point":{"x":116.32543419786319,"y":39.98238026181034},"tag":"旅游景点;博物馆","tel":"","uid":"5b25f446a72b99ea112935ad","zip":""}]
     * poiRegions : []
     * sematic_description : 北京远景国际公寓(中关村店)内0米
     * cityCode : 131
     */

    private ResultEntity result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        /**
         * lng : 116.32298699999993
         * lat : 39.98342407140365
         */

        private LocationEntity location;//lat纬度坐标 lng经度坐标
        private String formatted_address;//结构化地址信息
        private String business;//	所在商圈信息，如 "人民大学,中关村,苏州街"
        /**
         * country : 中国
         * country_code : 0
         * province : 北京市
         * city : 北京市
         * district : 海淀区
         * adcode : 110108
         * street : 中关村大街
         * street_number : 27号1101-08室
         * direction : 附近
         * distance : 7
         */

        private AddressComponentEntity addressComponent;//地址信息
        private String sematic_description;//当前位置结合POI的语义化结果描述。
        private int cityCode;
        /**
         * addr : 北京北京海淀海淀区中关村大街27号（地铁海淀黄庄站A1
         * cp :
         * direction : 内
         * distance : 0
         * name : 北京远景国际公寓(中关村店)
         * poiType : 房地产
         * point : {"x":116.32294589160055,"y":39.983610361549296}
         * tag : 房地产
         * tel :
         * uid : 35a08504cb51b1138733049d
         * zip :
         */

        private List<PoisEntity> pois;//周边poi数组
        private List<?> poiRegions;

        public LocationEntity getLocation() {
            return location;
        }

        public void setLocation(LocationEntity location) {
            this.location = location;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public AddressComponentEntity getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentEntity addressComponent) {
            this.addressComponent = addressComponent;
        }

        public String getSematic_description() {
            return sematic_description;
        }

        public void setSematic_description(String sematic_description) {
            this.sematic_description = sematic_description;
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public List<PoisEntity> getPois() {
            return pois;
        }

        public void setPois(List<PoisEntity> pois) {
            this.pois = pois;
        }

        public List<?> getPoiRegions() {
            return poiRegions;
        }

        public void setPoiRegions(List<?> poiRegions) {
            this.poiRegions = poiRegions;
        }

        public static class LocationEntity {
            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class AddressComponentEntity {
            private String country;
            private int country_code;
            private String province;
            private String city;
            private String district;
            private String adcode;
            private String street;
            private String street_number;
            private String direction;
            private String distance;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public int getCountry_code() {
                return country_code;
            }

            public void setCountry_code(int country_code) {
                this.country_code = country_code;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }

        public static class PoisEntity {
            private String addr;//	地址信息
            private String cp;//	数据来源
            private String direction;//和当前坐标点的方向
            private String distance;//离坐标点距离
            private String name;//poi名称
            private String poiType;//	poi类型，如’ 办公大厦,商务大厦’
            /**
             * x : 116.32294589160055
             * y : 39.983610361549296
             */

            private PointEntity point;//poi坐标{x,y}
            private String tag;
            private String tel;//电话
            private String uid;//poi唯一标识poi唯一标识
            private String zip;//邮编

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getCp() {
                return cp;
            }

            public void setCp(String cp) {
                this.cp = cp;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPoiType() {
                return poiType;
            }

            public void setPoiType(String poiType) {
                this.poiType = poiType;
            }

            public PointEntity getPoint() {
                return point;
            }

            public void setPoint(PointEntity point) {
                this.point = point;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getZip() {
                return zip;
            }

            public void setZip(String zip) {
                this.zip = zip;
            }

            public static class PointEntity {
                private double x;
                private double y;

                public double getX() {
                    return x;
                }

                public void setX(double x) {
                    this.x = x;
                }

                public double getY() {
                    return y;
                }

                public void setY(double y) {
                    this.y = y;
                }
            }
        }
    }
}
