package com.AurumPro.controllers.empresa;

import com.AurumPro.dtos.empresa.ConsultorDTO;
import com.AurumPro.dtos.empresa.CreateConsultorDTO;
import com.AurumPro.services.empresa.ConsultorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/consultores")
@RestController
public class ConsultorController {

    private final ConsultorService service;

    public ConsultorController(ConsultorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createConsultor(@RequestBody CreateConsultorDTO dto) throws Exception {
        service.createConsultor(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<List<ConsultorDTO>> findAllConsultor(@PathVariable("empresaId") Long empresaId) {
        return new ResponseEntity<>(service.findAllConsultor(empresaId),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, String>>> findAllTipoConsultor() {
        return new ResponseEntity<>(service.findAllTipoConsultor(),
                HttpStatus.OK);
    }
}
