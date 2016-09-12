package hack16.sequioa.com.seqhack2016.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yashodhan on 10/09/16.
 */
public class User {
    @SerializedName("name")
    private String userName;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("gender")
    private String gender;
    @SerializedName("dob")
    private String dob;
    @SerializedName("phone")
    private String phone;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUid(String uid) {
        this.uuid = uid;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getUid() {
        return uuid;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    private static final String UID = "uid";
    private  static final String NAME = "name";
    private static final String GENDER = "gender";
    private static final String DOB = "dob";

    public void setData(String key, String value) {
        if (key.equals(UID)) {
            uuid = value;
        }

        if (key.equals(NAME)) {
            userName = value;
        }

        if (key.equals(GENDER)) {
            gender = value;
        }

        if (key.equals(DOB)) {
            dob = value;
        }
    }

}
