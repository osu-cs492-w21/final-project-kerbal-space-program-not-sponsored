package com.example.afinal.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class AsteroidData implements Serializable {
//    public String date = "2021-03-12";
    private String name;
    private String detailsUrl;
    private int diameterMin;
    private int diameterMax;
    private boolean hazardous;
    private int epoch;
    private int velocity;
    private int distance;



    public AsteroidData(){
        this.name = null; //"N/A";
        this.detailsUrl = null; //"N/A";
        this.diameterMin = 0;
        this.diameterMax = 0;
        this.hazardous = false;
        this.epoch = 0;
        this.velocity = 0;
        this.distance = 0;


    }

    public AsteroidData(String name, String detailsUrl,  int diameterMin,
                        int diameterMax, boolean hazardous, int epoch, int velocity, int distance){
        this.name = name;
        this.detailsUrl = detailsUrl;
        this.diameterMin = diameterMin;
        this.diameterMax = diameterMax;
        this.hazardous = hazardous;
        this.epoch = epoch;
        this.velocity = velocity;
        this.distance = distance;

    }

    public String getName() {
        return name;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public int getDiameterMin() {
        return diameterMin;
    }

    public int getDiameterMax() {
        return diameterMax;
    }

    public boolean isHazardous() {
        return hazardous;
    }

    public int getEpoch() {
        return epoch;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getDistance() {
        return distance;
    }



    /**Deserializer
     *  list.name --> AsteroidData.name
     *  list.nasa_jpl_url --> AsteroidData.detailsUrl
     *  list.estimated_diameter.meters.estimated_diameter_min --> AsteroidData.diameterMin
     *  list.estimated_diameter.meters.estimated_diameter_max --> AsteroidData.diameterMax
     *  list.is_potentially_hazardous_asteroid --> AsteroidData.hazardous
     *  list.close_approach_data[0].epoch_date_close_approach --> AsteroidData.epoch
     *  list.close_approach_data[0].relative_velocity.kilometers_per_hour --> AsteroidData.velocity
     *  list.close_approach_data[0].miss_distance.kilometers --> AsteroidData.distance
     *
     */
   public static class JsonDeserializer implements com.google.gson.JsonDeserializer<AsteroidData> {
       @Override
       public AsteroidData deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//           JsonObject nearEarthObjectsObj = json.getAsJsonObject();
//           //how do I pass the date variable into the deserializer
//           JsonArray dateStrArr = nearEarthObjectsObj.getAsJsonArray("2021-03-12");

           JsonObject listObj = json.getAsJsonObject();
           JsonObject diameterObj = listObj.getAsJsonObject("esitmated_diameter");
           JsonObject metersObj = diameterObj.getAsJsonObject("meters");
           JsonArray approachArr = listObj.getAsJsonArray("close_approach_date");
           JsonObject approachObj = approachArr.get(0).getAsJsonObject();
           JsonObject velocityObj = approachObj.getAsJsonObject("relative_velocity");
           JsonObject distanceObj = approachObj.getAsJsonObject("miss_distance");

           return new AsteroidData(
                   listObj.getAsJsonPrimitive("name").getAsString(),
                   listObj.getAsJsonPrimitive("nasa_jpl_url").getAsString(),
                   (int)Math.round(metersObj.getAsJsonPrimitive("estimated_diameter_min").getAsDouble()),
                   (int)Math.round(metersObj.getAsJsonPrimitive("estimated_diameter_max").getAsDouble()),
                   listObj.getAsJsonPrimitive("is_potentially_hazardous_asteroid").getAsBoolean(),
                   approachObj.getAsJsonPrimitive("epoch_date_close_approach").getAsInt(),
                   (int)Math.round(velocityObj.getAsJsonPrimitive("kilometers_per_hour").getAsDouble()),
                   (int)Math.round(distanceObj.getAsJsonPrimitive("kilometers").getAsDouble())
           );
       }
   }

}
