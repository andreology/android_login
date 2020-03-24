package com.example.assignment_1;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Void, String> {

    // The TextView where we will show results
    private WeakReference<TextView> mTextView;

    // Constructor that provides a reference to the TextView from the WelcomeActivity
    SimpleAsyncTask(TextView tv){
        mTextView = new WeakReference<>(tv);
    }

    protected String doInBackground(Void... voids){
        // Generate a random # b/w 0-10
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the tast take long enough that we have time to rotate the phone while it is running
        int s = n * 200;

        // Sleep for the random amount of time
        try{
            Thread.sleep(s); // in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return a String result
        return "Awake after sleeping for " + s + " milliseconds.";
    }

    /**
     * Does something with the result on the UI thread; in this case updates TextView
     * @param result
     */
    protected  void onPostExecute(String result){
        mTextView.get().setText(result);
    }
}