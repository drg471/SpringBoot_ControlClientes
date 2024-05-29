package com.example.springbootdatajpa.app.models.dao;

import com.example.springbootdatajpa.app.models.entity.Cliente;

import java.util.List;

public interface IClienteDao {
    public List<Cliente> findAll();

    public void save(Cliente cliente);

    public Cliente findOne(Long id);

    public void delete(Long id);

}
