package edu.metrostate.ics372_androidstart_master;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
                    view.setText("Incomplete Information please enter:\n");
                    if (warehouseID.length() == 0) {
                        view.append("\t\tWarehouse ID\n");
                    }
                    if (warehouseName.length() == 0) {
                        view.append("\t\tWarehouse name\n");
                    }
                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    String ware_name = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareName)).getText().toString();
                    mainActivity.wareIn.addWarehouse(ware_id, ware_name);
                    view.setText(mainActivity.wareIn.getMessage());
                }
            }

        });

        removeWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0) {
                    view.setText("Incomplete Information please enter: \n Warehouse ID");

                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    mainActivity.wareIn.removeWarehouse(ware_id);
                    view.setText(mainActivity.wareIn.getMessage());
                }
            }
        });

        enableWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0) {
                    view.setText("Incomplete Information please enter: \n Warehouse ID");

                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    mainActivity.wareIn.setWarehouseReceipt(ware_id, true);
                    view.setText(mainActivity.wareIn.getMessage());
                }
            }
        });

        disableWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0) {
                    view.setText("Incomplete Information please enter: \n Warehouse ID");

                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    mainActivity.wareIn.setWarehouseReceipt(ware_id, false);
                    view.setText(mainActivity.wareIn.getMessage());
                }
            }
        });

        printWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(warehouseID.length() == 0) {
                    view.setText("Incomplete Information please enter: \n Warehouse ID");

                }else{

                    String ware_id = ((TextView)InfoActivity.this.findViewById(R.id.wareInfoWareID)).getText().toString();
                    mainActivity.wareIn.showData(ware_id, view);
                }
            }
        });


    }


}
