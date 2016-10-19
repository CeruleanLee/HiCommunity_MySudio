package cn.hi028.android.highcommunity.bean.Autonomous;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Lee on 2016/10/20.
 * 说明：
 */
public class Title_CommitQuestionAnswer implements Parcelable {
private    String id;
    private List<String> mutilOptionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMutilOptionId() {
        return mutilOptionId;
    }

    public void setMutilOptionId(List<String> mutilOptionId) {
        this.mutilOptionId = mutilOptionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeStringList(this.mutilOptionId);
    }

    public Title_CommitQuestionAnswer() {
    }

    protected Title_CommitQuestionAnswer(Parcel in) {
        this.id = in.readString();
        this.mutilOptionId = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Title_CommitQuestionAnswer> CREATOR = new Parcelable.Creator<Title_CommitQuestionAnswer>() {
        @Override
        public Title_CommitQuestionAnswer createFromParcel(Parcel source) {
            return new Title_CommitQuestionAnswer(source);
        }

        @Override
        public Title_CommitQuestionAnswer[] newArray(int size) {
            return new Title_CommitQuestionAnswer[size];
        }
    };
}
