package com.AurumPro.controllers.componentes;

import com.AurumPro.dtos.componentes.microServico.CreateMicroServicoDTO;
import com.AurumPro.dtos.componentes.microServico.MicroServicoDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.services.componentes.MicroServicoService;
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

@RequestMapping("/microServicos")
@RestController
public class MicroServicoController {

    private final MicroServicoService service;

    public MicroServicoController(MicroServicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createMicroServico(@RequestBody CreateMicroServicoDTO dto,
                                                   @AuthenticationPrincipal Empresa empresa){
        service.createMicroServico(dto, empresa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{servicoId}")
    public ResponseEntity<List<MicroServicoDTO>> findAllMicroServico(@PathVariable("servicoId") Long servicoId){
        return new ResponseEntity<>(service.findAll(servicoId),
                HttpStatus.OK);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<MicroServicoDTO> findMicroServico(@PathVariable("id") Long id){
        return new ResponseEntity<>(service.findMicroServico(id),
                HttpStatus.OK);
    }
}
