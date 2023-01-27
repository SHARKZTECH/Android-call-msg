package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button call,send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        call=findViewById(R.id.call);
        send=findViewById(R.id.sendMsg);

        runTimPerm();
    }

    public void call(){
        call.setOnClickListener(view -> {
//            String phoneNumber = "tel:" + "1234567890";
//            Intent dial = new Intent(Intent.ACTION_DIAL);
//            dial.setData(Uri.parse(phoneNumber));
//            startActivity(dial);

            String phoneNumber = "tel:" + "1234567890";
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(phoneNumber));
            startActivity(callIntent);

        });
    }
    public void sendMsg(){
        send.setOnClickListener(view -> {
            String phoneNumber = "1234567890";
            String message = "This is my message text.";

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        });
    }

    public void runTimPerm(){
        Dexter.withContext(getApplicationContext())
                .withPermissions(Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                         sendMsg();
                         call();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}