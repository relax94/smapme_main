package com.studio.a4kings.qr_code_app.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Managers.Api.Services.UserProfileService;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.WallService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.ImageSizes;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.MemberProfile;
import com.studio.a4kings.qr_code_app.Models.Response.MongoMemberResponse;
import com.studio.a4kings.qr_code_app.Models.Response.PhotoResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileResponse;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.DialogUtils;
import com.studio.a4kings.qr_code_app.Utils.ImageFilePath;
import com.studio.a4kings.qr_code_app.Utils.ImageLoader;
import com.studio.a4kings.qr_code_app.Utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.studio.a4kings.qr_code_app.Utils.Helper.checkValid;
import static com.studio.a4kings.qr_code_app.Utils.Helper.swapViews;

/**
 * Created by DUX on 03.02.2016.
 */
public class FillProfileForm extends AppCompatActivity implements View.OnClickListener, Callback<ProfileResponse> {

    Member member = new Member();

    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.scroll)
    LinearLayout scrollView;

    @Bind(R.id.date)
    EditText birthdayDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Bind(R.id.btnTakePic)
    Button btnTakePic;
    @Bind(R.id.btnTakePicGal)
    Button btnTakePicGal;
    @Bind(R.id.big)
    de.hdodenhof.circleimageview.CircleImageView mBigImage;
    @Bind(R.id.group)
    RadioGroup rg;
    @Bind(R.id.firstName)
    EditText firstName;
    @Bind(R.id.lastName)
    EditText lastName;
    @Bind(R.id.aboutMe)
    EditText aboutMe;
    @Bind(R.id.sendProfile)
    Button sendProfile;
    ImageUtils imageUtils;
    private UserProfileService userProfileService;
    Context context;

    @OnClick(R.id.big)
    public void click(View view) {
        dialogUtils.showDialog();
    }

    @OnClick(R.id.decline)
    public void decClick() {
        finish();
    }

    DialogUtils dialogUtils;
    boolean update;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form_profile);
        ButterKnife.bind(this);
        context = this;
        member = (Member) getIntent().getSerializableExtra("profile");
        if (member != null) {
            fillForm(member.getProfile());
        } else {
            member = new Member();
        }
        update = getIntent().getBooleanExtra("isUpdate", false);
        this.userProfileService = new UserProfileService(Constants.SITE_URL);
        this.userProfileService.setOperationToken(PrefsManager.getInstance(context).getFromCore(PrefsParam.TOKEN));
        if (update)
            userProfileService.getProfileAsync(this);
        imageUtils = new ImageUtils(context);
        dialogUtils = new DialogUtils(context, imageUtils);

        Calendar newCalendar = Calendar.getInstance();
        birthdayDate.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = dateFormatter.parse(member.getProfile().getBirthday());
            String datetime = dateFormatter.format(date);
            birthdayDate.setText(datetime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthdayDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        birthdayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        if (imageUtils.hasCamera() || imageUtils.hasDefaultCameraApp(MediaStore.ACTION_IMAGE_CAPTURE)) {
            btnTakePic.setOnClickListener(this);
        } else btnTakePic.setVisibility(View.INVISIBLE);
        btnTakePicGal.setOnClickListener(this);
        sendProfile.setOnClickListener(this);
    }


    private void fillForm(MemberProfile profile) {
        member.setProfile(profile);
        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        switch (profile.getGender()) {
            case "1":
                ((RadioButton) findViewById(R.id.radio_male)).setChecked(true);
                break;
            case "2":
                ((RadioButton) findViewById(R.id.radio_female)).setChecked(true);
                break;
            default:
                ((RadioButton) findViewById(R.id.radio_not_spec)).setChecked(true);
                break;
        }
        aboutMe.setText(profile.getAboutMe());
        birthdayDate.setText(profile.getBirthday());
        ImageLoader.loadImage(context, mBigImage,
                Constants.PATH_TO_USER_IMAGES + Constants.IMAGE_SIZES.get(ImageSizes.MEDIUM) + profile.getPhoto());
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_not_spec:
                if (checked)
                    break;
            case R.id.radio_male:
                if (checked)
                    break;
            case R.id.radio_female:
                if (checked)
                    break;
        }
    }


    private int genderEnum = -1;

    public String getGenderEnum() {
        if (genderEnum == -1) {
            if ((member.getAccountType() == 1 && member.getProfile().getGender().equals("2"))//vk
                    || (member.getAccountType() == 3 && member.getProfile().getGender().equals("0"))//
                    || (member.getAccountType() == 2 && member.getProfile().getGender().equals("male")))//
                return "1";//male
            else if ((member.getAccountType() == 1 && member.getProfile().getGender().equals("1"))//vk
                    || (member.getAccountType() == 3 && member.getProfile().getGender().equals("2"))
                    || (member.getAccountType() == 2 && member.getProfile().getGender().equals("female")))
                return "2";//female
        }
        return "0";//not specified
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTakePic:
                imageUtils.openCamera();
                break;
            case R.id.btnTakePicGal:
                imageUtils.openGallery();
                break;
            case R.id.sendProfile:
                sendMember();
                break;
        }
    }


    public void sendMember() {
        if (!checkValid(firstName)
                || !checkValid(lastName)
                || !checkValid(birthdayDate)
                || !checkValid(aboutMe)
                )
            return;
        MemberProfile profile = new MemberProfile();
        profile.setFirstName(firstName.getText().toString());
        profile.setLastName(lastName.getText().toString());
        profile.setBirthday(birthdayDate.getText().toString());
        profile.setAboutMe(aboutMe.getText().toString());
        int radioButtonID = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(radioButtonID);
        int idx = rg.indexOfChild(radioButton);
        profile.setGender(Integer.toString(idx != -1 ? idx : 0));
        swapViews(progressBar, scrollView);
        update = false;
        this.userProfileService.updateProfileAsync(profile, this);
    }


    Callback<PhotoResponse> photoResponse = new Callback<PhotoResponse>() {
        @Override
        public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
            if (response.body() != null) {
                if (response.body().getCode() == 200) {
                    member.getProfile().setPhoto(response.body().getFileName());
                    Toast.makeText(FillProfileForm.this, "Ok", Toast.LENGTH_SHORT).show();
                  //  synchronizedMongoMember(member.getProfile());
                    ImageLoader.loadImage(context,
                            mBigImage,
                            Constants.PATH_TO_FILES + Constants.IMAGE_FOLDER + Constants.IMAGE_SIZES.get(ImageSizes.MEDIUM) + member.getProfile().getPhoto());
                }
            } else Toast.makeText(FillProfileForm.this, "Not ok", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<PhotoResponse> call, Throwable t) {

        }
    };

    private void savePhoto(Bitmap bitmap) {
        this.userProfileService.updatePhotoAsync(bitmap, photoResponse);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.GALLERY_REQUEST:
                    imageUtils.setmCurrentPhotoPath(ImageFilePath.getPath(this, intent.getData()));
                    imageUtils.doCrop(mBigImage, mBigImage.getWidth(), mBigImage.getHeight());
                    break;
                case Constants.REQUEST_TAKE_PHOTO:
                    imageUtils.doCrop(mBigImage, mBigImage.getWidth(), mBigImage.getHeight());
                    break;
                case Constants.CROP_IMAGE_REQUEST:
                    Bitmap bitmap = imageUtils.decodeUriAsBitmap();
                    //mBigImage.setImageBitmap(bitmap);
                    savePhoto(bitmap);
                    break;
            }
        }
    }


    @Override
    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
        if (response.body() != null)
            if (response.body().getCode() != null && response.body().getCode() == 200) {
                member.setProfile(response.body().getProfile());
                PrefsManager.getInstance(context).putMember(member);
                if (!update) {
                    redirectToMainPage();
                }
                else
                    fillForm(response.body().getProfile());
            } else {
                Toast.makeText(this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                swapViews(scrollView, progressBar);
            }
        else {
            swapViews(scrollView, progressBar);
            Toast.makeText(this, "error on server", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<ProfileResponse> call, Throwable t) {
        swapViews(scrollView, progressBar);
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void redirectToMainPage() {
        swapViews(scrollView, progressBar);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
