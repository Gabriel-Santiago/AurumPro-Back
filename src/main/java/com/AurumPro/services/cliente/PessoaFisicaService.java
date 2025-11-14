package com.AurumPro.services.cliente;

import com.AurumPro.apis.Endereco;
import com.AurumPro.apis.ViaCep;
import com.AurumPro.dtos.cliente.pf.CreatePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.DeletePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.FindPessoaFisicaByUfDTO;
import com.AurumPro.dtos.cliente.pf.PessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.UpdateEmailAndTelefonePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.UpdateEnderecoPessoaFisicaDTO;
import com.AurumPro.entities.cliente.PessoaFisica;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.cliente.PessoFisicaNotFoundEmpresaException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.repositories.cliente.PessoaFisicaRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.CalculateAge;
import com.AurumPro.utils.ValidadeId;
import com.AurumPro.utils.ValidateCep;
import com.AurumPro.utils.ValidateCpf;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaFisicaService {

    private final CalculateAge calculateAge;
    private final EmpresaRepository empresaRepository;
    private final PessoaFisicaRepository repository;
    private final ValidateCep validateCep;
    private final ValidateCpf validateCpf;
    private final ValidadeId validadeId;
    private final ViaCep viaCep;

    public PessoaFisicaService(CalculateAge calculateAge,
                               EmpresaRepository empresaRepository,
                               PessoaFisicaRepository repository,
                               ValidateCep validateCep,
                               ValidateCpf validateCpf,
                               ValidadeId validadeId,
                               ViaCep viaCep) {
        this.calculateAge = calculateAge;
        this.empresaRepository = empresaRepository;
        this.repository = repository;
        this.validateCep = validateCep;
        this.validateCpf = validateCpf;
        this.validadeId = validadeId;
        this.viaCep = viaCep;
    }

    @Transactional
    public void createPessoaFisica(CreatePessoaFisicaDTO dto) throws Exception {
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        validateCep.validate(dto.cep());
        validateCpf.validate(dto.cpf());

        Endereco endereco = viaCep.viaCep(dto.cep());

        PessoaFisica pf = new PessoaFisica();
        pf.setNome(dto.nome());
        pf.setEmail(dto.email());
        pf.setCpf(dto.cpf());
        pf.setTelefone(dto.telefone());
        pf.setDataNascimento(dto.dataNascimento());

        var idade = calculateAge.idade(dto.dataNascimento());
        pf.setIdade(idade);

        pf.setCep(dto.cep());
        pf.setRua(endereco.logradouro());
        pf.setBairro(endereco.bairro());
        pf.setCidade(endereco.localidade());
        pf.setEstado(endereco.uf());
        pf.setNumero(dto.numero());

        pf.setEmpresa(empresa);

        repository.save(pf);
    }

    public List<PessoaFisicaDTO> findAll(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return repository
                .findAll()
                .stream()
                .map(dto -> new PessoaFisicaDTO(
                        dto.getId(),
                        dto.getNome(),
                        dto.getEmail(),
                        dto.getTelefone(),
                        dto.getIdade(),
                        dto.getCep(),
                        dto.getEstado(),
                        dto.getCidade(),
                        dto.getBairro(),
                        dto.getRua()
                ))
                .toList();
    }

    public List<PessoaFisicaDTO> findByUfPessoaFisica(FindPessoaFisicaByUfDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        List<PessoaFisica> pessoaFisicaList = repository.findByEmpresaAndEstado(empresa, dto.uf());

        return pessoaFisicaList
                .stream()
                .map(pf -> new PessoaFisicaDTO(
                        pf.getId(),
                        pf.getNome(),
                        pf.getEmail(),
                        pf.getTelefone(),
                        pf.getIdade(),
                        pf.getCep(),
                        pf.getEstado(),
                        pf.getCidade(),
                        pf.getBairro(),
                        pf.getRua()
                ))
                .toList();
    }

    @Transactional
    public void updateEmailAndTelefonePessoaFisica(UpdateEmailAndTelefonePessoaFisicaDTO dto){
        PessoaFisica pessoaFisica = validadeId.validate(dto.id(), repository);

        pessoaFisica.setEmail(dto.email());
        pessoaFisica.setTelefone(dto.telefone());

        repository.save(pessoaFisica);
    }

    @Transactional
    public void updateEnderecoPessoaFisica(UpdateEnderecoPessoaFisicaDTO dto) throws Exception {
        PessoaFisica pessoaFisica = validadeId.validate(dto.id(), repository);
        validateCep.validate(dto.cep());

        Endereco endereco = viaCep.viaCep(dto.cep());

        pessoaFisica.setCep(dto.cep());
        pessoaFisica.setRua(endereco.logradouro());
        pessoaFisica.setBairro(endereco.bairro());
        pessoaFisica.setCidade(endereco.localidade());
        pessoaFisica.setEstado(endereco.uf());
        pessoaFisica.setNumero(dto.numero());
    }

    @Transactional
    public void deletePessoaFisica(DeletePessoaFisicaDTO dto){
        Empresa empresa = validadeId.validate(dto.empresaId(), empresaRepository);
        PessoaFisica pessoaFisica = validadeId.validate(dto.id(), repository);

        if(!pessoaFisica.getEmpresa().getId().equals(empresa.getId())){
            throw new PessoFisicaNotFoundEmpresaException();
        }

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(pessoaFisica);
    }
}
