package com.example.springbootdatajpa.app.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

@Entity //Anotacion de JPA para indicar que la clase va a ser una entidad (tabla) en la base de datos
@Table(name = "clientes") //nombre de la tabla
public class Cliente implements Serializable {

    @Id //Llave primaria de la tabla de la BBDD
    @GeneratedValue(strategy = GenerationType.IDENTITY) //forma en la que se generan las claves
    private Long id;

    //Cuando anotamos una clase con @Entity todos sus atributos se mapean a la tabla de la BBDD de manera automática
    @NotEmpty //Evalua que el campo no esté vacio
    @Size(min=4, max = 12) //Evalua que el nombre tenga entre 4 y 12 caracteres
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    @Email //Evalua que el email tenga formato email
    private String email;

    //También se puede customizar las columnas añadiendo encima del atributo @Column(name="nombre_cliente")
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE) //indica el formato en que se va a guardar la fecha en la tabla de la BBDD
    private Date createAt;

    public Long getId() {
        return id;
    }

    @PrePersist //Automaticamente, JPA, llama a este metodo antes que el metodo Persist
    @PreUpdate //Automaticamente, JPA, llama a este metodo antes que el metodo Merge (actualizar)
    public void prePersist(){
        createAt = new Date();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
