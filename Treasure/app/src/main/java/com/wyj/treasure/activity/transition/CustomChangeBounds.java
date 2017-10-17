package com.wyj.treasure.activity.transition;


import android.animation.Animator;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

/**
 * Created by wangyujie
 * on 2017/10/13.14:33
 * TODO
 */

public class CustomChangeBounds extends ChangeBounds {

    /**
     * @param sceneRoot   屏幕根View，即DecorView，第二个Activity的DecorView。
     *
     * @param startValues 属性动画的起始属性值，TransitionValues 对象内部有各Map类型的属性values，
     *                    用于保存需要执行属性动画的属性。这个里面的属性值是在函数captureStartValues里放置，
     *                    因此你可以重写captureStartValues函数，并把你自定义的属性动画中的属性放进去
     * @param endValues   与startValues类似，表示属性动画结束时的属性值。可
     *                    以通过重写captureEndValues函数，
     *                    并把你自定义的属性动画里面的最终属性值放进去
     * @return
     */
    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues==null||endValues==null||sceneRoot==null)
            return null;
        Animator animator = super.createAnimator(sceneRoot, startValues, endValues);

        animator.setDuration(300);
        animator.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(),
                android.R.interpolator.fast_out_slow_in));
        return animator;


    }
}
