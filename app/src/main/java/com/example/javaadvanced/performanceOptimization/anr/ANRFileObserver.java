package com.example.javaadvanced.performanceOptimization.anr;

import android.os.FileObserver;
import android.util.Log;

import androidx.annotation.Nullable;

public class ANRFileObserver extends FileObserver {
    private String TAG = "ANRFileObserver";


    /**
     *
     * @param path 传 data/anr/ 这个目录
     */
    public ANRFileObserver(String path) {
        super(path);
    }

    public ANRFileObserver(String path, int mask) {
        super(path, mask);
    }

    /**
     * ANRFileObserver监控的路径/文件发生改变时会回调onEvent()方法，对应的各个事件类型如下
     * @param event
     * @param path
     */
    @Override
    public void onEvent(int event, @Nullable String path) {
        switch (event) {
            case FileObserver.ACCESS://文件被访问
                Log.i(TAG, "ACCESS: " + path);
                break;
            case FileObserver.ATTRIB://文件属性被修改，如 chmod、chown、touch 等
                Log.i(TAG, "ATTRIB: " + path);
                break;
            case FileObserver.CLOSE_NOWRITE://不可写文件被 close
                Log.i(TAG, "CLOSE_NOWRITE: " + path);
                break;
            case FileObserver.CLOSE_WRITE://可写文件被 close
                Log.i(TAG, "CLOSE_WRITE: " + path);
                break;
            case FileObserver.CREATE://创建新文件
                Log.i(TAG, "CREATE: " + path);
                break;
            case FileObserver.DELETE:// 文件被删除，如 rm
                Log.i(TAG, "DELETE: " + path);
                break;
            case FileObserver.DELETE_SELF:// 自删除，即一个可执行文件在执行时删除自己
                Log.i(TAG, "DELETE_SELF: " + path);
                break;
            case FileObserver.MODIFY://文件被修改
                Log.i(TAG, "MODIFY: " + path);
                break;
            case FileObserver.MOVE_SELF://自移动，即一个可执行文件在执行时移动自己
                Log.i(TAG, "MOVE_SELF: " + path);
                break;
            case FileObserver.MOVED_FROM://文件被移走，如 mv
                Log.i(TAG, "MOVED_FROM: " + path);
                break;
            case FileObserver.MOVED_TO://文件被移来，如 mv、cp
                Log.i(TAG, "MOVED_TO: " + path);
                break;
            case FileObserver.OPEN://文件被 open
                Log.i(TAG, "OPEN: " + path);
                break;
            default:
                //CLOSE ： 文件被关闭，等同于(IN_CLOSE_WRITE | IN_CLOSE_NOWRITE)
                //ALL_EVENTS ： 包括上面的所有事件
                Log.i(TAG, "DEFAULT(" + event + "): " + path);
                break;
        }
    }
}
