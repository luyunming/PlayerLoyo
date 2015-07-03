package cn.lym.playerloyo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.activity.MusicList;
import cn.lym.playerloyo.util.GetAllMusic;

public class AllMusicInMusicList extends Fragment {
    private List<String> allMusicPath;
    private List<Map<String, Object>> list;
    private ListView listView;
    private View view;
    private SearchHandler sh;
    private MusicList musicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_music_in_music_list, container, false);
        init();
        setListView();
        initHandler();
        startSearchMusic();
        return view;
    }

    public void init() {
        listView = (ListView) view.findViewById(R.id.list_view_all_music);
        allMusicPath = new ArrayList<>();
        list = new ArrayList<>();
        musicList = ((MusicList) getActivity());

    }


    public void setListView() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.list_view_item_music, new String[]{"id", "name"}, new int[]{R.id.all_music_id, R.id.text_view_list_item_music});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = allMusicPath.get(position);
                musicList.setMusicPath(path);
                musicList.startMusic();
            }
        });
    }

    public void initHandler() {
        sh = new SearchHandler(this);
        Message msg = new Message();
        sh.handleMessage(msg);
    }

    public void startSearchMusic() {
        Thread search = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                GetAllMusic s = new GetAllMusic();
                List<String> x = s.searchMusic("/mnt/sdcard");
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("allMusicPath", (ArrayList<String>) x);
                msg.setData(bundle);
                msg.what = 0x123;
                sh.sendMessage(msg);
            }
        };
        search.start();
    }

    public void setList() {
        for (int i = 1; i <= allMusicPath.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("name", new File(allMusicPath.get(i - 1)).getName());
            list.add(map);
        }
    }

    public void setAllMusicPath(ArrayList<String> s) {
        allMusicPath = s;
        musicList.setAllMusicPath(s);
    }

    public static class SearchHandler extends Handler {
        WeakReference<AllMusicInMusicList> weakReference;

        public SearchHandler(AllMusicInMusicList s) {
            weakReference = new WeakReference<AllMusicInMusicList>(s);
        }

        @Override
        public void handleMessage(Message msg) {
            AllMusicInMusicList allMusicInMusicList = weakReference.get();
            if (msg.what == 0x123) {
                allMusicInMusicList.setAllMusicPath(msg.getData().getStringArrayList("allMusicPath"));
                allMusicInMusicList.setList();
                allMusicInMusicList.setListView();

            }
        }
    }
}