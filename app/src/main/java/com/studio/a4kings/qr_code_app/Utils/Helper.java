package com.studio.a4kings.qr_code_app.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;

import com.amulyakhare.textdrawable.TextDrawable;
import com.studio.a4kings.qr_code_app.R;

import java.text.DecimalFormat;

public class Helper {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void swapViews(final View showView, final View hideView){
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = showView.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);

            showView.setVisibility(View.VISIBLE);
            showView.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    showView.setVisibility(View.VISIBLE);
                }
            });

            hideView.setVisibility(View.GONE);
            hideView.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    hideView.setVisibility(View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            hideView.setVisibility(View.GONE);
            showView.setVisibility(View.VISIBLE);
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);


        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public static boolean checkValid(EditText view) {
        if (!view.getText().toString().equals("")) {
            return true;
        } else {
            view.setError(view.getContext().getString(R.string.error_field_required));
            view.requestFocus();
            return false;
        }
    }

    public static Integer getColor(Float rating) {
        Integer color = Color.GREEN;
        if (rating <= 1) {
            color = Color.RED;
        } else if (rating <= 2) {
            color = Color.rgb(255, 153, 0);
        } else if (rating <= 3) {
            color = Color.YELLOW;
        } else if (rating <= 4) {
            color = Color.rgb(1, 200, 1);
        }
        return color;
    }

    public static TextDrawable getTextDrawable(Float rating) {
        Integer color = Helper.getColor(rating);
        return TextDrawable.builder()
                .buildRoundRect(new DecimalFormat("#0.0").format(rating), color, 50);
    }

}
