package com.wyj.treasure.activity.itemtouch;

/**
 * Created by wangyujie
 * on 2017/10/28.9:09
 * TODO
 */


public interface ItemTouchHelperAdapterCallback {
    //移动
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemSwiped(int position);
}