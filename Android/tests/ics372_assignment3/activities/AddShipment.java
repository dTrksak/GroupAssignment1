package edu.metrostate.ics372_assignment3.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.metrostate.ics372_assignment3.activities.R;
import edu.metrostate.ics372_assignment3.data.Warehouse;
import edu.metrostate.ics372_assignment3.fileio.RecoverData;

public class AddShipment extends AppCompatActivity {

    private EditText warehouseID;
    private EditText shipmentID;
    private EditText shipmentMethod;
    private EditText weightIP;
    private TextView result;
    private Button add;
    private Button remove;
    private String message;
    private MainActivity mainActivity = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipment);

        warehouseID = findViewById(R.id.warehouseId);
        shipmentID = findViewById(R.id.shipmentId);
        shipmentMethod = findViewById(R.id.shipmentMethod);
        weightIP = findViewById(R.id.weight);
        result = findViewById(R.id.result);
        add = (Button) findViewById(R.id.addButton);
        remove = (Button) findViewById(R.id.removeButton);

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                if(warehouseID.length() == 0 || shipmentID.length() == 0 || shipmentMethod.length() == 0 || weightIP.length() == 0) {
                    result.setText("Incomplete Information please enter:\n");
                    if (warehouseID.length() == 0) {
                        result.append("\t\tWarehouse Id\n");
                    }
                    if (shipmentID.length() == 0) {
                        result.append("\t\tShipment Id\n");
                    }
                    if (shipmentMethod.length() == 0) {
                        result.append("\t\tShipment Method\n");
                    }
                    if (weightIP.length() == 0) {
                        result.append("\t\tShipment Weight");
                    }
                }else {

                    String wareID = ((TextView)AddShipment.this.findViewById(R.id.warehouseId)).getText().toString();
                    String shipID = ((TextView)AddShipment.this.findViewById(R.id.shipmentId)).getText().toString();
                    String shipMethod = ((TextView)AddShipment.this.findViewById(R.id.shipmentMethod)).getText().toString();
                    Warehouse w = new MainActivity().wareIn.getWarehouse(wareID);



                    if (new MainActivity().wareIn.getWarehouse(wareID) == null) {
                        result.setText("Warehouse " + wareID + " does not exists, please create warehouse before adding shipments.");
                    } else {
                        String warehouseName = w.getWarehouseName();
                        Long time = System.currentTimeMillis();
                        float weight = Float.valueOf(weightIP.getText().toString());

                        mainActivity.wareIn.addShipment(wareID, warehouseName, shipID, shipMethod, weight, time);
                        result.setText(mainActivity.wareIn.getMessage());

                        //Save the data
                        try {
                            RecoverData.saveData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });

        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String message;

                if(warehouseID.length() == 0 || shipmentID.length() == 0) {
                    result.setText("Incomplete information please enter:\n");
                    if (warehouseID.length() == 0) {
                        result.append("\tWarehouse Id\n");
                    }
                    if (shipmentID.length() == 0) {
                        result.append("\tShipment Id");
                    }
                }else {
                    String wareID = ((TextView)AddShipment.this.findViewById(R.id.warehouseId)).getText().toString();
                    String shipID = ((TextView)AddShipment.this.findViewById(R.id.shipmentId)).getText().toString();

                    mainActivity.wareIn.removeShipment(wareID, shipID);
                    result.setText(mainActivity.wareIn.getMessage());

                    //Save the data
                    try {
                        RecoverData.saveData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


    }

}