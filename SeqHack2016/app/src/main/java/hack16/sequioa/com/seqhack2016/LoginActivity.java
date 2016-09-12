package hack16.sequioa.com.seqhack2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import hack16.sequioa.com.seqhack2016.apis.APIConstant;
import hack16.sequioa.com.seqhack2016.apis.Doctor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private View login;
    private EditText doctorIdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.submit);
        doctorIdText = (EditText) findViewById(R.id.doctor_id);

        UserPreferences preferences = UserPreferences.getInstance(this);
        final int doctorId = preferences.getDoctorId();
        if(doctorId != -1){
            goToHomeScreen();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIConstant.BASE_ENDPOINT+ Doctor.Base)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Doctor service = retrofit.create(Doctor.class);
                Call<hack16.sequioa.com.seqhack2016.entities.Doctor> call = service.getDoctor(Integer.valueOf(doctorIdText.getText().toString()));
                call.enqueue(new Callback<hack16.sequioa.com.seqhack2016.entities.Doctor>() {
                    @Override
                    public void onResponse(Call<hack16.sequioa.com.seqhack2016.entities.Doctor> call, Response<hack16.sequioa.com.seqhack2016.entities.Doctor> response) {
                        if(!response.isSuccessful()){
                            Log.d("LOGIN","Wrong");
                            Toast.makeText(LoginActivity.this,"Oops something went wrong",Toast.LENGTH_LONG).show();
                        }
                        UserPreferences.getInstance(LoginActivity.this).setDoctorId(Integer.valueOf(doctorIdText.getText().toString()));
                        goToHomeScreen();

                        Log.d("LOGIN","Success");
                    }

                    @Override
                    public void onFailure(Call<hack16.sequioa.com.seqhack2016.entities.Doctor> call, Throwable t) {
                        Log.d("LOGIN","Failed");

                        Toast.makeText(LoginActivity.this,"Invalid doctor Id",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    private void goToHomeScreen(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

