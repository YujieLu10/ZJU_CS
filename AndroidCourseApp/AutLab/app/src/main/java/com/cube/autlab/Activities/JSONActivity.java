package com.cube.autlab.Activities;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cube.autlab.BottomNavigationViewHelper;
import com.cube.autlab.MainActivity;
import com.cube.autlab.R;
import com.cube.autlab.UI.SelectionTextView;
import com.cube.autlab.file.FileActivity;
import com.cube.autlab.file.SDCardHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.R.color.black;

/**
 * Created by c-ten on 2017/11/25.
 */

public class JSONActivity extends AppCompatActivity {
    public static final String EXTRA_JSON =  "com.cube.lab.demo";//"com.yalantis.contextmenu.sample.extrafilepath";
    public static final String EXTRA_EXPORT = "no";
    private String extraJson;
    private String extraExport = "no";
    private Button button_ex,button_pre;
    private TextView mTextMessage;
    private static String FILE_NAME = "a";
    private static String FILE_JSON_NAME = "jsontest";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //创建一个意图对象
                    Intent main_int = new Intent();
                    //创建组件，通过组件来响应
                    ComponentName main_component = new ComponentName(JSONActivity.this, MainActivity.class);
                    main_int.setComponent(main_component);
                    startActivity(main_int);
                    //textView.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard://用户标注主界面
                    //创建一个意图对象
                    Intent text_int = new Intent();
                    //创建组件，通过组件来响应
                    ComponentName component = new ComponentName(JSONActivity.this, TextActivity.class);
                    text_int.setComponent(component);
                    startActivity(text_int);
                    // mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications://TODO:示例文本
                    //创建一个意图对象
                    Intent ex_int = new Intent();
                    //创建组件，通过组件来响应
                    ComponentName ex_component = new ComponentName(JSONActivity.this, ExampleActivity.class);
                    ex_int.setComponent(ex_component);
                    startActivity(ex_int);
                    // mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_json://TODO:导出json格式
                    //mTextMessage.setText("json");
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        extraExport = getIntent().getStringExtra(EXTRA_EXPORT);
        extraJson = getIntent().getStringExtra(EXTRA_JSON);

        mTextMessage = (TextView)this.findViewById(R.id.message);
        mTextMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTextMessage.setTextColor(getResources().getColor(black));

        button_ex = (Button) findViewById(R.id.btn_ex);
        button_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        button_pre = (Button) findViewById(R.id.btn_pre);
        button_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    objectToJson();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //showInputDialog();
            }
        });

        if(extraJson != null && extraExport.equals("yes")){
            System.out.println("传送成功"+extraJson+extraExport);
            showExample(extraJson);
        }
        else{
            System.out.println("传送失败");
        }


    }
    public void showExample(String filename){
        FILE_JSON_NAME = filename;
        FileInputStream fis = null;//定义一个全局变量
        try {
            fis = openFileInput(FILE_JSON_NAME);//打开要读取的文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            try {
                fis = openFileInput("沙特");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        try {
            if (fis.available() == 0){//判断文件是否为空，为空就直接返回
                //return;
                fis = openFileInput("沙特");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] readBytes = new byte[0];//把文件里的内容转化为字节
        try {
            readBytes = new byte[fis.available()];
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while(fis.read(readBytes) != -1){//读文件，直到读到最后
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = new String(readBytes);//把读到的字节转化为字符串
        //textView.setText(text);
        mTextMessage.setText(text);
    }
    public void objectToJson() throws Exception {
        JSONArray jsa = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("haha", 22);
        jsonObject.put("hey", 24);
        jsonObject.put("ha", "zhao");
        jsonObject.put("age", 25);
        Toast.makeText(JSONActivity.this,
                jsonObject.toString(),
                Toast.LENGTH_LONG).show();
        mTextMessage.setText(jsonObject.toString());
    }
    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(JSONActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(JSONActivity.this);
        inputDialog.setTitle("输入保存JSON名如json").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(JSONActivity.this,
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        FILE_NAME = editText.getText().toString();
                        String string = mTextMessage.getText().toString();
                        FileOutputStream outputStream;
                        try{
                            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                            outputStream.write(string.getBytes());
                            outputStream.close();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        mToolBarTextView.setText("标注工具");
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(mOnMenuItemClick);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private Toolbar.OnMenuItemClickListener mOnMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_file://TODO：浏览目录
                    Intent intent = new Intent();
                    //创建组件，通过组件来响应
                    ComponentName component = new ComponentName(JSONActivity.this, FileActivity.class);
                    intent.setComponent(component);
                    startActivity(intent);
                case R.id.action_save:  //TODO：保存
                    showInputDialog();
                    break;
                case R.id.action_edit:
                    showListDialog();
                    //   gotoFile();
                    break;
                case R.id.action_settings:
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                    else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                    break;
                case R.id.action_json:
                    showExDialog();
                    break;
                case R.id.action_pre:
                    Intent json_int = new Intent();
                    //创建组件，通过组件来响应
                    ComponentName json_component = new ComponentName(JSONActivity.this, JSONActivity.class);
                    json_int.setComponent(json_component);
                    startActivity(json_int);
            }
            return true;
        }
    };
    private void exportJson(){  //导出json 预览
        Intent intent = new Intent();
        ComponentName component = new ComponentName(JSONActivity.this, JSONActivity.class);
        intent.setComponent(component);
        //  String path = file.getPath();
        String flag = "yes";
        //   intent.putExtra(JSONActivity.EXTRA_JSON, path);
        intent.putExtra(JSONActivity.EXTRA_EXPORT, flag);
        startActivity(intent);
    }

    private void showListDialog() {
        final String[] items = { "沙特","菲律宾总统","北南国际","俄罗斯生化","日本选举","大师赛" };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(JSONActivity.this);
        listDialog.setTitle("读取内置纯文本示例");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                // ...To-do
                //String filename = "";
                //根据点击的item显示不同的内置文本
                if(which == 0){
                    FILE_NAME = "a";
                }else if(which == 1){
                    FILE_NAME = "b";
                }else if(which == 2){
                    FILE_NAME = "c";
                }else if (which == 3){
                    FILE_NAME = "d";
                }else if(which == 4){
                    FILE_NAME = "e";
                }
                else if(which == 5){
                    FILE_NAME = "f";
                }else{

                }
                Toast.makeText(JSONActivity.this,
                        "你点击了" + items[which],
                        Toast.LENGTH_SHORT).show();
                FileInputStream fis = null;//定义一个全局变量
                try {
                    fis = openFileInput(FILE_NAME);//打开要读取的文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    if (fis.available() == 0){//判断文件是否为空，为空就直接返回
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] readBytes = new byte[0];//把文件里的内容转化为字节
                try {
                    readBytes = new byte[fis.available()];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    while(fis.read(readBytes) != -1){//读文件，直到读到最后
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String text = new String(readBytes);//把读到的字节转化为字符串
                //textView.setText(text);
                mTextMessage.setText(text);
            }
        });
        listDialog.show();
    }
    private void showExDialog(){
        final EditText editText = new EditText(JSONActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(JSONActivity.this);
        inputDialog.setTitle("输入导出JSON的文件名如hi").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(JSONActivity.this,"成功导出JSON ："+
                                        editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        FILE_NAME = editText.getText().toString();
                        String string = mTextMessage.getText().toString();
                        FileOutputStream outputStream;
                        try{
                            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                            outputStream.write(string.getBytes());
                            outputStream.close();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }


}
