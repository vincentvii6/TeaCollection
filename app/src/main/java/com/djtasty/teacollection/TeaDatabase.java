package com.djtasty.teacollection;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = Tea.class, version = 1)
public abstract class TeaDatabase extends RoomDatabase {

    private static TeaDatabase instance;

    public abstract TeaDao teaDao();

    public static synchronized TeaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TeaDatabase.class, "tea_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TeaDao teaDao;

        private PopulateDbAsyncTask(TeaDatabase db) {
            teaDao = db.teaDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            teaDao.insert(new Tea("Name 1", "Type 1", 1));
            teaDao.insert(new Tea("Name 2", "Type 2", 2));
            teaDao.insert(new Tea("Name 3", "Type 3", 3));
            return null;
        }
    }

}
