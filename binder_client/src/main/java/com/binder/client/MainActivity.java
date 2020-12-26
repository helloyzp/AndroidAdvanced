package com.binder.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.binder.service.IMyAidl;
import com.binder.service.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private IMyAidl iMyAidl;

    private Button btn;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected(), name=" + name + " ,service=" + service);
            //onServiceConnected(), name=ComponentInfo{com.binder.service/com.binder.service.MyService} ,service=android.os.BinderProxy@305651b

            iMyAidl = IMyAidl.Stub.asInterface(service);

            Log.i(TAG, "iMyAidl=" + iMyAidl);
            //iMyAidl=com.binder.service.IMyAidl$Stub$Proxy@e6c5045
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected(), name=" + name);
            iMyAidl = null;
        }
    };

    private ServiceConnection mlocalserviceconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected(), name=" + name + " ,service=" + service);
            //onServiceConnected(), name=ComponentInfo{com.binder.client/com.binder.service.MyLocalService} ,service=com.binder.service.MyLocalService$1@e6c5045
            //MyLocalService$1@e6c5045其实就是MyLocalService中创建的Stub对象(准确说是Stub的匿名子类对象)

            iMyAidl = IMyAidl.Stub.asInterface(service);

            Log.i(TAG, "iMyAidl=" + iMyAidl);
            //iMyAidl=com.binder.service.MyLocalService$1@e6c5045
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected(), name=" + name);
            iMyAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_client);

        initView();
        bindService();

        //测试如果Service和客户端在同一个进程的情况
        //bindLocalService();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.but_click);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iMyAidl.addPerson(new Person("流川枫", 3));
                    List<Person> persons = iMyAidl.getPersonList();
                    Log.e(TAG, persons.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * MyService与客户端进程不在同一个进程
     */
    private void bindService() {
        Log.i(TAG, "bindService()");
        Intent intent = new Intent();
        //注意pkg参数是取applicationId的值，不是取AndroidManifest.xml中package的值（applicationId的值可能与package的值不一样）
        intent.setComponent(new ComponentName("com.binder.service", "com.binder.service.MyService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 测试如果Service和客户端在同一个进程的情况
     * MyLocalService与客户端进程在同一个进程，这种情况下，onServiceConnected()回调方法的service参数就是MyLocalService中创建的Stub对象
     */
    private void bindLocalService() {
        Log.i(TAG, "bindLocalService()");
        Intent intent = new Intent();
        //注意pkg参数是取applicationId的值，不是取AndroidManifest.xml中package的值（applicationId的值可能与package的值不一样）
        intent.setComponent(new ComponentName("com.binder.client", "com.binder.service.MyLocalService"));
        bindService(intent, mlocalserviceconnection, Context.BIND_AUTO_CREATE);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        unbindService(mlocalserviceconnection);
    }


}

