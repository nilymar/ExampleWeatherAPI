package com.example.android.exampleweatherapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

// with this we create the setting screen, accessible from the menu, where you can set the city and number of days for the weather forecast
public class SettingActivity extends AppCompatActivity {
    private static final String SHARED_PREFERENCES = "weatherappshared"; // name for sharedPreferences location(saves data for next time)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public static class WeatherPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_main);
            Preference queryWord = findPreference(getString(R.string.settings_city_key));
            bindPreferenceSummaryToValue(queryWord);
            Preference setForecastDays = findPreference(getString(R.string.settings_forecast_days_key));
            bindPreferenceSummaryToValue(setForecastDays);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            if (preference instanceof EditTextPreference) {
                if (value.toString().isEmpty())
                    preference.setSummary(getString(R.string.settings_city_default)); // if there was no city saved, that the default city
            } else if (preference instanceof NumberPickerPreference) {
                NumberPickerPreference numPref = (NumberPickerPreference) preference;
                String forecastDays = String.valueOf(numPref.getValue());
                savePreferences(preference.getKey(), forecastDays);
                preference.setSummary(forecastDays);
            } else preference.setSummary(value.toString());
            return true;
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        if (preference instanceof EditTextPreference) { // for the city
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        } else {
            String preferenceString = restorePreferences(preference.getKey());
            onPreferenceChange(preference, preferenceString);
        }
    }

    // This method to store the custom preferences changes
    public void savePreferences(String key, String value) {
        SharedPreferences myPreferences = this.getActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putString(key, value);
        myEditor.apply();
    }

    // This method to restore the custom preferences data
    public String restorePreferences(String key) {
        SharedPreferences myPreferences = this.getActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (myPreferences.contains(key))
            return myPreferences.getString(key, "");
        else return "";
    }
}

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}

