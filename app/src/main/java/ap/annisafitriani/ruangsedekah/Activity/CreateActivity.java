package ap.annisafitriani.ruangsedekah.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;


public class CreateActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ImageButton ibTime;
    private ImageButton ibLoc;
    private Button btnSubmit;
    private Button btnPickDate;
    private EditText etDateResult;
    private EditText etDesc;
    private EditText etNama;
    private TextView tvLocResult;
    private TextView tvTimeResult;


    DatabaseReference mDatabase;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        hideSoftKeyboard();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        etDateResult = (EditText) findViewById(R.id.et_DateResult);
        btnPickDate = (Button) findViewById(R.id.btn_pickdate);
        ibTime = (ImageButton) findViewById(R.id.ib_time);

        ibLoc = (ImageButton) findViewById(R.id.ib_loc);
        etNama = (EditText) findViewById(R.id.et_namaKegiatan);
        etDesc = (EditText) findViewById(R.id.et_desc);
        tvLocResult = (TextView) findViewById(R.id.tv_locResult);
        tvTimeResult = (TextView) findViewById(R.id.tv_timeResult);

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        ibTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Kegiatan");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createKegiatan();
            }
        });

        //TODO: coba pake placebuilder mbak
        ibLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // membuat Intent untuk Place Picker
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    //menjalankan place picker
                    startActivityForResult(builder.build(CreateActivity.this), PLACE_PICKER_REQUEST);

                    // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }

        });

    }





    private void createKegiatan() {
        //getting the values to save
        String date = etDateResult.getText().toString().trim();
        String nama = etNama.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String time = tvTimeResult.getText().toString().trim();
        String loc = tvLocResult.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(nama)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = mDatabase.push().getKey();

            //creating an Artist Object
            Kegiatan kegiatan = new Kegiatan(nama, date, time, desc, id, loc);

            //Saving the Artist
            mDatabase.child(id).setValue(kegiatan);

            //setting edittext to blank again
            etNama.setText("");

            //displaying a success toast
            Toast.makeText(this, "Event added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CreateActivity.this, HalamanUtama.class);
            startActivity(intent);
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }


    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newTime = Calendar.getInstance();
                tvTimeResult.setText("" + hourOfDay + ":" + minute);


            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                //text view bisa dimasukkan dari sini
                tvLocResult.setText(place.getName());
            }
        }
    }



    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
