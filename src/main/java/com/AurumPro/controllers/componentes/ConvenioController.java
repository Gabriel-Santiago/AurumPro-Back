package com.AurumPro.controllers.componentes;

import com.AurumPro.dtos.componentes.convenio.ConvenioDTO;
import com.AurumPro.dtos.componentes.convenio.CreateConvenioDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.services.componentes.ConvenioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/convenios")
@RestController
public class ConvenioController {

    private final ConvenioService service;

    public ConvenioController(ConvenioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createConvenio(@RequestBody CreateConvenioDTO dto,
                                               @AuthenticationPrincipal Empresa empresa){
        service.createConvenio(dto, empresa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConvenioDTO>> findAllConvenio(@AuthenticationPrincipal Empresa empresa){
        return new ResponseEntity<>(service.findAllConvenio(empresa.getId()),
                HttpStatus.OK);
    }
}
