package com.example.julin.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.julin.data.MqttData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataCharts extends AppCompatActivity {

    MqttData mqttdata;
    private static CountDownTimer mTimer;
    LineChart chart1;
    ArrayList<Entry> lineChartdata;
    float[] charts = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    String[] time = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_charts);

        Intent intent = getIntent();
        final int ID = intent.getIntExtra("ID",-1);
        final String text = intent.getStringExtra("tabName");

        final TextView textView = (TextView)findViewById(R.id.text);
        textView.setText("离线");

//初始化图表
        chart1 = (LineChart) findViewById(R.id.chart1);
//        lineChartdata = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            lineChartdata.add(new Entry(0, i));
//        }
        chart1.setNoDataText("暂无数据");
        LineDataSet lineDataSetMa = new LineDataSet(lineChartdata, text);
        lineDataSetMa.setDrawCubic(true);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(lineDataSetMa);

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
//            time[i] = String.valueOf(i);
            xVals.add(time[i]);
        }

        LineData lineData = new LineData(xVals, sets);
        chart1.setData(lineData);
        chart1.notifyDataSetChanged();

        MqttClient client;
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient("tcp://120.77.86.243:1883", "xiaoyuan", persistence);//链接服务器
            client.connect();
            client.subscribe("message", 1);//订阅message主题
            client.setCallback(new MqttCallback() {


                @Override
                public void messageArrived(String s, MqttMessage Message) throws Exception {//当message主题发布消息时会回调这个方法

                    mqttdata = new Gson().fromJson(Message.toString(), MqttData.class);
                    Log.d("debug", "messageArrived: "+Message.toString());
                    Log.d("debug", "Activity传递ID: " + String.valueOf(ID));

                    Log.d("debug", "Mqtt发布ID: " + String.valueOf(mqttdata.getMqttID()));

                    if(mqttdata.getMqttID() == ID){
                        runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        try{
                                            chart1 = (LineChart) findViewById(R.id.chart1);
                                            Calendar calendar = new GregorianCalendar();
                                            textView.setText("在线");
                                            for (int i = 0; i < 19; i++) {
                                                charts[i] = charts[i + 1];
                                                time[i] = time[i + 1];
                                            }
                                            charts[19] = mqttdata.getData();
                                            time[19] = calendar.get(Calendar.HOUR_OF_DAY)+"时"+calendar.get(Calendar.MINUTE)+"分"+calendar.get(Calendar.SECOND)+"秒";

                                            lineChartdata = new ArrayList<>();
                                            for (int i = 0; i < 20; i++) {
                                                lineChartdata.add(new Entry(charts[i], i));
                                            }
                                            LineDataSet lineDataSetMa = new LineDataSet(lineChartdata, text);
                                            lineDataSetMa.setDrawCubic(true);
                                            ArrayList<ILineDataSet> sets = new ArrayList<>();
                                            sets.add(lineDataSetMa);

                                            ArrayList<String> xVals = new ArrayList<>();
                                            for (int i = 0; i < 20; i++) {
                                                xVals.add(time[i]);
                                            }

                                            LineData lineData = new LineData(xVals, sets);
                                            chart1.setData(lineData);
                                            chart1.notifyDataSetChanged();
                                            chart1.invalidate();
                                            mTimer.start();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }


                                    }
                        });

                    }

                }


                @Override
                public void connectionLost(Throwable throwable) {

                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }

        mTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {

                textView.setText("离线");

            }
        };//end of mtimer


    }//end of Create

}

