package com.example.appmodulecall.impl.call_webrtc.module;

import androidx.annotation.NonNull;

import com.example.appmodulecall.impl.call_error.CallConnectionException;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnectionFactory;

/**
 * 音频 资源
 */
public class LocalAudioProxy {

    private AudioSource mAudioSource;

    private AudioTrack mAudioTrack;

    public void AudioFacade() {

    }

    public void init(@NonNull PeerConnectionFactory peerConnectionFactory, @NonNull String audioTrackId) throws CallConnectionException {
        MediaConstraints constraints = createConstraints();
        mAudioSource = peerConnectionFactory.createAudioSource(constraints);
        if (mAudioSource == null) {
            throw new CallConnectionException(
                    "peerConnectionFactory.createAudioSource : mAudioSource == null",
                    "创建音频源失败");
        }
        mAudioTrack = peerConnectionFactory.createAudioTrack(audioTrackId, mAudioSource);
        if (mAudioTrack == null) {
            throw new CallConnectionException(
                    "peerConnectionFactory.createAudioTrack : mAudioSource == null",
                    "创建音频轨道失败");
        }

        mAudioTrack.setEnabled(true);
    }

    private MediaConstraints createConstraints() {
        MediaConstraints audioConstraints = new MediaConstraints();

        // add all existing audio filters to avoid having echos
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googEchoCancellation", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googEchoCancellation2", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googDAEchoCancellation", "true"));

        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googTypingNoiseDetection", "true"));

        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAutoGainControl", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAutoGainControl2", "true"));

        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression2", "true"));

        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAudioMirroring", "false"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googHighpassFilter", "true"));

        return audioConstraints;
    }

    public AudioTrack getAudioTrack() {
        return mAudioTrack;
    }

    /**
     * 设置本地音频是否可用
     *
     * @param isEnable
     * @return
     */
    public boolean setAudioEnable(boolean isEnable) {
        return mAudioTrack.setEnabled(isEnable);
    }

    public boolean isLocalAudioEnabled() {
        return mAudioTrack.enabled();
    }

    public void clear() {
        if (mAudioSource != null) {
            mAudioSource.dispose();
            mAudioSource = null;
        }
        if (mAudioTrack != null) {
            mAudioTrack.dispose();
            mAudioTrack = null;
        }
    }


}
