package com.example.springbootdatajpa.app.models.dao;

import com.example.springbootdatajpa.app.models.entity.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

//Indica que esta clase se encargará de interactuar con la base de datos. (Repositorio de datos)
@Repository("ClienteDaoImpl") //Es una Bean (componente) de acceso a datos //También se encarga de traducir las excepciones que pueden ocurrir
public class ClienteDaoImpl implements IClienteDao{

    //Se utiliza para inyectar un EntityManager, que es la interfaz principal utilizada para interactuar con la capa de persistencia de datos en JPA
    @PersistenceContext //Inyecta el EntityManager segun la config de BBDD
    private EntityManager em; //Se encarga de manejar las clases de entidades

    @Transactional(readOnly = true) //Marcamos el metodo como solo de lectura
    @Override
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) { //Metodo para buscar a un Cliente
        return em.find(Cliente.class, id); //Con el EntityManager buscamos en la clase Cliente por el id
    }

    @Override
    @Transactional //Marcamos el metodo como solo de escritura (sin el readOnly=true)
    public void save(Cliente cliente) {
        if (cliente.getId() != null && cliente.getId() > 0){
            em.merge(cliente);
        } else{
            em.persist(cliente);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        em.remove(findOne(id));
    }
}
