package com.example.studentcommunityapp.ui.more;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Essay;
import com.example.studentcommunityapp.ui.home.EssayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class ArticleMoreFragment extends Fragment {

    private ArticleMoreViewModel mViewModel;
    private ImageView image;
    private List<Essay> essay=new ArrayList<>();
    private ArrayList<Essay> essay_list1=new ArrayList<>();
    private Essay show_essay;
    private RecyclerView mRecyclerView;
    private MoreEssayAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static ArticleMoreFragment newInstance() {
        return new ArticleMoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.article_more_fragment, container, false);

        image=root.findViewById(R.id.back_home);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_articleMoreFragment_to_navigation_home);
            }
        });


        initMoreEssays("http://81.70.27.208:8000/api/get_data?op=article&page=1");
        mRecyclerView=root.findViewById(R.id.recycler3);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter=new MoreEssayAdapter(essay);
        mRecyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ArticleMoreViewModel.class);
        // TODO: Use the ViewModel
    }

    public void initMoreEssays(String url)
    {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)//要访问的链接
                .build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                String res = response.body().string();
                Log.d(TAG, "onResponse: " + res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String state = String.valueOf(jsonObject.get("status"));
                    if(state.equals("1")){
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data_info")));
                        Log.d(TAG,"获取数据"+jsonArray);
                        for (int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            show_essay= new Essay(object);
                            essay_list1.add(new Essay(show_essay.getEssay_ID(),show_essay.getPicture(),show_essay.getEssay_title(),show_essay.getEssay_description()));

                        }
                        System.out.println(essay_list1.size());
                        essay.clear();
                        essay.addAll(essay_list1);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    else {
                        Log.d(TAG, "null");
                    }
                } catch (JSONException e){
                    Log.e(TAG, "onResponse_catch: ", e);
//                    e.printStackTrace();
                }

            }
        });
    }

}