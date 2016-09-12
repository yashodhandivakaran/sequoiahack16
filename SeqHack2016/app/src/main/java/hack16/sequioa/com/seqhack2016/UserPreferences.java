package hack16.sequioa.com.seqhack2016;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yashodhan on 11/09/16.
 */
public class UserPreferences {

    public static final String CREDENTIAL_STORE = "credentials";
    private static SharedPreferences preferences = null;
    private Context mContext;

    private static UserPreferences userPreferencesStore = null;

    public static final String DOCTOR_ID = "doctor_id";


    private UserPreferences(Context context) {
        preferences = context.getSharedPreferences(CREDENTIAL_STORE, 0);
        mContext = context;
    }

    public static UserPreferences getInstance(Context context) {
        if (userPreferencesStore == null) {
            userPreferencesStore = new UserPreferences(context);
        }

        return userPreferencesStore;
    }

    public void setDoctorId(int id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(DOCTOR_ID, id);
        editor.commit();
    }

    public int getDoctorId(){
        return  preferences.getInt(DOCTOR_ID,-1);
    }

}
