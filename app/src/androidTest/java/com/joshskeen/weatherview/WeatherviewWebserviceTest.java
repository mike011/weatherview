package com.joshskeen.weatherview;

import android.support.test.InstrumentationRegistry;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.hamcrest.core.StringContains.containsString;
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
    public WireMockRule wireMockRule = new WireMockRule(Integer.valueOf(BuildConfig.PORT));

    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        getActivity();
        String serviceEndpoint = "http://" + BuildConfig.IP + ":" + BuildConfig.PORT + "/api/" + BuildConfig.WEATHERVIEW_API_KEY + "/";
        mWeatherServiceManager = new WeatherServiceManager(serviceEndpoint);
    }

    @Test
    public void testGetCurrentWeatherReturnsExpected_API()
    {
        WireMock.configureFor(BuildConfig.IP, BuildConfig.PORT);
        String city = "atlanta";
        stubFor(get(urlMatching("/api/.*"))
                .atPriority(5)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile(city + "-conditions.json")));
        assertNotNull("Weather Service Manager is null", mWeatherServiceManager);
        List<WeatherCondition> conditionsForAtlanta = mWeatherServiceManager.getConditionsFor(city);
        assertEquals(1, conditionsForAtlanta.size());
    }

    @Test
    public void testGetCurrentWeatherReturnsExpected_Wiremock()
    {
        WireMock.configureFor(BuildConfig.IP, BuildConfig.PORT);
        String city = "atlanta";
        stubFor(get(urlMatching("/api/.*"))
                .atPriority(5)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile(city + "-conditions.json")));
        onView(withId(R.id.editText)).perform(typeText(city));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }

}
