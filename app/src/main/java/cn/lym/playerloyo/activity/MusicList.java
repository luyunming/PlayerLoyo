package cn.lym.playerloyo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.fragment.DetailMusic;
import cn.lym.playerloyo.fragment.GeneralMusic;
import cn.lym.playerloyo.service.MusicPlay;
import cn.lym.playerloyo.util.BinderParcel;
import cn.lym.playerloyo.util.MP3Info;

public class MusicList extends FragmentActivity implements View.OnClickListener {

    private String musicName;
    private String musicSinger;
    private String musicPath;
    private ImageView musicPicture, playOrPause, playingList;
    private TextView playingMusicName, playingMusicSinger;
    private MusicPlay.MusicBinder musicBinder;
    private ServiceConnection conn;
    private FragmentManager fm;
    private GeneralMusic generalMusic;
    private DetailMusic detailMusic;
    private MP3Info mp3Info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        initTest();
        initImageView();
        initTextView();
        setAllOnClickListener();
        initPlayMusicArea();
        initPlayService();
        initFragment();
    }

    public void initTest() {
        musicPath = "/mnt/sdcard/aaa1.mp3";
    }

    private void initImageView() {
        musicPicture = (ImageView) findViewById(R.id.image_view_music_list_music_picture);
        playOrPause = (ImageView) findViewById(R.id.image_view_music_list_play_or_pause);
        playingList = (ImageView) findViewById(R.id.image_view_music_list_playing_list);
    }

    private void initTextView() {
        playingMusicName = (TextView) findViewById(R.id.text_view_music_list_playing_music_name);
        playingMusicSinger = (TextView) findViewById(R.id.text_view_music_list_playing_music_singer);
    }

    public void setAllOnClickListener() {
        playOrPause.setOnClickListener(this);
        playingList.setOnClickListener(this);
        musicPicture.setOnClickListener(this);
    }

    private void initPlayMusicArea() {
        if (musicName == null) {
            playingMusicName.setText("无");
            playingMusicSinger.setText("无");
            playOrPause.setImageResource(R.drawable.img_play_1);
        } else {
            playingMusicName.setText(musicName);
            if (musicSinger == null) {
                playingMusicSinger.setText("无");
            } else {
                playingMusicSinger.setText(musicSinger);
            }
            if (musicBinder.isPlaying()) {
                playOrPause.setImageResource(R.drawable.img_pause_1);
            } else {
                playOrPause.setImageResource(R.drawable.img_play_1);
            }
        }
    }

    public void initPlayService() {
        Intent intent = new Intent(this, MusicPlay.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicBinder = (MusicPlay.MusicBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public void initFragment() {
        fm = getSupportFragmentManager();
        generalMusic = new GeneralMusic();
        detailMusic = new DetailMusic();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.linear_layout_music_list_1, generalMusic);
        ft.addToBackStack("generalMusic");
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_music_list_music_picture:
                sendSelectMusicToCurrentPlay();
                break;
            case R.id.image_view_music_list_play_or_pause:
                changePlayState();
                break;
            case R.id.image_view_music_list_playing_list:
                startMusic();
                break;
            default:
                break;
        }
    }

    private void changePlayState() {
        if (musicName == null) {
            initPlayMusicArea();
        } else if (musicBinder.isPlaying()) {
            musicBinder.pauseMusic();
            playOrPause.setImageResource(R.drawable.img_play_1);
        } else {
            musicBinder.startMusic();
            playOrPause.setImageResource(R.drawable.img_pause_1);
        }
    }

    public void startMusic() {
        File path = new File(musicPath);
        if (path.exists()) {
            musicName = path.getName();
            mp3Info = new MP3Info(musicPath);
            musicSinger = mp3Info.getSinger();
            musicBinder.initMusic(musicPath);
            musicBinder.startMusic();
            initPlayMusicArea();
        }
    }

    public void sendSelectMusicToCurrentPlay() {
        Intent intent = new Intent(MusicList.this, CurrentPlay.class);
        Bundle bundle = new Bundle();
        bundle.putString("musicPath", musicPath);
        bundle.putString("musicName", musicName);
        bundle.putString("musicSinger", musicSinger);
        bundle.putParcelable("musicBinderParcel", new BinderParcel(musicBinder));
        intent.putExtras(bundle);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 200) {
            musicPath = data.getStringExtra("musicPath");
            initPlayMusicArea();
        }
    }

    public void setMusicPath(String path) {
        this.musicPath = path;
    }

    public void exitThis() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
