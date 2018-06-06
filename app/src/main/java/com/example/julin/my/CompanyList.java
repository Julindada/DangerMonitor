package com.example.julin.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.julin.data.HttpCallbackListener;
import com.example.julin.data.HttpData;
import com.example.julin.data.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class CompanyList extends AppCompatActivity {

    List<HttpData> httpdata;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);


        listView = (ListView) findViewById(R.id.list_company);
        final String address = "http://120.77.86.243:7070/parklist";

        //TODO

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener(){
            @Override
            public void onFinish(String response){
                //对返回的数据进行处理
                Gson gson = new Gson();

                httpdata = gson.fromJson(response, new TypeToken<List<HttpData>>() {
                }.getType());

                Utils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        List<String> data = getData(httpdata);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CompanyList.this, android.R.layout.simple_list_item_1, data);

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                Intent intent = new Intent(CompanyList.this, DataList.class);

                                String str = String.valueOf(httpdata.get(position).getID());
                                intent.putExtra("id", str);
                                intent.putExtra("name", httpdata.get(position).getName());
                                startActivity(intent);
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.refresh_company);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                //TODO
                Toast.makeText(CompanyList.this, "Refresh", Toast.LENGTH_SHORT).show();

                HttpUtil.sendHttpRequest(address, new HttpCallbackListener(){
                    @Override
                    public void onFinish(String response){
                        //对返回的数据进行处理
                        Gson gson = new Gson();

                            httpdata = gson.fromJson(response, new TypeToken<List<HttpData>>() {
                            }.getType());

                        Utils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                List<String> data = getData(httpdata);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CompanyList.this, android.R.layout.simple_list_item_1, data);

                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                        Intent intent = new Intent(CompanyList.this, DataList.class);
                                        Log.d("debug", httpdata.get(position).getName());

                                        String str = String.valueOf(httpdata.get(position).getID());
                                        intent.putExtra("id", str);
                                        intent.putExtra("name", httpdata.get(position).getName());
                                        startActivity(intent);
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



    private List<String> getData(List<HttpData> httpdatalist){

        List<String> data = new ArrayList<String>();

        for(HttpData httpdata: httpdatalist){
            if(httpdata.getName().isEmpty()){
                data.add("空");
            }else {
                data.add(httpdata.getName());
            }
        }


        return data;
    }

}

class Utils {

    public static void runOnUiThread(Runnable runnable){
        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler.post(runnable);
    }
}

