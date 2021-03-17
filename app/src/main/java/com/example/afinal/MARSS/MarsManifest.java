package com.example.afinal.MARSS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarsManifest {
    @SerializedName("photo_manifest")
    @Expose
    private RoverPhotoManifest manifest;

    public RoverPhotoManifest getManifest() {
        return manifest;
    }

    public void setManifest(RoverPhotoManifest manifest) {
        this.manifest = manifest;
    }
}
