package com.example.myasynctaskapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {
    int[] integers=null;
    ProgressBar indicatorBar;
    TextView statusView, progressView;
    Button progressBtnStart;
    Button progressBtnStop;

    ProgressTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        integers = new int[100];
        for(int i=0;i<100;i++) {
            integers[i] = i + 1;
        }
        indicatorBar = (ProgressBar) findViewById(R.id.indicator);
        statusView = findViewById(R.id.statusView);

        progressView = findViewById(R.id.progressView);

        progressBtnStart = findViewById(R.id.progressBtnStart);


        progressBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new ProgressTask();
                statusView.setText("Статус: " + task.getStatus().toString());
                task.execute();
            }
        });

        progressBtnStop = findViewById(R.id.progressBtnStop);

        progressBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
                statusView.setText("Статус: " + task.getStatus().toString());
            }
        });
    }
    class ProgressTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... unused) {
            statusView.setText("Статус: " + task.getStatus().toString());

            for (int i = 0; i<integers.length;i++) {

                publishProgress(i);
                SystemClock.sleep(300);
                if (isCancelled())
                    return null;
            }
            return(null);
        }
        @Override
        protected void onProgressUpdate(Integer... items) {
            indicatorBar.setProgress(items[0]+1);
            progressView.setText("Прогрес: " + String.valueOf(items[0]+1));
        }
        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(getApplicationContext(), "Закінчено!", Toast.LENGTH_SHORT)
                    .show();
        }
        protected void onCancelled() {

            super.onCancelled();
            Toast toast = Toast.makeText(getBaseContext(),
                    "Зупинено!", Toast.LENGTH_LONG);
            toast.show();
            indicatorBar.setProgress(0);
            progressView.setText("Прогрес: " + String.valueOf(0));
            statusView.setText("Задачу відмінено!");

        }

    }
}