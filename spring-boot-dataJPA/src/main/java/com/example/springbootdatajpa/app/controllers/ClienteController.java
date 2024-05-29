package com.example.springbootdatajpa.app.controllers;

import com.example.springbootdatajpa.app.models.dao.IClienteDao;
import com.example.springbootdatajpa.app.models.entity.Cliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Controller
//Con los metodos GET se guarda un objeto Cliente en la session y lo pasa a la vista, en la vista queda en la session y el objeto queda persistente hasta un metodo POST
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    @Qualifier("ClienteDaoImpl")
    private IClienteDao clienteDao;

    @RequestMapping(value="listar", method = RequestMethod.GET)
    public String listar (Model model){
        model.addAttribute("titulo", "Listado de cliente");
        model.addAttribute("clientes", clienteDao.findAll());
        return "listar";
    }

    @RequestMapping(value="/form") //Utiliza el metodo GET por defecto
    public String crear(Map<String,Object> model){ //Le podemos pasar a la vista un Map
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", "Formulario de Cliente");
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    //Hay que anotar con @Valid la clase Cliente para usar las anotaciones de validacion de la clase
    public String guardar (@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status){
        if (result.hasErrors()){ //Si el BindingResult tras la validacion @Valid da error de validacion
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }
        clienteDao.save(cliente);
        status.setComplete(); //Elimina el objeto Cliente de la session
        return "redirect:listar";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar (@PathVariable(value = "id") Long id, Map<String,Object> model){
        Cliente cliente = null;
        if (id > 0) {
            cliente = clienteDao.findOne(id);
        }else {
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "form";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id){
        if (id > 0) {
            clienteDao.delete(id);
        }
        return "redirect:/listar";
    }
}
