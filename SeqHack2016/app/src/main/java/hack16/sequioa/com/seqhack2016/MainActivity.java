package hack16.sequioa.com.seqhack2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;
import java.util.List;

import hack16.sequioa.com.seqhack2016.adapters.PatientAdapter;
import hack16.sequioa.com.seqhack2016.apis.APIConstant;
import hack16.sequioa.com.seqhack2016.apis.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final int RC_BARCODE_CAPTURE = 9001;

    private RecyclerView patientList;

    private ArrayList<hack16.sequioa.com.seqhack2016.entities.Patient> patientArrayList = new ArrayList<>();
    private PatientAdapter patientAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        patientList = (RecyclerView)findViewById(R.id.patient_list);
        patientList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        patientAdapter = new PatientAdapter(patientArrayList,this);
        patientList.setAdapter(patientAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRCodeScannerActivity.class);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });

        getPatientList();
    }

    private void getPatientList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.BASE_ENDPOINT+ Patient.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Patient service = retrofit.create(Patient.class);
        Call<List<hack16.sequioa.com.seqhack2016.entities.Patient>> call =
                service.getPatients(UserPreferences.getInstance(this).getDoctorId());

        call.enqueue(new Callback<List<hack16.sequioa.com.seqhack2016.entities.Patient>>() {
            @Override
            public void onResponse(Call<List<hack16.sequioa.com.seqhack2016.entities.Patient>> call, Response<List<hack16.sequioa.com.seqhack2016.entities.Patient>> response) {
                List<hack16.sequioa.com.seqhack2016.entities.Patient> patients = response.body();
                if(patients == null ){
                    return;
                }
                patientArrayList.clear();
                patientArrayList.addAll(new ArrayList<hack16.sequioa.com.seqhack2016.entities.Patient>(patients));
                patientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<hack16.sequioa.com.seqhack2016.entities.Patient>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            UserPreferences.getInstance(this).setDoctorId(-1);
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                Log.d("Qrcode","Code received");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
