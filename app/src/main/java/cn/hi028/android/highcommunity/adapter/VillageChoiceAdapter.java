package cn.hi028.android.highcommunity.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 李凌云
 * @category  小区选择适配器
 */
public class VillageChoiceAdapter extends BaseExpandableListAdapter {
    Context mContext = null;
    Object mDataList = null;
    Drawable mExpand = null, mUnexpand = null, mGroup_class = null,
            mGroup_tearcher = null, mGroup_student = null;

    public VillageChoiceAdapter(Context context, Object DataList) {
        this.mContext = context;
        this.mDataList = DataList;
        Resources mResources = context.getResources();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean arg2, View convertView, ViewGroup arg4) {
        ChildViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ChildViewHolder();
//            convertView = LayoutInflater.from(mContext).inflate(
//                    R.layout.adapter_constacts_item, null);
//            mViewHolder.mIcon = (ImageView) convertView
//                    .findViewById(R.id.ConstactsItem_Icon);
//            mViewHolder.mName = (TextView) convertView
//                    .findViewById(R.id.ConstactsItem_Name);
//            mViewHolder.mLetter = (TextView) convertView
//                    .findViewById(R.id.ConstactsItem_Letter);
//            mViewHolder.mCount = (TextView) convertView
//                    .findViewById(R.id.ConstactsItem_Count);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ChildViewHolder) convertView.getTag();
        }
//        UserInfoBean bean = mDataList.getMyGroupInfoBeans().get(groupPosition)
//                .getUserList().get(childPosition);
//        mViewHolder.mName.setText(bean.getNickname());
//        String currentStr = bean.getLetter();
//        String previewStr = childPosition - 1 >= 0 ? mDataList
//                .getMyGroupInfoBeans().get(groupPosition).getUserList()
//                .get(childPosition - 1).getLetter() : "";
//                bean.getAvatar());
//        if (bean.getOfflineCount() != 0) {
//            mViewHolder.mCount.setVisibility(View.VISIBLE);
//            if (bean.getOfflineCount() > 99) {
//                mViewHolder.mCount.setText("99+");
//            } else {
//                mViewHolder.mCount.setText(bean.getOfflineCount() + "");
//            }
//        } else {
//            mViewHolder.mCount.setVisibility(View.GONE);
//        }
//        if (!currentStr.equals(previewStr)
//                && getGroup(groupPosition).getGroupID() != 1) {
//            mViewHolder.mLetter.setVisibility(View.VISIBLE);
//            mViewHolder.mLetter.setText(currentStr);
//        } else {
//            mViewHolder.mLetter.setVisibility(View.GONE);
//        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        if (mDataList.getMyGroupInfoBeans().get(groupPosition).getUserList() == null)
//            return 0;
//        return mDataList.getMyGroupInfoBeans().get(groupPosition).getUserList()
//                .size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
//        return mDataList.getMyGroupInfoBeans().get(groupPosition);
        return null;
    }

    @Override
    public int getGroupCount() {
//        return mDataList.getMyGroupInfoBeans().size();
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup arg3) {
        GroupViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new GroupViewHolder();
//            convertView = LayoutInflater.from(mContext).inflate(
//                    R.layout.adapter_constacts_item, null);
//            mViewHolder.mIcon = (ImageView) convertView
//                    .findViewById(R.id.ConstactsItem_Icon);
//            mViewHolder.mName = (TextView) convertView
//                    .findViewById(R.id.ConstactsItem_Name);
//            mViewHolder.mExpanded = (ImageView) convertView
//                    .findViewById(R.id.ConstactsItem_Expanded);
//            mViewHolder.mCount = (TextView) convertView
//                    .findViewById(R.id.ConstactsItem_Count);
//            convertView.findViewById(R.id.ConstactsItem_Line).setVisibility(
//                    View.VISIBLE);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (GroupViewHolder) convertView.getTag();
        }
