package com.example.administrador.myapplication.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.example.administrador.myapplication.model.services.CepService;
import com.example.administrador.myapplication.util.FormHelper;

public class ClientPersistActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPhone;
    private EditText editTextCep;
    private EditText editTextTipoLogradouro;
    private EditText editTextLogradouro;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;
    private Switch switchGenre;
    private CheckBox chkDebtor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_client);
        bindFields();
        getParameters();
    }

    private void bindFields() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);//Coloca o icone em uma determina posição de um elemento
        editTextName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextName.getRight() - editTextName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);//chama sempre o método onActivityResult
                    }
                }
                return false;
            }
        });

        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextCep = (EditText) findViewById(R.id.editTextCep);
        editTextCep.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_search, 0);
        editTextCep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextCep.getRight() - editTextCep.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (FormHelper.requireValidate(ClientPersistActivity.this, editTextCep)) {
                            {
                                new GetAddressByCep().execute(editTextCep.getText().toString());
                            }
                        }

                    }
                }
                return false;
            }
        });

        editTextTipoLogradouro = (EditText) findViewById(R.id.editTextTipoLogradouro);
        editTextLogradouro = (EditText) findViewById(R.id.editTextLogradouro);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextEstado = (EditText) findViewById(R.id.editTextEstado);
        switchGenre = (Switch) findViewById(R.id.swtGenre);
        switchGenre.setTextOn("Masculino");
        switchGenre.setTextOff("Feminino");
        chkDebtor = (CheckBox) findViewById(R.id.chkDebtor);


    }

    /**
     * @see <a href="http://developer.android.com/training/basics/intents/result.html">Getting a Result from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    editTextName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    editTextPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }


             }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getParameters() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            client = (Client)extras.getParcelable(CLIENT_PARAM);
            if(client == null){
                throw new IllegalArgumentException();
            }
            bindForm(client);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menuSave){

            if(FormHelper.requireValidate(ClientPersistActivity.this, editTextName, editTextAge, editTextPhone, editTextCep, editTextTipoLogradouro, editTextLogradouro, editTextBairro, editTextCidade, editTextEstado)){

                bindClient();
                client.save();
                Toast.makeText(ClientPersistActivity.this, getString(R.string.success), Toast.LENGTH_LONG).show();
                finish();

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void bindClient() {
        if (client == null) {
            client = new Client();
        }
        client.setName(editTextName.getText().toString());
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        client.setPhone(editTextPhone.getText().toString());
        client.setZip(editTextCep.getText().toString());
        client.setStreet(editTextTipoLogradouro.getText().toString());
        client.setStreetName(editTextLogradouro.getText().toString());
        client.setNeighborhood(editTextBairro.getText().toString());
        client.setCity(editTextCidade.getText().toString());
        client.setState(editTextEstado.getText().toString());
        if(switchGenre.isChecked()){
            client.setGenre(true);
        }
        else{
            client.setGenre(false);
        }

        if(chkDebtor.isChecked()){
            client.setDebt(true);
        }
        else{
            client.setDebt(false);
        }


    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextPhone.setText(client.getPhone());
        editTextCep.setText(client.getZip());
        editTextTipoLogradouro.setText(client.getStreet());
        editTextLogradouro.setText(client.getStreetName());
        editTextBairro.setText(client.getNeighborhood());
        editTextCidade.setText(client.getCity());
        editTextEstado.setText(client.getState());
        if(client.isGenre() == true){
            switchGenre.setChecked(true);
        }
        if(client.isDebt() == true){
            chkDebtor.setChecked(true);
        }
    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress>{ //Metodo recebe um PARAM e retorna um RESULT

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ClientPersistActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            try {
                return CepService.getAddressBy(params[0]);
            }catch(Exception e){
                Toast.makeText(ClientPersistActivity.this, "CEP inválido!", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            progressDialog.dismiss();
            editTextTipoLogradouro.setText(clientAddress.getTipoDeLogradouro());
            editTextLogradouro.setText(clientAddress.getLogradouro());
            editTextBairro.setText(clientAddress.getBairro());
            editTextCidade.setText(clientAddress.getCidade());
            editTextEstado.setText(clientAddress.getEstado());
        }
    }


}
