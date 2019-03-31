package com.djtasty.teacollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditTeaActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.djtasty.teacollection.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.djtasty.teacollection.EXTRA_NAME";
    public static final String EXTRA_TYPE =
            "com.djtasty.teacollection.EXTRA_TYPE";
    public static final String EXTRA_QUANTITY =
            "com.djtasty.teacollection.EXTRA_QUANTITY";

    private EditText editTextName;
    private EditText editTextType;
    private NumberPicker numberPickerQuantity;

    // Set fields for user information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tea);

        editTextName = findViewById(R.id.edit_text_name);
        editTextType = findViewById(R.id.edit_text_type);
        numberPickerQuantity = findViewById(R.id.number_picker_quantity);

        numberPickerQuantity.setMinValue(1);
        numberPickerQuantity.setMaxValue(20);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Tea");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextType.setText(intent.getStringExtra(EXTRA_TYPE));
            numberPickerQuantity.setValue(intent.getIntExtra(EXTRA_QUANTITY, 1));
        }
        else {
            setTitle("Add Tea");
        }


    }

    private void saveTea() {
        String name = editTextName.getText().toString();
        String type = editTextType.getText().toString();
        int quantity = numberPickerQuantity.getValue();

        if (name.trim().isEmpty() || type.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a tea name and type", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_TYPE, type);
        data.putExtra(EXTRA_QUANTITY, quantity);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_tea_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_tea:
                saveTea();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
