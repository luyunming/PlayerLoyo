package cn.lym.playerloyo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lym.playerloyo.R;

/**
 * Created by lym on 2015/6/25.
 */
public class MusicImageInCurrentPlay extends Fragment {
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View musicInfo = inflater.inflate(R.layout.fragment_music_image_in_current_play, container, false);
        return musicInfo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
