package com.studio.a4kings.qr_code_app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Activities.DetailEventActivity;
import com.studio.a4kings.qr_code_app.Adapters.Events.EventsAdapter;
import com.studio.a4kings.qr_code_app.Behaviours.EndlessRecyclerViewScrollListener;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.EventsService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Enums.UserEnum;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventResponseModel;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventsResponse;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsListFragment extends Fragment implements Callback<EventsResponse> {

    RecyclerView mRecyclerView;

    ArrayList<EventResponseModel> eventModels = new ArrayList<>();
    EventsService eventsService;
    Context context;
    Callback<EventsResponse> callback;
    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    UserEnum userEnum = null;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        context = this.getContext();

        callback = this;
        this.eventsService = new EventsService(Constants.SITE_URL);
        eventsService.setOperationToken(PrefsManager.getInstance(context).getFromCore(PrefsParam.TOKEN));

        eventsListAdapter = new EventsAdapter(context, eventModels);
        mRecyclerView.setAdapter(eventsListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager, load);
        mRecyclerView.setOnScrollListener(scrollListener);
        eventsListAdapter.setOnItemClickListener(onItemClickListener);

        Bundle bundle = this.getArguments();
        userEnum = (UserEnum) bundle.getSerializable(context.getString(R.string.sub_enum));

        if (userEnum != null)
            if (userEnum.equals(UserEnum.MY)) {
                eventsService.getMyEventsFetch(0, callback);
            } else {
                this.eventsService.getAllEventsFetch(0, callback);
            }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    EndlessRecyclerViewScrollListener.Load load = new EndlessRecyclerViewScrollListener.Load() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            if (userEnum != null)
                if (userEnum.equals(UserEnum.MY)) {
                    eventsService.getMyEventsFetch(page, callback);
                } else
                    eventsService.getAllEventsFetch(page, callback);

        }
    };
    EventsAdapter.OnItemClickListener onItemClickListener = new EventsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            // 1
            Intent transitionIntent = new Intent(context, DetailEventActivity.class);
            EventResponseModel event = eventModels.get(position);
            //transitionIntent.putExtra(context.getString(R.string.event), event);
            transitionIntent.putExtra("eventId", event.getId());
            ImageView placeImage = (ImageView) v.findViewById(R.id.eventImg);
            Drawable drawable = placeImage.getDrawable();
            if (drawable != null) {
                Bitmap bitmap = ((BitmapDrawable) placeImage.getDrawable()).getBitmap();
                if (bitmap != null)
                    transitionIntent.putExtra(context.getString(R.string.eventImg), bitmap);
            }
            startActivity(transitionIntent);
        }
    };

    EventsAdapter eventsListAdapter;

    @Override
    public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
        if (response.body() != null) {
            if (response.body().getCode() == 200) {
                if (response.body().getModel() != null) {
                    ArrayList<EventResponseModel> model = (ArrayList<EventResponseModel>) response.body().getModel();
                    if (model != null && !model.isEmpty()) {
                        if (scrollListener.getVisibleThreshold() > model.size())
                            scrollListener.setEnd(true);
                        eventModels.addAll(model);
                        // For efficiency purposes, notify the adapter of only the elements that got changed
                        // curSize will equal to the index of the first element inserted because the list is 0-indexed
                        int curSize = eventsListAdapter.getItemCount();
                        eventsListAdapter.notifyItemRangeInserted(curSize, eventModels.size() - 1);

                    } else scrollListener.setEnd(true);
                }
            } else {
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(context, "Error or server", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onFailure(Call<EventsResponse> call, Throwable t) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    SearchView searchView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(search);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
