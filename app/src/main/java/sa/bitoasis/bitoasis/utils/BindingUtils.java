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

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;

import sa.bitoasis.bitoasis.R;


public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"color"})
    public static void setTextColor(TextView textView, BigDecimal number) {
        if (number.compareTo(new BigDecimal(0))<1)
            textView.setTextColor(textView.getContext().getResources().getColor(android.R.color.holo_red_dark));
        else
            textView.setTextColor(textView.getContext().getResources().getColor(android.R.color.black));
    }
    @BindingAdapter({"arrowImg"})
    public static void setArrowImg(ImageView iv, BigDecimal number) {
        if (number.compareTo(new BigDecimal(0))<1)
            iv.setImageResource(R.drawable.ic_arrow_downward_red_24dp);
        else
            iv.setImageResource(R.drawable.ic_arrow_upward_green_24dp);
    }
}