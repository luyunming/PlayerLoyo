package cn.lym.playerloyo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import cn.lym.playerloyo.service.MusicPlay.MusicBinder;

/**
 * Created by lym on 2015/6/30.
 */
public class MyBinderParcel implements Parcelable {

    public static final Parcelable.Creator<MyBinderParcel> CREATOR = new Creator<MyBinderParcel>() {
        @Override
        public MyBinderParcel createFromParcel(Parcel source) {
            return new MyBinderParcel(source);
        }

        @Override
        public MyBinderParcel[] newArray(int size) {
            return new MyBinderParcel[size];
        }
    };
    MusicBinder myBinder;

    public MyBinderParcel(MusicBinder binder) {
        myBinder = binder;
    }

    public MyBinderParcel(Parcel parcel) {
        myBinder = (MusicBinder) parcel.readValue(MusicBinder.class.getClassLoader());
    }

    public MusicBinder getMyBinder() {
        return myBinder;
    }

    public void setMyBinder(MusicBinder binder) {
        myBinder = binder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(myBinder);
    }
}
