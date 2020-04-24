package edu.metrostate.ics372_androidstart_master;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class RemoveShipment extends AppCompatActivity {

    private EditText warehouseID;
    private EditText shipmentID;
    private EditText shipmentMethod;
    private EditText weightIP;
    private TextView result;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_shipment);


        warehouseID = findViewById(R.id.warehouseId);
        shipmentID = findViewById(R.id.shipmentId);
        shipmentMethod = findViewById(R.id.shipmentMethod);
        weightIP = findViewById(R.id.weight);
        result = findViewById(R.id.result);
        done = (Button) findViewById(R.id.doneButton);

        final String wareID = warehouseID.getText().toString();
        final String shipID = shipmentID.getText().toString();

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
                else if(handle.getWarehouse(wareID) == null) {
                    message = "Warehouse " + wareID + " does not exits, please double check the Warehouse ID";
                    result.setText(message);
                }
                else {
                    Shipment s;
                    s = w.removeShipment(shipID);
                    if (s != null) {
                        message = "Shipment " + shipID + " successfully removed!";
                        result.setText(message);
                        try {
                            RecoverData.saveData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        message = "Shipment " + shipID + " not found in Warehouse " + wareID + " please double check your input";
                        result.setText(message);
                    }
                }
            }

        });
    }
}