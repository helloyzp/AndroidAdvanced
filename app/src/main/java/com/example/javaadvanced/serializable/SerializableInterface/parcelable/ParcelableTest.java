package com.example.javaadvanced.serializable.SerializableInterface.parcelable;

import android.os.Parcel;
import android.util.Log;

public class ParcelableTest {

    public static String TAG = "ParcelableTest";

    public static void main(String[] args) {

        //往parcel中写入数据
        Parcel parcel = Parcel.obtain();
        parcel.writeInt(19);
        parcel.writeInt(12);

        //序列化
        byte[] bs = parcel.marshall();
        Log.i(TAG,"bs = $bs, ${bs.size}");

        parcel.setDataPosition(0);
        parcel.recycle();

        //反序列化
        parcel = Parcel.obtain();
        parcel.unmarshall(bs,0,bs.length);

        int  size = parcel.dataSize();
        for(int i = 0; i< size; i=i+4){
            parcel.setDataPosition(i);
            Log.i(TAG,"value = " + parcel.readInt());
        }
        parcel.recycle();
    }

}
