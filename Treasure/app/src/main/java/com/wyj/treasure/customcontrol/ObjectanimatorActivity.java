package com.wyj.treasure.customcontrol;

import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ObjectanimatorActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.mypointview)
    MyPointView mypointview;
    private ObjectAnimator animator;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_object_animator;

    }

    @Override
    protected void initData() {
        setTitle("ObjectAnimator的使用");
    }

    @OnClick({R.id.bt_start, R.id.bt_start_argb,R.id.bt_start_rotation, R.id.bt_start_point})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                /**
                 * 第一个参数用于指定这个动画要操作的是哪个控件
                 * 第二个参数用于指定这个动画要操作这个控件的哪个属性
                 * 第三个参数是可变长参数，这个就跟ValueAnimator中的可变长参数的意义一样了，
                 * 就是指这个属性值是从哪变到哪。像我们上面的代码中指定的就是将textview的alpha属性从0变到1再变到0；
                 * */
                animator = ObjectAnimator.ofFloat(tv, "alpha", 1, 0,1);
                animator.setDuration(3000);
                animator.start();
                break;
            case R.id.bt_start_rotation:
                animator = ObjectAnimator.ofFloat(tv, "rotationY", 0, 270);
                animator.setDuration(2000);
                animator.start();
                break;
            case R.id.bt_start_argb:

                break;
            case R.id.bt_start_point:
                animator = ObjectAnimator.ofFloat(mypointview, "pointRadius", 0, 270,100);
                animator.setDuration(2000);
                animator.start();
                break;
        }
    }
}
