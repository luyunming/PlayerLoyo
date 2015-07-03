package cn.lym.playerloyo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.util.GetAllMusic;

public class AllMusicInMusicList extends Fragment {
    List<Map<String, Object>> list;
    private ListView listView;
    private String[] allMusicPath;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_music_in_music_list, container, false);
//        allMusicPath = new String[]{};
        init();
        initHandler();
        setListView();
        initHandler();
        startSearchMusic();
        return view;
    }

    public void init() {
        listView = (ListView) view.findViewById(R.id.list_view_all_music);
        list = new ArrayList<>();
        for (int i = 1; i <= allMusicPath.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("name", allMusicPath[i - 1]);
            list.add(map);
        }
    }

    public void initHandler() {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    allMusicPath = msg.getData().getStringArray("allMusicPath");
                    init();
                    setListView();
                }
            }
        };
    }

    public void setListView() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.list_view_item_music, new String[]{"id", "name"}, new int[]{R.id.all_music_id, R.id.text_view_list_item_music});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = list.get(position);
            }
        });
    }

    public void startSearchMusic() {
        Thread search = new Thread() {
            @Override
            public void run() {
                super.run();
                GetAllMusic s = new GetAllMusic();
                String[] x = s.searchMusic("/mnt/sdcard");
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putStringArray("allMusicPath", x);
                msg.setData(bundle);
                msg.what = 0x123;
                new Handler().sendMessage(msg);
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}