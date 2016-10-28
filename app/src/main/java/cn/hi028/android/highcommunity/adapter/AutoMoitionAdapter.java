package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionBean;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_SupportedResultBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治大厅 提案adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoMoitionAdapter extends BaseFragmentAdapter {
    BpiHttpHandler.IBpiHttpHandler mIbpi;
    public static final String TAG = "~~~AutoMoitionAdapter";
    public static final int TAG_MOTION_DETAIL = 2;
    List<Auto_MotionBean.MotionDataEntity> mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    View view;
    Auto_SupportedResultBean.SupportedResultDataEntity mResultData;

    public AutoMoitionAdapter(List<Auto_MotionBean.MotionDataEntity> list, Context context, View view) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.view = view;
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

    ViewHolder mViewHolder = null;
    int clickPosition=-1;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        if (mBean.getIsSuggest().equals("1")) {
            mViewHolder.mBut_Support.setChecked(true);
            mViewHolder.mBut_Support.setText(" 已支持 ");

        } else {
            mViewHolder.mBut_Support.setChecked(false);
            mViewHolder.mBut_Support.setText(" 支持 ");
        }
        // if (mBean.getIsSuggest().equals("1"))
        final View finalConvertView = convertView;
        mViewHolder.mBut_Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition=position;
                HTTPHelper.SupportMotion(mIbpi, mBean.getId());
//                mViewHolder.mBut_Support.setChecked(true);
//                mViewHolder.mBut_Support.setText(" 已支持 ");
//                if (mResultData!=null){
//                    mViewHolder.mTv_Support.setText("支持率："+mResultData.getVote_percent()+"%");
//                }else{
//                    Log.e(TAG,"mResultData null");
//
//                }

            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent_report = new Intent(context, AutonomousAct_Third.class);
                mIntent_report.putExtra("title", TAG_MOTION_DETAIL);
                mIntent_report.putExtra("motion_id", mBean.getId());
                context.startActivity(mIntent_report);
            }
        });

        mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
            @Override
            public void onError(int id, String message) {
                Log.e(TAG, "onError");
                HighCommunityUtils.GetInstantiation().ShowToast(message, 0);

            }

            @Override
            public void onSuccess(Object message) {
                Log.e(TAG, "onSuccess");
                if (message == null) return;
                mResultData = (Auto_SupportedResultBean.SupportedResultDataEntity) message;
                Toast.makeText(context, "已支持", Toast.LENGTH_SHORT).show();
                mList.get(clickPosition).setVote_percent(mResultData.getVote_percent() + "");
                mList.get(clickPosition).setIsSuggest("1");
                notifyDataSetChanged();
            }
            @Override
            public Object onResolve(String result) {

                return HTTPHelper.ResolveSupportedResultData(result);
            }

            @Override
            public void setAsyncTask(AsyncTask asyncTask) {

            }

            @Override
            public void cancleAsyncTask() {

            }
        };


        return convertView;
    }


    private PopupWindow mWatingWindow;

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


    class ViewHolder {
        TextView mTitle;
        TextView mTime;
        TextView mTv_Support;
        CheckedTextView mBut_Support;


    }


}
