package com.example.javaadvanced.serializable.SerializableInterface.parcelable;

import android.os.Parcel;

import com.example.javaadvanced.serializable.SerializableInterface.parcelable.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public interface Parcelable {
    /** @hide */
    @com.example.javaadvanced.serializable.SerializableInterface.parcelable.IntDef(flag = true, prefix = { "PARCELABLE_" }, value = {
            PARCELABLE_WRITE_RETURN_VALUE,
            PARCELABLE_ELIDE_DUPLICATES,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface WriteFlags {}

    /**
     * writeToParcel() 方法中的参数，用于标识当前对象作为返回值返回
     * 有些实现类可能会在这时释放其中的资源
     */
    public static final int PARCELABLE_WRITE_RETURN_VALUE = 0x0001;


    /**
     * writeToParcel() 方法中的第二个参数，它标识父对象会管理内部状态中重复的数据
     */
    public static final int PARCELABLE_ELIDE_DUPLICATES = 0x0002;



    /** @hide */
    @IntDef(flag = true, prefix = { "CONTENTS_" }, value = {
            CONTENTS_FILE_DESCRIPTOR,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentsFlags {}

    /**
     * 用于 describeContents() 方法的位掩码，每一位都代表着一种对象类型
     */
    public static final int CONTENTS_FILE_DESCRIPTOR = 0x0001;


    /**
     * 描述当前 Parcelable 实例的对象类型
     * 比如说，如果对象中有文件描述符，这个方法就会返回上面的 CONTENTS_FILE_DESCRIPTOR
     * 其他情况会返回一个位掩码
     * @return
     */
    public @com.example.javaadvanced.serializable.SerializableInterface.parcelable.Parcelable.ContentsFlags
    int describeContents();


    /**
     * 将对象转换成一个 Parcel 对象
     * @param dest 表示要写入的 Parcel 对象
     * @param flags 示这个对象将如何写入
     */
    public void writeToParcel(Parcel dest, @com.example.javaadvanced.serializable.SerializableInterface.parcelable.Parcelable.WriteFlags int flags);


    /**
     * 实现类必须有一个 Creator 属性，用于反序列化，将 Parcel 对象转换为 Parcelable
     * @param <T>
     */
    public interface Creator<T> {

        public T createFromParcel(Parcel source);


        public T[] newArray(int size);
    }


    /**
     * 对象创建时提供的一个创建器
     * @param <T>
     */
    public interface ClassLoaderCreator<T> extends com.example.javaadvanced.serializable.SerializableInterface.parcelable.Parcelable.Creator<T> {

        /**
         * 使用类加载器和之前序列化成的 Parcel 对象反序列化一个对象
         * @param source
         * @param loader
         * @return
         */
        public T createFromParcel(Parcel source, ClassLoader loader);
    }
}
