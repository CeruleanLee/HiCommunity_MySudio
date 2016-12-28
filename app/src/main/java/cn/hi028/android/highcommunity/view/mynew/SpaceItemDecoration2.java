package cn.hi028.android.highcommunity.view.mynew;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @说明：
 * @作者： Lee_yting
 * @时间：2016/12/28 0028
 */
public class SpaceItemDecoration2 extends RecyclerView.ItemDecoration{

    private int space;
   int  SPAN_COUNT=2;
    public SpaceItemDecoration2(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) != 0){


//            outRect.top = space;
//
////        对于网格布局：
//// 网格布局，从第二列开始，left = mSpace，从第二行开始 top = mSpace
        int pos = parent.getChildLayoutPosition(view);
//        if (pos % SPAN_COUNT != 0) {
//            outRect.left = mSpace;
//        }
//        if (pos >= SPAN_COUNT) {
//            outRect.top = mSpace;
//        }


        // 设置左右间距
        outRect.set(space / 2, 0, space / 2, 0);

// 从第二行开始 top = mSpace
        if (pos >= SPAN_COUNT) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }

        }

    }
}


///然后在使用RecyclerView的地方：

//    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
//mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
//
//
//        Adapter填充上，再看看item就有间距了
