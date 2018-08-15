package com.wyj.SkateMenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkateMenuActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    private ArrayList<MyBean> myBeans;
    private SkateAdapter skateAdapter;
    SlideLayout mLayout;


    @Override
    protected int initView() {
        return R.layout.activity_skate_menu;
    }

    @Override
    protected void initData() {
        myBeans = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            MyBean bean = new MyBean("Content" + i);
            myBeans.add(bean);
        }
        skateAdapter = new SkateAdapter();
        listView.setAdapter(skateAdapter);
    }

    private class SkateAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return myBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return myBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder holder = null;
            if (convertView == null) {
                holder = new MyViewHolder();
                convertView = LayoutInflater.from(SkateMenuActivity.this).inflate(R.layout.item_main, null);
                holder.item_content = convertView.findViewById(R.id.item_content);
                holder.item_menu = convertView.findViewById(R.id.item_menu);

                convertView.setTag(holder);

            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

            holder.item_content.setText(myBeans.get(position).title);

            holder.item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(myBeans.get(position).title);
                }
            });
            holder.item_menu.setOnClickListener(v -> {
                SlideLayout slideLayout = (SlideLayout) v.getParent();
                slideLayout.closeMenu();
                myBeans.remove(position);
                notifyDataSetChanged();
                ToastUtil.show("删除第" + position + "行");
            });

            final SlideLayout slideLayout = (SlideLayout) convertView;
            slideLayout.setOnStateChangeListener(new SlideLayout.OnStateChangeListener() {
                @Override
                public void onClose(SlideLayout layout) {
                    if (mLayout == layout) {
                        mLayout = null;
                    }
                }

                @Override
                public void onDown(SlideLayout layout) {

                    if (mLayout != null && mLayout != layout) {
                        mLayout.closeMenu();
                    }
                }

                @Override
                public void onOpen(SlideLayout layout) {
                    mLayout = layout;
                }
            });


            return convertView;
        }

        public class MyViewHolder {
            private TextView item_content;
            private TextView item_menu;


        }
    }


}
