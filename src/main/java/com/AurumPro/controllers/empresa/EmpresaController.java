package com.AurumPro.controllers.empresa;

import com.AurumPro.dtos.empresa.CreateEmpresaDTO;
import com.AurumPro.dtos.empresa.DeleteEmpresaDTO;
import com.AurumPro.dtos.empresa.EmpresaDTO;
import com.AurumPro.dtos.empresa.LoginEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateCepEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateEmailEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateTelefoneEmpresaDTO;
import com.AurumPro.services.empresa.EmpresaService;
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

@RequestMapping("/empresas")
@RestController
public class EmpresaController {

    private final EmpresaService service;

    public EmpresaController(EmpresaService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<EmpresaDTO> login(@RequestBody LoginEmpresaDTO dto){
        return new ResponseEntity<>(service.login(dto),
                HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Void> createEmpresa(@RequestBody CreateEmpresaDTO dto) throws Exception {
        service.createEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> findAllEmpresas() {
        return new ResponseEntity<>(service.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findById(id),
                HttpStatus.OK);
    }

    @PatchMapping("/cep")
    public ResponseEntity<Void> updateCepEmpresa(@RequestBody UpdateCepEmpresaDTO dto) throws Exception {
        service.updateCepEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/telefone")
    public ResponseEntity<Void> updateTelefoneEmpresa(@RequestBody UpdateTelefoneEmpresaDTO dto) {
        service.updateTelefoneEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/email")
    public ResponseEntity<Void> updateEmailEmpresa(@RequestBody UpdateEmailEmpresaDTO dto) {
        service.updateEmailEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmpresa(@RequestBody DeleteEmpresaDTO dto) {
        service.deleteEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
