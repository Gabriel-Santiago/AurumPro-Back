package com.AurumPro.controllers.cliente;

import com.AurumPro.dtos.cliente.ClienteDTO;
import com.AurumPro.dtos.cliente.CreatePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.CreatePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.DeleteClienteDTO;
import com.AurumPro.dtos.cliente.FindTipoPessoabyUfDTO;
import com.AurumPro.dtos.cliente.UpdateEmailAndTelefoneClienteDTO;
import com.AurumPro.dtos.cliente.UpdateEnderecoClienteDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.services.cliente.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/clientes")
@RestController
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping("/pessoaFisica")
    public ResponseEntity<Void> createPessoaFisica(@RequestBody CreatePessoaFisicaDTO dto,
                                                   @AuthenticationPrincipal Empresa empresa) throws Exception {
        service.createPessoaFisica(dto, empresa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/pessoaJuridica")
    public ResponseEntity<Void> createPessoaJuridica(@RequestBody CreatePessoaJuridicaDTO dto,
                                                     @AuthenticationPrincipal Empresa empresa) throws Exception {
        service.createPessoaJuridica(dto, empresa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAllCliente(@AuthenticationPrincipal Empresa empresa) {
        return new ResponseEntity<>(service.findAllCliente(empresa.getId()),
                HttpStatus.OK);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTO> findCliente(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findById(id),
                HttpStatus.OK);
    }

    @GetMapping("/estados")
    public ResponseEntity<List<ClienteDTO>> findClienteByTipoPessoaAndUf(@RequestBody FindTipoPessoabyUfDTO dto,
                                                                         @AuthenticationPrincipal Empresa empresa) {
        return new ResponseEntity<>(service.findClienteByTipoPessoaAndUf(dto, empresa),
                HttpStatus.OK);
    }

    @PatchMapping("/email/telefone")
    public ResponseEntity<Void> updateEmailAndTelefoneCliente(@RequestBody UpdateEmailAndTelefoneClienteDTO dto) {
        service.updateEmailAndTelefoneCliente(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/endereco")
    public ResponseEntity<Void> updateEnderecoCliente(@RequestBody UpdateEnderecoClienteDTO dto) throws Exception {
        service.updateEnderecoCliente(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCliente(@RequestBody DeleteClienteDTO dto,
                                              @AuthenticationPrincipal Empresa empresa) {
        service.deletePessoa(dto, empresa);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
