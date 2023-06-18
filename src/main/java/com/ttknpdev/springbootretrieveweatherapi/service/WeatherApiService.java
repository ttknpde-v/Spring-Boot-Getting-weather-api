package com.ttknpdev.springbootretrieveweatherapi.service;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.ttknpdev.springbootretrieveweatherapi.entity.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherApiService {
    private OkHttpClient client;
    private Response response;
    private final String APIKEY = "9b58796a91eb98062a4cd0ba4f90f048";
    private String city;
    private String countryCode;

    private JSONObject getTheWeatherApi() throws IOException {
        client = new OkHttpClient();
        Request requestForClient = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?q="
                        + getCity() + ","
                        + getCountryCode()
                        + "&appid="
                        + APIKEY + "")
                .build();
        try {

            response = client.newCall(requestForClient).execute();

        } catch (IOException | JSONException e) {

            throw new RuntimeException("something was wrong in getTheWeatherApi method");

        }
        return new JSONObject(response.body().string()); // this line returns likes below
        /*
        {
            "rain":{ "1h":0.56 },
            "visibility":10000,"timezone":25200,
            "main":{"temp":302.19,
                    "temp_min":299.77,
                    "grnd_level":1007,
                    "humidity":28,
                    "pressure":1008,
                    "sea_level":1008,
                    "feels_like":300.95,
                    "temp_max":304.77},
                    "clouds":{"all":39}
                    ,"sys":{"country":"TH","sunrise":1687042253,"sunset":1687088819,"id":2035331,"type":2}
                    ,"dt":1687021460,
                    "coord":{"lon":100.5167,"lat":13.75}
                    ,"weather":[{"icon":"10n","description":"light rain","main":"Rain","id":500}],
                    "name":"Bangkok"
                    ,"cod":200,"id":1609350,"base":"stations",
                    "wind":{"deg":223,"speed":3.93,"gust":7.45}
                    }
        */
    }

    public Weather getWeather() throws IOException {
        Weather weather = new Weather();
            weather.setStatus((short) HttpStatus.ACCEPTED.value());
            weather.setCity(getCityName());
            weather.setCountry(getCountry());
            weather.setEnvironment(getEnvironment());
            weather.setDescription(getDescription());
            weather.setTemp(getTemp() + " °C");
            weather.setTempMin(getTempMin() + " °C");
            weather.setTempMax(getTempMax() + " °C");
            weather.setLatitude(getLatitude());
            weather.setLongitude(getLongitude());
        return weather;
    }

    private JSONArray getTheWeatherArrayWeather() throws IOException {
        JSONArray weatherArray = getTheWeatherApi().getJSONArray("weather");  // retrieve only Array named "weather"
        /* [
            {
                "icon":"10n",
                "description":"light rain",
                "main":"Rain",
                "id":500
             }
            ]
        *  we use json array because it's array , focus [] */
        return weatherArray;
    }

    private JSONObject getTheWeatherObjectMain() throws IOException {
        JSONObject main = getTheWeatherApi().getJSONObject("main"); // retrieve only object named "main"
        return main;
    }

    private JSONObject getTheWeatherObjectCoord() throws IOException {
        JSONObject coord = getTheWeatherApi().getJSONObject("coord");  // retrieve only object named "coord"
        return coord;
    }

    private JSONObject getTheWeatherObjectSys() throws IOException {
        // retrieve only object named "sys"
        JSONObject coord = getTheWeatherApi().getJSONObject("sys");
        return coord;
    }

    public void setCityAndCountry(String city, String countryCode) {
        this.city = city;
        this.countryCode = countryCode;
    }

    private String getCity() {
        return city;
    }

    private String getCountryCode() {
        return countryCode;
    }

    private String getTemp() throws IOException {
        JSONObject theWeatherObjectMain = getTheWeatherObjectMain();
        float kelvin = Float.parseFloat(String.format("%.2f", theWeatherObjectMain.getFloat("temp")));
        return String.valueOf(String.format("%.2f", (kelvin - 273.15F)));
    }

    private String getTempMin() throws IOException {
        JSONObject theWeatherObjectMain = getTheWeatherObjectMain();
        float kelvin = Float.parseFloat(String.format("%.2f", theWeatherObjectMain.getFloat("temp_min")));
        return String.valueOf(String.format("%.2f", (kelvin - 273.15F)));
    }

    private String getTempMax() throws IOException {
        JSONObject theWeatherObjectMain = getTheWeatherObjectMain();
        float kelvin = Float.parseFloat(String.format("%.2f", theWeatherObjectMain.getFloat("temp_max")));
        return String.valueOf(String.format("%.2f", (kelvin - 273.15F)));
    }


    private Float getLatitude() throws IOException {
        // values that you want to retrieve , type is same type method get As getDouble("name keys") , ...
        return getTheWeatherObjectCoord().getFloat("lat");
    }

    private Float getLongitude() throws IOException {
        return getTheWeatherObjectCoord().getFloat("lon");
    }

    private String getDescription() throws IOException {
        // retrieve json array element 0 and convert to json object
        JSONObject theWeatherObjectWeather = getTheWeatherArrayWeather().optJSONObject(0);
        // result looks like : { "icon":"03n","description":"scattered clouds","main":"Clouds","id":802 }
        // so , you can get any key thru method getString() , getDouble() ,...
        return theWeatherObjectWeather.getString("description");
    }

    private String getEnvironment() throws IOException {
        JSONObject theWeatherObjectWeather = getTheWeatherArrayWeather().optJSONObject(0);
        return theWeatherObjectWeather.getString("main");
    }

    private String getCityName() throws IOException  {
        return getTheWeatherApi().getString("name");
    }

    private String getCountry() throws IOException {
        return getTheWeatherObjectSys().getString("country");
    }

}
