package com.studio.a4kings.qr_code_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Models.EventModels.EventRequestModel;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventResponseModel;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.R;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUX on 22.05.2016.
 */
public class UpdateEventActivity extends AddEventActivity implements AdapterView.OnItemSelectedListener {

    @OnClick(R.id.okBtn)
    @Override
    public void Ok(final View v) {
        EventRequestModel eventModel = createModel();
        Toast.makeText(UpdateEventActivity.this, "Send", Toast.LENGTH_SHORT).show();
        this.eventsService.updateEvent(event.getId(), eventModel, new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.body() != null)
                    if (response.body().getCode() == 200) {
                        startActivity(new Intent(v.getContext(), MainActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(v.getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });
    }

    EventResponseModel event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = (EventResponseModel) this.getIntent().getSerializableExtra(context.getString(R.string.event));
        if (event != null)
            setUpForm(event);
    }

    public void setUpForm(EventResponseModel event) {
        String[] split = event.getStartDate().split(" ", 2);
        startDate.setText(split[0]);
        startTime.setText(split[1]);

        split = event.getEndDate().split(" ", 2);
        endDate.setText(split[0]);
        endTime.setText(split[1]);

        priceFrom.setText(event.getPriceFrom().toString());
        priceTo.setText(event.getPriceTo().toString());

        title.setText(event.getTitle());
        description.setText(event.getDescription());

      /*  category.setText(event.getTag() != null ? event.getTag() : "");
        category.setVisibility(View.VISIBLE);
        category.setEnabled(false);*/

        userCount.setText(event.getCountUsers().toString());
    }
}