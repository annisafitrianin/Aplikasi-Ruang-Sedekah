package ap.annisafitriani.ruangsedekah.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ap.annisafitriani.ruangsedekah.Fragment.Maps;
import ap.annisafitriani.ruangsedekah.R;


public class CreateActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ImageButton ibTime;
    private ImageButton ibLoc;
    private ImageButton ibDesc;
    private Button btnSubmit;
    private Button btnPickDate;
    private EditText etResult;
    private EditText etDesc;
    private EditText etNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        etResult = (EditText) findViewById(R.id.et_result);
        btnPickDate = (Button) findViewById(R.id.btn_pickdate);
        ibTime = (ImageButton) findViewById(R.id.ib_time);
        ibDesc = (ImageButton) findViewById(R.id.ib_desc);
        ibLoc = (ImageButton) findViewById(R.id.ib_loc);
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        ibTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });


        //TODO: coba pake placebuilder mbak
        ibLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Maps.class);
                startActivity(intent);
            }
        });
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }
}
