package com.studio.a4kings.qr_code_app.Managers;

import com.studio.a4kings.qr_code_app.Callbacks.SignalListener;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Dmitry Pavlenko on 05.06.2016.
 */
public class SignalManager {

    private static String CONNECTION_STRING;
    private static SignalManager _instance;

    private Socket socket;

    public static SignalManager getInstance(String url, String connectionId){
        if(_instance==null){
            _instance = new SignalManager(url, connectionId);
        }

        return _instance;
    }

    private SignalManager(String uri, String connectionId){
        CONNECTION_STRING = uri;
        this.openConnection(connectionId);
    }

    private void openConnection(final String connectionId){
        try {
            this.socket = IO.socket(CONNECTION_STRING);
            this.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    socket.emit("registerConnection", connectionId);
                }
            }).on("serverResponse", new SignalListener());
            this.socket.connect();

        }catch (URISyntaxException ex){

        }
    }

}