//        GroupInfoBean bean = mDataList.getMyGroupInfoBeans().get(groupPosition);
//        mViewHolder.mName.setText(bean.getName());
//        int count = 0;
//        switch (bean.getGroupID()) {
//            case 0:
//                mViewHolder.mIcon.setImageDrawable(mGroup_class);
//                mViewHolder.mExpanded.setVisibility(View.GONE);
//                count = bean.getClassUserbean().getOfflineCount();
//                if (count > 99) {
//                    mViewHolder.mCount.setVisibility(View.VISIBLE);
//                    mViewHolder.mCount.setText("99+");
//                } else if (count < 99 && count > 0) {
//                    mViewHolder.mCount.setVisibility(View.VISIBLE);
//                    mViewHolder.mCount.setText(count + "");
//                } else {
//                    mViewHolder.mCount.setVisibility(View.GONE);
//                }
//                break;
//            case 1:
//                mViewHolder.mIcon.setImageDrawable(mGroup_tearcher);
//                mViewHolder.mExpanded.setVisibility(View.VISIBLE);
//                if (!isExpanded) {
//                    count = EducationApplication.mMenuListData.get(3)
//                            .getMessageCount();
//                    if (count > 99) {
//                        mViewHolder.mCount.setVisibility(View.VISIBLE);
//                        mViewHolder.mCount.setText("99+");
//                    } else if (count < 99 && count > 0) {
//                        mViewHolder.mCount.setVisibility(View.VISIBLE);
//                        mViewHolder.mCount.setText(count + "");
//                    } else {
//                        mViewHolder.mCount.setVisibility(View.GONE);
//                    }
//                } else {
//                    mViewHolder.mCount.setVisibility(View.GONE);
//                }
//                break;
//            case 2:
//                mViewHolder.mIcon.setImageDrawable(mGroup_student);
//                mViewHolder.mExpanded.setVisibility(View.VISIBLE);
//                if (!isExpanded) {
//                    count = EducationApplication.mMenuListData.get(4)
//                            .getMessageCount()
//                            - EducationApplication.mMenuListData.get(3)
//                            .getMessageCount()
//                            - MyConstants.mClassUserBean.getOfflineCount();
//                    if (count > 99) {
//                        mViewHolder.mCount.setVisibility(View.VISIBLE);
//                        mViewHolder.mCount.setText("99+");
//                    } else if (count < 99 && count > 0) {
//                        mViewHolder.mCount.setVisibility(View.VISIBLE);
//                        mViewHolder.mCount.setText(count + "");
//                    } else {
//                        mViewHolder.mCount.setVisibility(View.GONE);
//                    }
//                } else {
//                    mViewHolder.mCount.setVisibility(View.GONE);
//                }
//                break;
//
//            default:
//                break;
//        }
//        if (isExpanded) {
//            mViewHolder.mExpanded.setBackgroundDrawable(mExpand);
//        } else {
//            mViewHolder.mExpanded.setBackgroundDrawable(mUnexpand);
//        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 设置搜索结果
     *
     * @param newdata
     */
//    public void setSelectData(ClassInfoBean newdata) {
//        mDataList = null;
//        mDataList = newdata;
//        this.notifyDataSetChanged();
//    }

    /**
     * 返回子项序号
     *
     * @param groupPosition
     * @param letter
     * @return
     */
    public int getSelectChildPostionByLetter(int groupPosition, String letter) {
        int position = -1;
//        if (mDataList.getMyGroupInfoBeans().get(groupPosition)
//                .getAlphaIndexer().containsKey(letter))
//            position = mDataList.getMyGroupInfoBeans().get(groupPosition)
//                    .getAlphaIndexer().get(letter);
        return position;
    }

    private class GroupViewHolder {
        ImageView mIcon;
        ImageView mExpanded;
        TextView mName;
        TextView mCount;
    }

    private class ChildViewHolder {
        TextView mLetter;
        ImageView mIcon;
        TextView mName;
        TextView mCount;
    }
}
