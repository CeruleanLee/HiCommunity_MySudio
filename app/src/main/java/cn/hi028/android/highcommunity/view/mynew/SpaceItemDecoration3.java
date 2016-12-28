package cn.hi028.android.highcommunity.view.mynew;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/28 0028
 */
public class SpaceItemDecoration3 extends RecyclerView.ItemDecoration{
    private int space;

    public SpaceItemDecoration3(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.top = space;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) %2==0) {
            outRect.right = 0;
        }else if (parent.getChildLayoutPosition(view) %2==1){
            outRect.right = space;

        }
    }
}


///然后在使用RecyclerView的地方：

//    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
//mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
//
//
//        Adapter填充上，再看看item就有间距了
