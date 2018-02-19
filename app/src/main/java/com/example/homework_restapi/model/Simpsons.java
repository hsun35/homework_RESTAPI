package com.example.homework_restapi.model;

import java.util.List;

/**
 * Created by hefen on 2/18/2018.
 */

public class Simpsons {
    String name;
    String description;
    String imageUrl;
    public static List<Simpsons> simpsons;

    public Simpsons(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
