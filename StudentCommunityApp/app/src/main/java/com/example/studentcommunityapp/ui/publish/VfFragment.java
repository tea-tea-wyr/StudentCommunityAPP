package com.example.studentcommunityapp.ui.publish;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentcommunityapp.R;

public class VfFragment extends Fragment {

    private View rootView;


    private Button submit;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                String status_meg;
                status_meg=(String)msg.obj;

                if(status_meg.equals("1")){
                    showSuc(getContext(),"成功提交");
                }
                else {
                    showFail(getContext(),"提交失败");
                }
            }
        }
    };

    public static void showSuc(Context context, String msg) {
        Toast toast = new Toast(context);
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.toast, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_toast);
        //设置文本
        tvMessage.setText(msg);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_LONG);
        //显示
        toast.show();
    }

    public static void showFail(Context context, String msg) {
        Toast toast = new Toast(context);
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.toast_fail, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_toast);
        //设置文本
        tvMessage.setText(msg);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_LONG);
        //显示
        toast.show();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.fragment_publish_2, container, false);


        submit=rootView.findViewById(R.id.vf_add_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message=new Message();
                message.what=100;
                message.obj="1111";
                handler.sendMessage(message);

//                    String postMessage1;
//                    String postMessage2;
//                    String postMessage3;
//                    String postMessage4;
//                    postMessage1=edtMsg1.getText().toString();
//                    postMessage2=edtMsg2.getText().toString();
//                    postMessage3=edtMsg3.getText().toString();
//                    postMessage4=edtMsg4.getText().toString();

//                    Log.d("getComment","postmessage: "+postMessage1);
//                final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//                final OkHttpClient client = new OkHttpClient();
//                final String url="http://81.70.27.208:8000/api/set_classtable";
//                HashMap<String,String> map=new HashMap<>();
//                    map.put("name",postMessage1);
//                    map.put("time",postMessage2);
//                    map.put("number",postMessage3);
//                    map.put("place",postMessage4);

//                final String cookie;
//                cookie= WebHelper.getCookie(getActivity());
//                Gson gson=new Gson();
//                final String data=gson.toJson(map);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Log.d("PostComment","in");
//                            RequestBody body = RequestBody.create(JSON,data);
//                            Request request = new Request.Builder()
//                                    .url(url)
//                                    .header("cookie",cookie)
//                                    .post(body)
//                                    .build();
//                            Response response = client.newCall(request).execute();
//                            String response_string=response.body().string();
//                            JSONObject jsonObject_data = new JSONObject(response_string);
//                            String status=jsonObject_data.getString("status");
//                            Log.d("PostComment",status);
//                            Message message=new Message();
//                            message.what=100;
//                            message.obj=status;
//                            handler.sendMessage(message);
//                        }
//                        catch (IOException e){
//                            Log.d("PostComment",e.toString());
//                        }
//                        catch (JSONException e){
//                            Log.d("PostComment",e.toString());
//                        }
//                    }
//                }).start();


            }

        });


        return rootView;
    }
}
