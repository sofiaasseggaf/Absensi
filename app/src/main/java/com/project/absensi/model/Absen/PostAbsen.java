
package com.project.absensi.model.Absen;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostAbsen implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<PostAbsen> CREATOR = new Creator<PostAbsen>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PostAbsen createFromParcel(Parcel in) {
            return new PostAbsen(in);
        }

        public PostAbsen[] newArray(int size) {
            return (new PostAbsen[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2937341611151131688L;

    protected PostAbsen(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public PostAbsen() {
    }

    /**
     * 
     * @param message
     * @param status
     */
    public PostAbsen(Boolean status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
