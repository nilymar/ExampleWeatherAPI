package com.example.android.exampleweatherapi;

public class Weather {
    /** name of city */
    private String mCity;
    /** date of forecast */
    private String mDate;
    /** minimum temp */
    private String mMinTemp;
    /* maximum temp */
    private String mMaxTemp;
    /* weather conditions */
    private String mConditions;
    /* humidity */
    private String mHumidity;

    /**
     * Create a new Weather object.
     * @param city
     *
     * @param date
     *
     * @param minTemp
     *
     * @param maxTemp
     *
     * @param conditions
     *
     * @param humidity
     */
    public Weather(String city, String date, String minTemp, String maxTemp, String conditions, String humidity) {
        mCity = city;
        mDate = date;
        mMinTemp = minTemp;
        mMaxTemp = maxTemp;
        mConditions = conditions;
        mHumidity = humidity;
    }

    // Get the city
    public String getCity() {
        return mCity;
    }

    // Get the date of forecast
    public String getDate() {
        return mDate;
    }

    // Get the min temp
    public String getMinTemp() {
        return mMinTemp;
    }

    // Get the max temp
    public String getMaxTemp() { return mMaxTemp; }

    // Get the weather conditions
    public String getConditions() { return mConditions; }

    // Get the humidity
    public String getHumidity() {return mHumidity; }
}

