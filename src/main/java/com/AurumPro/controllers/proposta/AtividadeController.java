package com.AurumPro.controllers.proposta;

import com.AurumPro.dtos.proposta.AtividadeDTO;
import com.AurumPro.dtos.proposta.CreateAtividadeDTO;
import com.AurumPro.dtos.proposta.UpdateAtividadeDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.services.proposta.AtividadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequestMapping("/atividades")
@RestController
public class AtividadeController {

    private final AtividadeService service;

    public AtividadeController(AtividadeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createAtividade(@RequestBody CreateAtividadeDTO dto,
                                                @AuthenticationPrincipal Empresa empresa) {
        service.createAtividade(dto, empresa);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{propostaId}")
    public ResponseEntity<List<AtividadeDTO>> getAllAtividades(@PathVariable("propostaId") Long propostaId,
                                                               @AuthenticationPrincipal Empresa empresa) {
        return new ResponseEntity<>(service.findAllAtividade(propostaId, empresa.getId()),
                HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Void> updateAtividade(@RequestBody UpdateAtividadeDTO dto,
                                                @AuthenticationPrincipal Empresa empresa) throws AccessDeniedException {
        service.updateConcluidaAtividade(dto, empresa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{atividadeId}")
    public ResponseEntity<Void> deleteAtividade(@PathVariable("atividadeId") Long atividadeId,
                                                @AuthenticationPrincipal Empresa empresa) throws AccessDeniedException {
        service.deleteAtividade(atividadeId, empresa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
