package com.example.android.exampleweatherapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private ArrayList<Weather> weathers = new ArrayList<>();
    private Context mContext;

    // this is the constructor for the Weather object adapter
    public WeatherAdapter(Context context, ArrayList<Weather> mWeathers) {
        mContext = context;
        weathers = mWeathers;
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.date_of_forecast)
        TextView forecastDate;
        @BindView(R.id.min_temp_text)
        TextView minTemp;
        @BindView(R.id.max_temp_text)
        TextView maxTemp;
        @BindView(R.id.conditions_text)
        TextView conditions;
        @BindView(R.id.humidity_text)
        TextView humidity;
        @BindView(R.id.condition_icon)
        ImageView conditionIcon;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindWeathers(Weather currentWeather) {
            // set the title string
            long dateLong = getCreatedAt(currentWeather.getDate());
            Date dateObject = new Date(dateLong);
            // the format of your date and time on screen
            String formattedDate = formatDate(dateObject);
            forecastDate.setText(formattedDate);
            Float minT = Float.parseFloat(currentWeather.getMinTemp());
            int minTint = Math.round(minT); // to get a rounded temp number
            String tempMin = minTint + " \u2103"; // adding celsius symbol to the temp
            minTemp.setText(tempMin);
            Float maxT = Float.parseFloat(currentWeather.getMaxTemp());
            int maxTint = Math.round(maxT);// to get a rounded temp number
            String tempMax = maxTint + " \u2103"; // adding celsius symbol to the temp
            maxTemp.setText(tempMax);
            String condition = currentWeather.getConditions();
            conditions.setText(condition);
            // replacing the ".0" with percentage symbol in humidity
            String humidityText = currentWeather.getHumidity().replace(".0", "%");
            humidity.setText(humidityText);
            // getting the possible weather conditions and matching icon list from arrays.xml file
            String[] conditions = mContext.getResources().getStringArray(R.array.conditions_strings);
            String[] icons = mContext.getResources().getStringArray(R.array.conditions_icons);
            conditionIcon.setImageResource(mContext.getResources().
                    getIdentifier(icons[0], null, mContext.getPackageName()));
            for (int i = 0; i < conditions.length; i++) {
                // setting the right condition icon to the weather condition
                if (condition.equalsIgnoreCase(conditions[i])) {
                    conditionIcon.setImageResource(mContext.getResources().
                            getIdentifier(icons[i], null, mContext.getPackageName()));
                    break; // if the icon was found - don't continue with the for loop
                }
            }
        }

        @Override
        public void onClick(View v) {
        // didn't put anything here
        }
    }

    // Return the formatted date string (i.e. "Jan 14, 1978") from a Date object.
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

//    // Return the formatted time string (i.e. "4:30 PM") from a Date object - left it here so you can use it, no need in this app.
//    private String formatTime(Date dateObject) {
//        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
//        return timeFormat.format(dateObject);
//    }

    // create a date format from the string we get from the json stream
    public long getCreatedAt(String createdAt){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date createDate = null;
        try {
            createDate = formatter.parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createDate.getTime();
    }

    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.WeatherViewHolder holder, final int position) {
        holder.bindWeathers(weathers.get(position)); // binding the view to the right object
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

}
