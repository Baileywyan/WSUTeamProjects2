package edu.wright.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar cal = Calendar.getInstance();
        final double tempNum = 80;
        final double pHNum = 6;

        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy KK:mm aaa");
        final String currentDate = df.format(cal.getTime());

        TextView textViewDate = findViewById(R.id.textView);
        textViewDate.setText(currentDate);


        final TextView temp = findViewById(R.id.textView5);
        temp.setText(tempNum + "\u00B0F");

        final TextView pH = findViewById(R.id.textView6);
        pH.setText(String.valueOf(pHNum));


        final TextView viewUpdate = findViewById(R.id.textView7);

        readFile(viewUpdate);

        Button connect = findViewById(R.id.button);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        Button scan = findViewById(R.id.button2);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    File path = getApplicationContext().getFilesDir();
                    FileOutputStream outputStream = new FileOutputStream(new File(path, "UpdateLog.txt"));
                    outputStream.write(currentDate.getBytes());
                    outputStream.close();

                    readFile(viewUpdate);
                }
                catch(IOException e){
                    viewUpdate.setText("Failure to update");
                }

                scanTank(tempNum, pHNum, temp, pH);

            }
        });
    
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("myChannel", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myChannel")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("Alert")
                .setContentText("Check the tank!");

            notification = builder.build();

            notificationManagerCompat = NotificationManagerCompat.from(this);
        }

    public void pushAlert()
    {
        System.out.println("pushAlert()");
        notificationManagerCompat.notify(1, notification);

    }

    public void openActivity2()
    {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    void readFile(TextView textView)
    {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "UpdateLog.txt");
        byte[] content = new byte[(int)readFrom.length()];

        try{
            FileInputStream inputStream = new FileInputStream(readFrom);
            inputStream.read(content);
            String str = new String(content);
            TextView textViewDate2 = findViewById(R.id.textView7);
            textViewDate2.setText("Last Updated: \n" + str);
        }
        catch(IOException e){
            textView.setText("Last Updated: NA");
        }
    }

    void scanTank(double tempNum, double pHNum, TextView temp, TextView pH)
    {
        DecimalFormat df1 = new DecimalFormat("0.0");
        DecimalFormat df2 = new DecimalFormat("0.00");

        Random rand = new Random();

        double num = rand.nextDouble();
        int pM = rand.nextInt(3);


        if(pM == 1){
            tempNum = tempNum - num;
            temp.setText(df1.format(tempNum) + "\u00B0F");

            pHNum = pHNum - (num * .1);
            pH.setText(df2.format(pHNum));
        }
        else{
            tempNum = tempNum + num;
            temp.setText(df1.format(tempNum) + "\u00B0F");

            pHNum = pHNum + (num * .1);
            pH.setText(df2.format(pHNum));
        }

        if(pHNum > 6.1 || tempNum > 81)
        {
            System.out.println("AHHHH");
            pushAlert();
        }


    }
}
