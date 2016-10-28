package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/28 0028.
 * 说明：业主认证认证bean
 */
public class Auto_CertificationInitBean {
    /**
     * success : true
     * code : 2000
     * msg : 成功
     * data : [{"id":"401","name":"小黑","tel":"18810976811","IDCard":"upload/ywh/owner_pic/20161027114748898872.jpg","IDCard_F":"upload/ywh/owner_pic/20161027114748851900.jpg","house_certificate":"upload/ywh/owner_pic/20161027114748661171.jpg","status":"1","reason":"","address":"格兰鼎城1栋1单元1-1-205号"}]
     */

    private boolean success;
    private String code;
    private String msg;
    private List<CertificationInitDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<CertificationInitDataEntity> data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<CertificationInitDataEntity> getData() {
        return data;
    }

    public static class CertificationInitDataEntity {
        /**
         * id : 401
         * name : 小黑
         * tel : 18810976811
         * IDCard : upload/ywh/owner_pic/20161027114748898872.jpg
         * IDCard_F : upload/ywh/owner_pic/20161027114748851900.jpg
         * house_certificate : upload/ywh/owner_pic/20161027114748661171.jpg
         * status : 1
         * reason :
         * address : 格兰鼎城1栋1单元1-1-205号
         */

        private String id;
        private String name;
        private String tel;
        private String IDCard;
        private String IDCard_F;
        private String house_certificate;
        private String status;
        private String reason;
        private String address;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setIDCard(String IDCard) {
            this.IDCard = IDCard;
        }

        public void setIDCard_F(String IDCard_F) {
            this.IDCard_F = IDCard_F;
        }

        public void setHouse_certificate(String house_certificate) {
            this.house_certificate = house_certificate;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getTel() {
            return tel;
        }

        public String getIDCard() {
            return IDCard;
        }

        public String getIDCard_F() {
            return IDCard_F;
        }

        public String getHouse_certificate() {
            return house_certificate;
        }

        public String getStatus() {
            return status;
        }

        public String getReason() {
            return reason;
        }

        public String getAddress() {
            return address;
        }
    }
}
