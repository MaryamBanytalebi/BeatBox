package com.example.beatbox.model;

import java.io.File;

public class Sound {

    private String mName;
    private String mAssetPath;
    private Integer msoundId;

    public Sound() {
    }

    public Integer getsoundId() {
        return msoundId;
    }

    public void setsoundId(Integer msoundId) {
        this.msoundId = msoundId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public void setAssetPath(String assetPath) {
        mAssetPath = assetPath;
    }

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] sections = assetPath.split(File.separator);
        String fileNameWithExtension = sections[sections.length-1];
        int lastDotIndex = fileNameWithExtension.lastIndexOf(".");

        mName = fileNameWithExtension.substring(0,lastDotIndex);
    }
}
