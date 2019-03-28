package com.djtasty.teacollection;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TeaViewModel extends AndroidViewModel {
    private TeaRepository repository;
    private LiveData<List<Tea>> allTea;

    public TeaViewModel(@NonNull Application application) {
        super(application);
        repository = new TeaRepository(application);
        allTea = repository.getAllTea();
    }

    public void insert(Tea tea) {
        repository.insert(tea);
    }

    public void update(Tea tea) {
        repository.update(tea);
    }
    public void delete(Tea tea) {
        repository.delete(tea);
    }

    public void deleteAllTea() {
        repository.deleteAllTea();
    }


    public LiveData<List<Tea>> getAllTea() {
        return allTea;
    }
}
