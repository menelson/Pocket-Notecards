package com.nelson.flashcards.study;

/**
 * Created by Mike on 9/11/2014.
 */
public class FileItem implements Comparable<FileItem> {

    private String name;
    private String image;
    private String path;

    public FileItem(String name, String image, String path) {
        this.name = name;
        this.image = image;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath(){
        return path;
    }

    public String getImage() {
        return image;
    }

    public int compareTo(FileItem o){
        if(this.name != null)
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        else
            throw new IllegalArgumentException();
    }
}
