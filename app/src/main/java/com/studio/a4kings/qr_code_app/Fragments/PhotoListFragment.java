package com.studio.a4kings.qr_code_app.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Adapters.Profile.PhotoListAdapter;
import com.studio.a4kings.qr_code_app.Callbacks.ResponseCallback;
import com.studio.a4kings.qr_code_app.Callbacks.ResponseMethods;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.EventsService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.ImageEventModel;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.util.ArrayList;

import retrofit2.Response;

public class PhotoListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PhotoListAdapter mAdapter;
    ArrayList<Bitmap> addedPhotos = new ArrayList<>();
    private int remotePhotoListSize;
    EventsService eventsService;
    Integer eventId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ArrayList<ImageEventModel> modelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        eventsService = new EventsService(Constants.SITE_URL);
        eventsService.setOperationToken(PrefsManager.getInstance(getContext()).get(PrefsParam.TOKEN));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.photo_list);
        mRecyclerView.setLayoutManager(layoutManager);

        Bundle bundle = this.getArguments();
        modelList = (ArrayList<ImageEventModel>) bundle.getSerializable(getContext().getString(R.string.imageList));
        eventId = bundle.getInt("eventId");
        remotePhotoListSize = modelList.size();

        mAdapter = new PhotoListAdapter(this.getContext(), modelList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mAdapter.setOnLongItemClick(onLongItemClickListener);
        return view;
    }


    PhotoListAdapter.OnItemClickListener onItemClickListener = new PhotoListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("images", modelList);
            bundle.putInt("position", position);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
            newFragment.setArguments(bundle);
            newFragment.show(ft, "slideshow");
        }
    };


    PhotoListAdapter.OnItemClickListener onLongItemClickListener = new PhotoListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, final int position) {
            if(eventId != -1) {
                Toast.makeText(v.getContext(), "Delete " + position, Toast.LENGTH_SHORT).show();
                //TODO: remove images from serv
                if (remotePhotoListSize - position < addedPhotos.size()) {
                    addedPhotos.remove(remotePhotoListSize - position);
                    modelList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                } else {
                    eventsService.deleteImageFromEvent(eventId, modelList.get(position).getPhotoId(), new ResponseCallback<MainResponse>(new ResponseMethods() {
                        @Override
                        public void onSuccess(Response<? extends MainResponse> response) {
                            modelList.remove(position);
                            mAdapter.notifyItemRemoved(position);
                        }
                    }));
                }
            }
        }
    };

    public void addNewImage(String path, Bitmap bitmap) {
        addedPhotos.add(bitmap);
        ImageEventModel imageEventModel = new ImageEventModel();
        imageEventModel.setPhotoName(path);
        modelList.add(imageEventModel);
        mAdapter.notifyItemInserted(modelList.size() - 1);
    }

    public ArrayList<Bitmap> getAddedPhotos() {
        return addedPhotos;
    }
}
