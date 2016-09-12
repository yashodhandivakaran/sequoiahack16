package hack16.sequioa.com.seqhack2016.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yashodhan on 11/09/16.
 */
public class Doctor {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("gender")
    String gender;

    @SerializedName("experienceYears")
    String experienceYears;

    @SerializedName("practoUrl")
    String practoUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(String experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getPractoUrl() {
        return practoUrl;
    }

    public void setPractoUrl(String practoUrl) {
        this.practoUrl = practoUrl;
    }
}
