package sa.bitoasis.bitoasis.user;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;

import lombok.Data;

/**
 * Created by Mohamed.Shaaban on 3/5/2018.
 */
@Data
@Entity(tableName = "User")
public class User implements IUser, Parcelable {

    @SerializedName("user_id")
    @PrimaryKey
    public Long userId;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "mobile")
    public String mobile;
    @ColumnInfo(name = "push_token")
    public String pushToken;
    @ColumnInfo(name = "device_type")
    public String deviceType;
    @ColumnInfo(name = "country")
    public String country;
    @ColumnInfo(name = "hashed_id")
    public String hashedId;
    @ColumnInfo(name = "token")
    public String token;
    @ColumnInfo(name = "country_code")
    public String countryCode;
    @ColumnInfo(name = "profile_img")
    public String profileImg;
    @ColumnInfo(name = "is_mentor")
    public boolean isMentor;

    @Inject
    public User() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.userId);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.mobile);
        dest.writeString(this.pushToken);
        dest.writeString(this.deviceType);
        dest.writeString(this.country);
        dest.writeString(this.hashedId);
        dest.writeString(this.token);
        dest.writeString(this.countryCode);
        dest.writeString(this.profileImg);
        dest.writeByte(this.isMentor ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.email = in.readString();
        this.password = in.readString();
        this.mobile = in.readString();
        this.pushToken = in.readString();
        this.deviceType = in.readString();
        this.country = in.readString();
        this.hashedId = in.readString();
        this.token = in.readString();
        this.countryCode = in.readString();
        this.profileImg = in.readString();
        this.isMentor = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
