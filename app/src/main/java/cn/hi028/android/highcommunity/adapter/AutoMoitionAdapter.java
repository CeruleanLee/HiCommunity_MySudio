package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.fragment.AddressListFrag;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionBean;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治大厅 提案adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoMoitionAdapter extends BaseFragmentAdapter {

    public AddressListFrag mFrag;
    List<Auto_MotionBean.MotionDataEntity> mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;

    public AutoMoitionAdapter(List<Auto_MotionBean.MotionDataEntity> list, Context context) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Auto_MotionBean.MotionDataEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_automotion, null);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.item_aotumotion_title);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.item_aotumotion_time);
            mViewHolder.mTv_Support = (TextView) convertView.findViewById(R.id.item_aotumotion_tv_support);
            mViewHolder.mBut_Support = (CheckedTextView) convertView.findViewById(R.id.item_aotumotion_but_support);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        final Auto_MotionBean.MotionDataEntity mBean = mList.get(position);
        mViewHolder.mTitle.setText(mBean.getTitle());
        mViewHolder.mTime.setText(TimeUtil.getYearMonthDay(Long.parseLong(mBean.getCreate_time())));
//        mViewHolder.mTime.setText(TimeUtil.longToDate(Long.parseLong(mBean.getCreate_time()),"yyyy年MM月dd日 HH时mm分ss秒").toString());
        mViewHolder.mTv_Support.setText("支持率：" + mBean.getVote_percent() + "%");
        if (mBean.getIsSuggest()=="1") {
            mViewHolder.mBut_Support.setChecked(true);
            mViewHolder.mBut_Support.setText(" 已支持 ");

        }else if (mBean.getIsSuggest()=="1"){
            mViewHolder.mBut_Support.setChecked(false);
            mViewHolder.mBut_Support.setText(" 支持 ");
        }
        mViewHolder.mBut_Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mViewHolder.mBut_Support.setChecked(TURE);
                mViewHolder.mBut_Support.setChecked(true);
                mViewHolder.mBut_Support.setText("已支持");
                Toast.makeText(context,"已支持",Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }


    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            mList = (List<Auto_MotionBean.MotionDataEntity>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }

    public void ClearData() {
        mList.clear();
        notifyDataSetChanged();
    }


    static class ViewHolder {
        TextView mTitle;
        TextView mTime;
        TextView mTv_Support;
        CheckedTextView mBut_Support;


    }
}
