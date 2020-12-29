package com.binder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试如果Service和客户端在同一个进程的情况
 */
public class MyLocalService extends Service {
    private String TAG = "MyLocalService";

    private ArrayList<Person> persons;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        persons = new ArrayList<>();
        Log.e(TAG, "onBind(), iBinder=" + iBinder);
        return iBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind(), iBinder=" + iBinder);

    }

    private IBinder iBinder = new IMyAidl.Stub() {
        @Override
        public void addPerson(Person person) throws RemoteException {
            Log.i(TAG, "addPerson()", new Exception());
            persons.add(person);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return persons;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate(): success");
    }

}
