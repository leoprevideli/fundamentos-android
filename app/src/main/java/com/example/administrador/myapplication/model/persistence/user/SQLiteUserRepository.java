package com.example.administrador.myapplication.model.persistence.user;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.model.persistence.DatabaseHelper;
import com.example.administrador.myapplication.util.AppUtil;

public class SQLiteUserRepository implements UserRepository {

    private static SQLiteUserRepository singletonInstance;

    private SQLiteUserRepository(){
        super();
    }

    public static SQLiteUserRepository getInstance(){
        if(SQLiteUserRepository.singletonInstance == null){
            SQLiteUserRepository.singletonInstance = new SQLiteUserRepository();
        }

        return SQLiteUserRepository.singletonInstance;
    }

    @Override
    public User getUser() {

        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, null, null, null, null, null);

        User user = UserContract.bind(cursor);

        db.close();
        helper.close();

        return user;
    }

}