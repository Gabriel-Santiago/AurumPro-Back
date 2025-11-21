package com.AurumPro.services.cliente;

import com.AurumPro.apis.Endereco;
import com.AurumPro.apis.ViaCep;
import com.AurumPro.dtos.cliente.pf.CreatePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.DeletePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.FindPessoaFisicaByUfDTO;
import com.AurumPro.dtos.cliente.pf.PessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.UpdateEmailAndTelefonePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.UpdateEnderecoPessoaFisicaDTO;
import com.AurumPro.entities.base.ClienteEntity;
import com.AurumPro.entities.cliente.PessoaFisica;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.cliente.PessoaFisicaMismatchException;
import com.AurumPro.exceptions.cliente.PessoaFisicaNotFoundEmpresaException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.CalculateAge;
import com.AurumPro.utils.ValidateCep;
import com.AurumPro.utils.ValidateCpf;
import com.AurumPro.utils.ValidadeId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaFisicaService {

    private final CalculateAge calculateAge;
    private final ClienteRepository repository;
    private final EmpresaRepository empresaRepository;
    private final ValidateCep validateCep;
    private final ValidateCpf validateCpf;
    private final ValidadeId validadeId;
    private final ViaCep viaCep;

    public PessoaFisicaService(CalculateAge calculateAge,
                               ClienteRepository repository,
                               EmpresaRepository empresaRepository,
                               ValidateCep validateCep,
                               ValidateCpf validateCpf,
                               ValidadeId validadeId,
                               ViaCep viaCep) {
        this.calculateAge = calculateAge;
        this.repository = repository;
        this.empresaRepository = empresaRepository;
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

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome(dto.nome());
        pessoaFisica.setEmail(dto.email());
        pessoaFisica.setCpf(dto.cpf());
        pessoaFisica.setTelefone(dto.telefone());
        pessoaFisica.setDataNascimento(dto.dataNascimento());

        var idade = calculateAge.idade(dto.dataNascimento());
        pessoaFisica.setIdade(idade);

        pessoaFisica.setCep(dto.cep());
        pessoaFisica.setRua(endereco.logradouro());
        pessoaFisica.setBairro(endereco.bairro());
        pessoaFisica.setCidade(endereco.localidade());
        pessoaFisica.setEstado(endereco.uf());
        pessoaFisica.setNumero(dto.numero());

        pessoaFisica.setEmpresa(empresa);

        repository.save(pessoaFisica);
    }

    public List<PessoaFisicaDTO> findAllPessoaFisica(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return repository
                .findAllPessoaFisica(empresaId)
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

        List<PessoaFisica> pessoaFisicaList = repository.findPessoFisicaByEmpresaAndEstado(empresa, dto.uf());

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
        ClienteEntity pessoaFisica = validatePessoaFisica(dto.id());

        pessoaFisica.setEmail(dto.email());
        pessoaFisica.setTelefone(dto.telefone());
    }

    @Transactional
    public void updateEnderecoPessoaFisica(UpdateEnderecoPessoaFisicaDTO dto) throws Exception {
        ClienteEntity pessoaFisica = validatePessoaFisica(dto.id());
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
        ClienteEntity pessoaFisica = validatePessoaFisica(dto.id());
        Empresa empresa = validadeId.validate(dto.empresaId(), empresaRepository);

        if(!pessoaFisica.getEmpresa().getId().equals(empresa.getId())){
            throw new PessoaFisicaNotFoundEmpresaException();
        }

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(pessoaFisica);
    }

    private PessoaFisica validatePessoaFisica(Long id) {
        ClienteEntity c = validadeId.validate(id, repository);
        if (!(c instanceof PessoaFisica pf)) {
            throw new PessoaFisicaMismatchException();
        }
        return pf;
    }
}
