package eu.siacs.conversations.apiClient;
/*
 *  Created by Yamini on 29/09/20
 */

import java.util.Map;

import eu.siacs.conversations.pojo.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @GET("weather?appid=1e44076401608e7306fe30ff7bcf88d1")
    Call<Weather> getWeatherDetails(@QueryMap Map<String, String> params);
}
