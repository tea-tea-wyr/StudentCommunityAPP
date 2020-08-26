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
import com.example.studentcommunityapp.bean.Audio;
import com.example.studentcommunityapp.bean.Video;

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

public class AudioMoreFragment extends Fragment {

    private AudioMoreViewModel mViewModel;
    private List<Audio> audio=new ArrayList<>();
    private ArrayList<Audio> audio_list=new ArrayList<>();
    private Audio show_audio;
    private MoreAudioAdapter adapter;
    private RecyclerView mRecyclerview;
    private StaggeredGridLayoutManager layoutManager;
    private ImageView image;
    public static AudioMoreFragment newInstance() {
        return new AudioMoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.audio_more_fragment, container, false);
        image=root.findViewById(R.id.back_home);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_audioMoreFragment_to_navigation_home);
            }
        });
        /**
         * 音频类的recyclerview
         */
        initAudios("http://81.70.27.208:8000/api/get_data?op=audio&page=1");
        mRecyclerview=root.findViewById(R.id.recycler4);
        layoutManager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(layoutManager);
        adapter=new MoreAudioAdapter(audio);
        mRecyclerview.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AudioMoreViewModel.class);
        // TODO: Use the ViewModel
    }

    public void initAudios(String url)
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
                    if (state.equals("1")) {
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data_info")));
                        Log.d(TAG, "获取数据" + jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            show_audio = new Audio(object);
                            audio_list.add(new Audio(show_audio.getAudio_ID(), show_audio.getAudio_picture(),show_audio.getAudio_title()));
                        }
                        System.out.println(audio_list.size());
                        audio.clear();
                        audio.addAll(audio_list);
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