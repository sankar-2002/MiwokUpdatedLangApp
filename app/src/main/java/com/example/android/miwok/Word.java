package com.example.android.miwok;

public class Word {

    private String mDefaultTranslation;  //default translation for words... //these are all the properties that our custom class will have...
    private String mMiwokTranslation; //miwok translation for words...
    private int mImageResourceId = NO_IMAGE_PROVIDED; //image resource id for the words...
    private static final int NO_IMAGE_PROVIDED = -1; //declaring a constant
    private int mAudioResourceId; //audio resource id for the word...

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) { //creating a new  constructor...
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation,int ImageResourceId,int audioResourceId) { //creating a new  constructor which will take image id too...i.e 3 inputs...constructor overloading..
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = ImageResourceId;
        mAudioResourceId = audioResourceId;
    }

    //get the Default translation of word....  using getters and setters..

    public String getmDefaultTranslation() { //getter methods...
        return mDefaultTranslation;
    }

    //get the miwok translation...

    public  String getmMiwokTranslation() {
        return  mMiwokTranslation;
    }

    //return the image resource id of the word...

    public int getmImageResourceId() { return mImageResourceId; }

    //returns whether or not there is image for this word..

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    //returns the audioResourceId

    public int getmAudioResourceId() {return mAudioResourceId;}

}
