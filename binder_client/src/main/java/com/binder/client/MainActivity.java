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

    private void bindService() {
        Log.i(TAG, "bindService()");
        Intent intent = new Intent();
        //注意pkg参数是取applicationId的值，不是取AndroidManifest.xml中package的值（applicationId的值可能与package的值不一样）
        intent.setComponent(new ComponentName("com.binder.service", "com.binder.service.MyService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }


}

