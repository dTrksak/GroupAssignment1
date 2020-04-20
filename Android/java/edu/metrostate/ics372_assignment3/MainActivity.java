package edu.metrostate.ics372_assignment3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.FileUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int WRITE_STORAGE_PERMISSION_REQUEST = 5;
    private List<Shipment> dataList = new ArrayList<Shipment>();
    private WarehouseApplication application;
    private TextView scroller;
    private Button jsonButton;
    private Button exportButton;
    private Button xmlButton;
    private Button infoButton;

    /**
     * Creates the view for the application
     * @param savedInstanceState saved state information for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application = (WarehouseApplication)getApplication();


        jsonButton = findViewById(R.id.jsonButton);
        exportButton = findViewById(R.id.exportButton);
        xmlButton = findViewById(R.id.xmlButton);
        infoButton = findViewById(R.id.infoButton);
        scroller = findViewById(R.id.scroll);



        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startInfo();
            }
        });

        xmlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        exportButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                exportJson();
            }
                                        });


        jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getJson();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });


        //  Check Storage Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        }

    }

    private void exportJson() {
        JsonHandler jsonOut = new JsonHandler();
        WarehouseHandler wareOut = new WarehouseHandler();
        try {
            ArrayList <Shipment> outList = new ArrayList<>();
            outList.addAll(dataList);
            wareOut.addShipmentList(dataList);
            outList = wareOut.getAllWarehouseShipments();

            Log.d("OUT", outList.toString());
            jsonOut.shipmentToJson(outList, "testFile");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void getJson() throws IOException {
        JsonHandler jsonIn = new JsonHandler();
        WarehouseHandler wareIn =WarehouseHandler.getInstance();
        String str;
        try {
            InputStream is = getAssets().open("example.json");
            str = FileOperations.getFileContents(is);


            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(str);




            //json = new String(buffer, "UTF-8");
               dataList = jsonIn.jsonToShipment(json);
               wareIn.addShipmentList(dataList);
               // JsonArray jsonArray = new JsonArray(json);
                dataList = wareIn.getAllWarehouseShipments();


        }catch(JsonIOException e){
            e.printStackTrace();
        }

        scroller.setText(dataList.toString());
        scroller.setMovementMethod(new ScrollingMovementMethod());
    }






    private void startInfo() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }


    /**
     * Indicates when the user has responded to a permission request
     * @param requestCode The request code
     * @param permissions The permissions requested
     * @param grantResults The result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_STORAGE_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission required, closing application", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }

}
