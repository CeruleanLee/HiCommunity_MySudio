package cn.hi028.android.highcommunity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.don.tools.BpiHttpHandler;

import net.duohuo.dhroid.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hi028.android.highcommunity.R;
import cn.hi028.android.highcommunity.activity.AutonomousAct_Third;
import cn.hi028.android.highcommunity.bean.Autonomous.Auto_MotionBean;
import cn.hi028.android.highcommunity.utils.HTTPHelper;
import cn.hi028.android.highcommunity.utils.HighCommunityUtils;
import cn.hi028.android.highcommunity.utils.TimeUtil;

/**
 * @功能：自治大厅 提案adapter<br>
 * @作者： Lee_yting<br>
 * @时间：2016/10/12<br>
 */
public class AutoMoitionAdapter extends BaseFragmentAdapter {

    public static final String TAG ="~~~AutoMoitionAdapter";
    public static final int TAG_MOTION_DETAIL = 2;
    List<Auto_MotionBean.MotionDataEntity> mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
    private Context context;
    private LayoutInflater layoutInflater;
    View view;
    public AutoMoitionAdapter(List<Auto_MotionBean.MotionDataEntity> list, Context context,View view) {
        super();
        this.mList = list;
        if (this.mList == null) {
            this.mList = new ArrayList<Auto_MotionBean.MotionDataEntity>();
        }
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.view=view;
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
    ViewHolder mViewHolder=null;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        }else if (mBean.getIsSuggest().equals("1")){
            mViewHolder.mBut_Support.setChecked(false);
            mViewHolder.mBut_Support.setText(" 支持 ");
        }
        final View finalConvertView = convertView;
        mViewHolder.mBut_Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mViewHolder.mBut_Support.setChecked(TURE);
//                mViewHolder.mBut_Support.setChecked(true);
//                mViewHolder.mBut_Support.setText("已支持");
                Activity act = (Activity) context;
                mWatingWindow = HighCommunityUtils.GetInstantiation().ShowWaittingPopupWindow(context, view, Gravity.CENTER);

                HTTPHelper.SupportMotion(mIbpi,mBean.getId());
//                mViewHolder.mBut_Support.setChecked(true);
//                mViewHolder.mBut_Support.setText(" 已支持 ");

            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent_report=new Intent(context, AutonomousAct_Third.class);
                mIntent_report.putExtra("title",TAG_MOTION_DETAIL);
                mIntent_report.putExtra("motion_id",mBean.getId());
                context.startActivity(mIntent_report);
            }
        });



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


    public BpiHttpHandler.IBpiHttpHandler mIbpi = new BpiHttpHandler.IBpiHttpHandler() {
        @Override
        public void onError(int id, String message) {
            mWatingWindow.dismiss();
            LogUtil.d(TAG + "---~~~onError");
            HighCommunityUtils.GetInstantiation().ShowToast(message, 0);


        }

        @Override
        public void onSuccess(Object message) {
            mWatingWindow.dismiss();
            HighCommunityUtils.GetInstantiation().ShowToast(message.toString(), 0);

            Toast.makeText(context,"已支持",Toast.LENGTH_SHORT).show();
            mViewHolder.mBut_Support.setChecked(true);
            mViewHolder.mBut_Support.setText(" 已支持 ");

//            mList = (List<Auto_MotionBean.MotionDataEntity>) message;
//            mAdapter.AddNewData(mList);
//            mListview.setAdapter(mAdapter);
//			mLoadingView.loadSuccess();
//			mLoadingView.setVisibility(View.GONE);
//			LogUtil.d(Tag+"---~~~initViewonSuccess");
////						if (null == message) return;
//			LogUtil.d(Tag+"---~~~ initView   message:"+message);
//			ThirdServiceBean mBean = (ThirdServiceBean) message;
//			mAdapter.AddNewData(mBean.getServices());
//			mGridView.setAdapter(mAdapter);
//			pagerAdapter.setImageIdList(mBean.getBanners());
//			HighCommunityUtils.GetInstantiation()
//			.setThirdServiceGridViewHeight(mGridView, mAdapter, 4);
//			tatalLayout.setVisibility(View.VISIBLE);

        }

        @Override
        public Object onResolve(String result) {
//			Log.e("renk", result);
//			LogUtil.d(Tag+"---~~~iresult"+result);
//            return HTTPHelper.ResolveMotionDataEntity(result);
            return result;
        }

        @Override
        public void setAsyncTask(AsyncTask asyncTask) {

        }

        @Override
        public void cancleAsyncTask() {

        }
    };

}
