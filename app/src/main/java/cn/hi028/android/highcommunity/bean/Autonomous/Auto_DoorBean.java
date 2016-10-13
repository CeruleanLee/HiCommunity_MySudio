package cn.hi028.android.highcommunity.bean.Autonomous;

import java.util.List;

/**
 * Created by Lee_yting on 2016/10/13 0013.
 * 说明：根据单元获取门牌号Bean
 */
public class Auto_DoorBean {
    /**
     * success : true
     * code : 2000
     * msg : 获取单元成功
     * data : [{"door_id":"16","door_name":"1-1-101"},{"door_id":"21","door_name":"1-1-201"},{"door_id":"22","door_name":"1-1-202"},{"door_id":"23","door_name":"1-1-203"},{"door_id":"24","door_name":"1-1-204"},{"door_id":"25","door_name":"1-1-205"},{"door_id":"26","door_name":"1-1-301"},{"door_id":"27","door_name":"1-1-302"},{"door_id":"28","door_name":"1-1-303"},{"door_id":"29","door_name":"1-1-304"},{"door_id":"30","door_name":"1-1-305"},{"door_id":"31","door_name":"1-1-401"},{"door_id":"32","door_name":"1-1-402"},{"door_id":"33","door_name":"1-1-403"},{"door_id":"34","door_name":"1-1-404"},{"door_id":"35","door_name":"1-1-405"},{"door_id":"36","door_name":"1-1-501"},{"door_id":"37","door_name":"1-1-502"},{"door_id":"38","door_name":"1-1-503"},{"door_id":"39","door_name":"1-1-504"},{"door_id":"40","door_name":"1-1-505"},{"door_id":"41","door_name":"1-1-601"},{"door_id":"42","door_name":"1-1-602"},{"door_id":"43","door_name":"1-1-603"},{"door_id":"44","door_name":"1-1-604"},{"door_id":"45","door_name":"1-1-605"},{"door_id":"46","door_name":"1-1-701"},{"door_id":"47","door_name":"1-1-702"},{"door_id":"48","door_name":"1-1-703"},{"door_id":"49","door_name":"1-1-704"},{"door_id":"50","door_name":"1-1-705"},{"door_id":"51","door_name":"1-1-801"},{"door_id":"52","door_name":"1-1-802"},{"door_id":"53","door_name":"1-1-803"},{"door_id":"54","door_name":"1-1-804"},{"door_id":"55","door_name":"1-1-805"},{"door_id":"56","door_name":"1-1-901"},{"door_id":"57","door_name":"1-1-902"},{"door_id":"58","door_name":"1-1-903"},{"door_id":"59","door_name":"1-1-904"},{"door_id":"60","door_name":"1-1-905"},{"door_id":"61","door_name":"1-1-1001"},{"door_id":"62","door_name":"1-1-1002"},{"door_id":"63","door_name":"1-1-1003"},{"door_id":"64","door_name":"1-1-1004"},{"door_id":"65","door_name":"1-1-1005"},{"door_id":"66","door_name":"1-1-1101"},{"door_id":"67","door_name":"1-1-1102"},{"door_id":"68","door_name":"1-1-1103"},{"door_id":"69","door_name":"1-1-1104"},{"door_id":"70","door_name":"1-1-1105"},{"door_id":"71","door_name":"1-1-1201"},{"door_id":"72","door_name":"1-1-1202"},{"door_id":"73","door_name":"1-1-1203"},{"door_id":"74","door_name":"1-1-1204"},{"door_id":"75","door_name":"1-1-1205"},{"door_id":"76","door_name":"1-1-1301"},{"door_id":"77","door_name":"1-1-1302"},{"door_id":"78","door_name":"1-1-1303"},{"door_id":"79","door_name":"1-1-1304"},{"door_id":"80","door_name":"1-1-1305"},{"door_id":"81","door_name":"1-1-1401"},{"door_id":"82","door_name":"1-1-1402"},{"door_id":"83","door_name":"1-1-1403"},{"door_id":"84","door_name":"1-1-1404"},{"door_id":"85","door_name":"1-1-1405"},{"door_id":"86","door_name":"1-1-1501"},{"door_id":"87","door_name":"1-1-1502"},{"door_id":"88","door_name":"1-1-1503"},{"door_id":"89","door_name":"1-1-1504"},{"door_id":"90","door_name":"1-1-1505"},{"door_id":"91","door_name":"1-1-1601"},{"door_id":"92","door_name":"1-1-1602"},{"door_id":"93","door_name":"1-1-1603"},{"door_id":"94","door_name":"1-1-1604"},{"door_id":"95","door_name":"1-1-1605"},{"door_id":"96","door_name":"1-1-1701"},{"door_id":"97","door_name":"1-1-1702"},{"door_id":"98","door_name":"1-1-1703"},{"door_id":"99","door_name":"1-1-1704"},{"door_id":"100","door_name":"1-1-1705"},{"door_id":"101","door_name":"1-1-1801"},{"door_id":"102","door_name":"1-1-1802"},{"door_id":"103","door_name":"1-1-1803"},{"door_id":"104","door_name":"1-1-1804"},{"door_id":"105","door_name":"1-1-1805"},{"door_id":"106","door_name":"1-1-1901"},{"door_id":"107","door_name":"1-1-1902"},{"door_id":"108","door_name":"1-1-1903"},{"door_id":"109","door_name":"1-1-1904"},{"door_id":"110","door_name":"1-1-1905"},{"door_id":"111","door_name":"1-1-2001"},{"door_id":"112","door_name":"1-1-2002"},{"door_id":"113","door_name":"1-1-2003"},{"door_id":"114","door_name":"1-1-2004"},{"door_id":"115","door_name":"1-1-2005"},{"door_id":"116","door_name":"1-1-2101"},{"door_id":"117","door_name":"1-1-2102"},{"door_id":"118","door_name":"1-1-2103"},{"door_id":"119","door_name":"1-1-2104"},{"door_id":"120","door_name":"1-1-2105"},{"door_id":"121","door_name":"1-1-2201"},{"door_id":"122","door_name":"1-1-2202"},{"door_id":"123","door_name":"1-1-2203"},{"door_id":"124","door_name":"1-1-2204"},{"door_id":"125","door_name":"1-1-2205"},{"door_id":"126","door_name":"1-1-2301"},{"door_id":"127","door_name":"1-1-2302"},{"door_id":"128","door_name":"1-1-2303"},{"door_id":"129","door_name":"1-1-2304"},{"door_id":"130","door_name":"1-1-2305"},{"door_id":"131","door_name":"1-1-2401"},{"door_id":"132","door_name":"1-1-2402"},{"door_id":"133","door_name":"1-1-2403"},{"door_id":"134","door_name":"1-1-2404"},{"door_id":"135","door_name":"1-1-2405"}]
     */
    private boolean success;
    private String code;
    private String msg;
    private List<DoorDataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DoorDataEntity> data) {
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

    public List<DoorDataEntity> getData() {
        return data;
    }

    public static class DoorDataEntity {
        /**
         * door_id : 16
         * door_name : 1-1-101
         */

        private String door_id;
        private String door_name;

        public void setDoor_id(String door_id) {
            this.door_id = door_id;
        }

        public void setDoor_name(String door_name) {
            this.door_name = door_name;
        }

        public String getDoor_id() {
            return door_id;
        }

        public String getDoor_name() {
            return door_name;
        }
    }
}
