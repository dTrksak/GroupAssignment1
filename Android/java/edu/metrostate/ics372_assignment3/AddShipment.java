package edu.metrostate.ics372_androidstart_master;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddShipment extends AppCompatActivity {

    private EditText warehouseID;
    private EditText shipmentID;
    private EditText shipmentMethod;
    private EditText weightIP;
    private TextView result;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipment);


        warehouseID = findViewById(R.id.warehouseId);
        shipmentID = findViewById(R.id.shipmentId);
        shipmentMethod = findViewById(R.id.shipmentMethod);
        weightIP = findViewById(R.id.weight);
        result = findViewById(R.id.result);
        done = (Button) findViewById(R.id.doneButton);

        final String wareID = warehouseID.getText().toString();
        final String shipID = shipmentID.getText().toString();
        final String shipMethod = shipmentMethod.getText().toString();



        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                WarehouseHandler handle = WarehouseHandler.getInstance();
                Warehouse w = handle.getWarehouse(wareID);
                String message;
                if(warehouseID.length() == 0){
                    result.setText("Please enter Warehouse Id");
                }
                else if(shipmentID.length() == 0){
                    result.setText("Please enter Shipment Id");
                }
                else if (shipmentMethod.length() == 0){
                    result.setText("Please enter Shipment Method");
                }
                else if (weightIP.length() == 0){
                    result.setText("Please enter Shipment Weight");
                }
                else if(handle.getWarehouse(wareID) == null) {
                    message = "Warehouse " + wareID + " does not exits, please create warehouse before adding shipments.";
                    result.setText(message);
                }
                else if(w.getAvailability() == false){
                    message = "Warehouse " + wareID + " is not receiving freights at this time.";
                    result.setText(message);
                }
                else {
                    String warehouseName = w.getWarehouseName();
                    Long time = System.currentTimeMillis();
                    float weight = Float.valueOf(weightIP.getText().toString());
                    handle.addShipment(wareID, warehouseName, shipID, shipMethod, weight, time);
                    result.setText("Shipment added successfully!");
                }
            }

        });





    }
}