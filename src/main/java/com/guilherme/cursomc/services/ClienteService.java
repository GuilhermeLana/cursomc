package com.guilherme.cursomc.services;

import com.guilherme.cursomc.domain.Cidade;
import com.guilherme.cursomc.domain.Cliente;
import com.guilherme.cursomc.domain.Cliente;
import com.guilherme.cursomc.domain.Endereco;
import com.guilherme.cursomc.domain.enums.TipoCliente;
import com.guilherme.cursomc.dto.ClienteDTO;
import com.guilherme.cursomc.dto.ClienteNewDTO;
import com.guilherme.cursomc.repositories.ClienteRepository;
import com.guilherme.cursomc.repositories.EnderecoRepository;
import com.guilherme.cursomc.services.exceptions.DataIntegrityException;
import com.guilherme.cursomc.services.exceptions.ObjectNotFountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFountException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
        ));
    }

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);
        clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj){
        Cliente newCliente = find(obj.getId());
        updateData(newCliente, obj);
        return clienteRepository.save(newCliente);
    }

    public void delete(Integer id){
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir pois possui entidades relacionadas!");
        }
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO clienteNewDTO){
        Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()));
        Cidade cid = new Cidade(clienteNewDTO.getCidadeId(), null, null);
        Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
                clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(clienteNewDTO.getTelefone1());
        if (clienteNewDTO.getTelefone2()!=null){
            cli.getTelefones().add(clienteNewDTO.getTelefone2());
        }
        if (clienteNewDTO.getTelefone3()!=null){
            cli.getTelefones().add(clienteNewDTO.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newCliente, Cliente cliente){
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}
