package com.example.studentcommunityapp.ui.more;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Essay;
import com.example.studentcommunityapp.bean.Video;
import com.example.studentcommunityapp.ui.home.VideoAdapter;

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

public class VideoMoreFragment extends Fragment {

    private VideoMoreViewModel mViewModel;
    private ImageView image;
    private Video show_video;
    private List<Video> video=new ArrayList<>();
    private ArrayList<Video> video_list=new ArrayList<>();
    private MoreVideoAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    private RecyclerView mRecyclerview;

    public static VideoMoreFragment newInstance() {
        return new VideoMoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.video_more_fragment, container, false);
        image=root.findViewById(R.id.back_home);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_videoMoreFragment_to_navigation_home);
            }
        });

        /**
         * 视频类的recyclerview
         */
        initVideos("http://81.70.27.208:8000/api/get_data?op=video&page=1");
        mRecyclerview=root.findViewById(R.id.recycler5);
        layoutManager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(layoutManager);
        adapter=new MoreVideoAdapter(video);
        mRecyclerview.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VideoMoreViewModel.class);
        // TODO: Use the ViewModel
    }

    public void initVideos(String url) {
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
                    if (state.equals("1")) {
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data_info")));
                        Log.d(TAG, "获取数据" + jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            show_video = new Video(object);
                            video_list.add(new Video(show_video.getVideo_picture(), show_video.getVideo_ID()));

                        }
                        System.out.println(video_list.size());
                        video.clear();
                        video.addAll(video_list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        Log.d(TAG, "null");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse_catch: ", e);
//                    e.printStackTrace();
                }

            }
        });

    }


}