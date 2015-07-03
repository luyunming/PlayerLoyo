package cn.lym.playerloyo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetAllMusic {
    List<String> list;

    public GetAllMusic() {
        list = new ArrayList<>();
    }

    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    public String[] searchMusic(String path) {
        File file = new File(path);
        File files[] = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    searchMusic(f.toString());
                } else if (f.getName().toLowerCase().endsWith(".mp3")) {
                    list.add(f.toString());
                }
            }
        }
        String[] x = (String[]) list.toArray();
        return x;
    }
}
