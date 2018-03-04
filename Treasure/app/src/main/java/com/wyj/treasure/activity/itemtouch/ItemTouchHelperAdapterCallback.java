package com.wyj.treasure.activity.itemtouch;

/**
 * Created by wangyujie
 * on 2017/10/28.9:09
 * TODO
 */


public interface ItemTouchHelperAdapterCallback {
    /**
     * 当拖拽的时候调用
     * 可以在此方法里面实现:拖拽条目并实现刷新效果
     *
     * @param fromPosition 从什么位置托动
     * @param toPosition 到什么位置
     * @return 是否回调了
     * */
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemSwiped(int position);
}