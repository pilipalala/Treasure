package com.wyj.treasure.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;

/**
 * Created by wangyujie
 * on 2017/10/24.14:59
 * TODO
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {
    private int jobId = 1;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*开启第一个轮寻*/
        JobInfo.Builder jobBuilder = new JobInfo.Builder(jobId, new ComponentName(this, JobWakeUpService.class));
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobBuilder.setPeriodic(5000);

        jobScheduler.schedule(jobBuilder.build());
        return START_STICKY;

    }

    @Override
    public boolean onStartJob(JobParameters params) {
        /*开启定时任务，定时轮寻，看MyService是否被杀死*/

        boolean local = CommonUtils.isServiceWork(ProtectedService.class.getName());
        if (!local) {
            LogUtil.i("JobWakeUpService " + local);
            startService(new Intent(this, ProtectedService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
