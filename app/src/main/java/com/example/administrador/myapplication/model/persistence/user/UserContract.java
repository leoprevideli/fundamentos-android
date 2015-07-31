package com.example.administrador.myapplication.model.persistence.user;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.administrador.myapplication.model.entities.User;

public class UserContract {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ADMIN = "admin";
    public static final String[] COLUMNS = { ID, USERNAME, PASSWORD };

    public static String getSqlCreateTable(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(USERNAME + " TEXT, ");
        sql.append(PASSWORD + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static String insertIntoUserTable(){
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + ", ");
        sql.append(USERNAME + ", ");
        sql.append(PASSWORD);
        sql.append(" ) ");
        sql.append(" VALUES ");
        sql.append(" ( ");
        sql.append(1 + ", ");
        sql.append("'" +ADMIN +"'"+ ", ");
        sql.append("'" +ADMIN +"'");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(UserContract.ID, user.getId());
        values.put(UserContract.USERNAME, user.getUsername());
        values.put(UserContract.PASSWORD, user.getPassword());
        return  values;
    }

    public static User bind(Cursor cursor){
        if(!cursor.isBeforeFirst() || cursor.moveToNext()){
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(UserContract.USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.PASSWORD)));

            return user;
        }
        return null;
    }

}
