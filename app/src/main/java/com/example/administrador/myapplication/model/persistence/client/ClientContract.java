package com.example.administrador.myapplication.model.persistence.client;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientContract {

    public static final String TABLE = "client";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PHONE = "phone";
    public static final String ZIP = "zip";
    public static final String STREET = "street";
    public static final String STREET_NAME = "street_name";
    public static final String NEIGHBORHOOD = "neighborhood";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String GENRE = "genre";
    public static final String MONEY = "money";
    public static final String ID = "id";
    public static final String[] COLUMNS = { ID, NAME, AGE, PHONE, ZIP, STREET, STREET_NAME, NEIGHBORHOOD, CITY, STATE, GENRE, MONEY };

    public static String getSqlCreateTable(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(ZIP + " TEXT, ");
        sql.append(STREET + " TEXT, ");
        sql.append(STREET_NAME + " TEXT, ");
        sql.append(NEIGHBORHOOD + " TEXT, ");
        sql.append(CITY + " TEXT, ");
        sql.append(STATE + " TEXT, ");
        sql.append(GENRE + " INTEGER, ");
        sql.append(MONEY + " INTEGER ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Client client){
        ContentValues values = new ContentValues();
        values.put(ClientContract.ID, client.getId());
        values.put(ClientContract.NAME, client.getName());
        values.put(ClientContract.AGE, client.getAge());
        values.put(ClientContract.PHONE, client.getPhone());
        values.put(ClientContract.ZIP, client.getZip());
        values.put(ClientContract.STREET, client.getStreet());
        values.put(ClientContract.STREET_NAME, client.getStreetName());
        values.put(ClientContract.NEIGHBORHOOD, client.getNeighborhood());
        values.put(ClientContract.CITY, client.getCity());
        values.put(ClientContract.STATE, client.getState());
        values.put(ClientContract.GENRE, client.isGenre());
        values.put(ClientContract.MONEY, client.isDebt());

        return  values;
    }

    public static Client bind(Cursor cursor){
        if(!cursor.isBeforeFirst() || cursor.moveToNext()){
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(ClientContract.PHONE)));
            client.setZip(cursor.getString(cursor.getColumnIndex(ClientContract.ZIP)));
            client.setStreet(cursor.getString(cursor.getColumnIndex(ClientContract.STREET)));
            client.setStreetName(cursor.getString(cursor.getColumnIndex(ClientContract.STREET_NAME)));
            client.setNeighborhood(cursor.getString(cursor.getColumnIndex(ClientContract.NEIGHBORHOOD)));
            client.setCity(cursor.getString(cursor.getColumnIndex(ClientContract.CITY)));
            client.setState(cursor.getString(cursor.getColumnIndex(ClientContract.STATE)));
            client.setGenre(cursor.getInt(cursor.getColumnIndex(ClientContract.GENRE)) > 0);
            client.setDebt(cursor.getInt(cursor.getColumnIndex(ClientContract.MONEY)) > 0);

            return client;
        }
        return null;
    }

    public static List<Client> bindList(Cursor cursor){

        final List<Client> clients = new ArrayList<>();

        while(cursor.moveToNext()) {
            clients.add(bind(cursor));
        }
        return clients;
    }

}
