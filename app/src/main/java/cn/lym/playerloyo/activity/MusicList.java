package cn.lym.playerloyo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.adapter.MusicListFragment;
import cn.lym.playerloyo.fragment.AccordingFolderInMusicList;
import cn.lym.playerloyo.fragment.AccordingSingerInMusicList;
import cn.lym.playerloyo.fragment.AllMusicInMusicList;
import cn.lym.playerloyo.parcelable.MyBinderParcel;
import cn.lym.playerloyo.service.MusicPlay;

public class MusicList extends FragmentActivity implements View.OnClickListener {

    private int offset = 0;// 动画图片偏移量
    private int currentPageIndex = 0;
    private int currentPageImageWidth;

    private String musicName;
    private String musicSinger;
    private String musicPath;
    private ViewPager musicPager;
    private ImageView returnCurrentPlay, searchInThere, currentPage, musicPicture, playOrPause, playingList;
    private TextView currentCatalogue, playingMusicName, playingMusicSinger;
    private ArrayList<Fragment> fragments;// 页面列表
    private Fragment allMusic, accordingSinger, accordingFolder;
    private MusicPlay.MusicBinder musicBinder;
    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        initTest();
        initImageView();
        initTextView();
        initViewPager();
        setAllOnClickListener();
        initPlayMusicArea();
        initPlayService();
    }

    public void initTest() {
        musicPath = "/mnt/sdcard/aaa1.mp3";
    }

    private void initImageView() {
        currentPage = (ImageView) findViewById(R.id.image_view_music_list_current_page);
        returnCurrentPlay = (ImageView) findViewById(R.id.image_view_music_list_return_current_play);
        searchInThere = (ImageView) findViewById(R.id.image_view_music_list_search_in_there);
        musicPicture = (ImageView) findViewById(R.id.image_view_music_list_music_picture);
        playOrPause = (ImageView) findViewById(R.id.image_view_music_list_play_or_pause);
        playingList = (ImageView) findViewById(R.id.image_view_music_list_playing_list);
        initCurrentPageLocate();
    }

    private void initTextView() {
        currentCatalogue = (TextView) findViewById(R.id.text_view_music_list_current_catalogue);
        playingMusicName = (TextView) findViewById(R.id.text_view_music_list_playing_music_name);
        playingMusicSinger = (TextView) findViewById(R.id.text_view_music_list_playing_music_singer);
        currentCatalogue.setText("本地音乐");
    }

    private void initViewPager() {
        musicPager = (ViewPager) findViewById(R.id.view_pager_music_list_1);
        fragments = new ArrayList<>();
        allMusic = new AllMusicInMusicList();
        accordingSinger = new AccordingSingerInMusicList();
        accordingFolder = new AccordingFolderInMusicList();
        fragments.add(allMusic);
        fragments.add(accordingSinger);
        fragments.add(accordingFolder);
        musicPager.setAdapter(new MusicListFragment(getSupportFragmentManager(), fragments));
        musicPager.setCurrentItem(0);
        musicPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int one = offset * 2 + currentPageImageWidth;// 页卡1 -> 页卡2 偏移量
            int two = one * 2;// 页卡1 -> 页卡3 偏移量

            public void onPageScrollStateChanged(int arg0) {


            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            public void onPageSelected(int arg0) {
                Animation animation = new TranslateAnimation(one * currentPageIndex, one * arg0, 0, 0);
                currentPageIndex = arg0;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                currentPage.startAnimation(animation);
                Toast.makeText(MusicList.this, "您选择了" + musicPager.getCurrentItem() + "页卡", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAllOnClickListener() {
        returnCurrentPlay.setOnClickListener(this);
        searchInThere.setOnClickListener(this);
        playOrPause.setOnClickListener(this);
        playingList.setOnClickListener(this);
    }

    private void initPlayMusicArea() {
        if (musicName == null) {
            playingMusicName.setText("无");
            playingMusicSinger.setText("无");
            playOrPause.setImageResource(R.drawable.img_play_1);
        } else {
            playingMusicName.setText(musicName);
            playingMusicSinger.setText(musicSinger);
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

    public void initCurrentPageLocate() {
        currentPageImageWidth = BitmapFactory.decodeResource(getResources(), R.drawable.img_page_indicator).getWidth();// 获取图片宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics); //获取屏幕分辨率信息
        int screenW = displayMetrics.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - currentPageImageWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        currentPage.setImageMatrix(matrix);// 设置动画初始位置
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_music_list_return_current_play:
                sendSelectMusicToCurrentPlay();
                break;
            case R.id.image_view_music_list_play_or_pause:
                changePlayState();
                break;
            case R.id.image_view_music_list_search_in_there:
                startMusic();
                break;
            case R.id.image_view_music_list_menu_more:
                exitThis();
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
        musicName = path.getName();
        musicBinder.initMusic(musicPath);
        musicBinder.startMusic();
        initPlayMusicArea();
    }

    public void sendSelectMusicToCurrentPlay() {
        Intent intent = new Intent(MusicList.this, CurrentPlay.class);
        Bundle bundle = new Bundle();
        bundle.putString("musicPath", musicPath);
        bundle.putString("musicName", musicName);
        bundle.putParcelable("musicBinderParcel", new MyBinderParcel(musicBinder));
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

    public void exitThis() {
        System.exit(0);
    }
}
