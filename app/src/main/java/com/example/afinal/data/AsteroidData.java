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
    private int diameterMinMeters;
    private int diameterMaxMeters;
    private int diameterMinFeet;
    private int diameterMaxFeet;
    private boolean hazardous;
    private int epoch;
    //kilometers/hour
    private int velocityKmph;
    //Miles/hour
    private int velocityMph;
    private int distanceKilometers;
    private int distanceMiles;



    public AsteroidData(){
        this.name = null; //"N/A";
        this.detailsUrl = null; //"N/A";
        this.diameterMinMeters = 0;
        this.diameterMaxMeters = 0;
        this.diameterMinFeet = 0;
        this.diameterMaxFeet = 0;
        this.hazardous = false;
        this.epoch = 0;
        this.velocityKmph = 0;
        this.velocityMph = 0;
        this.distanceKilometers = 0;
        this.distanceMiles = 0;


    }

    public AsteroidData(String name, String detailsUrl,  int diameterMinMeters,
                        int diameterMaxMeters, int diameterMinFeet, int diameterMaxFeet,
                        boolean hazardous, int epoch, int velocityKmph, int velocityMph,
                        int distanceKilometers, int distanceMiles){
        this.name = name;
        this.detailsUrl = detailsUrl;
        this.diameterMinMeters = diameterMinMeters;
        this.diameterMaxMeters = diameterMaxMeters;
        this.diameterMinFeet = diameterMinFeet;
        this.diameterMaxFeet = diameterMaxFeet;
        this.hazardous = hazardous;
        this.epoch = epoch;
        this.velocityKmph = velocityKmph;
        this.velocityMph = velocityMph;
        this.distanceKilometers = distanceKilometers;
        this.distanceMiles = distanceMiles;

    }

    public String getName() {
        return this.name;
    }

    public String getDetailsUrl() {
        return this.detailsUrl;
    }

    public int getDiameterMinMeters() {
        return this.diameterMinMeters;
    }

    public int getDiameterMaxMeters() {
        return this.diameterMaxMeters;
    }

    public int getDiameterMinFeet() {
        return this.diameterMinFeet;
    }

    public int getDiameterMaxFeet() {
        return this.diameterMaxFeet;
    }

    public boolean isHazardous() {
        return this.hazardous;
    }

    public int getEpoch() {
        return this.epoch;
    }

    public int getVelocityKmph() {
        return this.velocityKmph;
    }

    public int getVelocityMph() {
        return this.velocityMph;
    }

    public int getDistanceKilometers() {
        return this.distanceKilometers;
    }

    public int getDistanceMiles() {
        return this.distanceMiles;
    }

    public int getDiameterMin(String units) {
        if(units.equals("feet")) {
            return getDiameterMinFeet();
        }
        return getDiameterMinMeters();
    }

    public int getDiameterMax(String units) {
        if(units.equals("feet")) {
            return getDiameterMaxFeet();
        }
        return getDiameterMaxMeters();
    }

    public int getVelocityMetric(boolean metric) {
        if(!metric) {
            return getVelocityMph();
        }
        return getVelocityKmph();
    }

    public int getDistanceMetric(boolean metric) {
        if(!metric) {
            return getDistanceMiles();
        }
        return getDistanceKilometers();
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
           JsonObject diameterObj = listObj.getAsJsonObject("estimated_diameter");
           JsonObject metersObj = diameterObj.getAsJsonObject("meters");
           JsonObject feetObj = diameterObj.getAsJsonObject("feet");
           JsonArray approachArr = listObj.getAsJsonArray("close_approach_data");
           JsonObject approachObj = approachArr.get(0).getAsJsonObject();
           JsonObject velocityObj = approachObj.getAsJsonObject("relative_velocity");
           JsonObject distanceObj = approachObj.getAsJsonObject("miss_distance");

           return new AsteroidData(
                   listObj.getAsJsonPrimitive("name").getAsString(),
                   listObj.getAsJsonPrimitive("nasa_jpl_url").getAsString(),
                   (int)Math.round(metersObj.getAsJsonPrimitive("estimated_diameter_min").getAsDouble()),
                   (int)Math.round(metersObj.getAsJsonPrimitive("estimated_diameter_max").getAsDouble()),
                   (int)Math.round(feetObj.getAsJsonPrimitive("estimated_diameter_min").getAsDouble()),
                   (int)Math.round(feetObj.getAsJsonPrimitive("estimated_diameter_max").getAsDouble()),
                   listObj.getAsJsonPrimitive("is_potentially_hazardous_asteroid").getAsBoolean(),
                   approachObj.getAsJsonPrimitive("epoch_date_close_approach").getAsInt(),
                   (int)Math.round(velocityObj.getAsJsonPrimitive("kilometers_per_hour").getAsDouble()),
                   (int)Math.round(velocityObj.getAsJsonPrimitive("miles_per_hour").getAsDouble()),
                   (int)Math.round(distanceObj.getAsJsonPrimitive("kilometers").getAsDouble()),
                   (int)Math.round(distanceObj.getAsJsonPrimitive("miles").getAsDouble())

           );
       }
   }

}
