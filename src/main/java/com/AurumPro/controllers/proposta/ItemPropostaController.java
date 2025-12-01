package com.AurumPro.controllers.proposta;

import com.AurumPro.dtos.proposta.CreateItemPropostaDTO;
import com.AurumPro.dtos.proposta.ItemPropostaDTO;
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
    public ResponseEntity<ItemPropostaDTO> createItemProposta(@RequestBody CreateItemPropostaDTO dto){
        ItemPropostaDTO itemPropostaDTO = service.createItemProposta(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemPropostaDTO);
    }
}
