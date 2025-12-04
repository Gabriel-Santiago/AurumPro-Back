package com.AurumPro.controllers.empresa;

import com.AurumPro.dtos.empresa.ColaboradorDTO;
import com.AurumPro.dtos.empresa.CreateColaboradorDTO;
import com.AurumPro.services.empresa.ColaboradorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Void> createColaborador(@RequestBody CreateColaboradorDTO dto) throws Exception {
        service.createColaborador(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<List<ColaboradorDTO>> findAllColaborador(@PathVariable("empresaId") Long empresaId) {
        return new ResponseEntity<>(service.findAllColaborador(empresaId),
                HttpStatus.OK);
    }

    @GetMapping("/funcoes")
    public ResponseEntity<List<String>> findAllFuncao() {
        return new ResponseEntity<>(service.findAllFuncao(),
                HttpStatus.OK);
    }
}
