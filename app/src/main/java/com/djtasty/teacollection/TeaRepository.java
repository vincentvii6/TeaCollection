package com.djtasty.teacollection;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TeaRepository {
    private TeaDao teaDao;
    private LiveData<List<Tea>> allTea;

    public TeaRepository(Application application) {
        TeaDatabase database = TeaDatabase.getInstance(application);
        teaDao = database.teaDao();
        allTea = teaDao.getAllTea();
    }

    public void insert(Tea tea) {
        new InsertTeaAsycnTask(teaDao).execute(tea);
    }

    public void update(Tea tea) {
        new InsertTeaAsycnTask(teaDao).execute(tea);
    }

    public void delete(Tea tea) {
        new InsertTeaAsycnTask(teaDao).execute(tea);
    }

    public void deleteAllTea() {
        new InsertTeaAsycnTask(teaDao).execute();
    }

    public LiveData<List<Tea>> getAllTea() {
        return allTea;
    }

    private static class InsertTeaAsycnTask extends AsyncTask<Tea, Void, Void> {
        private TeaDao teaDao;

        private InsertTeaAsycnTask(TeaDao teaDao) {this.teaDao = teaDao;}

        @Override
        protected Void doInBackground(Tea... teas) {
            teaDao.insert(teas[0]);
            return null;
        }
    }

    private static class UpdateTeaAsycnTask extends AsyncTask<Tea, Void, Void> {
        private TeaDao teaDao;

        private UpdateTeaAsycnTask(TeaDao teaDao) {this.teaDao = teaDao;}

        @Override
        protected Void doInBackground(Tea... teas) {
            teaDao.update(teas[0]);
            return null;
        }
    }

    private static class DeleteTeaAsycnTask extends AsyncTask<Tea, Void, Void> {
        private TeaDao teaDao;

        private DeleteTeaAsycnTask(TeaDao teaDao) {this.teaDao = teaDao;}

        @Override
        protected Void doInBackground(Tea... teas) {
            teaDao.delete(teas[0]);
            return null;
        }
    }

    private static class DeleteAllTeaAsycnTask extends AsyncTask<Void, Void, Void> {
        private TeaDao teaDao;

        private DeleteAllTeaAsycnTask(TeaDao teaDao) {this.teaDao = teaDao;}

        @Override
        protected Void doInBackground(Void... voids) {
            teaDao.deleteAllTea();
            return null;
        }
    }



}
