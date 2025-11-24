package com.AurumPro.controllers.componentes;

import com.AurumPro.dtos.componentes.custo.CreateCustoDTO;
import com.AurumPro.services.componentes.CustoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/custos")
@RestController
public class CustoController {

    private final CustoService service;

    public CustoController(CustoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createCusto(@RequestBody CreateCustoDTO dto){
        service.createCusto(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
