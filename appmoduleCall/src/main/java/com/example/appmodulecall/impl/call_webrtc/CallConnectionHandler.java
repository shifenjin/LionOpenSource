package com.example.appmodulecall.impl.call_webrtc;

import com.example.appmodulecall.api.CallType;
import com.example.appmodulecall.impl.Call;
import com.example.appmodulecall.impl.call_error.CallConnectionException;
import com.example.appmodulecall.impl.call_msg.CallMsg;
import com.example.appmodulecall.impl.call_msg.CallMsgKey;
import com.example.appmodulecall.impl.call_msg.CallMsgManager;
import com.example.appmodulecall.impl.call_msg.CallMsgType;
import com.oney.WebRTCModule.EglUtils;

import org.webrtc.DataChannel;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 呼叫 连接处理器
 */
public class CallConnectionHandler {

    private static PeerConnectionFactory mPeerConnectionFactory = null;
    private Call call;
    private CallType callType;

    private CallLocalMediaHandler localCallLocalMediaHandler;

    private MediaStream mRemoteMediaStream;

    private PeerConnection mPeerConnection;

    public CallConnectionHandler(Call call, CallType callType, CallLocalMediaHandler callLocalMediaHandler) {
        this.call = call;
        this.callType = callType;
        this.localCallLocalMediaHandler = callLocalMediaHandler;
    }

    /**
     * 初始化 PeerConnectionFactory
     */
    public synchronized PeerConnectionFactory initPeerConnectionFactory() throws CallConnectionException {
        if (mPeerConnectionFactory == null) {
            mPeerConnectionFactory = new PeerConnectionFactory(null);

            // Initialize EGL contexts required for HW acceleration.
            EglBase.Context eglContext = EglUtils.getRootEglBaseContext();
            if (eglContext == null) {
                throw new CallConnectionException(
                        "initPeerConnectionFactory - getRootEglBaseContext error : eglContext == null",
                        "设置视频硬件加速失效");
            } else {
                mPeerConnectionFactory.setVideoHwAccelerationOptions(eglContext, eglContext);
            }
        }

        return mPeerConnectionFactory;
    }

    public void initPeerConnection() throws CallConnectionException {
        List<PeerConnection.IceServer> iceServers = createIceServers();

        MediaConstraints peerConnectionConstraints = new MediaConstraints();
        peerConnectionConstraints.optional.add(new MediaConstraints.KeyValuePair("RtpDataChannels", "true"));

        mPeerConnection = mPeerConnectionFactory.createPeerConnection(iceServers, peerConnectionConstraints, new PeerConnection.Observer() {
            @Override
            public void onSignalingChange(PeerConnection.SignalingState signalingState) {

            }

            @Override
            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {

                switch (iceConnectionState) {
                    case CONNECTED: {
                        if (CallType.VIDEO.equals(callType)) {
                            localCallLocalMediaHandler.setFullScreenStream(mRemoteMediaStream);
                            localCallLocalMediaHandler.setFloatedScreenStream(localCallLocalMediaHandler.getLocalMediaStream());
                        }

                        call.getCallLifeCycleHandler().notifyCallConnected();

                        break;
                    }
                    case FAILED: {

                        CallConnectionException exception = new CallConnectionException(
                                "onIceConnectionChange - iceConnectionState : FAILED",
                                "IceConnect 失败"
                        );
                        call.onEndForLocalException(exception);

                        break;
                    }
                }
            }

            @Override
            public void onIceConnectionReceivingChange(boolean b) {

            }

            @Override
            public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {

            }

            @Override
            public void onIceCandidate(IceCandidate iceCandidate) {

                Map<String, String> data = new HashMap<>();
                data.put(CallMsgKey.KEY_SDP_M_LINE_INDEX, String.valueOf(iceCandidate.sdpMLineIndex));
                data.put(CallMsgKey.KEY_SDP_MID, String.valueOf(iceCandidate.sdpMid));
                data.put(CallMsgKey.KEY_CANDIDATE, String.valueOf(iceCandidate.sdp));

                CallMsg callMsg = new CallMsg(CallMsgType.SEND_ICE_CANDIDATE, data);
                CallMsgManager.sendCallMsg(call, callMsg);

            }

            @Override
            public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {

            }

            @Override
            public void onAddStream(final MediaStream mediaStream) {
                if (CallType.VIDEO.equals(callType)) {
                    mRemoteMediaStream = mediaStream;
                    VideoTrack remoteVideoTrack = mRemoteMediaStream.videoTracks.get(0);
                    remoteVideoTrack.setEnabled(true);
                }
            }

            @Override
            public void onRemoveStream(MediaStream mediaStream) {
                VideoTrack remoteVideoTrack = mRemoteMediaStream.videoTracks.get(0);
                if (null != remoteVideoTrack) {
                    remoteVideoTrack.dispose();
                }
                if (mRemoteMediaStream != null)
                    mRemoteMediaStream.dispose();
            }

            @Override
            public void onDataChannel(DataChannel dataChannel) {

            }

            @Override
            public void onRenegotiationNeeded() {

            }

            @Override
            public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {

            }
        });

        if (mPeerConnection == null) {
            throw new CallConnectionException(
                    "initPeerConnection : mPeerConnection == null",
                    "创建呼叫连接失败");
        }
    }

    private List<PeerConnection.IceServer> createIceServers() {
        // build ICE servers list
        List<PeerConnection.IceServer> iceServers = new ArrayList<>();

        String username = null;
        String password = null;
        List<String> uris = null;

        for (int index = 0; index < uris.size(); index++) {
            String url = uris.get(index);

            if ((null != username) && (null != password)) {
                iceServers.add(new PeerConnection.IceServer(url, username, password));
            } else {
                iceServers.add(new PeerConnection.IceServer(url));
            }
        }

        // define at least on server
        if (iceServers.isEmpty()) {
            iceServers.add(new PeerConnection.IceServer("stun:stun.l.google.com:19302"));
        }

        return iceServers;
    }

