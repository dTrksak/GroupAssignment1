package edu.metrostate.ics372_assignment3.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import edu.metrostate.ics372_assignment3.activities.R;

public class InfoActivity extends AppCompatActivity {

    private Button addWarehouse;
    private Button removeWarehouse;
    private Button enableWarehouse;
    private Button disableWarehouse;
    private Button printWarehouse;
    private TextView view;
    private EditText warehouseID;
    private EditText warehouseName;
    private MainActivity mainActivity = new MainActivity();
    private AlertDialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        addWarehouse = findViewById(R.id.wareInfoAddWarehouse);
        removeWarehouse = findViewById(R.id.wareInfoRemove);
        enableWarehouse = findViewById(R.id.enableWarehouse);
        disableWarehouse = findViewById(R.id.disableWarehouse);
        printWarehouse = findViewById(R.id.printOne);
        view = findViewById(R.id.wareInfoView);
        warehouseID = findViewById(R.id.wareInfoWareID);
        warehouseName = findViewById(R.id.wareInfoWareName);

        addWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0 || warehouseName.length() == 0) {
                    if (warehouseID.length() == 0) {
                        toastMe("Incomplete Information please enter: Warehouse ID");
                    }
                    if (warehouseName.length() == 0) {
                        toastMe("Incomplete Information please enter: Warehouse name");
                    }
                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    String ware_name = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareName)).getText().toString();
                    mainActivity.wareIn.addWarehouse(ware_id, ware_name);

                    toastMe(mainActivity.wareIn.getMessage());
                }
            }

        });

        removeWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0) {
                    toastMe("Incomplete Information please enter: Warehouse ID");

                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    mainActivity.wareIn.removeWarehouse(ware_id);
                    toastMe(mainActivity.wareIn.getMessage());
                }
            }
        });

        enableWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0) {
                    toastMe("Incomplete Information please enter: Warehouse ID");

                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    mainActivity.wareIn.setWarehouseReceipt(ware_id, true);
                    toastMe(mainActivity.wareIn.getMessage());
                }
            }
        });

        disableWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0) {
                    toastMe("Incomplete Information please enter: Warehouse ID");

                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    mainActivity.wareIn.setWarehouseReceipt(ware_id, false);
                    toastMe(mainActivity.wareIn.getMessage());
                }
            }
        });

        printWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0) {
                    toastMe("Incomplete Information please enter: Warehouse ID");

                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    mainActivity.wareIn.showData(ware_id, view);
                }
            }
        });
    }

    private void toastMe(String text)
    {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(Color.GREEN);
        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.RED);
        toast.show();
    }
}
