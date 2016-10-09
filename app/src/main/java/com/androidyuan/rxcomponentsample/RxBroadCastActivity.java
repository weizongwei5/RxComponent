package com.androidyuan.rxcomponentsample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.androidyuan.rxbroadcast.RxLocalBroadCastManager;
import com.androidyuan.rxbroadcast.component.RxBroadCastReceiver;
import com.androidyuan.rxbroadcast.component.RxBroadCastReceiverBackground;
import com.androidyuan.rxbroadcast.component.RxOnReceive;

public class RxBroadCastActivity extends AppCompatActivity implements RxOnReceive {

    RxBroadCastReceiver broadCastReceiverAsync = new RxBroadCastReceiver(this);
    RxBroadCastReceiver broadCastReceiverback = new RxBroadCastReceiverBackground(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        broadCastReceiverAsync.putFilter("testthread");
        broadCastReceiverAsync.commit();

        broadCastReceiverback.putFilter("testthread");
        broadCastReceiverback.commit();
        //the test repeated registration.
        broadCastReceiverback.commit();
        broadCastReceiverback.commit();
        broadCastReceiverback.commit();

        RxLocalBroadCastManager.getInstance().sendBroadcast("testthread", "scream");


    }

    @SuppressLint("LongLogTag")

    /**
     * 测试发现 多次 commit 都没有收到多条 解决 使用　LocalBroadCastManager　时带来的问题
     */
    @Override
    public void call(Object o) {

        Log.d("RxLocalBroadCastManager send:",
                "" + o + " ,onReceive is MainThraed=" + isMainTread());
    }

    private boolean isMainTread() {

        return Looper.myLooper() == Looper.getMainLooper();
    }

    // we need unRegister.
    @Override
    protected void onDestroy() {

        super.onDestroy();


        broadCastReceiverAsync.unRegister();
        broadCastReceiverback.unRegister();
    }
}