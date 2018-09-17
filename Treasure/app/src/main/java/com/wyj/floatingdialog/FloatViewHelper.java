package com.wyj.floatingdialog;

import android.content.Context;

import com.wyj.treasure.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyujie
 * @date 2018/9/11.11:31
 * @describe
 */
public class FloatViewHelper {
    private static final String TAG = "FloatViewHelper";
    private static List<String> notShowList = new ArrayList();

    static {
        //添加不需要显示的页面
//        notShowList.add("MainActivity");
    }

    public static void showFloatView(Context context, BaseFloatView floatView) {
        //应用不在前台  或 当前 activity 在过滤列表中
        if (floatView == null || !CommonUtils.isAppOnForeground(context)
                || CommonUtils.isTargetRunningForeground(context, notShowList)) {
            return;
        }
        floatView.show();
    }

    public static void hideFloatView(Context context, BaseFloatView floatView) {
        //应用在后台  且 当前 activity 在过滤列表中
        if (CommonUtils.isAppOnForeground(context) && CommonUtils.isTargetRunningForeground(context, notShowList)) {
            return;
        }
        if (floatView == null || floatView.getWindowToken() == null) {
            return;
        }
        floatView.hide();
    }

    public static void addFilterActivities(List<String> activityNames) {
        notShowList.addAll(activityNames);
    }
}
