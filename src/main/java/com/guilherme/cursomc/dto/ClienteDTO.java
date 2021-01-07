package com.guilherme.cursomc.dto;

import com.guilherme.cursomc.domain.Cliente;
import com.guilherme.cursomc.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ClienteUpdate
public class ClienteDTO {

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório!")
    @Length(min = 5, max = 240, message = "Mínimo de 5 caracteres e máximo de 240!")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório!")
    @Email(message = "E-mail inválido")
    private String email;

    public ClienteDTO(){

    }

    public ClienteDTO(Cliente cliente){
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
