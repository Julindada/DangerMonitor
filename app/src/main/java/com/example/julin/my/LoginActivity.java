package com.example.julin.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button login;
    public static final int LOGING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView = (ImageView) findViewById(R.id.image_view);
        final EditText username = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.passWord);
        login = (Button) findViewById(R.id.logIn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("123456")&&password.getText().toString().equals("123456")){
                    Intent intent = new Intent(LoginActivity.this, CompanyList.class);
                    startActivity(intent);
                }

                //makePostRequest(username.getText().toString(), password.getText().toString());

            }
        });

    }

//    private Handler handler = new Handler(){
//        public void handleMessage(Message msg){
//            int responsecode = 0;
//
//            switch (msg.what){
//                case LOGING:{
//                    responsecode = msg.arg1;
//                    Log.d("debug", String.valueOf(responsecode));
//                    if (responsecode == 200){
//                        Intent intent1 = new Intent(LoginActivity.this, CompanyList.class);
//                        startActivity(intent1);
//                    }else {
//                        Log.d("debug", "error");
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//    };

//    private void makePostRequest(final String username, final String password){
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                int responseCode = 0;
//                String address = "http://120.77.86.243:7070/auth";
//                HttpURLConnection conn = null;
//                try {
//                    URL mURL = new URL(address);
//                    conn = (HttpURLConnection) mURL.openConnection();
//                    conn.setRequestMethod("POST");
//                    conn.setReadTimeout(5000);
//                    conn.setConnectTimeout(10000);
//                    conn.setDoOutput(true);
////                    String data = "=" + username + "&password=" + password;
//                    JSONObject jsonObj = new JSONObject();
//                    jsonObj.put("username", username);
//                    jsonObj.put("password", password);
//                    String data = jsonObj.toString();
//                    Log.d("debug", data);
//                    OutputStream out = conn.getOutputStream();
//                    out.write(data.getBytes());
//                    out.flush();
//                    out.close();
//                    InputStream is = conn.getInputStream();
//                    responseCode = conn.getResponseCode();
//                    Message message = new Message();
//                    message.what=LOGING;
//                    message.arg1 = responseCode;
//                    handler.sendMessage(message);
//
//                }catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (conn != null) {
//                        conn.disconnect();// 关闭连接
//                    }
//                }
//
//            }
//        }).start();
//    }

}
