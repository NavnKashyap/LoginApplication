package com.loginapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectUtils {
    public static final String TAG = "ProjectUtility";
    private static ProgressDialog mProgressDialog;

    /**
     * Static method to pause the progress dialog.
     */
    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else if (email.equals("")) {
            return false;
        }
        return false;
    }

    public static boolean isPasswordValid(String number) {

        //String regexStr = "^([0-9\\(\\)\\/\\+ \\-]*)$";
        String regexStr = " (?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,20})$";

        if (number.length() < 8 || number.length() > 16 /*|| number.matches(regexStr) == false*/) {
            //	Log.d("tag", "Number is not valid");
            return false;
        }

        return true;
    }

    public static String getEditTextValue(EditText text) {
        return text.getText().toString().trim().replace(",","");
    }

    /**
     * Checks if any text box is null or not.
     *
     * @param text : Text view for which validation is to be checked.
     * @return True if not null.
     */
    public static boolean isEditTextFilled(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void InternetAlertDialog(Context mContext) {
        Builder alertDialog = new Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle("Error Connecting");

        //Setting Dialog Message
        alertDialog.setMessage("No Internet Connection");

        //On Pressing Setting button
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

}