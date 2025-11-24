package com.AurumPro.controllers.cliente;

import com.AurumPro.dtos.cliente.pj.CreatePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.DeletePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.FindPessoaJuridicaByUfDTO;
import com.AurumPro.dtos.cliente.pj.PessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.UpdateEmailAndTelefonePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.UpdateEnderecoPessoaJuridicaDTO;
import com.AurumPro.services.cliente.PessoaJuridicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pessoasJuridica")
@RestController
public class PessoaJuridicaController {

    private final PessoaJuridicaService service;

    public PessoaJuridicaController(PessoaJuridicaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createPessoaJuridica(@RequestBody CreatePessoaJuridicaDTO dto) throws Exception {
        service.createPessoaJuridica(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<List<PessoaJuridicaDTO>> findAllPessoaJuridica(@PathVariable("empresaId") Long empresaId) {
        return new ResponseEntity<>(service.findAll(empresaId),
                HttpStatus.OK);
    }

    @GetMapping("/estados")
    public ResponseEntity<List<PessoaJuridicaDTO>> findByUfPessoaJuridica(@RequestBody FindPessoaJuridicaByUfDTO dto) {
        return new ResponseEntity<>(service.findByUfPessoaJuridica(dto),
                HttpStatus.OK);
    }

    @PatchMapping("/email/telefone")
    public ResponseEntity<Void> updateEmailAndTelefonePessoaJuridica(@RequestBody UpdateEmailAndTelefonePessoaJuridicaDTO dto) {
        service.updateEmailAndTelefonePessoaJuridica(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/endereco")
    public ResponseEntity<Void> updateEnderecoPessoaJuridica(@RequestBody UpdateEnderecoPessoaJuridicaDTO dto) throws Exception {
        service.updateEnderecoPessoaJuridica(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePessoaJuridica(@RequestBody DeletePessoaJuridicaDTO dto) {
        service.deletePessoaJuridica(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
