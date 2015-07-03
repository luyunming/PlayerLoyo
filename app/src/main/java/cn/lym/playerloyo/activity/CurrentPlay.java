package cn.lym.playerloyo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.adapter.CurrentPlayMusicFragment;
import cn.lym.playerloyo.constant.Constant;
import cn.lym.playerloyo.fragment.MusicImageInCurrentPlay;
import cn.lym.playerloyo.fragment.MusicInfoInCurrentPlay;
import cn.lym.playerloyo.fragment.MusicWordsInCurrentPlay;
import cn.lym.playerloyo.service.MusicPlay;
import cn.lym.playerloyo.util.BinderParcel;

public class CurrentPlay extends FragmentActivity implements View.OnClickListener {

    private int playModeIndex;

    private String musicPath;
    private String musicName;
    private String musicSinger;
    private ViewPager current_play_music;
    private ImageView toMusicList;
    private TextView showMusicName;
    private ImageView showMore;
    private ImageView viewPagerIndicator1, viewPagerIndicator2, viewPagerIndicator3;
    private ImageView playMode, playPrevious, playOrPause, playNext;
    private Fragment musicInfo, musicImage, musicWords;
    private ArrayList<Fragment> fragmentList;
    private MusicPlay.MusicBinder musicBinder;
    private Intent musicListInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_play);
        initImageView();
        initTextView();
        initViewPager();
        setTheseOnClickListener();
        getMusicIntent();
    }

    public void initImageView() {
        toMusicList = (ImageView) findViewById(R.id.image_view_current_play_to_music_list);
        showMore = (ImageView) findViewById(R.id.image_view_current_play_show_more);
        viewPagerIndicator1 = (ImageView) findViewById(R.id.image_view_view_page_indicator_1);
        viewPagerIndicator2 = (ImageView) findViewById(R.id.image_view_view_page_indicator_2);
        viewPagerIndicator3 = (ImageView) findViewById(R.id.image_view_view_page_indicator_3);
        playMode = (ImageView) findViewById(R.id.image_view_play_mode);
        playPrevious = (ImageView) findViewById(R.id.image_view_play_previous);
        playOrPause = (ImageView) findViewById(R.id.image_view_play_or_pause);
        playNext = (ImageView) findViewById(R.id.image_view_play_next);
    }

    public void initTextView() {
        showMusicName = (TextView) findViewById(R.id.text_view_current_play_show_music_name);
    }

    public void initViewPager() {
        current_play_music = (ViewPager) findViewById(R.id.view_pager_current_play);
        musicInfo = new MusicInfoInCurrentPlay();
        musicImage = new MusicImageInCurrentPlay();
        musicWords = new MusicWordsInCurrentPlay();
        fragmentList = new ArrayList<>();
        fragmentList.add(musicInfo);
        fragmentList.add(musicImage);
        fragmentList.add(musicWords);
        current_play_music.setAdapter(new CurrentPlayMusicFragment(getSupportFragmentManager(), fragmentList));
        current_play_music.setCurrentItem(1);
        viewPagerIndicator1.setImageResource(R.drawable.img_page_unfocused);
        viewPagerIndicator2.setImageResource(R.drawable.img_page_focused);
        viewPagerIndicator3.setImageResource(R.drawable.img_page_unfocused);
        current_play_music.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    viewPagerIndicator1.setImageResource(R.drawable.img_page_focused);
                    viewPagerIndicator2.setImageResource(R.drawable.img_page_unfocused);
                    viewPagerIndicator3.setImageResource(R.drawable.img_page_unfocused);
                } else if (position == 1) {
                    viewPagerIndicator1.setImageResource(R.drawable.img_page_unfocused);
                    viewPagerIndicator2.setImageResource(R.drawable.img_page_focused);
                    viewPagerIndicator3.setImageResource(R.drawable.img_page_unfocused);
                } else {
                    viewPagerIndicator1.setImageResource(R.drawable.img_page_unfocused);
                    viewPagerIndicator2.setImageResource(R.drawable.img_page_unfocused);
                    viewPagerIndicator3.setImageResource(R.drawable.img_page_focused);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setTheseOnClickListener() {
        playMode.setOnClickListener(this);
        toMusicList.setOnClickListener(this);
        showMore.setOnClickListener(this);
        playPrevious.setOnClickListener(this);
        playOrPause.setOnClickListener(this);
        playNext.setOnClickListener(this);

    }

    public void getMusicIntent() {
        musicListInfo = getIntent();
        musicPath = musicListInfo.getStringExtra("musicPath");
        musicName = musicListInfo.getStringExtra("musicName");
        musicSinger = musicListInfo.getStringExtra("musicSinger");
        BinderParcel musicBinderParcel = musicListInfo.getParcelableExtra("musicBinderParcel");
        musicBinder = (MusicPlay.MusicBinder) musicBinderParcel.getBinder();
        initPlayMusic();

    }

    public void initPlayMusic() {
        if (musicPath == null) {
            playOrPause.setImageResource(R.drawable.img_play);
        } else {
            showMusicName.setText(musicName);
            playOrPause.setImageResource(R.drawable.img_pause);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.image_view_play_mode:
                changePlayMode();
                break;
            case R.id.image_view_play_or_pause:
                changePlayState();
                break;
            case R.id.image_view_current_play_to_music_list:
                returnMusicList();
                break;
            case R.id.image_view_current_play_show_more:
                exitThis();
                break;
            default:
                break;
        }

    }

    public void changePlayMode() {
        playModeIndex++;
        if (playModeIndex > 3) {
            playModeIndex = 0;
        }
        switch (playModeIndex) {
            case Constant.REPEAT_ALL:
                playMode.setImageResource(R.drawable.img_play_mode_repeat_all);
                break;
            case Constant.REPEAT_ONE:
                playMode.setImageResource(R.drawable.img_play_mode_repeat_one);
                break;
            case Constant.SEQUENCE:
                playMode.setImageResource(R.drawable.img_play_mode_sequence);
                break;
            case Constant.SHUFFLE:
                playMode.setImageResource(R.drawable.img_play_mode_shuffle);
                break;
            default:
                break;
        }
    }

    public void changePlayState() {
        if (musicPath == null) {
            playOrPause.setImageResource(R.drawable.img_play);
        } else if (musicBinder.isPlaying()) {
            musicBinder.pauseMusic();
            playOrPause.setImageResource(R.drawable.img_play);
        } else {
            musicBinder.startMusic();
            playOrPause.setImageResource(R.drawable.img_pause);
        }
    }

    public void returnMusicList() {
        Bundle bundle = new Bundle();
        bundle.putString("musicPath", musicPath);
        musicListInfo.putExtras(bundle);
        CurrentPlay.this.setResult(200, musicListInfo);
        CurrentPlay.this.finish();
    }

    public void exitThis() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
