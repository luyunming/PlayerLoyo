package cn.lym.playerloyo.util;

import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lym on 2015/6/30.
 */
public class BinderParcel implements Parcelable {

    public static final Parcelable.Creator<BinderParcel> CREATOR = new Creator<BinderParcel>() {
        @Override
        public BinderParcel createFromParcel(Parcel source) {
            return new BinderParcel(source);
        }

        @Override
        public BinderParcel[] newArray(int size) {
            return new BinderParcel[size];
        }
    };
    private Binder binder;

    public BinderParcel(Binder binder) {
        this.binder = binder;
    }

    public BinderParcel(Parcel parcel) {
        binder = (Binder) parcel.readValue(Binder.class.getClassLoader());
    }

    public Binder getBinder() {
        return this.binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.binder);
    }
}
