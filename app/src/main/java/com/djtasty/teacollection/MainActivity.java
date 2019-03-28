package com.djtasty.teacollection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TEA_REQUEST = 1;

    private TeaViewModel teaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddTea = findViewById(R.id.button_add_tea);
        buttonAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTeaActivity.class);
                startActivityForResult(intent, ADD_TEA_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TeaAdapter adapter = new TeaAdapter();
        recyclerView.setAdapter(adapter);

        teaViewModel = ViewModelProviders.of(this).get(TeaViewModel.class);
        teaViewModel.getAllTea().observe(this, new Observer<List<Tea>>() {
            @Override
            public void onChanged(@Nullable List<Tea> teas) {
                adapter.setTeas(teas);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TEA_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddTeaActivity.EXTRA_NAME);
            String type = data.getStringExtra(AddTeaActivity.EXTRA_TYPE);
            int quantity = data.getIntExtra(AddTeaActivity.EXTRA_QUANTITY, 1);

            Tea tea = new Tea(name, type, quantity);
            teaViewModel.insert(tea);

            Toast.makeText(this, "Tea Saved", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Tea Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}



















