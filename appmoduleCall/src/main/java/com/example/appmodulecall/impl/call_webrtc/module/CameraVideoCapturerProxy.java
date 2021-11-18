package com.example.appmodulecall.impl.call_webrtc.module;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.appmodulecall.impl.call_error.CallConnectionException;
import com.example.appmodulecall.impl.call_error.CallException;

import org.webrtc.Camera1Enumerator;
import org.webrtc.Camera2Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.CameraVideoCapturer;

public class CameraVideoCapturerProxy {

    private CameraVideoCapturer mCameraVideoCapturer;

    public CameraVideoCapturerProxy() {

    }

    public CameraVideoCapturer getCameraVideoCapturer() {
        return mCameraVideoCapturer;
    }

    public void init(Context context) throws CallConnectionException {

        // 前置相机是否已被使用
        if (isCameraInUse(context, true))
            return;

        // 创建 前置视频捕获器
        mCameraVideoCapturer = createVideoCapturer(context);
        if (mCameraVideoCapturer == null) {
            throw new CallConnectionException(
                    "CameraVideoCapturerProxy.createVideoCapturer : mCameraVideoCapturer == null",
                    "创建视频捕获器失败");
        }
    }

    /**
     * 创建 前置视频捕获器
     *
     * @param context
     * @return
     */
    private CameraVideoCapturer createVideoCapturer(Context context) {
        CameraVideoCapturer cameraVideoCapturer = null;

        CameraEnumerator camerasEnumerator = getCameraEnumerator(context);
        final String[] deviceNames = camerasEnumerator.getDeviceNames();


        if ((null != deviceNames) && (deviceNames.length > 0)) {
            for (String name : deviceNames) {
                if (camerasEnumerator.isFrontFacing(name)) {
                    cameraVideoCapturer = camerasEnumerator.createCapturer(name, null);
                    if (null != cameraVideoCapturer) {
                        break;
                    }
                }
            }

            if (null == cameraVideoCapturer) {
                cameraVideoCapturer = camerasEnumerator.createCapturer(deviceNames[0], null);
            }
        }

        return cameraVideoCapturer;
    }

    /**
     * Get a camera enumerator
     *
     * @param context the context
     * @return the camera enumerator
     */
    private CameraEnumerator getCameraEnumerator(Context context) {
        if (Camera2Enumerator.isSupported(context)) {
            return new Camera2Enumerator(context);
        } else {
            return new Camera1Enumerator(false);
        }
    }

    /**
     * Test if the camera is not used by another app.
     * It is used to prevent crashes at org.webrtc.Camera1Session.create(Camera1Session.java:80)
     * when the front camera is not available.
     *
     * @param context    the context
     * @param isFrontOne true if the camera is the
     * @return true if the camera is used.
     */
    @SuppressLint("Deprecation")
    private static boolean isCameraInUse(Context context, boolean isFrontOne) {
        boolean isUsed = false;

        if (!Camera2Enumerator.isSupported(context)) {
            int cameraId = -1;
            int numberOfCameras = android.hardware.Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; i++) {
                android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
                android.hardware.Camera.getCameraInfo(i, info);

                if ((info.facing == android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT) && isFrontOne) {
                    cameraId = i;
                    break;
                } else if ((info.facing == android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK) && !isFrontOne) {
                    cameraId = i;
                    break;
                }
            }

            if (cameraId >= 0) {
                android.hardware.Camera c = null;
                try {
                    c = android.hardware.Camera.open(cameraId);
                } catch (Exception e) {
                } finally {
                    isUsed = (null == c);
                    if (c != null) {
                        c.release();
                    }
                }
            }
        }

        return isUsed;
    }

    public void clear() {
        if (mCameraVideoCapturer != null) {
            mCameraVideoCapturer.dispose();
            mCameraVideoCapturer = null;
        }
    }
}
