package com.wyj.treasure.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin
 * on 2017/8/30.
 * TODO
 */

public class RecommendFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private GridLayoutManager layoutManager;

    private RecommendAdapter adapter;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recommend, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void processData() {
        layoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecommendAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class RecommendAdapter extends RecyclerView.Adapter<RecommendHolder> {
        @Override
        public int getItemCount() {
            return 30;
        }

        @Override
        public RecommendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            return new RecommendHolder(inflater.inflate(R.layout.item_fragment_recommend, parent, false));
        }

        @Override
        public void onBindViewHolder(RecommendHolder holder, int position) {

        }
    }

    private class RecommendHolder extends RecyclerView.ViewHolder {
        public RecommendHolder(View itemView) {
            super(itemView);
        }
    }
}
