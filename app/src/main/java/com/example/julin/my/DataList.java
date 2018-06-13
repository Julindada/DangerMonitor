package com.example.julin.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.julin.data.HttpCallbackListener;
import com.example.julin.data.HttpUtil;
import com.example.julin.data.Point;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.List;

public class DataList extends AppCompatActivity {

    private static CountDownTimer mTimer;
    List<Point> nodes;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        final StringBuilder address = new StringBuilder("http://120.77.86.243:7070/parknodes?id=");
        final Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        address.append(intent.getStringExtra("id"));
        Log.d("debug",intent.getStringExtra("id"));
        final StringBuilder tabName = new StringBuilder(name);
        tabName.append(" - ");


        listView = (ListView) findViewById(R.id.list_data);

        HttpUtil.sendHttpRequest(address.toString(), new HttpCallbackListener(){
            @Override
            public void onFinish(String response){
                //对返回的数据进行处理
                Gson gson = new Gson();

                        Log.d("debug", response);
                nodes = gson.fromJson(response, new TypeToken<List<Point>>() {}.getType());
                Log.d("debug",response.toString());

//                        Log.d("debug", "here");

                Utils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        List<String> data = getData(nodes);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DataList.this, android.R.layout.simple_list_item_1, data);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

//                                        Log.d("debug", String.valueOf(nodes.get(position)));
                                tabName.append(nodes.get(position).getDanger().getSpecies());
                                tabName.append(" - ");
                                tabName.append(nodes.get(position).getDanger().getName());

                                Intent intent1 = new Intent(DataList.this, DataCharts.class);
                                intent1.putExtra("ID", nodes.get(position).getNodesID());
//                                        Log.d("debug",tabName.toString());
                                intent1.putExtra("tabName", tabName.toString());
                                startActivity(intent1);

                            }
                        });

                    }
                });

            }

            @Override
            public void onError(Exception e) {
                //对异常情况进行处理
                Log.d("debug", e.toString());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.refresh_data);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Click action
                Toast.makeText(DataList.this, "Refresh", Toast.LENGTH_SHORT).show();

//                Log.d("debug", address.toString());

                //TODO
                HttpUtil.sendHttpRequest(address.toString(), new HttpCallbackListener(){
                    @Override
                    public void onFinish(String response){
                        //对返回的数据进行处理
                        Gson gson = new Gson();

                        nodes.clear();
                        nodes = gson.fromJson(response, new TypeToken<List<Point>>() {}.getType());

                        Utils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                List<String> data = getData(nodes);

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DataList.this, android.R.layout.simple_list_item_1, data);

                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

//                                        Log.d("debug", String.valueOf(nodes.get(position)));
                                        tabName.append(nodes.get(position).getDanger().getSpecies());
                                        tabName.append(" - ");
                                        tabName.append(nodes.get(position).getDanger().getName());

                                        Intent intent1 = new Intent(DataList.this, DataCharts.class);
                                        intent1.putExtra("ID", nodes.get(position).getNodesID());
                                        intent1.putExtra("tabName", tabName.toString());
                                        startActivity(intent1);

                                    }
                                });

                            }
                        });

                    }

                    @Override
                    public void onError(Exception e) {
                        //对异常情况进行处理
                        Log.d("debug", e.toString());
                    }
                });


            }
        });

    }



    private List<String> getData(List<Point> nodes){

        List<String> data = new ArrayList<String>();

           for (Point point : nodes){

                   StringBuilder stringBuilder;
                   stringBuilder= new StringBuilder(point.getDanger().getSpecies());
                   stringBuilder.append(" : ");
                   stringBuilder.append(point.getDanger().getName());

                   data.add(stringBuilder.toString());

           }

        return data;
    }
    private List<Point> enable(final List<Point> points) throws MqttException {
        try {
            MqttClient client;
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient("tcp://115.29.55.106:1883", "xiaoyuan", persistence);//链接服务器
            client.connect();
            client.subscribe("message", 2);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

                        points.get(mqttMessage.getId()-1).getDanger().setaBoolean(true);

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });



        } catch (Exception e){
            e.printStackTrace();
        }
        return points;

    }

    private void onTimer(){
        mTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {

                //TODO 节点变暗
                Log.d("debug","finish");

            }
        };
    }

}
