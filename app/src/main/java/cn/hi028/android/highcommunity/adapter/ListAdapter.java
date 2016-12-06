package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

import cn.hi028.android.highcommunity.R;

/**
 * Created by Lee_yting on 2016/12/6 0006.
 * 说明：
 */
public class ListAdapter extends BaseFragmentAdapter {
    private List<PoiInfo> dataList;
    private int checkPosition;
    Context context;
    public ListAdapter(int checkPosition, List<PoiInfo> dataList, Context context) {
        this.checkPosition = checkPosition;
        this.context=context;
        this.dataList=dataList;
    }

    public void setCheckposition(int checkPosition) {
        this.checkPosition = checkPosition;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_loclist, null);

            holder.textView = (TextView) convertView.findViewById(R.id.item_loclist_address);
            holder.textAddress = (TextView) convertView.findViewById(R.id.item_loclist_addressDetail);
//            holder.imageLl = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.i("mybaidumap", "name地址是：" + dataList.get(position).name);
        Log.i("mybaidumap", "address地址是：" + dataList.get(position).address);

        holder.textView.setText(dataList.get(position).name);
        holder.textAddress.setText(dataList.get(position).address);
        if (checkPosition == position) {
//            holder.imageLl.setVisibility(View.VISIBLE);
        } else {
//            holder.imageLl.setVisibility(View.GONE);
        }


        return convertView;
    }

    class ViewHolder {
        TextView textView;
        TextView textAddress;
//        ImageView imageLl;
    }

    @Override
    public void AddNewData(Object mObject) {
        if (mObject instanceof List<?>) {
            dataList = (List<PoiInfo>) mObject;
        }
        notifyDataSetChanged();
        super.AddNewData(mObject);
    }
    public void ClearData() {
        dataList.clear();
        notifyDataSetChanged();
    }
}

