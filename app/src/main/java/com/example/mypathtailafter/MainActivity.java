package com.example.mypathtailafter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private Button btnOne, btnTwo;
    private TextView text;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        text = findViewById(R.id.text);

        final MyService myService=new MyService();

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);
                btnOne.setText("已打开");
                btnTwo.setText("已关闭");
                myService.getObservable().subscribe(new Observer<AddressMessageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddressMessageBean addressMessageBean) {
                        Log.e(TAG, "纬度: " + addressMessageBean.getLatitude());
                        Log.e(TAG, "经度: " + addressMessageBean.getLongitude());
                        Log.e(TAG, "speed: " + addressMessageBean.getSpeed());
                        Log.e(TAG, "毫秒数: " + addressMessageBean.getTime());
                        Log.e(TAG, "电量: " + addressMessageBean.getElectric_quantity());
                        Log.e(TAG, "加速度方向: " + addressMessageBean.getAcceleratio_direction());
                        Log.e(TAG, "海拔: " + addressMessageBean.getElevation());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MyService.class));
                btnOne.setText("打开服务");
                btnTwo.setText("已打开");
            }
        });

    }


}
