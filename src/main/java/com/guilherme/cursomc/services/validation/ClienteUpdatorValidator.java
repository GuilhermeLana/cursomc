package com.guilherme.cursomc.services.validation;

import com.guilherme.cursomc.domain.Cliente;
import com.guilherme.cursomc.dto.ClienteDTO;
import com.guilherme.cursomc.repositories.ClienteRepository;
import com.guilherme.cursomc.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdatorValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann){

    }
    @Override
    public boolean isValid(ClienteDTO objDto,ConstraintValidatorContext context){
        List<FieldMessage> list = new ArrayList<>();

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer urlId = Integer.parseInt(map.get("id"));

        Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
        if (cliente != null && !cliente.getId().equals(urlId)){
            list.add(new FieldMessage("email", "E-mail já existente"));
        }

        for(FieldMessage e : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
