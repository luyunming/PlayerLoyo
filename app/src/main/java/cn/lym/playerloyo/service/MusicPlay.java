package cn.lym.playerloyo.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicPlay extends Service {
    String musicPath;
    private MediaPlayer musicPlayer = new MediaPlayer();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    public void initMusicPlay(String path) {
        musicPath = path;
        if (musicPath == null) {
        } else {
            try {
                musicPlayer.reset();
                System.out.println(musicPath);
                musicPlayer.setDataSource(musicPath);
                musicPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class MusicBinder extends Binder {
        public MusicBinder() {
            super();
        }

        public void initMusic(String path) {
            MusicPlay.this.initMusicPlay(path);
        }

        public void pauseMusic() {
            musicPlayer.pause();
        }

        public void startMusic() {
            musicPlayer.start();
        }

        public boolean isPlaying() {
            return musicPlayer.isPlaying();
        }
    }
}
