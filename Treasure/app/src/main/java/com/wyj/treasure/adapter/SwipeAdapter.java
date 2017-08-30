package com.wyj.treasure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.MyUtils;
import com.wyj.treasure.widget.SlidingButtonView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * on 2017/8/23.
 * TODO
 */

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.MyViewHolder> implements SlidingButtonView.IonSlidingButtonListener {
    private Context mContext;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private List<String> mDatas = new ArrayList<String>();
    private SlidingButtonView mMenu = null;

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);

    }

    public void addData(int position) {
        mDatas.add(position, "item");
        notifyItemInserted(position);
    }

    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);

        void onDeleteBtnCilck(View view, int position);
    }

    public SwipeAdapter(Context context) {
        mContext = context;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
        for (int i = 0; i < 10; i++) {
            mDatas.add(i + "");
        }
    }

    @Override
    public SwipeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(SwipeAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
        holder.layout_content.getLayoutParams().width = MyUtils.getScreenWidth(mContext);
        holder.textView.setOnClickListener(v -> {
            //判断是否有删除菜单打开
            if (menuIsOpen()) {
                closeMenu();//关闭菜单
            } else {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onItemClick(v, n);
            }
        });
        holder.btn_Delete.setOnClickListener(v -> {
            int n = holder.getLayoutPosition();
            mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView btn_Delete;
        public TextView textView;
        public ViewGroup layout_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            textView = (TextView) itemView.findViewById(R.id.text);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);

            ((SlidingButtonView) itemView).setSlidingButtonListener(SwipeAdapter.this);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        Log.i("asd", "mMenuΪnull");
        return false;
    }
}
