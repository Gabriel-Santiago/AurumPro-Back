package com.AurumPro.services.cliente;

import com.AurumPro.apis.Endereco;
import com.AurumPro.apis.ViaCep;
import com.AurumPro.dtos.cliente.pj.CreatePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.DeletePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.FindPessoaJuridicaByUfDTO;
import com.AurumPro.dtos.cliente.pj.PessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.UpdateEmailAndTelefonePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.UpdateEnderecoPessoaJuridicaDTO;
import com.AurumPro.entities.cliente.PessoaJuridica;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.cliente.PessoaJuridicaNotFoundEmpresaException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.repositories.cliente.PessoaJuridicaRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.ValidadeId;
import com.AurumPro.utils.ValidateCep;
import com.AurumPro.utils.ValidateCnpj;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaJuridicaService {

    private final EmpresaRepository empresaRepository;
    private final PessoaJuridicaRepository repository;
    private final ValidateCep validateCep;
    private final ValidateCnpj validateCnpj;
    private final ValidadeId validadeId;
    private final ViaCep viaCep;

    public PessoaJuridicaService(EmpresaRepository empresaRepository,
                                 PessoaJuridicaRepository repository,
                                 ValidateCep validateCep,
                                 ValidateCnpj validateCnpj,
                                 ValidadeId validadeId,
                                 ViaCep viaCep) {
        this.empresaRepository = empresaRepository;
        this.repository = repository;
        this.validateCep = validateCep;
        this.validateCnpj = validateCnpj;
        this.validadeId = validadeId;
        this.viaCep = viaCep;
    }

    @Transactional
    public void createPessoaJuridica(CreatePessoaJuridicaDTO dto) throws Exception {
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        validateCep.validate(dto.cep());
        validateCnpj.validate(dto.cnpj());

        Endereco endereco = viaCep.viaCep(dto.cep());

        PessoaJuridica pj = new PessoaJuridica();
        pj.setNome(dto.nome());
        pj.setEmail(dto.email());
        pj.setCnpj(dto.cnpj());
        pj.setTelefone(dto.telefone());

        pj.setCep(dto.cep());
        pj.setRua(endereco.logradouro());
        pj.setBairro(endereco.bairro());
        pj.setCidade(endereco.localidade());
        pj.setEstado(endereco.uf());
        pj.setNumero(dto.numero());

        pj.setEmpresa(empresa);

        repository.save(pj);
    }

    public List<PessoaJuridicaDTO> findAll(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return repository
                .findAll()
                .stream()
                .map(pj -> new PessoaJuridicaDTO(
                        pj.getId(),
                        pj.getNome(),
                        pj.getEmail(),
                        pj.getTelefone(),
                        pj.getCnpj(),
                        pj.getCep(),
                        pj.getEstado(),
                        pj.getCidade(),
                        pj.getBairro(),
                        pj.getRua()
                ))
                .toList();
    }

    public List<PessoaJuridicaDTO> findByUfPessoaJuridica(FindPessoaJuridicaByUfDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        List<PessoaJuridica> pessoaJuridicaList = repository.findByEmpresaAndEstado(empresa, dto.uf());

        return pessoaJuridicaList
                .stream()
                .map(pj -> new PessoaJuridicaDTO(
                        pj.getId(),
                        pj.getNome(),
                        pj.getEmail(),
                        pj.getTelefone(),
                        pj.getCnpj(),
                        pj.getCep(),
                        pj.getEstado(),
                        pj.getCidade(),
                        pj.getBairro(),
                        pj.getRua()
                ))
                .toList();
    }

    @Transactional
    public void updateEmailAndTelefonePessoaJuridica(UpdateEmailAndTelefonePessoaJuridicaDTO dto){
        PessoaJuridica pessoaJuridica = validadeId.validate(dto.id(), repository);

        pessoaJuridica.setEmail(dto.email());
        pessoaJuridica.setTelefone(dto.telefone());

        repository.save(pessoaJuridica);
    }

    @Transactional
    public void updateEnderecoPessoaJuridica(UpdateEnderecoPessoaJuridicaDTO dto) throws Exception {
        PessoaJuridica pessoaJuridica = validadeId.validate(dto.id(), repository);
        validateCep.validate(dto.cep());

        Endereco endereco = viaCep.viaCep(dto.cep());

        pessoaJuridica.setCep(dto.cep());
        pessoaJuridica.setRua(endereco.logradouro());
        pessoaJuridica.setBairro(endereco.bairro());
        pessoaJuridica.setCidade(endereco.localidade());
        pessoaJuridica.setEstado(endereco.uf());
        pessoaJuridica.setNumero(dto.numero());
    }

    @Transactional
    public void deletePessoaJuridica(DeletePessoaJuridicaDTO dto){
        Empresa empresa = validadeId.validate(dto.empresaId(), empresaRepository);
        PessoaJuridica pessoaJuridica = validadeId.validate(dto.id(), repository);

        if(!pessoaJuridica.getEmpresa().getId().equals(empresa.getId())){
            throw new PessoaJuridicaNotFoundEmpresaException();
        }

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(pessoaJuridica);
    }
}
