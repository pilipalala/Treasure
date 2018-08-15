package com.wyj.treasure.activity.transition;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.adapter.BeautyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TransitionsListActivity extends BaseActivity {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.add_button)
    ImageButton addButton;
    private static String[] names = {"朱茵", "张柏芝", "张敏", "莫文蔚", "黄圣依", "赵薇", "如花"};

    private static String[] pics = {"p1", "p2", "p3", "p4", "p5", "p6", "p7"};

    private static String[] works = {"大话西游", "喜剧之王", "p3", "p4", "p5", "p6", "p7"};

    private static String[] role = {"紫霞仙子", "柳飘飘", "p3", "p4", "p5", "p6", "p7"};

    private static String[][] picGroups = {{"p1", "p1_1", "p1_2", "p1_3"}, {"p2", "p2_1", "p2_2", "p2_3"}, {"p3"}, {"p4"}, {"p5"}, {"p6"}, {"p7"}};
    public static List<Beauty> beauties = new ArrayList<>();

    private BeautyAdapter adapter;
    private boolean isAdd = true;

    @Override
    public boolean isStartAnimation() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected int initView() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Explode().setDuration(1000));
        }
        return R.layout.activity_transitions_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
       setTitle("RecycleView 跳转activity 的过渡动画");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addButton.setOutlineProvider(new ViewOutlineProvider() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    int shapeSize = (int) getResources().getDimension(R.dimen.shape_size);
                    outline.setRoundRect(0, 0, shapeSize, shapeSize, shapeSize / 2);
                }
            });
            addButton.setClipToOutline(true);
        }

        beauties.add(new Beauty(names[0], pics[0], works[0], role[0], picGroups[0]));


        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setHasFixedSize(true);
        adapter = new BeautyAdapter(this, beauties);
        list.setAdapter(adapter);


        addButton.setOnClickListener(v -> {
            Animator animator = createAnimation(v);
            animator.start();

            if (adapter.getItemCount() != names.length && isAdd) {

                beauties.add(new Beauty(names[adapter.getItemCount()], pics[adapter.getItemCount()], works[adapter.getItemCount()], role[adapter.getItemCount()], picGroups[adapter.getItemCount()]));
                list.scrollToPosition(adapter.getItemCount() - 1);
                adapter.notifyDataSetChanged();
            }
            // delete item
            else {
                beauties.remove(adapter.getItemCount() - 1);
                list.scrollToPosition(adapter.getItemCount() - 1);
                adapter.notifyDataSetChanged();
            }

            if (adapter.getItemCount() == 0) {
                addButton.setImageDrawable(getDrawable(android.R.drawable.ic_input_add));
                isAdd = true;
            }
            if (adapter.getItemCount() == names.length) {
                addButton.setImageDrawable(getDrawable(android.R.drawable.ic_delete));
                isAdd = false;
            }


        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Animator createAnimation(View v) {
        Animator animator = ViewAnimationUtils.createCircularReveal(
                v,
                v.getWidth() / 2,
                v.getHeight() / 2,
                0,
                v.getWidth());
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);


        return animator;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startActivity(final View v, final int position) {
        View pic = v.findViewById(R.id.pic);
        View add_btn = this.findViewById(R.id.add_button);

        Transition ts = new ChangeTransform();
        ts.setDuration(3000);
        getWindow().setExitTransition(ts);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(pic, position + "pic"),
                Pair.create(add_btn, "ShareBtn")).toBundle();

        Intent intent = new Intent(this, TransitionsDetailActivity.class);
        intent.putExtra("pos", position);
        startActivity(intent, bundle);

    }
}
