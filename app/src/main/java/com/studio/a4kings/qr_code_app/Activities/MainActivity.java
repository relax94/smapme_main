package com.studio.a4kings.qr_code_app.Activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.studio.a4kings.qr_code_app.Fragments.ActivityFragment;
import com.studio.a4kings.qr_code_app.Fragments.CategoryFragment;
import com.studio.a4kings.qr_code_app.Fragments.EventsListFragment;
import com.studio.a4kings.qr_code_app.Fragments.SelfProfile;
import com.studio.a4kings.qr_code_app.Interfaces.IAuthentication;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.WallService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Managers.SignalManager;
import com.studio.a4kings.qr_code_app.Managers.Social.SocialConnectors;
import com.studio.a4kings.qr_code_app.Models.Callbacks.RFBroadcastResponse;
import com.studio.a4kings.qr_code_app.Models.Callbacks.RRFCallback;
import com.studio.a4kings.qr_code_app.Models.Enums.ImageSizes;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Enums.UserEnum;
import com.studio.a4kings.qr_code_app.Models.Events.CallbackResponseEvent;
import com.studio.a4kings.qr_code_app.Models.Events.SignalSentEvent;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.MemberProfile;
import com.studio.a4kings.qr_code_app.Models.Response.WallResponse;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Services.SignalService;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IAuthentication {
    DrawerLayout drawer;
    Member member;
    Context context;
   /* @Bind(R.id.imageView)
    ImageView imageView;*/
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;
        member = PrefsManager.getInstance(context).getMember();
        if(member != null)
        {
            MemberProfile profile = member.getProfile();
            View headerView = navigationView.getHeaderView(0);
            ImageView imageView = (ImageView)headerView.findViewById(R.id.imageView);
            TextView textView = (TextView)headerView.findViewById(R.id.signature);
            textView.setText(member.getProfile().getSignature());
            if (imageView != null) {
                ImageLoader.loadImage(context,
                        imageView,
                        Constants.PATH_TO_FILES + Constants.IMAGE_FOLDER + Constants.IMAGE_SIZES.get(ImageSizes.SMALL) + profile.getPhoto());
            }
        }

        setSupportActionBar(toolbar);
        String userId = PrefsManager.getInstance(context).get(PrefsParam.USER_ID);
        member = PrefsManager.getInstance(context).getMember();
        member.setId(userId);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new CategoryFragment()).commit();

        Log.d("Finger",SocialConnectors.getInstance(this).getFingerprints()[0]);


     //  SignalManager.getInstance(AppSettings.TRIP_BACKEND, userId);

        this.startSignalService(userId);
    }

    private void startSignalService(String connectionId){
        Intent signalIntent = new Intent(getApplicationContext(), SignalService.class);
        signalIntent.putExtra("connectionId", connectionId);
        startService(signalIntent);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_update_profile) {
            Intent intent = new Intent(this, FillProfileForm.class);
            intent.putExtra("isUpdate", true);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Intent intent = null;
        Bundle bundle = null;
        if (id == R.id.nav_mail) {
        } else if (id == R.id.nav_add_event) {
            startActivity(new Intent(this, AddEventActivity.class));
        } else if (id == R.id.nav_profile) {
            fragment = new SelfProfile();
        } else if (id == R.id.nav_category)
            fragment = new CategoryFragment();
        else if (id == R.id.nav_events_list) {
            fragment = new EventsListFragment();
            bundle = new Bundle();
            bundle.putSerializable(context.getString(R.string.sub_enum), UserEnum.MY);
        } else if (id == R.id.nav_foreign_events_list) {
            fragment = new EventsListFragment();
            bundle = new Bundle();
            bundle.putSerializable(context.getString(R.string.sub_enum), UserEnum.NOT_MY);
        }
        else if (id == R.id.nav_maps) {
            intent = new Intent(context, Map.class);
            intent.putExtra("requestCode", 300);
            ((Activity) context).startActivityForResult(intent, 300);
        } else if (id == R.id.nav_friends) {
            intent = new Intent(context, FriendsOrSubscribersListActivity.class);
            intent.putExtra(context.getString(R.string.sub_enum), UserEnum.MY);
        } else if (id == R.id.nav_people) {
            intent = new Intent(context, FriendsOrSubscribersListActivity.class);
            intent.putExtra(context.getString(R.string.sub_enum), UserEnum.NOT_MY);
        } else if (id == R.id.nav_bugs) {
            try {
                this.openMessenger();
            } catch (Exception exp) {

            }
        } else if (id == R.id.nav_exit) {
            PrefsManager instance = PrefsManager.getInstance(context);
            instance.clearAll();
            startActivity(new Intent(context, SplashScreenActivity.class));
            finish();
        }
        else if(id == R.id.nav_notifications){
            intent = new Intent(this, NotificationActivity.class);
        }
        else if(id == R.id.nav_activity){
            fragment = new ActivityFragment();
        }

        if(intent != null)
            startActivity(intent);
        // Insert the fragment by replacing any existing fragment
        changeFragment(fragment, bundle);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openMessenger() {
        Intent mainMessActivity = new Intent("com.a4kings.dmitrypavlenko.smapme_mess.MFA");
        mainMessActivity.putExtra("object", new Gson().toJson(member));
        mainMessActivity.putExtra("redirectType", 2);
        startActivity(mainMessActivity);
    }

    public void changeFragment(Fragment fragment, Bundle bundle){
        if (fragment != null) {
            if(bundle != null)
                fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void authenticationResult(boolean isAuthenticate, Member member) {

    }
}
