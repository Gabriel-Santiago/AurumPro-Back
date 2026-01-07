package com.AurumPro.controllers.empresa;

import com.AurumPro.dtos.empresa.ColaboradorDTO;
import com.AurumPro.dtos.empresa.CreateColaboradorDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.services.empresa.ColaboradorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/colaboradores")
@RestController
public class ColaboradorController {

    private final ColaboradorService service;

    public ColaboradorController(ColaboradorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createColaborador(@RequestBody CreateColaboradorDTO dto,
                                                  @AuthenticationPrincipal Empresa empresa) {
        service.createColaborador(dto, empresa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ColaboradorDTO>> findAllColaborador(@AuthenticationPrincipal Empresa empresa){
        return new ResponseEntity<>(service.findAllColaborador(empresa.getId()),
                HttpStatus.OK);
    }

    @GetMapping("/funcoes")
    public ResponseEntity<List<String>> findAllFuncao() {
        return new ResponseEntity<>(service.findAllFuncao(),
                HttpStatus.OK);
    }
}
