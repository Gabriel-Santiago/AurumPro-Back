package com.AurumPro.controllers.proposta;

import com.AurumPro.dtos.proposta.AtividadeDTO;
import com.AurumPro.dtos.proposta.CreateAtividadeDTO;
import com.AurumPro.dtos.proposta.UpdateAtividadeDTO;
import com.AurumPro.services.proposta.AtividadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/atividades")
@RestController
public class AtividadeController {

    private final AtividadeService service;

    public AtividadeController(AtividadeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createAtividade(@RequestBody CreateAtividadeDTO dto) {
        service.createAtividade(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/propostaId")
    public ResponseEntity<List<AtividadeDTO>> getAllAtividades(@PathVariable("propostaId") Long propostaId) {
        return new ResponseEntity<>(service.findAllAtividade(propostaId),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateAtividade(@RequestBody UpdateAtividadeDTO dto) {
        service.updateConcluidaAtividade(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/atividadeId")
    public ResponseEntity<Void> deleteAtividade(@PathVariable("atividadeId") Long atividadeId) {
        service.deleteAtividade(atividadeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
