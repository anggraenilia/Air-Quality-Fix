package com.blogspot.mekatronika.airmonitoringquality;

import com.google.firebase.database.Exclude;

public class Upload {
    private String nameImage;
    private String imageUrl;
    private String bKey;

    public Upload()
    {

    }

    public Upload(String name, String url)
    {
        if(name.trim().equals(""))
        {
            name="No Name";
        }
        nameImage=name;
        imageUrl=url;
    }

    public String getName()
    {
        return nameImage;
    }

    public void setName(String name){
        nameImage=name;
    }
    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String url)
    {
        imageUrl=url;
    }

    @Exclude
    public String getKey() {
        return bKey;
    }

    @Exclude
    public void setKey(String key) {
        bKey = key;
    }

}
