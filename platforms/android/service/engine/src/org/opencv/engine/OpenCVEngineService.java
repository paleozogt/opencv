package org.opencv.engine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenCVEngineService extends Service
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IBinder mEngineInterface = null;
    private MarketConnector mMarket;
    private BinderConnector mNativeBinder;

    public void onCreate() {
        logger.info("Service starting");
        super.onCreate();
        logger.info("Engine binder component creating");
        mMarket = new MarketConnector(getBaseContext());
        mNativeBinder = new BinderConnector(mMarket);
        if (mNativeBinder.Init()) {
            mEngineInterface = mNativeBinder.Connect();
            logger.info("Service started successfully");
        } else {
            logger.error("Cannot initialize native part of OpenCV Manager!");
            logger.error("Using stub instead");

            mEngineInterface = new OpenCVEngineInterface.Stub() {

                @Override
                public boolean installVersion(String version) throws RemoteException {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public String getLibraryList(String version) throws RemoteException {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public String getLibPathByVersion(String version) throws RemoteException {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public int getEngineVersion() throws RemoteException {
                    return -1;
                }
            };
        }
    }

    public IBinder onBind(Intent intent) {
        logger.info("Service onBind called for intent " + intent.toString());
        return mEngineInterface;
    }

    public boolean onUnbind(Intent intent)
    {
        logger.info("Service onUnbind called for intent " + intent.toString());
        return true;
    }
    public void OnDestroy()
    {
        logger.info("OpenCV Engine service destruction");
        mNativeBinder.Disconnect();
    }

}
