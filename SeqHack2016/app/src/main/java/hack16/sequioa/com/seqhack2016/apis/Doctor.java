package hack16.sequioa.com.seqhack2016.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Yashodhan on 11/09/16.
 */
public interface Doctor {
    String Base = "doctor/";

    @Headers("Content-Type: application/json")
    @GET("find/{id}")
    Call<hack16.sequioa.com.seqhack2016.entities.Doctor> getDoctor(@Path("id") int doctoId);

}
