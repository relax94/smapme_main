package com.studio.a4kings.qr_code_app.Adapters.Map;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.studio.a4kings.qr_code_app.R;

/**
 * Created by Dmitry Pavlenko on 29.12.2015.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View _myInfoWindowView;
    private final Context _mContext;
    private final Activity _mActivity;

    public MyInfoWindowAdapter(Context context){
        this._mContext = context;
        this._mActivity = (Activity)this._mContext;
        this._myInfoWindowView = this._mActivity.getLayoutInflater().inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {


        ImageView icon = (ImageView)this._myInfoWindowView.findViewById(R.id.icon);

        TextView tvTitle = (TextView)this._myInfoWindowView.findViewById(R.id.title);
        TextView tvSnippet = (TextView)this._myInfoWindowView.findViewById(R.id.snippet);

        tvSnippet.setText(marker.getTitle());
        tvTitle.setText(marker.getSnippet());
        icon.setImageResource(R.drawable.majack_back);

        return _myInfoWindowView;
    }
}
