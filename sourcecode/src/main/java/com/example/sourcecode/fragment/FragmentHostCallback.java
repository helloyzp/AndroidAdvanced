/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sourcecode.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Preconditions;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManagerImpl;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Integration points with the Fragment host.
 * <p>
 * Fragments may be hosted by any object; such as an {@link Activity}. In order to
 * host fragments, implement {@link FragmentHostCallback}, overriding the methods
 * applicable to the host.
 * fragment可以被任何对象持有，要使一个对象具有持有和管理fragment生命周期的能力，我们只需要实现FragmentHostCallback中的函数
 * 用来管理Fragment生命周期，同时作为代理提供fragment需要与外界打交道时的函数实现的比如从fragment中启动新的Activity, 请求权限等等。
 * 其Fragment生命周期的的管理由FragmentManager负责，其余部分代理功能由其自己负责
 * 
 * Fragment宿主的回调接口，要使一个对象具有持有和管理fragment生命周期的能力，我们只需要实现FragmentHostCallback中的函数。
 * 当我们对Fragment进行操作的时候，就会对调用FragmentController的操作，从而间接的调用FragmentHostCallback对象。
 * 在FragmentHostCallback中，暴露出了对Fragment操作的一些接口，通过重写这些接口，
 * 可以将Fragment操作与Fragment宿主（例如FragmentActivity）捆绑起来
 */
public abstract class FragmentHostCallback<E> extends FragmentContainer {
    @Nullable private final Activity mActivity;
    @NonNull private final Context mContext;
    @NonNull private final Handler mHandler;
    private final int mWindowAnimations;
    final FragmentManagerImpl mFragmentManager = new FragmentManagerImpl();

    public FragmentHostCallback(@NonNull Context context, @NonNull Handler handler,
            int windowAnimations) {
        this(context instanceof Activity ? (Activity) context : null, context, handler,
                windowAnimations);
    }

    FragmentHostCallback(@NonNull androidx.fragment.app.FragmentActivity activity) {
        this(activity, activity /*context*/, activity.mHandler, 0 /*windowAnimations*/);
    }

    FragmentHostCallback(@Nullable Activity activity, @NonNull Context context,
            @NonNull Handler handler, int windowAnimations) {
        mActivity = activity;
        mContext = Preconditions.checkNotNull(context, "context == null");
        mHandler = Preconditions.checkNotNull(handler, "handler == null");
        mWindowAnimations = windowAnimations;
    }

    /**
     * Print internal state into the given stream.
     *
     * @param prefix Desired prefix to prepend at each line of output.
     * @param fd The raw file descriptor that the dump is being sent to.
     * @param writer The PrintWriter to which you should dump your state. This will be closed
     *                  for you after you return.
     * @param args additional arguments to the dump request.
     */
    public void onDump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
    }

    /**
     * Return {@code true} if the fragment's state needs to be saved.
     */
    public boolean onShouldSaveFragmentState(androidx.fragment.app.Fragment fragment) {
        return true;
    }

    /**
     * Return a {@link LayoutInflater}.
     * See {@link Activity#getLayoutInflater()}.
     */
    @NonNull
    public LayoutInflater onGetLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    /**
     * Return the object that's currently hosting the fragment. If a {@link androidx.fragment.app.Fragment}
     * is hosted by a {@link androidx.fragment.app.FragmentActivity}, the object returned here should be
     * the same object returned from {@link androidx.fragment.app.Fragment#getActivity()}.
     */
    @Nullable
    public abstract E onGetHost();

    /**
     * Invalidates the activity's options menu.
     * See {@link androidx.fragment.app.FragmentActivity#supportInvalidateOptionsMenu()}
     */
    public void onSupportInvalidateOptionsMenu() {
    }

    /**
     * Starts a new {@link Activity} from the given fragment.
     * See {@link androidx.fragment.app.FragmentActivity#startActivityForResult(Intent, int)}.
     */
    public void onStartActivityFromFragment(androidx.fragment.app.Fragment fragment, Intent intent, int requestCode) {
        onStartActivityFromFragment(fragment, intent, requestCode, null);
    }

    /**
     * Starts a new {@link Activity} from the given fragment.
     * See {@link androidx.fragment.app.FragmentActivity#startActivityForResult(Intent, int, Bundle)}.
     */
    public void onStartActivityFromFragment(
            androidx.fragment.app.Fragment fragment, Intent intent, int requestCode, @Nullable Bundle options) {
        if (requestCode != -1) {
            throw new IllegalStateException(
                    "Starting activity with a requestCode requires a FragmentActivity host");
        }
        mContext.startActivity(intent);
    }

    /**
     * Starts a new {@link IntentSender} from the given fragment.
     * See {@link Activity#startIntentSender(IntentSender, Intent, int, int, int, Bundle)}.
     */
    public void onStartIntentSenderFromFragment(androidx.fragment.app.Fragment fragment, IntentSender intent,
                                                int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues,
                                                int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        if (requestCode != -1) {
            throw new IllegalStateException(
                    "Starting intent sender with a requestCode requires a FragmentActivity host");
        }
        ActivityCompat.startIntentSenderForResult(mActivity, intent, requestCode, fillInIntent,
                flagsMask, flagsValues, extraFlags, options);
    }

    /**
     * Requests permissions from the given fragment.
     * See {@link androidx.fragment.app.FragmentActivity#requestPermissions(String[], int)}
     */
    public void onRequestPermissionsFromFragment(@NonNull androidx.fragment.app.Fragment fragment,
            @NonNull String[] permissions, int requestCode) {
    }

    /**
     * Checks whether to show permission rationale UI from a fragment.
     * See {@link FragmentActivity#shouldShowRequestPermissionRationale(String)}
     */
    public boolean onShouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }

    /**
     * Return {@code true} if there are window animations.
     */
    public boolean onHasWindowAnimations() {
        return true;
    }

    /**
     * Return the window animations.
     */
    public int onGetWindowAnimations() {
        return mWindowAnimations;
    }

    @Nullable
    @Override
    public View onFindViewById(int id) {
        return null;
    }

    @Override
    public boolean onHasView() {
        return true;
    }

    @Nullable
    Activity getActivity() {
        return mActivity;
    }

    @NonNull
    Context getContext() {
        return mContext;
    }

    @NonNull
    Handler getHandler() {
        return mHandler;
    }

    FragmentManagerImpl getFragmentManagerImpl() {
        return mFragmentManager;
    }

    void onAttachFragment(Fragment fragment) {
    }
}
