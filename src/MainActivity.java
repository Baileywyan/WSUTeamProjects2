package edu.wright.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance();

        String currentDate = DateFormat.getDateInstance().format(cal.getTime());

        TextView textViewDate = findViewById(R.id.textView);
        textViewDate.setText(currentDate);
    }
}