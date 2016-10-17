package cn.hi028.android.highcommunity.bean.Autonomous;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee_yting on 2016/10/11 0011.
 * 说明：初始化自治大厅 bean
 */
public class Auto_InitBean implements Parcelable {


    /**
     * success : true
     * code : 2000
     * data : {"status":2,"village":{"village_id":"5113","village_name":"格兰鼎城"},"building":[{"building_id":"1","building_name":"1栋"},{"building_id":"2","building_name":"2栋"},{"building_id":"3","building_name":"3栋"},{"building_id":"4","building_name":"4栋"}]}
     * msg : 请进行认证!
     */

    private boolean success;
    private String code;
    private Auto_Init_DataEntity data;
    private String msg;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(Auto_Init_DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public Auto_Init_DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static class Auto_Init_DataEntity implements Parcelable {
        /**
         * status : 2
         * village : {"village_id":"5113","village_name":"格兰鼎城"}
         * building : [{"building_id":"1","building_name":"1栋"},{"building_id":"2","building_name":"2栋"},{"building_id":"3","building_name":"3栋"},{"building_id":"4","building_name":"4栋"}]
         */

        private int status;
        private int owner_id;



        private VillageEntity village;
        private List<BuildingEntity> building;

        public void setStatus(int status) {
            this.status = status;
        }

        public void setVillage(VillageEntity village) {
            this.village = village;
        }

        public void setBuilding(List<BuildingEntity> building) {
            this.building = building;
        }

        public int getStatus() {
            return status;
        }
        public int getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(int owner_id) {
            this.owner_id = owner_id;
        }
        public VillageEntity getVillage() {
            return village;
        }

        public List<BuildingEntity> getBuilding() {
            return building;
        }

        public static class VillageEntity implements Parcelable {
            /**
             * village_id : 5113
             * village_name : 格兰鼎城
             */

            private String village_id;
            private String village_name;

            public void setVillage_id(String village_id) {
                this.village_id = village_id;
            }

            public void setVillage_name(String village_name) {
                this.village_name = village_name;
            }

            public String getVillage_id() {
                return village_id;
            }

            public String getVillage_name() {
                return village_name;
            }

            @Override
            public String toString() {
                return "VillageEntity{" +
                        "village_id='" + village_id + '\'' +
                        ", village_name='" + village_name + '\'' +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.village_id);
                dest.writeString(this.village_name);
            }

            public VillageEntity() {
            }

            protected VillageEntity(Parcel in) {
                this.village_id = in.readString();
                this.village_name = in.readString();
            }

            public static final Creator<VillageEntity> CREATOR = new Creator<VillageEntity>() {
                public VillageEntity createFromParcel(Parcel source) {
                    return new VillageEntity(source);
                }

                public VillageEntity[] newArray(int size) {
                    return new VillageEntity[size];
                }
            };
        }

        public static class BuildingEntity implements Parcelable {
            /**
             * building_id : 1
             * building_name : 1栋
             */

            private String building_id;
            private String building_name;

            public void setBuilding_id(String building_id) {
                this.building_id = building_id;
            }

            public void setBuilding_name(String building_name) {
                this.building_name = building_name;
            }

            public String getBuilding_id() {
                return building_id;
            }

            public String getBuilding_name() {
                return building_name;
            }

            @Override
            public String toString() {
                return "BuildingEntity{" +
                        "building_id='" + building_id + '\'' +
                        ", building_name='" + building_name + '\'' +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.building_id);
                dest.writeString(this.building_name);
            }

            public BuildingEntity() {
            }

            protected BuildingEntity(Parcel in) {
                this.building_id = in.readString();
                this.building_name = in.readString();
            }

            public static final Creator<BuildingEntity> CREATOR = new Creator<BuildingEntity>() {
                public BuildingEntity createFromParcel(Parcel source) {
                    return new BuildingEntity(source);
                }

                public BuildingEntity[] newArray(int size) {
                    return new BuildingEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.status);
            dest.writeParcelable(this.village, flags);
            dest.writeList(this.building);
        }

        public Auto_Init_DataEntity() {
        }

        protected Auto_Init_DataEntity(Parcel in) {
            this.status = in.readInt();
            this.village = in.readParcelable(VillageEntity.class.getClassLoader());
            this.building = new ArrayList<BuildingEntity>();
            in.readList(this.building, List.class.getClassLoader());
        }

        public static final Parcelable.Creator<Auto_Init_DataEntity> CREATOR = new Parcelable.Creator<Auto_Init_DataEntity>() {
            public Auto_Init_DataEntity createFromParcel(Parcel source) {
                return new Auto_Init_DataEntity(source);
            }

            public Auto_Init_DataEntity[] newArray(int size) {
                return new Auto_Init_DataEntity[size];
            }
        };
    }

    @Override
    public String toString() {
        return "Auto_InitBean{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(success ? (byte) 1 : (byte) 0);
        dest.writeString(this.code);
        dest.writeParcelable(this.data, 0);
        dest.writeString(this.msg);
    }

    public Auto_InitBean() {
    }

    protected Auto_InitBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.code = in.readString();
        this.data = in.readParcelable(Auto_Init_DataEntity.class.getClassLoader());
        this.msg = in.readString();
    }

    public static final Parcelable.Creator<Auto_InitBean> CREATOR = new Parcelable.Creator<Auto_InitBean>() {
        public Auto_InitBean createFromParcel(Parcel source) {
            return new Auto_InitBean(source);
        }

        public Auto_InitBean[] newArray(int size) {
            return new Auto_InitBean[size];
        }
    };
}
