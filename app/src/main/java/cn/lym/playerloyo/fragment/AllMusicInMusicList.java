package cn.lym.playerloyo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lym.playerloyo.R;

/**
 * Created by lym on 2015/6/29.
 */
public class AllMusicInMusicList extends Fragment {
    private ListView listView;
    private String[] names = new String[]{"a.mp3", "b.mp3", "c.mp3", "d.mp3", "e.mp3", "f.mp3", "g.mp3", "h.mp3", "i.mp3", "j.mp3", "k.mp3", "l.mp3", "m.mp3", "n.mp3", "o.mp3", "p.mp3"};

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_music_in_music_list, container, false);
        listView = (ListView) view.findViewById(R.id.list_view_all_music);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 1; i <= names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("name", names[i - 1]);
            list.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.list_view_item_music, new String[]{"id", "name"}, new int[]{R.id.all_music_id, R.id.text_view_list_item_music});
        listView.setAdapter(simpleAdapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}