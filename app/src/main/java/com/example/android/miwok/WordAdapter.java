package com.example.android.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

//default adapter works only with one textView..therefore we'll create custom adapter
public class WordAdapter  extends ArrayAdapter<Word> { //this is the custom adapter created which will inherit the parent arrayAdapter...
                                                        //this custom adapter will provide layout for each listItem based on a data source which is the list of objects..

    private  int mColorResourceId; //resource id for background colours of this list items..


    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) { //creating a constructor ...
        //here we initialize arrayAdapters internal storage for context and the list..
        //the second argument is used when the arrayAdapter is populating a single textView..
        //because this is the custom adapter for 2 textviews and the adapter is not going to use this second argument so here it can be any value here we used 0
        super(context,0,words);
        mColorResourceId = colorResourceId;
    }


    @Override    //superclass ke ek method ko overide kar rahe hai...
    public View getView(int position, View convertView, ViewGroup parent) {
        //checking whether the existing view is being reused otherwise inflating the view..
        View listItemView = convertView;
        if(listItemView == null) {  //inflating a new one completely from the scratch...
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position); //for locating and positioning the object in the list

        //finding the textView in the list_item.xml layout with id version name..

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        //get the miwokTranslation from current word object and set this text on the miwokTextView
        miwokTextView.setText(currentWord.getmMiwokTranslation());


                   //repeating the same for default textView...
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        //get the default translation from current word object and set this text on the defaultTextView..
       defaultTextView.setText(currentWord.getmDefaultTranslation());


       //find the ImageView in the list_item.xml layout with the Id image...
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        //set the imageview to the image resource specified in the current word...
        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getmImageResourceId());

            imageView.setVisibility(View.VISIBLE);  //Make sure the view is visible...
        }
        else { //otherwise hide the ImageView (set Visibility to gone)
            imageView.setVisibility(View.GONE);
        }

        //set the theme color for list item...
        View textContainer = listItemView.findViewById(R.id.text_container);
        //find the color that resource id maps to....
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        //set the background color of text container view...
        textContainer.setBackgroundColor(color);

       return listItemView; //returning the whole list item layout containing 2 textviews so that it can be shown in the list view..




    }
}
