package edu.metrostate.ics372_androidstart_master;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddShipment extends AppCompatActivity {

    private EditText warehouseID;
    private EditText shipmentID;
    private EditText shipmentMethod;
    private EditText weightIP;
    private TextView result;
    private Button add;
    private Button remove;
    private WarehouseHandler handle = WarehouseHandler.getInstance();

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

                String message;
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
                        message = "Warehouse " + wareID + " does not exits, please create warehouse before adding shipments.";
                        result.setText(message);
                    } else if (w.getAvailability() == false) {
                        message = "Warehouse " + wareID + " is not receiving freights at this time.";
                        result.setText(message);
                    } else {
                        String warehouseName = w.getWarehouseName();
                        Long time = System.currentTimeMillis();
                        float weight = Float.valueOf(weightIP.getText().toString());
                        new MainActivity().wareIn.addShipment(wareID, warehouseName, shipID, shipMethod, weight, time);
                        result.setText("Shipment added successfully!");
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
                    Warehouse w = new MainActivity().wareIn.getWarehouse(wareID);

                    if (new MainActivity().wareIn.getWarehouse(wareID) == null) {
                        message = "Warehouse " + wareID + " does not exits, please double check the Warehouse ID";
                        result.setText(message);
                    } else {
                        Shipment s = w.removeShipment(shipID);
                        if (s != null) {
                            message = "Shipment " + shipID + " successfully removed!";
                            result.setText(message);
                            try {
                                RecoverData.saveData();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            message = "Shipment " + shipID + " not found in Warehouse " + wareID + " please double check your input";
                            result.setText(message);
                        }
                    }
                }
            }

        });

    }
}
