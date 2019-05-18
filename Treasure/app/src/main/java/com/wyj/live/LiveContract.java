package com.wyj.live;

import com.wyj.mvp.view.IView;

/**
 * @author wangyujie
 * @date 2018/10/8.14:56
 * @describe 添加描述
 */
public class LiveContract {
    public interface View extends IView {
        void onSuccess(LivePingTai livePingTai);

        void onError(String message);

        void onComplete();
    }

    public interface Presenter {
        void getLivePingTai();
    }

}
