package com.example.administrador.myapplication.model.persistence.client;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.List;

public interface ClientRepository {

    public void save(Client client);
    public List<Client> getAll();
    public void delete(Client client);

}
