package com.wyj.mvp.contract;

import com.wyj.mvp.entity.zhihu.ZhiHuDaily;
import com.wyj.mvp.view.IView;

/**
 * @author wangyujie
 * @date 2018/10/8.14:56
 * @describe 添加描述
 */
public class ZhiHuContract {
    public interface View extends IView {
        void onSuccess(ZhiHuDaily zhiHuDetails);

        void onError(String message);

        void onComplete();
    }

    public interface Presenter {
        void getStories();
    }

}
