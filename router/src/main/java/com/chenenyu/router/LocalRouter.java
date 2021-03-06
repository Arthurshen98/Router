package com.chenenyu.router;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.chenenyu.router.util.RLog;

/**
 * Router for non-main process.
 * <p>
 * Created by Cheney on 2017/3/30.
 */
public class LocalRouter extends AbsRouter {
    private static final String TAG = LocalRouter.class.getSimpleName();
    private static LocalRouter sInstance = null;
    private IRouterInterface mRouterInterface;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RLog.i(TAG, "Connected to service in " + name);
            mRouterInterface = IRouterInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            RLog.i(TAG, "Disconnected from service in " + name);
            mRouterInterface = null;
        }
    };

    private LocalRouter() {
    }

    static synchronized LocalRouter getInstance() {
        if (sInstance == null) {
            sInstance = new LocalRouter();
        }
        return sInstance;
    }

    private boolean checkConnection() {
        return mRouterInterface != null;
    }

    private void callback(RouteResult result, String msg) {
        if (result != RouteResult.SUCCEED) {
            RLog.w(msg);
        }
        if (mRouteRequest.getCallback() != null) {
            mRouteRequest.getCallback().callback(result, mRouteRequest.getUri(), msg);
        }
    }

    @Override
    public Intent getIntent(Context context) {
        if (!checkConnection()) {
            callback(RouteResult.UNCONNECTED, "Unconnected with main process.");
            return null;
        }
        try {
            RouteResponse response = mRouterInterface.route(mRouteRequest);
            if (response.getResult() != RouteResult.SUCCEED) {
                callback(response.getResult(), response.getMsg());
            }
            return response.getData();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void go(Context context) {
        super.go(context);
        callback(RouteResult.SUCCEED, null);
    }
}
