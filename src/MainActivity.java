package edu.wright.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import java.text.DateFormat;
import java.util.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance();

        String currentDate = DateFormat.getDateInstance().format(cal.getTime());

        TextView textViewDate = findViewById(R.id.textView);
        textViewDate.setText(currentDate);

        TextView viewUpdate = findViewById(R.id.textView7);

        try{
            InputStream inputStream = getAssets().open("UpdateLog.txt");
            int size = inputStream.available();
            byte[] b = new byte[size];
            inputStream.read(b);
            String str = new String(b);
            TextView textViewDate2 = findViewById(R.id.textView7);
            textViewDate2.setText("Last Updated: " + str);
        }
        catch(IOException e){
            viewUpdate.setText("Last Updated: NA");
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}