package cn.lym.playerloyo.activity;

import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
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

import java.util.ArrayList;

import cn.lym.playerloyo.R;
import cn.lym.playerloyo.adapter.MusicListFragment;
import cn.lym.playerloyo.fragment.AccordingFolderInMusicList;
import cn.lym.playerloyo.fragment.AccordingSingerInMusicList;
import cn.lym.playerloyo.fragment.AllMusicInMusicList;
import cn.lym.playerloyo.parcelable.MyBinderParcel;
import cn.lym.playerloyo.service.MusicPlay;

/**
 * Created by lym on 2015/6/23.
 */
public class MusicList extends FragmentActivity implements View.OnClickListener {
    private String musicName;
    private String musicSinger;
    private ViewPager musicPager;
    private ImageView returnCurrentPlay, searchInThere, currentPage, musicPicture, playOrPause, playingList;
    private TextView currentCatalogue, playingMusicName, playingMusicSinger;
    private ArrayList<Fragment> fragments;// 页面列表
    private int offset = 0;// 动画图片偏移量
    private int currentPageIndex = 0;
    private int currentPageImageWidth;
    private Fragment allMusic, accordingSinger, accordingFolder;
    private MusicPlay.MusicBinder musicBinder;
    private Intent intent;
    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        initImageView();
        initCurrentPageLocate();
        initTextView();
        initViewPager();
        setAllOnClickListener();
        initIntent();
        initPlayState();
    }

    private void initPlayState() {
        playingMusicName.setText(musicName);
        playingMusicSinger.setText(musicSinger);
        if (musicName.equals("无")) {
            playOrPause.setImageResource(R.drawable.img_play_1);
        } else {
            if (musicBinder.isPlaying()) {
                playOrPause.setImageResource(R.drawable.img_pause);
            } else {
                playOrPause.setImageResource(R.drawable.img_play_1);
            }
        }
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
                Animation animation = new TranslateAnimation(one * currentPageIndex, one * arg0, 0, 0);//显然这个比较简洁，只有一行代码。
                currentPageIndex = arg0;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                currentPage.startAnimation(animation);
                Toast.makeText(MusicList.this, "您选择了" + musicPager.getCurrentItem() + "页卡", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTextView() {
        currentCatalogue = (TextView) findViewById(R.id.text_view_music_list_current_catalogue);
        playingMusicName = (TextView) findViewById(R.id.text_view_music_list_playing_music_name);
        playingMusicSinger = (TextView) findViewById(R.id.text_view_music_list_playing_music_singer);
    }

    private void initImageView() {
        currentPage = (ImageView) findViewById(R.id.image_view_music_list_current_page);
        returnCurrentPlay = (ImageView) findViewById(R.id.image_view_music_list_return_current_play);
        searchInThere = (ImageView) findViewById(R.id.image_view_music_list_search_in_there);
        musicPicture = (ImageView) findViewById(R.id.image_view_music_list_music_picture);
        playOrPause = (ImageView) findViewById(R.id.image_view_music_list_play_or_pause);
        playingList = (ImageView) findViewById(R.id.image_view_music_list_playing_list);
    }

    public void setAllOnClickListener() {
        returnCurrentPlay.setOnClickListener(this);
        searchInThere.setOnClickListener(this);
        playOrPause.setOnClickListener(this);
        playingList.setOnClickListener(this);
    }

    public void initCurrentPageLocate() {
        currentPageImageWidth = BitmapFactory.decodeResource(getResources(), R.drawable.img_page_indicator).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - currentPageImageWidth) / 2;// 计算偏移量
        System.out.println(offset);
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        currentPage.setImageMatrix(matrix);// 设置动画初始位置
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_music_list_return_current_play:
                MusicList.this.finish();
                break;
            case R.id.image_view_music_list_play_or_pause:
                changePlayState();
                break;
            case R.id.image_view_music_list_search_in_there:
                sendSelectMusicToCurrentPlay();
                break;
            default:
                break;
        }
    }

    private void changePlayState() {
        if (musicName.equals("无")) {
            initPlayState();
        } else if (musicBinder.isPlaying()) {
            musicBinder.pauseMusic();
            playOrPause.setImageResource(R.drawable.img_play_1);
        } else {
            musicBinder.startMusic();
            playOrPause.setImageResource(R.drawable.img_pause);
        }
    }

    public void initIntent() {
        intent = getIntent();
        musicName = intent.getStringExtra("musicName");
        musicSinger = intent.getStringExtra("musicSinger");
        MyBinderParcel myBinderParcel = intent.getParcelableExtra("musicBinder");
        musicBinder = myBinderParcel.getMyBinder();
    }

    public void sendSelectMusicToCurrentPlay() {
        Bundle bundle = new Bundle();
        bundle.putString("musicPath", "/mnt/sdcard/aaaaaa/aaa1.mp3");
        intent.putExtras(bundle);
        MusicList.this.setResult(200, intent);
        MusicList.this.finish();
    }

    public void exitThis() {
        System.exit(0);
    }
}
