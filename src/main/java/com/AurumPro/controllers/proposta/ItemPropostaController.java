package com.AurumPro.controllers.proposta;

import com.AurumPro.dtos.proposta.CreateItemPropostaDTO;
import com.AurumPro.services.proposta.ItemPropostaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/itensProposta")
@RestController
public class ItemPropostaController {

    private final ItemPropostaService service;

    public ItemPropostaController(ItemPropostaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createItemProposta(@RequestBody CreateItemPropostaDTO dto){
        service.createItemProposta(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
