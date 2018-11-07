/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package sa.bitoasis.bitoasis.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.TypedValue;

import com.google.common.base.Strings;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import sa.bitoasis.bitoasis.R;


public final class CommonUtils {

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public static final Pattern MOBILE = Pattern.compile("^(\\+97[\\s]{0,1}[\\-]{0,1}[\\s]{0,1}1|0)(50|55|56|52|54)[\\s]{0,1}[\\-]{0,1}[\\s]{0,1}[1-9]{1}[0-9]{6}$");
    public static final Pattern FAX = Pattern.compile("^(\\+97[\\s]{0,1}[\\-]{0,1}[\\s]{0,1}1|0)(2|3|4|6|7|9)[\\s]{0,1}[\\-]{0,1}[\\s]{0,1}[1-9]{1}[0-9]{6}$");
    public static final Pattern ADDRESS = Pattern.compile("[A-Za-z0-9\\s]+");
    public static final Pattern ACCOUNT_NAME = Pattern.compile("[A-Za-z0-9\\s]+");
    public static final Pattern PASSWORD = Pattern.compile("((?=\\S*\\d)(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*[!~@#$%^&*(){}<>?/+=-_,.]).{8,12})");


    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static int convertDip2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        //  progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String target) {
        if (Strings.isNullOrEmpty(target)) {
            return false;
        } else {
            return EMAIL_ADDRESS_PATTERN.matcher(target).matches();

        }
    }

    public static boolean isValidAddress(String address) {
        return address != null && ADDRESS.matcher(address).matches();

    }

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE.matcher(mobile).matches();

    }

    public static boolean isValidName(String name) {
        return name != null && ACCOUNT_NAME.matcher(name).matches();

    }

    public static boolean isValidPassword(String password) {
        //  return password != null && PASSWORD.matcher(password).matches();
        return password != null && password.length() > 4;

    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static HashMap<Integer, String> toIntegerMap(JSONObject jsonObject) {
        if (jsonObject == null)
            return null;

        HashMap<Integer, String> valuesMap = new HashMap<>();

        Iterator<String> keysIterator = jsonObject.keys();
        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            valuesMap.put(Integer.parseInt(key), jsonObject.optString(key));
        }
        return valuesMap;
    }

    public static HashMap<String, String> toMap(JSONObject jsonObject) {
        if (jsonObject == null)
            return null;

        HashMap<String, String> valuesMap = new HashMap<>();

        Iterator<String> keysIterator = jsonObject.keys();
        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            valuesMap.put(key, jsonObject.optString(key));
        }
        return valuesMap;
    }

    public static JSONObject assetJSONFile(String filename, Context context) throws Exception {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new JSONObject(new String(formArray));
    }

}
