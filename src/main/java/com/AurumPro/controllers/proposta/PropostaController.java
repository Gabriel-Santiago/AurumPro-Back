package com.AurumPro.controllers.proposta;

import com.AurumPro.dtos.proposta.CreatePropostaDTO;
import com.AurumPro.dtos.proposta.PropostaDTO;
import com.AurumPro.services.proposta.PropostaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createProposta(@RequestBody CreatePropostaDTO dto){
        service.createProposta(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<List<PropostaDTO>> findAllProposta(@PathVariable("empresaId") Long empresaId){
        return new ResponseEntity<>(service.findAll(empresaId),
                HttpStatus.OK);
    }

    @GetMapping("/{empresaId}/{clienteId}")
    public ResponseEntity<List<PropostaDTO>> findPropostaByCliente(@PathVariable("empresaId") Long empresaId,
                                                                   @PathVariable("clienteId") Long clienteId){
        return new ResponseEntity<>(service.findPropostaByCliente(empresaId, clienteId),
                HttpStatus.OK);
    }
}
