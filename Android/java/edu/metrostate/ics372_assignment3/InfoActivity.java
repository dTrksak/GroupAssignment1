
package edu.metrostate.ics372_androidstart_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private TextView data;
    private EditText warehouseID;
    private Button wareButton;
    private Button addRemoveShipment;
    private Button printAll;
    private Button printOne;
    private Button wareName;
    private WarehouseHandler handle = WarehouseHandler.getInstance();
    private InputHandler ihandle = new InputHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        data = (TextView) findViewById(R.id.data);
        wareButton = (Button) findViewById(R.id.addWarehouse);
        wareName = (Button) findViewById(R.id.getWarehouseName);
        addRemoveShipment = (Button) findViewById(R.id.addShipment);
        printAll = (Button) findViewById(R.id.printAll);
        printOne = (Button) findViewById(R.id.printOne);
        warehouseID = (EditText) findViewById(R.id.printOneWarehouse);

        wareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ware_house = ((TextView)InfoActivity.this.findViewById(R.id.Warehouse_name)).getText().toString();
                String ware_name = ((TextView)InfoActivity.this.findViewById(R.id.Warehouse_nam)).getText().toString();

                Warehouse x = InfoActivity.this.createWarehouse(ware_house,ware_name);
            }
        });


        addRemoveShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRemoveShipment();
            }

        });


        printAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printAll();
            }
        });

        printOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String warehouseId= ((TextView)InfoActivity.this.findViewById(R.id.printOneWarehouse)).getText().toString();
                printOne(warehouseId);
            }
        });

        wareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setText("Warehouse added");
                addWarehouse();
            }

        });

    }


    private void printOne(String warehouseId) {
        Warehouse w = new MainActivity().wareIn.getWarehouse(warehouseId);
        if(w != null) {
            List<Shipment> printData = w.getShipmentList();
            if (printData.size() == 0) {
                data.setText("Warehouse does not contain any shipments at this time");
            } else {
                ihandle.showData(printData, warehouseId, data);
            }
        }else{
            data.setText("Warehouse does not exist please double check warehouse Id");
        }
        data.setText("Warehouse Doesnt Exist");
        data.setText("");
    }

    private void printAll() {
        List<Warehouse> printData = new ArrayList<>();
        printData = new MainActivity().wareIn.getAllWarehouses();
        data.setText("");
        ihandle.showAllData(printData, data);

    }



    private void addRemoveShipment() {
        Intent intentAdd = new Intent(this, AddShipment.class);
        startActivity(intentAdd);
    }

    private void removeShipment() {
        Intent intentRemove = new Intent(this, RemoveShipment.class);
        startActivity(intentRemove);
    }

    public Warehouse createWarehouse(String name, String id)
    {
        Warehouse x = new  MainActivity().wareIn.addWarehouse(name,id);

        //   Snackbar.make(findViewById(R.id.textView), R.string.email_sent, Snackbar.LENGTH_SHORT).show();


        //   Snackbar.make(findViewById(R.id.textView), R.string.exists, Snackbar.LENGTH_SHORT).show();



        return x;
    }

    private void addWarehouse(){
        Warehouse w = new MainActivity().wareIn.addWarehouse("1234", "Shakopee");
        Shipment s = new MainActivity().wareIn.addShipment("1234", "Shakopee", "New1", "air", (float) 235.1, System.currentTimeMillis());
    }


}

