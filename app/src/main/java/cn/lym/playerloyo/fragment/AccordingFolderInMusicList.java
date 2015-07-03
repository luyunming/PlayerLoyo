package cn.lym.playerloyo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.activity.MusicList;

public class AccordingFolderInMusicList extends Fragment {

    private List<String> allMusicPath;
    private List<Map<String, Object>> list;
    private Map<String, String> thePath;
    private ListView listView;
    private View view;
    private MusicList musicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_according_folder_in_music_list, container, false);
        init();
        setList();
        setListView();
        return view;
    }

    public void init() {
        listView = (ListView) view.findViewById(R.id.list_view_according_folder);
        list = new ArrayList<>();
        musicList = ((MusicList) getActivity());
        allMusicPath = musicList.getAllMusicPath();
    }

    public void setListView() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.list_view_item_according, new String[]{"pathName", "count", "path"}, new int[]{R.id.text_view_according_1, R.id.text_view_according_2, R.id.text_view_according_3});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    public void setList() {
        List<String> thesePath = new ArrayList<>();
        List<String> thesePathName = new ArrayList<>();
        Map<String, Integer> theseCount = new TreeMap<>();
        for (String s : allMusicPath) {
            File f = new File(s);
            s = s.replace(f.getName(), "");
            File g = new File(s);
            if (thesePath.contains(s)) {
                int i = theseCount.get(g.getName());
                i++;
                theseCount.remove(g.getName());
                theseCount.put(g.getName(), i);
            } else {
                thesePath.add(s);
                thesePathName.add(g.getName());
                theseCount.put(g.getName(), 1);
            }
        }
        for (int i = 0; i < thesePath.size(); i++) {
            Map<String, Object> map = new TreeMap<>();
            map.put("pathName", thesePathName.get(i));
            map.put("path", thesePath.get(i));
            map.put("count", theseCount.get(thesePathName.get(i)));
            list.add(map);
        }
    }
}
