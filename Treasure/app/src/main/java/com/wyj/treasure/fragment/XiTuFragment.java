package com.wyj.treasure.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.XiTuAdapter;


/**
 * Created by Administrator on 2017/1/3.
 */
@SuppressLint("ValidFragment")
public class XiTuFragment extends Fragment {
    private static final String TAG = "XiTuFragment";
    private final String title;


    public String getTitle() {
        return title;
    }

    private final int num;
    private RecyclerView myRecyclerView;
    private Context mContext;

    @SuppressLint("ValidFragment")
    public XiTuFragment(String title, int num) {
        this.title = title;
        this.num = num;
    }

    /**
     * 得到上下文
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    /**
     * 显示视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_item, null);
        myRecyclerView = view.findViewById(R.id.myRecyclerView);
        return view;

    }

    /**
     * 绑定数据
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myRecyclerView.setAdapter(new XiTuAdapter(mContext, num));
        /**
         * 第一个参数是Context，
         * 第二个参数是布局方向，其值可以取
         * LinearLayoutManager.HORIZONTAL 水平
         * LinearLayoutManager.VERTICAL 垂直
         * 第三个参数是是否逆向布局如果设置为true，*/
        myRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        Log.e(TAG, "onActivityCreated: "+num);
    }

}
