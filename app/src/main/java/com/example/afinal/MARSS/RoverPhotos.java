package com.example.afinal.MARSS;

//Information of picture taken
//Picture of photo, photo id, sol/mars day picture was taken,
//eath day picture taken on, name of camera used to take the picture
public class RoverPhotos {
    private int id;
    private int sol;
    private RoverCamera camera;
    private String img_src;
    private String earth_date;
    private RoverInfo rover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public RoverCamera getCamera() {
        return camera;
    }

    public void setCamera(RoverCamera camera) {
        this.camera = camera;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getEarth_date() {
        return earth_date;
    }

    public void setEarth_date(String earth_date) {
        this.earth_date = earth_date;
    }

    public RoverInfo getRover() {
        return rover;
    }

    public void setRover(RoverInfo rover) {
        this.rover = rover;
    }
}
