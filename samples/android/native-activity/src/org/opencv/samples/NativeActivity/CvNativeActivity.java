package org.opencv.samples.NativeActivity;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import android.app.Activity;
import android.content.Intent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CvNativeActivity extends Activity {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    logger.info("OpenCV loaded successfully");
                    System.loadLibrary("native_activity");
                    Intent intent = new Intent(CvNativeActivity.this, android.app.NativeActivity.class);
                    CvNativeActivity.this.startActivity(intent);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public CvNativeActivity() {
        logger.info("Instantiated new " + this.getClass());
    }

   @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }
}
