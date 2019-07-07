package com.example.bahroel.sigapbencana.network.model;

public class Notif {
    String image;
    String status;
    String time;
    String description;

    public Notif(String image, String status, String time, String description) {
        this.image = image;
        this.status = status;
        this.time = time;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
