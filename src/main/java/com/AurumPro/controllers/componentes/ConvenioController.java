package com.AurumPro.controllers.componentes;

import com.AurumPro.dtos.componentes.convenio.ConvenioDTO;
import com.AurumPro.dtos.componentes.convenio.CreateConvenioDTO;
import com.AurumPro.services.componentes.ConvenioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Void> createConvenio(@RequestBody CreateConvenioDTO dto){
        service.createConvenio(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<List<ConvenioDTO>> findAllConvenio(@PathVariable("empresaId") Long empresaId){
        return new ResponseEntity<>(service.findAll(empresaId),
                HttpStatus.OK);
    }
}
