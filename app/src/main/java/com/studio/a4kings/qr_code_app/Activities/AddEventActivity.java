package com.studio.a4kings.qr_code_app.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Managers.Api.Services.EventsService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventRequestModel;
import com.studio.a4kings.qr_code_app.Models.Response.CreateEventResponse;
import com.studio.a4kings.qr_code_app.Models.Response.TagsResponse;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.studio.a4kings.qr_code_app.Utils.Helper.checkValid;

public class AddEventActivity extends AppCompatActivity implements Callback<TagsResponse>, AdapterView.OnItemSelectedListener {

    @Bind(R.id.startDate)
    EditText startDate;
    @Bind(R.id.endDate)
    EditText endDate;
    @Bind(R.id.startTime)
    EditText startTime;
    @Bind(R.id.endTime)
    EditText endTime;
    @Bind(R.id.description)
    EditText description;
    @Bind(R.id.title)
    EditText title;
    @Bind(R.id.userCount)
    EditText userCount;
    @Bind(R.id.priceSpinner)
    Spinner spinner;
    @Bind(R.id.tagsSpinner)
    Spinner tagSpinner;
    @Bind(R.id.priceView)
    LinearLayout priceView;
    @Bind(R.id.priceFrom)
    EditText priceFrom;
    @Bind(R.id.priceTo)
    EditText priceTo;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.eventsService = new EventsService(Constants.SITE_URL);
        this.eventsService.setOperationToken(PrefsManager.getInstance(this).getFromCore(PrefsParam.TOKEN));
        this.eventsService.getTags(this);
        setUpForm();
        Calendar newCalendar = Calendar.getInstance();
        setUpDate(newCalendar);
        setUpTime(newCalendar);
    }

    TimePickerDialog timePickerDialog;

    @OnClick(R.id.startDate)
    public void onStartDateClick(View v) {
        isStart = true;
        datePickerDialog.show();
    }

    @OnClick(R.id.endDate)
    public void onEndDateClick(View v) {
        isStart = false;
        datePickerDialog.show();
    }

    @OnClick(R.id.startTime)
    public void onStartTimeClick(View v) {
        isStart = true;
        timePickerDialog.show();
    }


    @OnClick(R.id.endTime)
    public void onEndTimeClick(View v) {
        isStart = false;
        timePickerDialog.show();
    }

    @OnClick(R.id.okBtn)
    public void Ok(final View v) {
        EventRequestModel eventModel = createModel();
        if (eventModel != null) {
            Toast.makeText(AddEventActivity.this, "Send", Toast.LENGTH_SHORT).show();
            this.eventsService.createEvent(eventModel, new Callback<CreateEventResponse>() {
                @Override
                public void onResponse(Call<CreateEventResponse> call, Response<CreateEventResponse> response) {
                    if (response.body() != null)
                        if (response.body().getCode() == 200)
                            startActivity(new Intent(v.getContext(), MainActivity.class));
                        else
                            Toast.makeText(v.getContext(), "Error on server", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<CreateEventResponse> call, Throwable t) {

                }

            });
        }
    }

    @OnClick(R.id.cancelBtn)
    public void Cancel(View v) {
        finish();
    }

    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    boolean isStart = true;
    EventsService eventsService;
    Context context;

    //

    public void setUpForm() {
        tagSpinner.setEnabled(false);
        startDate.setInputType(InputType.TYPE_NULL);
        endDate.setInputType(InputType.TYPE_NULL);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priceSpinnerArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void setUpDate(Calendar newCalendar) {
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (isStart)
                    startDate.setText(dateFormatter.format(newDate.getTime()));
                else
                    endDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setUpTime(Calendar newCalendar) {
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        StringBuilder time = new StringBuilder();
                        time.append(hourOfDay)
                                .append(":")
                                .append(minute > 9 ? minute : "0" + minute)
                                .append(":00 ")
                                .append(AM_PM);
                        if (isStart) startTime.setText(time.toString());
                        else endTime.setText(time.toString());
                    }
                }, newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);
    }


    public EventRequestModel createModel() {
        if (!checkValid(title)
                || !checkValid(userCount)
                || !checkValid(description)
                || !checkValid(startDate)
                || !checkValid(startTime)
                || !checkValid(endDate)
                || !checkValid(endTime)
                )
            return null;

        EventRequestModel eventModel = new EventRequestModel();
        eventModel.setTitle(title.getText().toString());
        if (priceFlag) {
            if (!checkValid(priceFrom) || !checkValid(priceTo))
                return null;
            eventModel.setPriceFrom(Double.parseDouble(priceFrom.getText().toString()));
            eventModel.setPriceTo(Double.parseDouble(priceTo.getText().toString()));
        } else {
            eventModel.setPriceFrom(0d);
            eventModel.setPriceTo(0d);
        }
        eventModel.setDescription(description.getText().toString());
        eventModel.setStartDate(String.format("%s %s", startDate.getText().toString(), startTime.getText().toString()));
        eventModel.setEndDate(String.format("%s %s", endDate.getText().toString(), endTime.getText().toString()));


        //tagSpinner.getPos
        int selectedItemPosition = tagSpinner.getSelectedItemPosition();
        eventModel.setTag(tags.get(selectedItemPosition));

        eventModel.setCountUsers(Integer.parseInt(userCount.getText().toString()));
        eventModel.setPrivateLevel(0);
        return eventModel;
    }


    Boolean priceFlag = false;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.priceSpinner) {
            switch (position) {
                case 0:
                    priceFlag = false;
                    priceView.setVisibility(View.GONE);
                    break;
                case 1:
                    priceFlag = true;
                    priceView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    List<String> tags = Arrays.asList("Музыка", "Спорт");

    @Override
    public void onResponse(Call<TagsResponse> call, Response<TagsResponse> response) {
        if (response.body() != null) {
            ArrayList<String> model = response.body().getModel();
            if (model != null && !model.isEmpty()) {
                tags = model;
            }
            ArrayAdapter<String> tagsAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, tags);
            tagsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tagSpinner.setAdapter(tagsAdapter);
            tagSpinner.setEnabled(true);
        }
    }

    @Override
    public void onFailure(Call<TagsResponse> call, Throwable t) {
    }
}
