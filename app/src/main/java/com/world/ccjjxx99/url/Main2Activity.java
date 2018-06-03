package com.world.ccjjxx99.url;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
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
        String message="";
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
        mViewText.setText(message);
    }
}
