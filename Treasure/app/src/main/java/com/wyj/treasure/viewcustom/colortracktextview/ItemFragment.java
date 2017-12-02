package com.wyj.treasure.viewcustom.colortracktextview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ItemFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.tv_item)
    TextView tvItem;
    Unbinder unbinder;

    private String mParam1;


    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * @param param1 Parameter 1.
     * @return
     */
    public static ItemFragment newInstance(String param1) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("position--->"+1);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        LogUtil.e("position--->"+2);
        unbinder = ButterKnife.bind(this, view);
        tvItem.setText(mParam1);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
