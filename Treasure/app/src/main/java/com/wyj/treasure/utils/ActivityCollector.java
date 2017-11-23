package com.wyj.treasure.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/5/30.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    private static Activity topActivity;

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity a : activities) {
            a.finish();
        }
    }

    public static Activity getTopActivity() {
        if (activities.isEmpty()) {
            return null;
        } else {
            return activities.get(activities.size() - 1);
        }
    }
}
