package com.example.eye;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.eye.databinding.ActivityMainBinding;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    AppCompatActivity context;
    ActivityMainBinding binding;
    //permission handling
    private String phone_number = "";
    // control commands as strings
    private static final String ACTIVATE_MSG = "$SDIG,1,1";
    private static final String DEACTIVATE_MSG = "$SDIG,1,0";
    private static final String FRONT_MSG = "$SDIG,3,0$SDIG,4,1";
    private static final String LEFT_MSG = "$SDIG,3,0$SDIG,4,0";
    private static final String RIGHT_MSG = "$SDIG,3,1$SDIG,4,0";
    private static final String REAR_MSG = "$SDIG,3,1$SDIG,4,1";

    //rtsp urls for proboxes ideally we should read them from a json file
    //{  noah_202z": rtsp://10.8.0.58:9001/stream"}
    //url links for rtsp videos
    String noah_202z = "rtsp://10.8.0.58:9001/stream";
    String noah_203z = "rtsp://10.8.0.58:9001/stream";
    String noah_204z = "rtsp://10.8.0.58:9001/stream";
    String noah_205z = "rtsp://10.8.0.58:9001/stream";

    //number plates
    String[] plates = {"KCT202Z", "KCT203Z", "KCT204Z", "KCT205Z", "KCT206Z"};

    // activation numbers

    //0748985155 ,0748779144,0748779211 ,0748985151

    SmsManager smsManager = SmsManager.getDefault();
    VideoView videoView;
    private Object AdapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Spinner spin = (Spinner) findViewById(R.id.editTextPhoneNumber);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spin.setAdapter(adapter);

        binding.editTextPhoneNumber.setAdapter(adapter);

        switch (Arrays.toString(plates)) {
            case "KCT202Z":
                phone_number = "0710854823";
                break;
            case "KCT203Z":
                phone_number = "0710854823";
                break;
            case "KCT204Z":
                phone_number = "0710854823";
                break;
            case "KCT205Z":
                phone_number = "0710854823";
                break;
            case "KCT206Z":
                phone_number = "0710854823";
                break;
        }
        //spin.setOnItemSelectedListener((android.widget.AdapterView.OnItemSelectedListener) this);
        binding.editTextPhoneNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, View view, int i, long l) {

                activate_system();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {

            }
        });

        binding.switch1.setOnClickListener(v -> {
            try {
                SmsManager smgr = SmsManager.getDefault();
                //smgr.sendTextMessage(phone_number, null, ACTIVATE_MSG, null, null);
                activate_system();
                Toast.makeText(context, "Device Activated successfully ", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Sms Failed .Device not activated ", Toast.LENGTH_SHORT).show();

            }

            activate_system();
        });

        //deactivate the system
        binding.buttonDeactivate.setOnClickListener(v -> deactivate_system());

        // switch forward camera
        binding.buttonFront.setOnClickListener(v -> activate_front());

        binding.buttonRear.setOnClickListener(v -> activate_rear());

        binding.buttonRight.setOnClickListener(v -> activate_right());
        // switch left camera
        binding.buttonLeft.setOnClickListener(v -> activate_left());
        //binding.buttonDeactivate.setOnClickListener(v -> stream_video(noah_202z));
        //binding
        //rtspUrl = (EditText) this.findViewById(R.id.editText);
        //videoView = (VideoView) this.findViewById(R.id.rtspVideo);

    }

    // @Override
    public void onItemSelected(int position) {
        Toast.makeText(getApplicationContext(), "Selected User: " + plates[position], Toast.LENGTH_SHORT).show();
    }


    private void activate_system() {
        //  Log.i("my Tag","successfully activated system");
        sendSMS(phone_number.toString(), ACTIVATE_MSG);

    }

    private void deactivate_system() {
        //sendSMS(binding.editTextPhoneNumber.getText().toString(), DEACTIVATE_MSG);

    }

    private void activate_front() {
        //sendSMS(binding.editTextPhoneNumber.getText().toString(), FRONT_MSG);

    }

    private void activate_left() {
        //sendSMS(binding.editTextPhoneNumber.getText().toString(), LEFT_MSG);

    }

    private void activate_right() {
        //sendSMS(binding.editTextPhoneNumber.getText().toString(), RIGHT_MSG);

    }

    // cooorect rear
    private void activate_rear() {
        //sendSMS(binding.editTextPhoneNumber.getText().toString(), REAR_MSG);

    }

    private void sendSMS(String phoneNunber, String smsCommand) {
        Permissions.check(this/*context*/, Manifest.permission.SEND_SMS, null, new
                PermissionHandler() {
                    @Override
                    public void onGranted() {
                        smsManager.sendTextMessage(phoneNunber, null, smsCommand, null, null);
                    }
                });
    }
/*

    //@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDeactivate:

                // select which stream to activate  from the number listed
               // RtspStream(noah_202z);
                break;
        }
    }
*/

    private void stream_video(String rtspUrl) {
        videoView.setVideoURI(Uri.parse(rtspUrl));
        videoView.requestFocus();
        videoView.start();
    }

}
