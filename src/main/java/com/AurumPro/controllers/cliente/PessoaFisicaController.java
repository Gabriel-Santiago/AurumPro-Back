package com.AurumPro.controllers.cliente;

import com.AurumPro.dtos.cliente.pf.CreatePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.DeletePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.FindPessoaFisicaByUfDTO;
import com.AurumPro.dtos.cliente.pf.PessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.UpdateEmailAndTelefonePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.UpdateEnderecoPessoaFisicaDTO;
import com.AurumPro.services.cliente.PessoaFisicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/pessoasFisica")
@RestController
public class PessoaFisicaController {

    private final PessoaFisicaService service;

    public PessoaFisicaController(PessoaFisicaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createPessoaFisica(@RequestBody CreatePessoaFisicaDTO dto) throws Exception {
        service.createPessoaFisica(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<List<PessoaFisicaDTO>> findAllPessoaFisica(@PathVariable("empresaId") Long empresaId) {
        return new ResponseEntity<>(service.findAllPessoaFisica(empresaId),
                HttpStatus.OK);
    }

    @GetMapping("/estados")
    public ResponseEntity<List<PessoaFisicaDTO>> findByUfPessoaFisica(@RequestBody FindPessoaFisicaByUfDTO dto) {
        return new ResponseEntity<>(service.findByUfPessoaFisica(dto),
                HttpStatus.OK);
    }

    @PatchMapping("/email/telefone")
    public ResponseEntity<Void> updateEmailAndTelefonePessoaFisica(@RequestBody UpdateEmailAndTelefonePessoaFisicaDTO dto) {
        service.updateEmailAndTelefonePessoaFisica(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/endereco")
    public ResponseEntity<Void> updateEnderecoPessoaFisica(@RequestBody UpdateEnderecoPessoaFisicaDTO dto) throws Exception {
        service.updateEnderecoPessoaFisica(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePessoaFisica(@RequestBody DeletePessoaFisicaDTO dto) {
        service.deletePessoaFisica(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
