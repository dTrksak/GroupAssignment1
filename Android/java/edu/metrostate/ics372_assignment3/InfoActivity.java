package edu.metrostate.ics372_assignment3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    private Button wareButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        wareButton = findViewById(R.id.wareButton);

        wareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ware_house = ((TextView)InfoActivity.this.findViewById(R.id.Warehouse_name)).getText().toString();
                String ware_name = ((TextView)InfoActivity.this.findViewById(R.id.Warehouse_nam)).getText().toString();

                Warehouse x = InfoActivity.this.createWarehouse(ware_house,ware_name);
            }
        });

     //   ArrayAdapter<Shipment> ships = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, x.)

    }

    public Warehouse createWarehouse(String name, String id)
    {
            Warehouse x = new  MainActivity().wareIn.addWarehouse(name,id);

         //   Snackbar.make(findViewById(R.id.textView), R.string.email_sent, Snackbar.LENGTH_SHORT).show();


         //   Snackbar.make(findViewById(R.id.textView), R.string.exists, Snackbar.LENGTH_SHORT).show();



        return x;
    }

}
