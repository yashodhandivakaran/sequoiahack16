package hack16.sequioa.com.seqhack2016;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import hack16.sequioa.com.seqhack2016.apis.APIConstant;
import hack16.sequioa.com.seqhack2016.apis.Patient;
import hack16.sequioa.com.seqhack2016.entities.User;
import hack16.sequioa.com.seqhack2016.entities.UserRecord;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientRecordEntryActivity extends AppCompatActivity {

    private User user;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;

    private ImageView mThumbnail;

    private TextView uid;
    private TextView name;
    private TextView gender;
    private TextView dob;

    private EditText phone;
    private EditText sympton;
    private EditText description;
    private EditText price;


    private View submitButton;
    private View priscriptionButton;


    private CircularProgressDrawable mCircularProgressDrawable;
    private ProgressBar loader;

    private View staticDataContainer;
    private View inputDataContainer;

    int atStep = 0;
    private Retrofit retrofit;
    private Patient service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_entry);

        uid = (TextView) findViewById(R.id.uid);
        name = (TextView) findViewById(R.id.name);
        gender = (TextView) findViewById(R.id.gender);
        dob = (TextView) findViewById(R.id.dob);

        phone = (EditText) findViewById(R.id.phone);
        description = (EditText) findViewById(R.id.description);
        price = (EditText) findViewById(R.id.price);
        mThumbnail = (ImageView) findViewById(R.id.thumbnail);

        submitButton = findViewById(R.id.submit);

        loader = (ProgressBar) findViewById(R.id.loader);
        staticDataContainer = findViewById(R.id.static_data);
        inputDataContainer = findViewById(R.id.input_container);

        sympton = (EditText) findViewById(R.id.sympton);

        priscriptionButton = findViewById(R.id.upload_prescription);
        priscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        if (mCircularProgressDrawable == null) {
            final float scale = getResources().getDisplayMetrics().density;
            mCircularProgressDrawable = new CircularProgressDrawable(getResources().getColor(R.color.cm_dark_grey), (4 * scale + 0.5f));
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoader();
                if (atStep == 0) {
                    validateCall();
                } else {
                    addRecord();
                }
            }
        });

        if (getIntent().getExtras() != null) {
            String adhaarData = getIntent().getExtras().getString("user_data");

            XmlPullParser xpp = null;
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                xpp = factory.newPullParser();
                xpp.setInput(new StringReader(adhaarData));
                parseContent(xpp);
                setUpView();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private void addRecord() {
        UserRecord userRecord = new UserRecord();
        userRecord.setUuid(user.getUid());
        userRecord.setDoctorId(1000);
        userRecord.setSympton(sympton.getText().toString());
        userRecord.setDescription(description.getText().toString());
        userRecord.setCost(Integer.valueOf(price.getText().toString().trim()));
        userRecord.setPrescription("sample_url");

        //Upload image

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIConstant.BASE_ENDPOINT + Patient.BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        if (service == null) {
            service = retrofit.create(Patient.class);
        }

        Call<ResponseBody> call = service.addRecord(userRecord);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Record add
                if (response.isSuccessful()) {
                    Toast.makeText(PatientRecordEntryActivity.this, "Record added successfully", Toast.LENGTH_LONG).show();
                    PatientRecordEntryActivity.this.finish();
                    Log.d("API", "Record added");
                } else {
                    Toast.makeText(PatientRecordEntryActivity.this, "Oops something went wrong. Record was not added", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(PatientRecordEntryActivity.this, "Oops something went wrong. Record was not added", Toast.LENGTH_LONG).show();
                Log.d("API", "Record failed");
            }
        });


    }

    private void validateCall() {
        user.setPhone(phone.getText().toString().trim());

        //TODO: make api call
        retrofit = new Retrofit.Builder()
                .baseUrl(APIConstant.BASE_ENDPOINT + Patient.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Patient.class);
        Call<ResponseBody> call = service.validateUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("API", "USER data added :" + response.body());
                atStep = 1;
                stopLoader();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //TODO:Show Toast that invalid data
                Log.d("API", "Error occured :" + t.getMessage());
                Toast.makeText(PatientRecordEntryActivity.this, "Oops something went wrong", Toast.LENGTH_LONG).show();
                stopLoader();
            }
        });


    }

    private void setUpView() {
        if (user != null) {
            uid.setText(user.getUid());
            name.setText(user.getUserName());
            gender.setText(user.getGender().equals("M") ? "Male" : "Female");
            dob.setText(user.getDob());
        }

    }

    private void parseContent(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int eventType;
        String attribute;
        String value;
        user = new User();
        while ((eventType = parser.next()) != XmlPullParser.END_TAG) {
            if (eventType == XmlPullParser.START_TAG) {
                int acount = parser.getAttributeCount();
                if (acount != -1) {
                    for (int x = 0; x < acount; x++) {
                        attribute = parser.getAttributeName(x);
                        value = parser.getAttributeValue(x);
                        user.setData(attribute, value);
//                        Log.d("Xml_parse",attribute+" = "+value);

                    }
                }
            }
        }

    }

    private void startLoader() {
        loader.setVisibility(View.VISIBLE);
        staticDataContainer.setVisibility(View.GONE);
        inputDataContainer.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
    }

    private void stopLoader() {
        loader.setVisibility(View.GONE);

        if (atStep == 1) {
            phone.setEnabled(false);
            sympton.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            priscriptionButton.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
            ((TextView) submitButton.findViewById(R.id.submit_button_text)).setText("Submit");
        }
        staticDataContainer.setVisibility(View.VISIBLE);
        inputDataContainer.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.VISIBLE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

            Glide.with(this)
                    .load(mCurrentPhotoPath)
                    .into(mThumbnail);
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras == null) {
                    Log.d("Photo","Data was null");
                    return;
                }
                mThumbnail.setVisibility(View.VISIBLE);
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    mThumbnail.setImageBitmap(imageBitmap);
                }
            }
        }
    }

}
