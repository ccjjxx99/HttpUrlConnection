package com.world.ccjjxx99.url;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private static String message;
    private static String txt;
    private Button mBtn;
    private TextView mViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iniView();
    }

    private void iniView() {
        mBtn = findViewById(R.id.my_btn);
        mViewText = findViewById(R.id.view_text);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.my_btn) {
            get();//服务器端获取数据
        }
    }

    public void get(){
        new Thread() {
            @Override
            public void run() {
                //这里写入子线程需要做的工作
                try {
                    URL url=new URL("http://wanandroid.com/tools/mockapi/3875/android");
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5*1000);
                    connection.setUseCaches(true);
                    connection.setRequestProperty("Content-Type","application/json");
                    InputStream inputStream=connection.getInputStream();
                    byte[] data=new byte[1024];
                    StringBuffer sb=new StringBuffer();
                    int length=0;
                    while ((length=inputStream.read(data))!=-1){
                        String s=new String(data, Charset.forName("utf-8"));
                        sb.append(s);
                    }
                    message=sb.toString();
                    inputStream.close();
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    parseJson(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        mViewText.setText(txt);//显示到textView
    }

    public static void parseJson(String jsonStr) throws JSONException {
        //新建JSONObject,JsonString字符串中为上面的JSON对象的文本
        JSONObject demoJson = new JSONObject(jsonStr);
        //获取name名称对应的值
        String name = demoJson.getString("name");
        JSONObject address = demoJson.getJSONObject("address");
        String street = address.getString("street");
        String city = address.getString("city");
        String country = address.getString("country");
        JSONArray links = demoJson.getJSONArray("links");
        String linksName[] = new String[3];
        String linksUrl[] = new String[3];
        for (int i = 0; i < links.length(); i++) {
            linksName[i] = links.getJSONObject(i).getString("name");
        }
        for (int i = 0; i < links.length(); i++){
            linksUrl[i] = links.getJSONObject(i).getString("url");
        }
        txt = "name:" + name + "\n"
                + "address:\n"
                + "\tstreet:" + street + "\n"
                + "\tcity:" + city + "\n"
                + "\tcountry:" + country + "\n"
                + "links:\n"
                + "\tname:" +linksName[0] + "\turl:" + linksUrl[0] +"\n"
                + "\tname:" +linksName[1] + "\turl:" + linksUrl[1] +"\n"
                + "\tname:" +linksName[2] + "\turl:" + linksUrl[2] ;
    }
}
