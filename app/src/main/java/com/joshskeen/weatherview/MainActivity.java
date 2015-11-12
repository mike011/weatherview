package com.joshskeen.weatherview;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
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

        button.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        List<WeatherCondition> conditionsForCity = mWeatherServiceManager.getConditionsFor(text.getText().toString());
                        if (conditionsForCity != null)
                        {
                            StringBuffer out = new StringBuffer();
                            for (WeatherCondition wc : conditionsForCity)
                            {
                                out.append(wc.toString());
                            }

                            TextView outText = (TextView) findViewById(R.id.textView);
                            outText.setText(out.toString());
                        }
                    }
                });
    }
}
