package com.example.hms;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ExampleAsyncTask extends AsyncTask<Void, Void, Void> {
    private final Context context;

    // Constructor to receive the context
    public ExampleAsyncTask(Context context) {
        this.context = context;
    }

    // This method will be executed in the background thread
    @Override
    protected Void doInBackground(Void... voids) {
        // Perform time-consuming task here
        // For example, simulate a task that takes 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // This method will be executed on the UI thread after doInBackground
    @Override
    protected void onPostExecute(Void aVoid) {
        // Update UI or perform any post-execution tasks here
        // For example, show a toast indicating task completion
        Toast.makeText(context, "Task completed!", Toast.LENGTH_SHORT).show();
    }
}


