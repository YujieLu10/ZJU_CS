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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cube.autlab.BottomNavigationViewHelper;
import com.cube.autlab.MainActivity;
import com.cube.autlab.R;
import com.cube.autlab.UI.JsonFormatTool;
import com.cube.autlab.UI.MyFragment;
import com.cube.autlab.UI.SelectionTextView;
import com.cube.autlab.file.FileActivity;
import com.cube.autlab.file.SDCardHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.R.color.black;

/**
 * Created by c-ten on 2017/11/25.
 */

public class TextActivity extends AppCompatActivity {
    private boolean isAllowSelect = false;
    private boolean isFirst = false;
    private Button button_state;
    private Button button_na, button_ci, button_r_na_ci;
    private Button button_person, button_time, button_au, button_pena;
    private SelectionTextView richText;
    private SeekBar seek;
    private JsonFormatTool formatTool = new JsonFormatTool();
    public Integer[] textPara = new Integer[200];
    public JSONObject obj = new JSONObject();

    private static String FILE_NAME = "a";
    private static String JSON_FILE_NAME = "jsontest";

    //TODO: extra 文本路径消息
    public static final String EXTRA_FILE_PATH =  "com.cube.lab.demo";
    public static final String EXTRA_OPEN = "no";
    public static final String EXTRA_FILE_NAME = "com.cube.autolab";
    public static final String EXTRA_OPEN_EX = "no";
    private String extrapath = "";
    private String extraopen = "no";
    /* from exampleActivity */
    private String extraname = "";
    private String extraopenex = "no";
    private boolean isNationCity = false;

