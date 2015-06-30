package cn.lym.playerloyo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lym.playerloyo.R;

/**
 * Created by lym on 2015/6/29.
 */
public class AccordingSingerInMusicList extends Fragment {
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View musicInfo = inflater.inflate(R.layout.fragment_according_singer_in_music_list, container, false);
        return musicInfo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