    /**
     * 设置 本地媒体流
     */
    public void addLocalStream() throws CallConnectionException {

        boolean b = mPeerConnection.addStream(localCallLocalMediaHandler.getLocalMediaStream());
        if (!b) {
            throw new CallConnectionException(
                    "addLocalStream : mPeerConnection.addStream return false",
                    "设置本地媒体流失败");
        }
    }

    /**
     * 发起 peerConnection 请求
     */
    public void createOffer(CallType callType) {

        MediaConstraints constraints = createSdpMediaConstraints(callType);

        SdpObserver offerSdpObserver = new OfferSdpObserver();

        mPeerConnection.createOffer(offerSdpObserver, constraints);
    }

    private MediaConstraints createSdpMediaConstraints(CallType callType) {
        MediaConstraints constraints = new MediaConstraints();
        constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        if (CallType.AUDIO.equals(callType))
            constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "false"));
        else if (CallType.VIDEO.equals(callType))
            constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"));

        return constraints;
    }

    /**
     * 设置 远程sdp
     */
    public void setRemoteSdp(String sdp) {

        SessionDescription answerSdp = new SessionDescription(SessionDescription.Type.ANSWER, sdp);
        mPeerConnection.setRemoteDescription(new SdpObserver() {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
            }

            @Override
            public void onSetSuccess() {
            }

            @Override
            public void onCreateFailure(String s) {
            }

            @Override
            public void onSetFailure(String s) {
            }
        }, answerSdp);
    }

    /**
     * 添加 iceCandidate
     */
    public void addIceCandidate(Map<String, String> callMsgData) {
        String candidate = callMsgData.get(CallMsgKey.KEY_CANDIDATE);
        String sdpMid = callMsgData.get(CallMsgKey.KEY_SDP_MID);

        String sdpLineIndexStr = callMsgData.get(CallMsgKey.KEY_SDP_M_LINE_INDEX);
        if (sdpLineIndexStr == null) {
            // todo -> error
            return;
        }
        int sdpLineIndex = Integer.valueOf(sdpLineIndexStr);

        mPeerConnection.addIceCandidate(new IceCandidate(sdpMid, sdpLineIndex, candidate));
    }

    public void createAnswer(CallType callType) {
        MediaConstraints constraints = createSdpMediaConstraints(callType);

        SdpObserver answerSdpObserver = new AnswerSdpObserver();
        mPeerConnection.createAnswer(answerSdpObserver, constraints);
    }

    public void clear() {
        if (mPeerConnection != null) {
            mPeerConnection.dispose();
            mPeerConnection = null;
        }

        localCallLocalMediaHandler.clear();
    }

    class OfferSdpObserver implements SdpObserver {

        private SessionDescription offerSdp;

        @Override
        public void onCreateSuccess(SessionDescription sessionDescription) {

            offerSdp = new SessionDescription(sessionDescription.type, sessionDescription.description);
            mPeerConnection.setLocalDescription(this, offerSdp);
        }

        @Override
        public void onSetSuccess() {
            Map<String, String> data = new HashMap<>();
            if (offerSdp == null) {
                // todo error
                return;
            }
            data.put("sdp", offerSdp.description);
            data.put("type", offerSdp.type.canonicalForm());
            CallMsg callMsg = new CallMsg(CallMsgType.SEND_OFFER_SDP, data);

            CallMsgManager.sendCallMsg(call, callMsg);

            call.getCallLifeCycleHandler().notifyCallConnecting();
        }

        @Override
        public void onCreateFailure(String s) {
            CallConnectionException e = new CallConnectionException(
                    "OfferSdpObserver onCreateFailure : " + s,
                    "创建 offer sdp 失败"
            );
            call.onEndForLocalException(e);
        }

        @Override
        public void onSetFailure(String s) {
            CallConnectionException e = new CallConnectionException(
                    "OfferSdpObserver onCreateFailure : " + s,
                    "设置 offer sdp 失败"
            );
            call.onEndForLocalException(e);
        }
    }

    class AnswerSdpObserver implements SdpObserver {


        private SessionDescription answerSdp;

        @Override
        public void onCreateSuccess(SessionDescription sessionDescription) {
            answerSdp = new SessionDescription(sessionDescription.type, sessionDescription.description);

            mPeerConnection.setLocalDescription(this, answerSdp);
        }

        @Override
        public void onSetSuccess() {
            Map<String, String> data = new HashMap<>();
            if (answerSdp == null) {
                // todo error
                return;
            }
            data.put(CallMsgKey.KEY_SDP, answerSdp.description);
            data.put(CallMsgKey.KEY_SDP_TYPE, answerSdp.type.canonicalForm());
            CallMsg callMsg = new CallMsg(CallMsgType.SEND_ANSWER_SDP, data);

            CallMsgManager.sendCallMsg(call, callMsg);

            call.getCallLifeCycleHandler().notifyCallConnecting();
        }

        @Override
        public void onCreateFailure(String s) {
            CallConnectionException e = new CallConnectionException(
                    "OfferSdpObserver onCreateFailure : " + s,
                    "创建 answer sdp 失败"
            );
            call.onEndForLocalException(e);
        }

        @Override
        public void onSetFailure(String s) {
            CallConnectionException e = new CallConnectionException(
                    "OfferSdpObserver onCreateFailure : " + s,
                    "设置 answer sdp 失败"
            );
            call.onEndForLocalException(e);
        }
    }
}
