package com.joshskeen.weatherview;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.joshskeen.weatherview.model.WeatherCondition;
import com.joshskeen.weatherview.service.WeatherServiceManager;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends BaseActivity
{

    @Inject
    WeatherServiceManager mWeatherServiceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<WeatherCondition> conditionsForAtlanta = mWeatherServiceManager.getConditionsForAtlanta();
        StringBuffer out = new StringBuffer();
        for (WeatherCondition wc : conditionsForAtlanta)
        {
            out.append(wc.toString());
        }

        // globally
        TextView myAwesomeTextView = (TextView) findViewById(R.id.textView);

//in your OnCreate() method
        myAwesomeTextView.setText(out.toString());
    }

}
