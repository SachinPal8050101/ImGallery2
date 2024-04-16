package com.example.imgallery;

public class GalleryModel {
    private String image_name;
    private String image_id;
    public GalleryModel(String image_name, String image_id ){
        this.image_name = image_name;
        this.image_id = image_id;

    }

    public String getImageId(){
        return this.image_id;
    }
    public void setImageId(String image_id){
         this.image_id = image_id;
    }

    public void setImageName(String image_name){
        this.image_name = image_name;
    }
    public String getImageName(){
       return this.image_name;
    }

}
