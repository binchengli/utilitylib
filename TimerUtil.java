package com.meetingreat.mgwall.utils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {
    public interface TimerTaskCallBack{
        void onActive();
    }
    private Timer myTimer;
    public void startTimer(TimerTaskCallBack callBack,long delay,long period)
    {
        removeMyTimer();
        if(delay>0&&period>0){
            myTimer=new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(callBack!=null){
                        callBack.onActive();
                    }
                }
            },delay>0?delay:0,period);
        }else if(delay>0){
            myTimer=new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(callBack!=null){
                        callBack.onActive();
                    }
                }
            },delay);
        }

    }

    public void removeMyTimer()
    {
        if(myTimer!=null)
        {
            myTimer.cancel();
            myTimer.purge();
            myTimer=null;
        }

    }
}
