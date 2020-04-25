package edu.metrostate.ics372_androidstart_master;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    public WarehouseHandler wareIn = WarehouseHandler.getInstance();
    private static final int WRITE_STORAGE_PERMISSION_REQUEST = 5;
    private static final int READ_REQUEST_CODE = 42;
    private  List<Shipment> dataList = new ArrayList<>();
    private WarehouseApplication application;
    private Button printButton;
    private TextView scroller;
    private Button jsonButton;
    private Button exportButton;
    private Button xmlButton;
    private Button infoButton;
    private Button shipInfo;
    private Object IOException;


    /**
     * Creates the view for the application
     *
     * @param savedInstanceState saved state information for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application = (WarehouseApplication) getApplication();

        jsonButton = findViewById(R.id.jsonButton);
        exportButton = findViewById(R.id.exportButton);
        xmlButton = findViewById(R.id.xmlButton);
        infoButton = findViewById(R.id.infoButton);
        scroller = findViewById(R.id.scroll);
        shipInfo = findViewById(R.id.shipmentInfo);
        printButton = findViewById(R.id.printButton);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInfo();
            }
        });

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print();
            }
        });

        shipInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipInfo();
            }
        });

        xmlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    importXML();
                } catch (IOException | ParserConfigurationException | SAXException e) {
                    e.printStackTrace();
                }
            }
        });
        exportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                exportJson();
            }
        });


        jsonButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                fileSearch();


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

    private void print() {
        wareIn.showAllData(scroller);
    }

    private void shipInfo() {
        Intent intent = new Intent(this, AddShipment.class);
        startActivity(intent);
    }

    private void  getJson(Uri input) throws IOException {
        JsonHandler jsonIn = new JsonHandler();
        WarehouseHandler wareIn =WarehouseHandler.getInstance();
        String str;

        try {
            InputStream is = getContentResolver().openInputStream(input);
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




    private void fileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK){

            if(data != null) {

                Uri uri = data.getData();

                try {
                    getJson(uri);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }else{
                scroller.setText("data is null");
            }

        }

    }



    private void importXML() throws IOException, ParserConfigurationException, SAXException {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, READ_REQUEST_CODE );


        /*scroller.setText(dataList.toString());
        scroller.setMovementMethod(new ScrollingMovementMethod());*/
    }


    private void exportJson() {
        JsonHandler jsonOut = new JsonHandler();
        WarehouseHandler wareOut = new WarehouseHandler();

        try {
            ArrayList <Shipment> outList = new ArrayList<>();
            //outList.addAll(dataList);
            wareOut.addShipmentList(dataList);
            outList = wareOut.getAllWarehouseShipments();

            jsonOut.shipmentToJson(outList, "testFile");
            Toast toast=Toast.makeText(this,"Shipments successfully Imported",Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
            scroller.setText("");
        }catch(IOException e){
            e.printStackTrace();
        }
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
