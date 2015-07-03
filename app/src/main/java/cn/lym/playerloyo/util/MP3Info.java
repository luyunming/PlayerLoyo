package cn.lym.playerloyo.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

//    获得MP3文件的信息
public class MP3Info {

    private String charset = "gbk";//解析MP3信息时用的字符编码
    private boolean flag;
    private byte[] buf;//MP3的标签信息的byte数组

    //    构造方法，获取文件的最后128字节
    public MP3Info(String mp3Path) {
        File mp3 = new File(mp3Path);
        buf = new byte[128];//初始化标签信息的byte数组
        try {
            RandomAccessFile raf = new RandomAccessFile(mp3, "r");
            raf.seek(raf.length() - 128);//移动到文件倒数128字节
            raf.read(buf);//读取128字节
            raf.close();//关闭文件
        } catch (Exception e) {
            //todo: printStackTrace
            e.printStackTrace();
        }
        flag = "TAG".equalsIgnoreCase(new String(buf, 0, 3));
    }

    //    获得目前解析时用的字符编码
    public String getCharset() {
        return charset;
    }

    //    设置解析时用的字符编码
    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSongName() {
        if (flag) {
            try {
                return new String(buf, 3, 30, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 3, 30).trim();
            }
        } else {
            return null;
        }
    }

    public String getSinger() {
        if (flag) {
            try {
                return new String(buf, 33, 30, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 33, 30).trim();
            }
        } else {
            return null;
        }
    }

    //    获取专辑
    public String getAlbum() {
        if (flag) {
            try {
                return new String(buf, 63, 30, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 63, 30).trim();
            }
        } else {
            return null;
        }
    }

    public String getYear() {
        if (flag) {
            try {
                return new String(buf, 93, 4, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 93, 4).trim();
            }
        } else {
            return null;
        }
    }

    //    获取备注
    public String getRemark() {
        if (flag) {
            try {
                return new String(buf, 97, 28, charset).trim();
            } catch (UnsupportedEncodingException e) {
                return new String(buf, 97, 28).trim();
            }
        } else {
            return null;
        }
    }
}
