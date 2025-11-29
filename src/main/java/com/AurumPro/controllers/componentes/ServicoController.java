package com.AurumPro.controllers.componentes;

import com.AurumPro.dtos.componentes.servico.CreateServicoDTO;
import com.AurumPro.dtos.componentes.servico.ServicoDTO;
import com.AurumPro.services.componentes.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/servicos")
@RestController
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createServico(@RequestBody CreateServicoDTO dto){
        service.createServico(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<List<ServicoDTO>> findAllServico(@PathVariable("empresaId") Long empresaId){
        return new ResponseEntity<>(service.findAll(empresaId),
                HttpStatus.OK);
    }
}
