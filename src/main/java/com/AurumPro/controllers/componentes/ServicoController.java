package com.AurumPro.controllers.componentes;

import com.AurumPro.dtos.componentes.servico.CreateServicoDTO;
import com.AurumPro.dtos.componentes.servico.ServicoDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.services.componentes.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Void> createServico(@RequestBody CreateServicoDTO dto,
                                              @AuthenticationPrincipal Empresa empresa){
        service.createServico(dto, empresa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> findAllServico(@AuthenticationPrincipal Empresa empresa){
        return new ResponseEntity<>(service.findAll(empresa.getId()),
                HttpStatus.OK);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<ServicoDTO> findServico(@PathVariable("id") Long id){
        return new ResponseEntity<>(service.findServico(id),
                HttpStatus.OK);
    }
}
