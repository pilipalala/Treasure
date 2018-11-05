package com.wyj.handler;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AsyncTaskActivity extends BaseActivity {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private MyAsyncTask task;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_async_task;
    }

    @Override
    protected List<ItemInfo> getListData() {
        return null;
    }

    @Override
    protected void initData() {
        /**
         * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
         * 注：AsyncTask子类的实例必须在UI线程中创建
         */
        task = new MyAsyncTask();
        /**
         * 步骤3：手动调用execute(Params... params) 从而执行异步线程任务
         * 注：
         *    a. 必须在UI线程中调用
         *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
         *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute()
         *    d. 不能手动调用上述方法
         */

    }

    @OnClick({R.id.button, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                task.execute("http://www.baidu.com");
                break;
            case R.id.cancel:
                task.cancel(true);
                break;
        }
    }

    /**
     * 步骤1：创建AsyncTask子类
     * 注：
     * a. 继承AsyncTask类
     * b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     * c. 根据需求，在AsyncTask子类内实现核心方法
     */

    public class MyAsyncTask extends AsyncTask<String, Integer, String> {

        // 方法1：onPreExecute（）
        // 作用:执行 线程任务前的操作
        @Override
        protected void onPreExecute() {
            text.setText("加载中。。。");

        }

        // 方法2：doInBackground（）
        //作用:接收输入参数、执行任务中的耗时操作、返回线程任务执行的结果
        //注：必须复写，从而自定义线程任务
        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < 101; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                publishProgress(i);
            }
            return "加载完毕！";
        }

        // 方法3：onProgressUpdate（）
        //作用:在主线程 显示线程任务执行的进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            text.setText("loading..." + values[0] + "%");

        }

        // 方法4：onPostExecute（）
        //作用:接收线程任务执行结果、将执行结果显示到UI组件
        //必须复写 从而自定义UI操作
        @Override
        protected void onPostExecute(String result) {
            text.setText(result);
        }

        // 方法5：onCancelled()
        //作用:将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {
            text.setText("已取消");
            progressBar.setProgress(0);
        }
    }
}
