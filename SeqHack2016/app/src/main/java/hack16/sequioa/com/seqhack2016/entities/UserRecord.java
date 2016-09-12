package hack16.sequioa.com.seqhack2016.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yashodhan on 11/09/16.
 */
public class UserRecord {

    @SerializedName("uuid")
    String uuid;
    @SerializedName("doctorid")
    int doctorId;
    @SerializedName("symptom")
    String sympton;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("description")
    String description;


    @SerializedName("prescription")
    String prescription;
    @SerializedName("state")
    int state = 0;
    @SerializedName("cost")
    int cost;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSympton() {
        return sympton;
    }

    public void setSympton(String sympton) {
        this.sympton = sympton;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
