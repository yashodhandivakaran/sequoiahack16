package hack16.sequioa.com.seqhack2016.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yashodhan on 11/09/16.
 */
public class Patient {
    @SerializedName("id")
    private int id;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("doctorid")
    private int doctorId;
    @SerializedName("sympton")
    private String symptom;
    @SerializedName("description")
    private String description;
    @SerializedName("prescription")
    private String prescription;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @SerializedName("patientName")
    private String patientName;


    @SerializedName("state")
    int state;
    @SerializedName("cost")
    double cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
