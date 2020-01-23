
package com.project.absensi.model.Absen;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("data_absen")
    @Expose
    private List<DataAbsen> dataAbsen = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2226228073851463686L;

    protected Data(Parcel in) {
        in.readList(this.dataAbsen, (com.project.absensi.model.Absen.DataAbsen.class.getClassLoader()));
    }

    public Data() {
    }

    public List<DataAbsen> getDataAbsen() {
        return dataAbsen;
    }

    public void setDataAbsen(List<DataAbsen> dataAbsen) {
        this.dataAbsen = dataAbsen;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(dataAbsen);
    }

    public int describeContents() {
        return  0;
    }

}
