package eu.siacs.conversations.ui;
/*
 *  Created by Yamini on 28/09/20
 */

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import eu.siacs.conversations.R;
import eu.siacs.conversations.apiClient.APIClient;
import eu.siacs.conversations.apiClient.APIInterface;
import eu.siacs.conversations.databinding.FragmentWeatherBinding;
import eu.siacs.conversations.pojo.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment implements View.OnClickListener {

    private FragmentWeatherBinding binding;
    private APIInterface apiInterface;

    private final String KEY_DELHI = "Delhi";
    private final String KEY_MUMBAI = "Mumbai";
    private final String KEY_BANGALORE = "Bangalore";
    private final String KEY_HYDERABAD = "Hyderabad";
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    private final String city = "q";
    private final String units = "units";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        apiInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        preference = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = preference.edit();

        binding.delhi.setOnClickListener(this);
        binding.mumbai.setOnClickListener(this);
        binding.bangalore.setOnClickListener(this);
        binding.hyderabad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delhi:
                if(binding.delhiExpanded.getVisibility() == View.VISIBLE){
                    binding.delhiDropdown.setRotation(0f);
                    binding.delhiExpanded.setVisibility(View.GONE);
                }else{
                    binding.delhiDropdown.setRotation(90f);
                    binding.delhiExpanded.setVisibility(View.VISIBLE);
                }
                binding.delhiProgress.setVisibility(View.VISIBLE);
                loadWeatherData(KEY_DELHI);
                break;

            case R.id.mumbai:
                if(binding.mumbaiExpanded.getVisibility() == View.VISIBLE){
                    binding.mumbaiDropdown.setRotation(0f);
                    binding.mumbaiExpanded.setVisibility(View.GONE);
                }else{
                    binding.mumbaiDropdown.setRotation(90f);
                    binding.mumbaiExpanded.setVisibility(View.VISIBLE);
                }
                binding.mumbaiProgress.setVisibility(View.VISIBLE);
                loadWeatherData(KEY_MUMBAI);
                break;

            case R.id.bangalore:
                if(binding.bangaloreExpanded.getVisibility() == View.VISIBLE){
                    binding.bangaloreDropdown.setRotation(0f);
                    binding.bangaloreExpanded.setVisibility(View.GONE);
                }else{
                    binding.bangaloreDropdown.setRotation(90f);
                    binding.bangaloreExpanded.setVisibility(View.VISIBLE);
                }
                binding.bangaloreProgress.setVisibility(View.VISIBLE);
                loadWeatherData(KEY_BANGALORE);
                break;

            case R.id.hyderabad:
                if(binding.hyderabadExpanded.getVisibility() == View.VISIBLE){
                    binding.hyderabadDropdown.setRotation(0f);
                    binding.hyderabadExpanded.setVisibility(View.GONE);
                }else{
                    binding.hyderabadDropdown.setRotation(90f);
                    binding.hyderabadExpanded.setVisibility(View.VISIBLE);
                }
                binding.hyderabadProgress.setVisibility(View.VISIBLE);
                loadWeatherData(KEY_HYDERABAD);
                break;

        }
    }

    private void loadWeatherData(String cityName) {
        if(TextUtils.isEmpty(preference.getString(cityName, ""))) {
            Map<String, String> params = new HashMap<String, String>();
            params.put(city, cityName);
            params.put(units, "metric");

            Call<Weather> call = apiInterface.getWeatherDetails(params);
            call.enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {

                    if (response.code() == 200) {
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());
                        editor.putString(cityName, json);
                        editor.apply();
                        populateUI(response.body(), cityName);
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong! Please try again!!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something went wrong! Please try again!!", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            String cityData = preference.getString(cityName, "");
            if (!TextUtils.isEmpty(cityData)) {

                Gson gson = new Gson();
                Weather weather = gson.fromJson(cityData, Weather.class);
                if (weather != null) {

                    populateUI(weather, cityName);

                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateUI(Weather weather, String cityName) {
        switch (cityName){
            case KEY_DELHI:
                binding.delhiCurrentTemp.setText(weather.getMain().getTemp()+getString(R.string.celsius));
                binding.delhiMinTemp.setText(getString(R.string.min_temperature_is)+" "+weather.getMain().getTemp_min()+getString(R.string.celsius));
                binding.delhiMaxTemp.setText(getString(R.string.max_temperature_is)+" "+weather.getMain().getTemp_max()+getString(R.string.celsius));
                binding.delhiHumidity.setText(getString(R.string.humidity_is)+" "+weather.getMain().getHumidity());
                binding.delhiProgress.setVisibility(View.GONE);
                break;

            case KEY_MUMBAI:
                binding.mumbaiCurrentTemp.setText(weather.getMain().getTemp()+getString(R.string.celsius));
                binding.mumbaiMinTemp.setText(getString(R.string.min_temperature_is)+" "+weather.getMain().getTemp_min()+getString(R.string.celsius));
                binding.mumbaiMaxTemp.setText(getString(R.string.max_temperature_is)+" "+weather.getMain().getTemp_max()+getString(R.string.celsius));
                binding.mumbaiHumidity.setText(getString(R.string.humidity_is)+" "+weather.getMain().getHumidity());
                binding.mumbaiProgress.setVisibility(View.GONE);
                break;

            case KEY_BANGALORE:
                binding.bangaloreCurrentTemp.setText(weather.getMain().getTemp()+getString(R.string.celsius));
                binding.bangaloreMinTemp.setText(getString(R.string.min_temperature_is)+" "+weather.getMain().getTemp_min()+getString(R.string.celsius));
                binding.bangaloreMaxTemp.setText(getString(R.string.max_temperature_is)+" "+weather.getMain().getTemp_max()+getString(R.string.celsius));
                binding.bangaloreHumidity.setText(getString(R.string.humidity_is)+" "+weather.getMain().getHumidity());
                binding.bangaloreProgress.setVisibility(View.GONE);
                break;

            case KEY_HYDERABAD:
                binding.hyderabadCurrentTemp.setText(weather.getMain().getTemp()+getString(R.string.celsius));
                binding.hyderabadMinTemp.setText(getString(R.string.min_temperature_is)+" "+weather.getMain().getTemp_min()+getString(R.string.celsius));
                binding.hyderabadMaxTemp.setText(getString(R.string.max_temperature_is)+" "+weather.getMain().getTemp_max()+getString(R.string.celsius));
                binding.hyderabadHumidity.setText(getString(R.string.humidity_is)+" "+weather.getMain().getHumidity());
                binding.hyderabadProgress.setVisibility(View.GONE);
                break;
        }
    }
}
