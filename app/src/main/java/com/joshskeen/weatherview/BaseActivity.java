package com.joshskeen.weatherview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.joshskeen.weatherview.inject.WeatherviewApplication;

public abstract class BaseActivity extends Activity implements View.OnClickListener
{
    protected EditText text;
    protected Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WeatherviewApplication.get(this).inject(this);

        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.editText);
        text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        text.setText("");
    }
}
