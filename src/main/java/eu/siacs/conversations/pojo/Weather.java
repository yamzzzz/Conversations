package eu.siacs.conversations.pojo;
/*
 *  Created by Yamini on 29/09/20
 */

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("timezone")
    private String timezone;
    @SerializedName("id")
    private int id;
    @SerializedName("cod")
    private int cod;
    @SerializedName("name")
    private String name;
    @SerializedName("main")
    private Main main;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }


    public static class Main {
        @SerializedName("temp")
        private float temp;
        @SerializedName("temp_min")
        private float temp_min;
        @SerializedName("temp_max")
        private float temp_max;
        @SerializedName("pressure")
        private float pressure;
        @SerializedName("humidity")
        private float humidity;

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(float temp_min) {
            this.temp_min = temp_min;
        }

        public float getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(float temp_max) {
            this.temp_max = temp_max;
        }

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }
    }

}
