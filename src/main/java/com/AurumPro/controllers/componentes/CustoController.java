package com.AurumPro.controllers.componentes;

import com.AurumPro.dtos.componentes.custo.CreateCustoDTO;
import com.AurumPro.dtos.componentes.custo.CustoDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.services.componentes.CustoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<CustoDTO> createCusto(@RequestBody CreateCustoDTO dto,
                                                @AuthenticationPrincipal Empresa empresa){
        CustoDTO custoDTO = service.createCusto(dto, empresa);

        return ResponseEntity.status(HttpStatus.CREATED).body(custoDTO);
    }
}
