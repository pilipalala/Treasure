package com.wyj.treasure.utils;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

public final class ClickBinder {
    public static void bind(final View view, final OnViewClickListener listener) {
        bind(view, 500, TimeUnit.MILLISECONDS, listener);
    }

    /**
     * @param view
     * @param duration
     * @param listener
     */
    public static void bind(final View view, long duration, final OnViewClickListener listener) {
        bind(view, duration, TimeUnit.SECONDS, listener);
    }

    public static void bindViews(OnViewClickListener listener, View... views) {
        for (View view : views) {
            bind(view, 500, TimeUnit.MILLISECONDS, listener);
        }

    }

    public static void bind(final View view, long duration, TimeUnit timeUnit, final OnViewClickListener listener) {
            RxView.clicks(view)
                    .throttleFirst(duration, timeUnit)
                    .subscribe(aVoid -> {
                        if (listener != null) {
                            listener.onViewClick(view);
                        }
                    });
    }

    public interface OnViewClickListener {
        void onViewClick(View v);
    }
}
