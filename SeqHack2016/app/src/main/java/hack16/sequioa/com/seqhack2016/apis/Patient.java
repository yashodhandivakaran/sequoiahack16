package hack16.sequioa.com.seqhack2016.apis;

import java.util.List;

import hack16.sequioa.com.seqhack2016.entities.User;
import hack16.sequioa.com.seqhack2016.entities.UserRecord;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Yashodhan on 11/09/16.
 */

public interface Patient {
    String BASE ="patient/";

    @Headers("Content-Type: application/json")
    @POST("validate")
    Call<ResponseBody> validateUser(@Body User body);

    @Headers("Content-Type: application/json")
    @POST("record/add")
    Call<ResponseBody> addRecord(@Body UserRecord body);

    @Headers("Content-Type: application/json")
    @GET("record/find/doctorid/{id}")
    Call<List<hack16.sequioa.com.seqhack2016.entities.Patient>> getPatients(@Path("id")int doctorId);

}
