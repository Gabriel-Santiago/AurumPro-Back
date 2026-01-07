package com.AurumPro.controllers.proposta;

import com.AurumPro.dtos.proposta.CreatePropostaDTO;
import com.AurumPro.dtos.proposta.PropostaDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.services.proposta.PropostaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/propostas")
@RestController
public class PropostaController {

    private final PropostaService service;

    public PropostaController(PropostaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createProposta(@RequestBody CreatePropostaDTO dto,
                                               @AuthenticationPrincipal Empresa empresa){
        service.createProposta(dto, empresa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PropostaDTO>> findAllProposta(@AuthenticationPrincipal Empresa empresa){
        return new ResponseEntity<>(service.findAll(empresa.getId()),
                HttpStatus.OK);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<PropostaDTO>> findPropostaByCliente(@AuthenticationPrincipal Empresa empresa,
                                                                   @PathVariable("clienteId") Long clienteId){
        return new ResponseEntity<>(service.findPropostaByCliente(empresa.getId(), clienteId),
                HttpStatus.OK);
    }

    @DeleteMapping("/{propostaId}")
    public ResponseEntity<Void> deleteProposta(@PathVariable("propostaId") Long propostaId) {
        service.delete(propostaId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
