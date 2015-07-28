package com.example.administrador.myapplication.model.services;

import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class CepService {

    private static final String URL = "http://correiosapi.apphb.com/cep/";

    private CepService(){
        super();
    }

    public static ClientAddress getAddressBy(String cep){

        ClientAddress address = null;

        try {
            URL url = new URL(URL + cep);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json"); //So aceita uma resposta em JSON
            int responseCode = conn.getResponseCode();//Executa o get e retorna o codigo de resposta

            if(responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error code: " + responseCode);
            }
            //else

            InputStream inputStream = conn.getInputStream();//Recupera o body, dentro do InputStream fica os bytes do JSON

            ObjectMapper objectMapper = new ObjectMapper();
            address = objectMapper.readValue(inputStream, ClientAddress.class);

            conn.disconnect(); // Desconecta do servidor


        } catch (IOException e) {
            e.printStackTrace();
        }


        return address;
    }
}
