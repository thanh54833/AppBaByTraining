package com.example.thanh.appbabytraining.main.object;

import java.io.File;

public class IteamPassenger {
    private File pathImage;
    private String name;

    public IteamPassenger(File pathImage, String name) {
        this.pathImage = pathImage;
        this.name = name;
    }

    public File getPathImage() {
        return pathImage;
    }

    public void setPathImage(File pathImage) {
        this.pathImage = pathImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
