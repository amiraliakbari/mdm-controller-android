package com.sabaos.mdmcontroller;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void pushMessage(View view) {

        final String messsageString = "{" +
                "\"type\":\"sendpush\"," +
                "\"token\":\"" + new SharedPref(getApplicationContext()).loadData("marketToken") +
                "\",\"data\":\"" + "first test push message" + "\"" +
                "}";


        OkHttpClient client = new OkHttpClient.Builder().pingInterval(4, TimeUnit.SECONDS).connectTimeout(1, TimeUnit.DAYS).build();
        Request request = new Request.Builder().url("wss://push.sabaos.com").build();
        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);

                webSocket.send(messsageString);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);


            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Log.i("Main Activity WebSocket", "");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.i("Main Activity WebSocket", "");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
//                super.onFailure(webSocket, t, response);
                Log.i("Main Activity WebSocket", "");

            }
        };
        WebSocket ws = client.newWebSocket(request, listener);

    }

    public void showToken(View view) {
        EditText editText = (EditText) findViewById(R.id.textbox1);
        editText.setText(new SharedPref(getApplicationContext()).loadData("marketToken"));
    }
}

