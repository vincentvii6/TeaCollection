package com.djtasty.teacollection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TEA_REQUEST = 1;
    public static final int EDIT_TEA_REQUEST = 2;


    private TeaViewModel teaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddTea = findViewById(R.id.button_add_tea);
        buttonAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTeaActivity.class);
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                teaViewModel.delete(adapter.getTeaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Tea Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tea tea) {
                Intent intent = new Intent(MainActivity.this, AddEditTeaActivity.class);
                intent.putExtra(AddEditTeaActivity.EXTRA_ID, tea.getId());
                intent.putExtra(AddEditTeaActivity.EXTRA_NAME, tea.getName());
                intent.putExtra(AddEditTeaActivity.EXTRA_TYPE, tea.getType());
                intent.putExtra(AddEditTeaActivity.EXTRA_QUANTITY, tea.getQuantity());
                startActivityForResult(intent, EDIT_TEA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TEA_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditTeaActivity.EXTRA_NAME);
            String type = data.getStringExtra(AddEditTeaActivity.EXTRA_TYPE);
            int quantity = data.getIntExtra(AddEditTeaActivity.EXTRA_QUANTITY, 1);

            Tea tea = new Tea(name, type, quantity);
            teaViewModel.insert(tea);

            Toast.makeText(this, "Tea Saved", Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == EDIT_TEA_REQUEST && resultCode == RESULT_OK) {
           int id = data.getIntExtra(AddEditTeaActivity.EXTRA_ID, -1);

           if (id == -1) {
               Toast.makeText(this, "Tea can't be updated", Toast.LENGTH_SHORT).show();
               return;
           }

            String name = data.getStringExtra(AddEditTeaActivity.EXTRA_NAME);
            String type = data.getStringExtra(AddEditTeaActivity.EXTRA_TYPE);
            int quantity = data.getIntExtra(AddEditTeaActivity.EXTRA_QUANTITY, 1);

            Tea tea = new Tea(name, type, quantity);
            tea.setId(id);
            teaViewModel.update(tea);

            Toast.makeText(this, "Tea updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Tea Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_teas:
                teaViewModel.deleteAllTea();
                Toast.makeText(this, "All teas deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}



