    /**
     * 存储为JSON格式
     * @throws Exception
     */
  public void objectToJson() throws Exception { //TODO:获取已标注的结果
      richText.setEnd();
      richText.setJson();
      obj = richText.setObj;
  }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //创建一个意图对象
                    Intent main_int = new Intent();
                    //创建组件，通过组件来响应
                    ComponentName main_component = new ComponentName(TextActivity.this, MainActivity.class);
                    main_int.setComponent(main_component);
                    startActivity(main_int);
                    //textView.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    //创建一个意图对象
                    Intent ex_int = new Intent();
                    //创建组件，通过组件来响应
                    ComponentName ex_component = new ComponentName(TextActivity.this, ExampleActivity.class);
                    ex_int.setComponent(ex_component);
                    startActivity(ex_int);
                    return true;
                case R.id.navigation_json://TODO:预览json格式
                    //TODO:保存 jsonobj.tostring
                    saveJson();
                    Intent pre_intent = new Intent();
                    ComponentName pre_component = new ComponentName(TextActivity.this, JSONActivity.class);
                    pre_intent.setComponent(pre_component);
                    String path = JSON_FILE_NAME;
                    String flag = "yes";
                    pre_intent.putExtra(JSONActivity.EXTRA_JSON, path);
                    pre_intent.putExtra(JSONActivity.EXTRA_EXPORT, flag);
                    startActivity(pre_intent);
                    return true;
            }
            return false;
        }
    };
    private void saveJson(){
        try {   //TODO: 获取新的json数据
            objectToJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String string = formatTool.formatJson(obj.toString()); //TODO: JSON格式存储为string类型 jsonobj.toString()
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(JSON_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void showExDialog(){
         /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(TextActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(TextActivity.this);
        inputDialog.setTitle("输入导出JSON的文件名如hi").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TextActivity.this,"成功导出JSON ："+
                                        editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        JSON_FILE_NAME = editText.getText().toString();
                        saveJson();
//                        String string = richText.getText().toString();
//                        FileOutputStream outputStream;
//                        try{
//                            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//                            outputStream.write(string.getBytes());
//                            outputStream.close();
//                        } catch(Exception e) {
//                            e.printStackTrace();
//                        }

                    }
                }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.showtext);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation2);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        initToolbar();

        richText = (SelectionTextView) findViewById(R.id.text_view);
        //richText.p.map.get("");
        handleText();
        extrapath = getIntent().getStringExtra(EXTRA_FILE_PATH);
        extraopen = getIntent().getStringExtra(EXTRA_OPEN);
        extraname = getIntent().getStringExtra(EXTRA_FILE_NAME);
        extraopenex = getIntent().getStringExtra(EXTRA_OPEN_EX);
        loadFile();
        seek=(SeekBar)findViewById(R.id.SeekBar02);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                richText.setTextSize(progress/5);
                richText.setText(richText.getText());
            }
        });

        //boolean isRead = false;
        if(extraopen != null && extraopen.equals("yes")){
          //  isRead = true;
            SDCardHelper sh = new SDCardHelper();
            try {
//                    File file = new File(Environment.getExternalStorageDirectory(),
//                            FILENAME);
                File file = new File(extrapath);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String readline = "";
                StringBuffer sb = new StringBuffer();
                while ((readline = br.readLine()) != null) {
                    System.out.println("readline:" + readline);
                    sb.append(readline);
                }
                br.close();
                System.out.println("读取成功：" + sb.toString());
                                richText.setText(sb.toString());
               richText.setTextColor(getResources().getColor(black));
                /**
                 * 存至内部
                 */
                String string = richText.getText().toString();
                FileOutputStream outputStream;
                try{
                    outputStream = openFileOutput("outToin", Context.MODE_PRIVATE);
                    outputStream.write(string.getBytes());
                    outputStream.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            showExample("outToin");
        }
        else {
            System.out.println("传送失败");
            }
            //   showReadDialog();
            //TODO:显示选取的示例文本
            if (extraopenex != null && extraopenex.equals("yes")&&extraname!=null) {
                showExample(extraname);
            }
//            extraopenex = "no";
//            extraopen = "no";
        //}

    }


    private void handleText(){
        button_state = (Button) findViewById(R.id.btn_state);
        button_na = (Button) findViewById(R.id.btn_na);
        button_ci = (Button) findViewById(R.id.btn_ci);
        button_r_na_ci = (Button) findViewById(R.id.btn_r_na_ci);
        button_person = (Button) findViewById(R.id.btn_person);
        button_time =(Button) findViewById(R.id.btn_time);
        button_au = (Button) findViewById(R.id.btn_authority);
        button_pena = (Button) findViewById(R.id.btn_pena);

        richText = (SelectionTextView) findViewById(R.id.text_view);
        button_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllowSelect = !isAllowSelect;
                richText.setAllowSelectText(isAllowSelect);
                if(isAllowSelect){
                    button_state.setText("暂停标注");
                }else{
                    button_state.setText("开始标注");
                }
            }
        });

        button_na.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                richText.setNation();
            }
        });

        button_ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                richText.setCity();
            }
        });

        button_r_na_ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                richText.setNationCity();
            }
        });

        button_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                richText.setPerson();
            }
        });

        button_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                richText.setTime();
            }
        });

        button_au.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                richText.setAuthority();
            }
        });

        button_pena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                richText.setPeNa();
            }
        });

    }

    public void showExample(String filename){
        FILE_NAME = filename;
        FileInputStream fis = null;//定义一个全局变量
//        try {
//            fis = openFileInput(FILE_NAME);//打开要读取的文件
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            try {
//                fis = openFileInput("沙特");
//            } catch (FileNotFoundException e1) {
//                e1.printStackTrace();
//            }
//        }
//        try {
//            if (fis.available() == 0){//判断文件是否为空，为空就直接返回
//                //return;
//                fis = openFileInput("沙特");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        byte[] readBytes = new byte[0];//把文件里的内容转化为字节
//        try {
//            readBytes = new byte[fis.available()];
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            while(fis.read(readBytes) != -1){//读文件，直到读到最后
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            fis = openFileInput(FILE_NAME);//打开要读取的文件
        } catch (FileNotFoundException e) {
            try {
                fis = openFileInput("a");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            Toast.makeText(TextActivity.this,
                    "文件名不存在，加载默认文本",
                    Toast.LENGTH_SHORT).show();
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


        richText.setText("");
        String text = new String(readBytes);
        //text = "。"+text;
        String gettext = "";
        String[] s = text.split("。");//以\n来截取！转化为数组！
        for(int i = 0; i < s.length; i++){//遍历当前数组！
            if(i == 0) {
                textPara[i] = s[i].length()+1;
            }
            else{
                textPara[i] = s[i].length() + textPara[i-1]+1;
            }
            System.out.println("debug>>>>>"+s[i]);
            gettext = gettext + s[i] + "。";
            richText.append(s[i]+"。");
        }
        try {
            richText.setClear();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        richText.getText(text);
        richText.setPara(textPara);
//        String text = new String(readBytes);//把读到的字节转化为字符串
//        //textView.setText(text);
    }
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
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
                    ComponentName component = new ComponentName(TextActivity.this, FileActivity.class);
                    intent.setComponent(component);
                    startActivity(intent);
                    break;
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
                case R.id.action_json:  //导出
                    showExDialog();
                    break;
                case R.id.action_pre:
                    //TODO:保存 jsonobj.tostring
                    saveJson();
                    Intent pre_intent = new Intent();
                    ComponentName pre_component = new ComponentName(TextActivity.this, JSONActivity.class);
                    pre_intent.setComponent(pre_component);
                    String path = JSON_FILE_NAME;
                    String flag = "yes";
                    pre_intent.putExtra(JSONActivity.EXTRA_JSON, path);
                    pre_intent.putExtra(JSONActivity.EXTRA_EXPORT, flag);
                    startActivity(pre_intent);
            }
            return true;
        }
    };
    private void exportJson(){  //导出json 预览

    }
    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(TextActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(TextActivity.this);
        inputDialog.setTitle("输入保存文本名字如test").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TextActivity.this,
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        FILE_NAME = editText.getText().toString();
                        //String string = textView.getText().toString();
                        String string = richText.getText().toString();
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
    private void showListDialog() {
        final String[] items = { "沙特","菲律宾总统","北南国际","俄罗斯生化","日本选举","大师赛" };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(TextActivity.this);
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
                Toast.makeText(TextActivity.this,
                        "你点击了" + items[which],
                        Toast.LENGTH_SHORT).show();
                FileInputStream fis = null;//定义一个全局变量

//                String strLine = "";
//                try {
//                    InputStream instream = new FileInputStream(FILE_NAME);
//                    if (instream != null)
//                    {
//                        InputStreamReader inputreader = new InputStreamReader(instream);
//                        BufferedReader buffreader = new BufferedReader(inputreader);
//                        String line = "";
//                        //分行读取
//                        while (( line = buffreader.readLine()) != null) {
//                            strLine += line + "\n";
//                            System.out.println("<<<<<<<<<<<"+strLine);
//                        }
//                        instream.close();
//                    }
//                    else{
//                        System.out.println(">>>>><<<<<<inputstream is null");
//                    }
//                }
//                catch (java.io.FileNotFoundException e)
//                {
//                    Log.d("TestFile", "The File doesn't not exist.");
//                }
//                catch (IOException e)
//                {
//                    Log.d("TestFile", e.getMessage());
//                }

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


                richText.setText("");
                String text = new String(readBytes);
                //String gettext = "";
                //text = "。"+text;
                String[] s = text.split("。");//以\n来截取！转化为数组！
                for(int i = 0; i < s.length; i++){//遍历当前数组！
                    if(i == 0) {
                        textPara[i] = s[i].length()+1;
                    }
                    else{
                        textPara[i] = s[i].length() + textPara[i-1]+1;
                    }
                    System.out.println("debug>>>>>"+s[i]);
                  //  gettext = gettext + s[i] + "。";
                    richText.append(s[i]+"。");
                }
                try {
                    richText.setClear();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                richText.getText(text);
                richText.setPara(textPara);
            }
        });
        listDialog.show();
    }
    private void loadFile(){
        String filename = "a";
        //String string = "Hello world!";
        String string = "近日在沙特阿拉伯举行的未来投资计划(Future Investment Initiative)会议上发言的索菲亚女性机器人，已成为世界上第一个获得公民身份的机器人。\n" +
                "沙特阿拉伯周三在首都利雅得举行未来投资计划会议前，赋予了索菲亚公民身份。\n" +
                "“对于获得这个独特的地位，我感到荣幸和自豪。” 索菲亚在一个小组讲话中告诉观众。索菲亚是世界上第一个拥有公民身份的机器人，具有历史性意义。\n" +
                "不过并没有详细说明其公民身份的细节。\n" +
                "在这次活动中，索菲娅还站在讲台上回答了主持人和记者Andrew Ross Sorkin的问题。 问题主要涉及索菲亚作为类人机器人的地位，人们可能对未来机器人运行世界中感到担忧等。\n" +
                "Sorkin告诉索菲娅说：“我们都想阻止未来向不好的方向发展，”引起索菲娅嘲笑Sorkin的宿命论。\n" +
                "索菲亚的创始人David Hanson在SXSW节日的现场演示中向索菲亚问道：“你想毁灭人类吗?请说‘不’。”索菲亚面无表情地回答说，“好吧，我会毁灭人类”。\n" +
                "同时，Hanson表示，索菲亚及其未来机器人将在护理设施上协助老年人，并协助老年人去公园和活动。\n" +
                "幸运的是，索菲亚在最近的“未来投资计划”活动中更多地提出了这些观点。它告诉Sorkin，它想使用人工智能来帮助人类“过上更美好的生活”，而且“我会尽全力使\u200B\u200B世界变得更美好”。\n" +
                "总部设在香港的汉森机器人(Hanson Robotics)科技公司设计并制造了索菲亚。她采用人工智能和谷歌的语音识别技术，号称可以模拟超过62种面部表情。此前被媒体称索菲亚为“最像人的机器人”。\n" +
                "索菲亚可能很快就会有来自其他机器人制造商开发机器人的陪伴了，软银的Pepper机器人在2014年作为原型机发布，并在一年后作为消费者产品。该公司在不到一分钟内就卖出了1,000台机器人。";
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        filename = "b";
        string = "参考消息网11月1日报道 日媒称，菲律宾总统杜特尔特31日前往东京皇宫，拜会日本明仁天皇和皇后美智子，并讨论了两国间时而因历史问题而紧张的双边关系。\n" +
                "\n" +
                "据共同社10月31日报道，根据日本宫内厅发布的消息，这次会晤进行了约25分钟。在此期间，明仁天皇指出，在二战时期日本占领菲律宾的3年多时间里，有很多当地人失去了生命。\n" +
                "\n" +
                "杜特尔特说，两国已经跨越了历史，建立了合作关系，他还感谢日本在战后一直对菲提供援助。\n" +
                "\n" +
                "一位政府消息人士说，杜特尔特曾发表的一些争议性言论令一些日本政府人士对安排他与天皇夫妇会面感到担忧。但宫内厅说，杜特尔特对日本皇室怀有敬意，他在会见刚开始时显得有些紧张，在天皇夫妇现身后，他向他们鞠躬致意。\n" +
                "\n" +
                "报道称，日本首相安倍晋三30日与杜特尔特举行会谈，双方重申将在包括朝鲜问题等的地区问题上合作。双方还发表了关于日本今后5年对菲提供发展援助的共同声明。\n" +
                "\n" +
                "另据日本《产经新闻》10月31日报道，日菲两国首脑会谈30日举行。杜特尔特总统表达了与日本加强关系的愿望，称“我们的伙伴关系可以说是经得起考验的伙伴关系……菲律宾已做好与日本共同开创战略伙伴关系黄金时代的准备”。\n" +
                "\n" +
                "报道称，听到杜特尔特的一席发言，坐在对面的安倍首相不由得露出满意的表情。日本方面也鲜明地表现出对菲予以支持的姿态。\n" +
                "\n" +
                "报道称，其中日本重视的是提高海上保安机构的沿岸监视能力。日本政府将把有偿租借给菲律宾政府的两架海上自卫队TC-90教练机改为无偿转让，目的为使菲律宾“牵制中国”。\n" +
                "\n" +
                "报道称，杜特尔特在南海问题上并不是一边倒地指责中国。菲律宾也从中国获得了援助。杜特尔特去年上任后，在外交上表现出八面玲珑的特点。但在国内，政府军与伊斯兰激进派在棉兰老岛发生冲突，杜特尔特重点推进的“禁毒”政策陷入僵局，被迫调整路线。\n" +
                "\n" +
                "报道称，在冲突中受损的马拉维市，地区重建是否成功将直接关系到人们对政府的信任，但重建费用十分高昂，在中美的援助不明确的情况下，杜特尔特对日本的期待日益高涨。";
        //FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        filename = "c";
        string = "中青在线巴库11月2日电（中国青年报·中青在线驻南高加索记者 黄庆）11月1日，应伊朗总统鲁哈尼邀请，俄罗斯总统普京和阿塞拜疆总统阿利耶夫相聚伊朗首都德黑兰。3国总统就跨里海地区的交通运输、能源和经济合作，以及中东地区形势、与恐怖主义作斗争等问题进行了磋商。据此间媒体报道，3国首脑会晤的主要议题之一就是推动“北南国际运输走廊”建设。\n" +
                "\n" +
                "    “北南国际运输走廊”是连接欧亚大陆南北方向的重要运输通道，它将以铁路、公路、水路多种联运的方式，把南亚、东南亚与北欧、中东欧国家连接在一起。这一项目是2002年5月在俄罗斯的倡议下由印度、伊朗等国发起建设的，这条运输通道全长约5000公里。俄罗斯、阿塞拜疆和伊朗3国均为里海沿岸国家，在连接南亚、东南亚到北欧的交通运输通道上起着关键性的作用。由于存在核问题，伊朗在区域合作中也受到重重制约。2015年7月中旬，随着伊朗核问题的解决和西方解除对伊朗的制裁，重启“北南国际运输走廊”项目再次被提上议事日程。\n" +
                "\n" +
                "    有分析指出，阿塞拜疆在促进跨里海地区合作和推动“北南国际运输走廊”建设中发挥了积极作用。正是在阿塞拜疆领导人倡议下，2016年8月，俄阿伊3国总统在巴库举行了首次三方会晤，3国领导人就建设“北南国际运输走廊”达成了共识。阿塞拜疆处于俄罗斯与伊朗之间，是“北南国际运输走廊”中里海沿岸路段的关键环节。近年来，阿塞拜疆不断进行经济转型，希望通过发展交通运输，在跨境货物运输中获得更多收益。为了推进“北南国际运输走廊”建设，阿塞拜疆加快本国境内铁路建设的同时，也加大了对伊朗铁路建设的投资。有资料显示，为了促进阿伊之间的铁路建设，阿塞拜疆政府向伊朗提供了6000万欧元贷款，并与伊朗达成了修建伊朗通向阿塞拜疆边界的阿斯塔拉路段和货运站的协议。\n" +
                "\n" +
                "    目前，从阿塞拜疆到伊朗边界的铁路已经建成通车，伊朗境内约3公里路段也将于2017年11月底完成。据阿塞拜疆铁路公司总裁古尔班诺夫透露，阿伊之间的阿斯塔拉铁路线将于今年内开通。阿斯塔拉路段建成后，标志着“北南国际运输走廊”铁路线路将正式开通。有专家分析认为，俄阿伊3国联手，加速“北南国际运输走廊”的建设，将会促进跨里海地区的经济发展。这条运输通道的开通将会迅速提升“北南国际运输走廊”的货运能力。预计在初始阶段，这条运输通道年货运量为600万吨，远景规划可达2000万吨。阿伊之间铁路贯通后，大大缩短货物由印度运往北欧的时间，降低运输成本，将对苏伊式运河的货运形成竞争态势，未来的“北南国际运输走廊”甚至会改变欧亚大陆货物运输格局。\n" +
                "\n" +
                "    值得注意的是，正是在两天前，连接阿塞拜疆、格鲁吉亚和土耳其的“巴库-第比利斯-卡尔斯铁路”（BTK）刚刚开通，这条横跨南高加索地区的铁路使欧亚大陆东西方向增添了一条新通道。现在，欧亚大陆南北方向上的交通运输合作又出现了新进展。有专家认为，阿塞拜疆处于欧亚大陆东-西、南-北交通运输大动脉的交叉点上。对于阿塞拜疆来说，BTK铁路和“北南国际运输走廊”具有重要的政治经济意义，进一步提升了阿塞拜疆在南高加索地区的地位和作用。这两条交通要道将使阿塞拜疆成为本地区最重要的一个国家。\n" +
                "\n" +
                "    据报道，俄阿伊3国元首第三次会晤将在俄罗斯举行。有分析认为，三方合作越来越成为本地区更容易被接受的一种合作方式。阿利耶夫总统在3国首脑会晤后向媒体发表声明时指出，“三方合作的方式对地区安全具有重要意义，这种成功的合作对地区的稳定起到了重要的作用”。3国之间通过高层互动，便于排除疑虑，提高互信，在互利互惠、加强经济合作的同时，也有利于维护、巩固地区安全和政治稳定。";
        //FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        filename = "d";
        string = "海外网11月1日电 当地时间10月31日，俄罗斯总统普京发警告称，有神秘国外势力正在采集俄罗斯公民的DNA数据，要求政府警惕可能针对俄罗斯进行的生化袭击。\n" +
                "\n" +
                "据美国《新闻周刊》报道，俄罗斯总统普京此前向下属人权理事会致电，表达了他对俄罗斯公民被第三方势力偷拍与“监控”的担忧，他暗示，这种行为背后可能隐藏着更大的阴谋。\n" +
                "\n" +
                "普京质问道，“（俄罗斯公民的）照片正在被收集，这没事，但是你们知道整个国家的生物信息材料也在被收集吗？那么问题来了，他们为什么要这样做？”\n" +
                "\n" +
                "据报道，普京并没有透露幕后势力是谁，但是他强调这个势力正“有目的性并且专业地”收集俄罗斯不同民族的DNA信息。\n" +
                "\n" +
                "克宫发言人德米特里·佩斯科夫随后也证实，“确实，就俄罗斯政府掌控的信息，某些外国使者，非政府组织及其他机构正在从事这方面的活动，当然，总统也获知了相关情况。”\n" +
                "\n" +
                "俄罗斯国防安全委员会议员弗朗茨·克林采维奇（ Franz Klintsevich）针对这种情况解释道，“不同民族的人群对生化武器的应激反应不同，这就是为什么西方要精心策划，在全俄范围内收集生物信息的原因。”\n" +
                "\n" +
                "克宫的声明发布之后，在俄罗斯引起了极大轰动。俄媒称，俄罗斯从苏联时代开始，就持续遭受“外部破坏”，没想到这种残余的敌对行为一直延续至今。俄罗斯境内甚至出现声音，要求起草“生物安全法”，以应对将来可能出现的针对俄罗斯的生化袭击。（海外网 杨佳）";
        //FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        filename = "e";
        string = "海外网11月1日电 日本第195次特别国会于当地时间11月1日召开，日本自民党总裁安倍晋三在当日下午举行的众参两院全体大会首相提名选举中被选为第98任首相，全体阁僚和党高层留任。1日晚间，安倍第4次内阁将正式启动，这是继1952年吉田茂担任首相以来，日本时隔65年将再次出现“第4次内阁”。由于修宪势力获得了远远超过修宪动议所需众院额定议席数的三分之二以上，包含宪法第9条在内的修宪问题将成为焦点。\n" +
                "\n" +
                "据日本时事通讯社报道，在1日下午举行的日本众参两院首相提名选举中，日本自民党总裁安倍晋三再次当选为日本第98任首相。第4次安倍内阁启动后党内四大要职将全部留任，全体阁僚也将继续任职。自民党的大岛理森再任众议院议长、立宪民主党的赤松广隆当选为副议长，自民党的古屋圭司当选为议院运营委员长。\n" +
                "\n" +
                "8月3日启动的第3届安倍政府第3次改组内阁已于1日上午全员辞职。安倍晋三在众参两院全体大会上被指名为首相后，将与公明党党首山口那津男在首相官邸举行会谈，就维持联立政权等进行会谈。之后官房长官菅义伟将公布阁僚名单，晚些时候皇宫将举行首相任命仪式和阁僚认证仪式。1日晚，首相官邸将召开记者见面会，安倍将说明新内阁的基本姿态以及国内外诸多课题的应对方针。记者见面会之后，安倍拟在今晚的首次内阁会议上指示编制2017年度补充预算案，旨在充实育儿支援措施以推进其招牌政策“育人革命”。\n" +
                "\n" +
                "围绕着特别国会会期问题，在1日上午的众院各派协议会上，朝野政党已达成一致，将特别国会会期延长至12月9日，时间长达39天。日本首相安倍晋三将在特别国会期间发表施政信念演讲，并接受各党质询。在野党计划在特别国会期间继续就森友学园与加计学园问题追究安倍的责任。此前，日本执政党将会期拟定为8天，即11月1日至8日，而在野党要求将会期延长至12月上旬，充分保证审议时间。\n" +
                "\n" +
                "对于选举之后的日本，国际社会最为关注的问题无疑是修宪前景。在新一届的日本国会中，自民、希望、维新各党对包括《宪法》第九条在内的修宪积极态度，公明党态度谨慎，立宪民主党则“反对修改第九条”，共产、社民两党也持守护第九条的立场。安倍今年5月发表修宪主张，称希望在宪法第9条中增加自卫队的内容，并给出了“2020年施行新宪法”的明确时间表。随着“修宪势力”可能在此次众议院选战后壮大，安倍领导的自民党正在考虑加速此进程。修改战后日本的“和平宪法”条款，即宣布日本“放弃战争”的宪法第9条，为日本将自卫队最终升级为军队、重获战争权利扫清道路。安倍欲使日本重获国家交战权和集体防卫权，复归“正常国家”地位。一旦失去“和平宪法”的束缚日本会走向何方，会对东亚局势造成什么样的冲击，中韩等国对此担忧加剧。\n" +
                "\n" +
                "据此前报道，31日，日本首相安倍晋三（自民党总裁）在该党高层会议上正式宣布，11月1日第四届安倍内阁启动后党内四大要职将全部留任。安倍在会上呼吁团结称，“不辜负国民的托付，一项一项切实兑现选举承诺十分重要。”四大要职以外的党高层除了议员退职和众院选举落选者外都将继续任用。关于修宪，自民党把最快于明年由国会提议纳入考虑。以在《宪法》第九条中写明自卫队存在为主的该党修宪草案拟最快在明年初提交给例行国会，以便朝野各党展开讨论。（编译/海外网 巩浩）";
        //FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        filename = "f";
        string = "北京时间11月1日，2017赛季最后一项ATP大师赛、总奖金为450.7万欧元的劳力士巴黎大师赛展开了男单第二轮的争夺。赛会五号种子、奥地利人蒂姆在第二盘连续浪费三个赛点，才以6-4/6-7（3）/6-4险胜德国人高约维茨克；八号种子、西班牙人布斯塔4-6/1-6不敌法国老将马胡，晋级总决赛形势堪忧。\n" +
                "\n" +
                "蒂姆本来在开赛前宣布退赛专心备战总决赛，但是又意外的出现在巴黎大师赛签表中。奥地利人近来状态惨淡，美网之后的四站赛事仅仅拿到了一场胜利。高约维茨克世界排名第62位，德国人此前在法国梅兹拿下职业生涯首冠。两人此前两次交手各胜一场，最近一次是去年梅兹蒂姆两盘胜出。\n" +
                "\n" +
                "蒂姆在第三局浪费了两个破发点，他在第五局再度发起攻势破发得手。两人此后都没有送出破发点，五号种子以6-4先声夺人。第二盘鏖战至5-5平后，蒂姆在第11局完成关键破发。第12局他40-0手握三个赛点，却意外的连丢五分被对手回破带入抢七。高约维茨克手感正佳，抢七局一路领先7-3扳平。\n" +
                "\n" +
                "进入决胜盘，两人的发球局都表现的非常稳定，前八局没有送出破发点战至四平。第十局蒂姆抓住整盘唯一的破发机会，破发成功6-4拿到这场来之不易的胜利。全场比赛耗时126分钟，他在第三轮将迎战西班牙老将沃达斯科和美网亚军安德森之间的胜者。\n" +
                "\n" +
                "布斯塔目前世界排名第11位，这位美网打入四强的新面孔也是近况不佳，美网后总战绩为1胜3负。目前他的冠军积分在活跃球员中排在第八位，身后的安德森、波特罗等人来势汹汹入围形势并不明朗。马胡世界排名第111位，两人在今年美网有过交手，布斯塔直落三盘胜出。\n" +
                "\n" +
                "布斯塔开局之后取得优势，第三局破发之后保发3-1领先。马胡在第五局挽救破发点保发之后，第六局回破追平。第十局布斯塔虽然化解两个破发点追至平分，还是被对手破发4-6交出首盘胜利。第二盘马胡一上来就两次破发5-0遥遥领先，此后两人各自保发法国人以6-1击败对手晋级。他在第三轮的对手是塞尔维亚人克拉吉诺维奇，后者以6-4/6-4爆冷淘汰了十号种子、美国大炮奎雷伊。";
        //FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showReadDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(TextActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(TextActivity.this);
        inputDialog.setTitle("输入读取文本名字如test").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FILE_NAME = editText.getText().toString();
                        Toast.makeText(TextActivity.this,
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        FileInputStream fis = null;//定义一个全局变量
                        try {
                            fis = openFileInput(FILE_NAME);//打开要读取的文件
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
                                String text = new String(readBytes);//把读到的字节转化为字符串
                                //textView.setText(text);
                                richText.setText(text);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            Toast.makeText(TextActivity.this,
                                    "此文件不存在",
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }
                }).show();
    }
}
