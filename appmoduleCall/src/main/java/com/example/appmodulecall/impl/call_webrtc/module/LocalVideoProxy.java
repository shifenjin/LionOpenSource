package com.example.appmodulecall.impl.call_webrtc.module;

import android.content.Context;

import com.example.appmodulecall.impl.call_error.CallConnectionException;

import org.webrtc.PeerConnectionFactory;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

/**
 * 视频 资源
 */
public class LocalVideoProxy {

    private VideoTrack videoTrack;
    private VideoSource videoSource;
    private CameraVideoCapturerProxy cameraVideoCapturerProxy;

    public LocalVideoProxy() {

    }

    public void init(Context context, PeerConnectionFactory peerConnectionFactory, String videoTrackId) throws CallConnectionException {
        cameraVideoCapturerProxy = new CameraVideoCapturerProxy();
        cameraVideoCapturerProxy.init(context);
        videoSource = peerConnectionFactory.createVideoSource(cameraVideoCapturerProxy.getCameraVideoCapturer());
        if (videoSource == null) {
            throw new CallConnectionException(
                    "peerConnectionFactory.createVideoSource : videoSource == null",
                    "创建视频源失败");
        }
        videoTrack = peerConnectionFactory.createVideoTrack(videoTrackId, videoSource);
        if (videoTrack == null) {
            throw new CallConnectionException(
                    "peerConnectionFactory.createVideoTrack : videoTrack == null",
                    "创建视频轨道失败");
        }
        videoTrack.setEnabled(true);

    }

    public VideoTrack getVideoTrack() {
        return videoTrack;
    }

    /**
     * 设置本地视频是否可用
     *
     * @param isEnable
     * @return
     */
    public boolean setVideoEnable(boolean isEnable) {
        return videoTrack.setEnabled(isEnable);
    }

    public boolean isLocalVideoEnabled() {
        return videoTrack.enabled();
    }

    public void clear() {
        if (cameraVideoCapturerProxy != null) {
            cameraVideoCapturerProxy.clear();
            cameraVideoCapturerProxy = null;
        }

        if (videoSource != null) {
            videoSource.dispose();
            videoSource = null;
        }

        if (videoTrack != null) {
            videoTrack. dispose();
            videoTrack = null;
        }
    }


}
