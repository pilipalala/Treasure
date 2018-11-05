package com.wyj.treasure.activity.transition;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TransitionsDetailActivity extends BaseActivity {

    @BindView(R.id.detail_pic)
    ImageView detailPic;
    @BindView(R.id.detail_btn)
    ImageButton detailBtn;
    @BindView(R.id.detail_name)
    TextView detailName;
    @BindView(R.id.detail_works)
    TextView detailWorks;
    @BindView(R.id.detail_role)
    TextView detailRole;
    private int position;
    private Beauty beauties;
    private int picIndex = 0;

    @Override
    public boolean isStartAnimation() {
        return false;
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_transitions_detail;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        position = getIntent().getIntExtra("pos", 0);
        beauties = TransitionsListActivity.beauties.get(position);
        detailPic.setTransitionName(position + "pic");
        detailPic.setImageDrawable(getDrawable(beauties.getImageResourceId(this)));
        detailName.setText("姓名：" + beauties.name);
        detailWorks.setText("代表作"+beauties.works);
        detailRole.setText("饰演" + beauties.role);
        detailBtn.setImageDrawable(getDrawable(android.R.drawable.ic_menu_gallery));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        close();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void close() {
        detailPic.setImageDrawable(getDrawable(beauties.getImageResourceId(this, beauties.picName)));
        finishAfterTransition();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void doSecondAnim() {
        detailPic.setImageDrawable(getDrawable(beauties.getImageResourceId(this, beauties.getPics()[picIndex])));
        Animator animator = createAnimation(detailPic, false);
        animator.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Animator createAnimation(ImageView view, boolean isFirst) {
        Animator animator;

        if (isFirst) {
            animator = ViewAnimationUtils.createCircularReveal(
                    view,
                    view.getWidth() / 2,
                    view.getHeight() / 2,
                    view.getWidth(),
                    0);
        } else {
            animator = ViewAnimationUtils.createCircularReveal(
                    view,
                    view.getWidth() / 2,
                    view.getHeight() / 2,
                    0,
                    view.getWidth());
        }

        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500);



//        animator.setInterpolator(new LinearOutSlowInInterpolator());//out到in
//        Animator animator1 = ViewAnimationUtils.createCircularReveal(
//                v, 0, 0, 0, (float) Math.hypot(v.getWidth(), v.getHeight()));//宽的平方加上高的平方的根号
//        animator1.setInterpolator(new LinearInterpolator());//插补器有没有不影响
//        animator1.setDuration(2000);
        return animator;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.detail_btn)
    public void onViewClicked() {

        Animator animator = createAnimation(detailPic, true);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                picIndex++;
                if (beauties.getPics() != null) {
                    if (picIndex >= beauties.getPics().length) {
                        picIndex = 0;
                    }
                    doSecondAnim();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
}
