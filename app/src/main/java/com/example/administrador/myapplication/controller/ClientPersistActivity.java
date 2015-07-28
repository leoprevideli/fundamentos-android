package com.example.administrador.myapplication.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private Button buttonFindCep;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_client);
        bindFields();
        getParameters();
    }

    private void bindFields() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextCep = (EditText) findViewById(R.id.editTextCep);
        editTextTipoLogradouro = (EditText) findViewById(R.id.editTextTipoLogradouro);
        editTextLogradouro = (EditText) findViewById(R.id.editTextLogradouro);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextEstado = (EditText) findViewById(R.id.editTextEstado);

        bindButtonFindCep();
    }

    private void bindButtonFindCep() {
        buttonFindCep = (Button) findViewById(R.id.buttonFindCep);
        buttonFindCep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new GetAddressByCep().execute(editTextCep.getText().toString());
            }
        });
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

            if(FormHelper.requireValidate(ClientPersistActivity.this, editTextName, editTextAge, editTextPhone, editTextTipoLogradouro, editTextLogradouro, editTextBairro, editTextCidade, editTextEstado)){

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
            return CepService.getAddressBy(params[0]);
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
