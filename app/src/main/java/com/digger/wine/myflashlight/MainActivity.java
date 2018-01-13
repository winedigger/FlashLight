package com.digger.wine.myflashlight;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.noob.noobcameraflash.managers.NoobCameraManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button buttonEnable;
    private Button sendMorse;
    private ImageView imageFlashlight;
    private TextView morseText;
    private static final int CAMERA_REQUEST = 50;
    boolean isFlashon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageFlashlight =  findViewById(R.id.imageFlashlight);
        buttonEnable = findViewById(R.id.buttonEnable);
        sendMorse = findViewById(R.id.sendmorse);
        morseText =  findViewById(R.id.morsetext);
        HashMap<String,int[]> imap;
        imap = buildDictionnary();
        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        buttonEnable.setEnabled(!isEnabled);
        imageFlashlight.setEnabled(isEnabled);
        buttonEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        });
        try {
            NoobCameraManager.getInstance().init(this);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        final HashMap<String, int[]> finalImap = imap;
        sendMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] s = morseText.getText().toString().split("");
                    for (String ch: s)
                    {
                        if(finalImap.containsKey(ch))
                        {
                            for(int caracter: finalImap.get(ch))
                            {
                                flashLightOn();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        flashLightOff();
                                    }
                                }, caracter);
                            }
                        }

                    }
                }

        });
        imageFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    NoobCameraManager.getInstance().toggleFlash();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void flashLightOn() {
            imageFlashlight.setImageDrawable(getResources().getDrawable(R.drawable.off_botton));
        try {
            NoobCameraManager.getInstance().turnOnFlash();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void flashLightOff() {
            imageFlashlight.setImageDrawable(getResources().getDrawable(R.drawable.on_botton));
        try {
            NoobCameraManager.getInstance().turnOffFlash();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case CAMERA_REQUEST :
                if (grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    buttonEnable.setEnabled(false);
                    buttonEnable.setText("Camera Enabled");
                    imageFlashlight.setEnabled(true);
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied for the Camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public HashMap<String, int[]> buildDictionnary()
    {
        HashMap<String,int[]> map = new HashMap<String,int[]>();
        int dot = 200;
        int dah = 3*dot;
        map.put("a", new int[]{dot, dah});
        map.put("b",new int[]{dah, dot,dot,dot});
        map.put("c",new int[]{dah,dot,dah,dot});
        map.put("d",new int[]{dah,dot,dot});
        map.put("e",new int[]{dot});
        map.put("f",new int[]{dot, dot,dah,dot});
        map.put("g",new int[]{dah,dah,dot});
        map.put("h",new int[]{dot, dot,dot,dot});
        map.put("i",new int[]{dot, dot});
        map.put("j",new int[]{dot,dah,dah,dah});
        map.put("k",new int[]{dah,dot,dah});
        map.put("l",new int[]{dot,dah,dot,dot});
        map.put("m",new int[]{dah,dah});
        map.put("n",new int[]{dah,dot});
        map.put("o",new int[]{dah,dah,dah});
        map.put("p",new int[]{dot,dah,dah,dot});
        map.put("q",new int[]{dah,dah,dot,dah});
        map.put("r",new int[]{dot,dah,dot});
        map.put("s",new int[]{dot,dot,dot});
        map.put("t",new int[]{dah});
        map.put("u",new int[]{dot,dot,dah});
        map.put("v",new int[]{dot,dot,dot,dah});
        map.put("w",new int[]{dot,dah,dah});
        map.put("x",new int[]{dah,dot,dot,dah});
        map.put("y",new int[]{dah,dot,dah,dah});
        map.put("z",new int[]{dah,dah,dot,dot});
        return  map;

    }


}