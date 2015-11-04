package com.joshskeen.weatherview;

import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.joshskeen.weatherview.model.WeatherCondition;
import com.joshskeen.weatherview.service.WeatherServiceManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
//import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class WeatherviewWebserviceTest extends
        ActivityInstrumentationTestCase2<MainActivity>
{
    public WeatherviewWebserviceTest()
    {
        super(MainActivity.class);
    }


    public WeatherServiceManager mWeatherServiceManager;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(Integer.valueOf(BuildConfig.WIRE_MOCK_PORT));

    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        //  Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        String serviceEndpoint = "http://" + BuildConfig.WIRE_MOCK_IP + ":" + BuildConfig.WIRE_MOCK_PORT + "/api/" + BuildConfig.WEATHERVIEW_API_KEY + "/";
        mWeatherServiceManager = new WeatherServiceManager(serviceEndpoint);
    }

    @Test
    public void testGetCurrentWeatherReturnsExpected()
    {
        WireMock.configureFor(BuildConfig.WIRE_MOCK_IP, BuildConfig.WIRE_MOCK_PORT);
        stubFor(get(urlMatching("/api/.*"))
                .atPriority(5)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("atlanta-conditions.json")));
        assertNotNull("Weather Service Manager is null", mWeatherServiceManager);
        List<WeatherCondition> conditionsForAtlanta = mWeatherServiceManager.getConditionsForAtlanta();
        //assertThat(conditionsForAtlanta.size()).isEqualTo(1);
    }

}
