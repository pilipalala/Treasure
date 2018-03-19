package com.wyj.materialdesign.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wyj.treasure.R;


/**
 * Created by Administrator on 2017/2/23.
 */
public class PaletteFragment extends Fragment {

    private static final int[] drawables = {R.mipmap.one, R.mipmap.two, R.mipmap.four, R.mipmap
            .three, R.mipmap.five};
    private static final String ARG_POSITION = "position";
    private int position;

    public static PaletteFragment newInstance(int position) {
        PaletteFragment frament = new PaletteFragment();

        /*通过bundle传递position*/
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        frament.setArguments(bundle);

        return frament;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(params);
        frameLayout.setBackgroundResource(drawables[position]);
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());

        TextView textView = new TextView(getActivity());
        params.setMargins(margin, margin, margin, margin);
        textView.setLayoutParams(params);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.BOTTOM);
        textView.setText("CARD " + (position + 1));

        frameLayout.addView(textView);
        return frameLayout;
    }

    /**
     * 提供当前Fragment的主色调的bitmap对象，供Palette解析颜色
     *
     * @param selectViewPagerItem
     * @return
     */
    public static int getBackgroundBitmapPosition(int selectViewPagerItem) {
        return drawables[selectViewPagerItem];
    }
}
