package com.wyj.treasure.activity.itemtouch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by wangyujie
 * on 2017/10/28.9:07
 * TODO
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final ItemTouchHelperAdapterCallback adapterCallback;

    public ItemTouchHelperCallback(ItemTouchHelperAdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }

    /**
     * CallBack 回调监听时 先调用的，用来判断当前是什么动作，
     * 比如
     * 判断是否需要监听哪个动作：侧滑、拖拽
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 当移动的时候回调的方法  --  拖拽
     *
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //1、刷新数据
        //2、刷新adapter
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        /*在拖拽过程中不断调用adapter.notifyItemMoved(frome , to)*/
        adapterCallback.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 侧滑时候回调的
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        /*侧滑监听 删除数据*/
        adapterCallback.onItemSwiped(viewHolder.getAdapterPosition());
    }

    /**
     * 是否允许长按拖拽效果
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //侧滑缩放、透明度
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //0~1:0~width
            float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);
            if (alpha <= 0) {
                viewHolder.itemView.setAlpha(1);
                viewHolder.itemView.setScaleX(1);
                viewHolder.itemView.setScaleY(1);
            }
            if (dX <= -0.5f * viewHolder.itemView.getWidth()) {
                viewHolder.itemView.setTranslationX(-0.5f * viewHolder.itemView.getWidth());
            } else {
                viewHolder.itemView.setTranslationX(dX);
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        /*判断选中状态*/
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.RED);

        }
        super.onSelectedChanged(viewHolder, actionState);

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //恢复
        viewHolder.itemView.setBackgroundColor(Color.WHITE);

        super.clearView(recyclerView, viewHolder);
    }
}
