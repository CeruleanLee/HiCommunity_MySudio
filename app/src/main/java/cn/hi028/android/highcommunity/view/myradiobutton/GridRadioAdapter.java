package cn.hi028.android.highcommunity.view.myradiobutton;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class GridRadioAdapter extends BaseAdapter {
    private List<RadioItemModel> mList;
    private Context mContext;
    private Resources mResources;
    private OnItemCheckedListener mListener;
  //RadioButton选中时的回调函数
    public interface OnItemCheckedListener {
        void onItemChecked(RadioItemModel model, int position);
    }

    public void setOnItemCheckedListener(OnItemCheckedListener listener) {
        this.mListener = listener;
    }


    public GridRadioAdapter(Context context, List<RadioItemModel> list) {
        mContext = context;
        mList = list;
        check();

    }

    private void check() {
        if (mList.size()>=0 && mList!=null) {
//            checkDefaultChecked();
            checkMutex();
        }
    }

    /**
     * 检查互斥事件   默认只能选中一个 如果有多个选择，就选中最后一个
     */
    private void checkMutex() {
        int checkCount = 0;
        for (RadioItemModel item : mList) {
            if (item.isChecked) {
                checkCount++;
            }
        }
        if (checkCount >= 2) {
            setOtherUnChecked(checkCount - 1);
        }
    }

    private void setOtherUnChecked(int position) {
        for (int i = 0; i < mList.size(); i++) {
            if (i != position) {
                RadioItemModel item = mList.get(i);
                item.isChecked = false;
            } else {
                mList.get(position).isChecked = true;
            }
        }
    }

    public RadioItemModel getItemChecked() {
        for (int i = 0; i < mList.size(); i++) {
            RadioItemModel item = mList.get(i);
            if (item.isChecked)
                return item;
        }
        return null;
    }

    /**
     * 检查是否有默认选中的按钮  如果没有 默认第一个选中
     */
    private void checkDefaultChecked() {
        for (RadioItemModel item : mList) {
            if (item.isChecked)
                return;
        }
        mList.get(0).isChecked = true;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList != null && mList.size() > position)
            return mList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        IconRadioButton iconBean = null;
        iconBean = new IconRadioButton(mContext);
        final RadioItemModel model = mList.get(position);
        if (!TextUtils.isEmpty(model.text)) {
            iconBean.setText(model.text);
        }
        iconBean.setChecked(model.isChecked);
        if (model.textColor != null) {
            iconBean.setTextColor(model.textColor);
        }
        iconBean.hiddenRadio(model.hiddenRadio);
        final IconRadioButton finalIconBean = iconBean;
        iconBean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finalIconBean.isChecked()) {
                    setOtherUnChecked(position);
                    if (mListener != null) {
                        mListener.onItemChecked(model, position);
                    }
                    notifyDataSetChanged();

                }
            }
        });
        return iconBean;
    }

    public void addAll(List<RadioItemModel> list){
        mList.clear();
        if(list.size()>=0 && list!=null){
            mList.addAll(list);
            check();
            notifyDataSetChanged();

        }
    }

}