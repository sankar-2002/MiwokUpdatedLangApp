/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer; //for sounds...

    private AudioManager mAudioManager; //handles audioFocus when playing a sound file..

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0); //when the focus is received then it will start from the beginning
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() { //interface
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources and if u want You can print a toast message that "finished playing"
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //create and setup the audioManager to request audio focus...

        //creating an arrayList to store english and miwok number words...

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word ("One","lutti",R.drawable.number_one, R.raw.number_one));    //creating an obj and adding it in the list...
        words.add(new Word ("Two","Otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word ("Three","Tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word ("Four","Oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word ("Five","Massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word ("Six","Temmokka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word ("Seven","Kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word ("Eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word ("Nine","Wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word ("Ten","Na'aacha",R.drawable.number_ten,R.raw.number_ten));


                                          //displaying the data in the form of list using adapters...


        WordAdapter adapter = //stores our data and translates it into a list view and powers the listview..
                new WordAdapter(this,words,R.color.category_numbers);  //calling the constructor in word.java and passing the views...
        ListView listView = (ListView) findViewById(R.id.list); //finding the view(activity_numbers) by id and storing it in the listView...
        listView.setAdapter(adapter); //connecting the listView with the adapter..

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position); //this is for storing the position of words for sending it to mediaplayer //due to this line i declared arraylist as final

                releaseMediaPlayer(); //Release the mediaPlayer If it currently exist because we are about to play a different sound file..

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT); //returns audio focus request failed or audio focus request granted..

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                     //We have audioFocus Now...


                mMediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getmAudioResourceId());
                mMediaPlayer.start(); //no need to call prepare() as the create() does the same


                //setup a listener on mediaPlayer so that once audio stops we can release the memory used by mediaPlayer before the next audio starts..
                mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }

            }
        });






                              //conventional way of displaying of data in the screen with the help of loops...

//        LinearLayout rootView =(LinearLayout)findViewById(R.id.rootView); //view ko id se dhunda and variable mai save karadiya.. //parent view
//
//        for(int index = 0; index<words.size(); index++) {
//            TextView wordView = new TextView(this); //childView banaya namely wordView..
//            wordView.setText(words.get(index)); //0 index vaale text ko print karwaya..
//            rootView.addView(wordView); //childView ko Parent View se jod diya..


    }

    @Override
    protected void onStop() {  //related to activity lifecycle {when activity is stopped}
        super.onStop();
        releaseMediaPlayer(); //when the activity is stopped,release the mediaPlayer Resources because we won;t be playing anymore sounds...
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;


             // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener); //abandon audiofocus when playback completes...we have released the audiofocus as we no longer need it
        }
    }
}
