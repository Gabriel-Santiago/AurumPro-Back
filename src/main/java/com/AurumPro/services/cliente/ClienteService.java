package com.AurumPro.services.cliente;

import com.AurumPro.apis.DadosReceita;
import com.AurumPro.apis.Endereco;
import com.AurumPro.apis.ReceitaWS;
import com.AurumPro.apis.ViaCep;
import com.AurumPro.dtos.cliente.pf.CreatePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.DeletePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.FindPessoaFisicaByUfDTO;
import com.AurumPro.dtos.cliente.pf.PessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.UpdateEmailAndTelefonePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pf.UpdateEnderecoPessoaFisicaDTO;
import com.AurumPro.dtos.cliente.pj.CreatePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.DeletePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.FindPessoaJuridicaByUfDTO;
import com.AurumPro.dtos.cliente.pj.PessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.UpdateEmailAndTelefonePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.UpdateEnderecoPessoaJuridicaDTO;
import com.AurumPro.entities.cliente.PessoaFisica;
import com.AurumPro.entities.cliente.PessoaJuridica;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.cliente.PessoaFisicaNotFoundEmpresaException;
import com.AurumPro.exceptions.cliente.PessoaJuridicaNotFoundEmpresaException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.cliente.PessoaFisicaRepository;
import com.AurumPro.repositories.cliente.PessoaJuridicaRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.CalculateAge;
import com.AurumPro.utils.ValidateCep;
import com.AurumPro.utils.ValidateCnpj;
import com.AurumPro.utils.ValidateCpf;
import com.AurumPro.utils.ValidadeId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final CalculateAge calculateAge;
    private final ClienteRepository repository;
    private final EmpresaRepository empresaRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    private final ReceitaWS receitaWS;
    private final ValidateCep validateCep;
    private final ValidateCnpj validateCnpj;
    private final ValidateCpf validateCpf;
    private final ValidadeId validadeId;
    private final ViaCep viaCep;

    public ClienteService(CalculateAge calculateAge,
                          ClienteRepository repository,
                          EmpresaRepository empresaRepository,
                          PessoaFisicaRepository pessoaFisicaRepository,
                          PessoaJuridicaRepository pessoaJuridicaRepository,
                          ReceitaWS receitaWS,
                          ValidateCep validateCep,
                          ValidateCnpj validateCnpj,
                          ValidateCpf validateCpf,
                          ValidadeId validadeId,
                          ViaCep viaCep) {
        this.calculateAge = calculateAge;
        this.repository = repository;
        this.empresaRepository = empresaRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
        this.receitaWS = receitaWS;
        this.validateCep = validateCep;
        this.validateCnpj = validateCnpj;
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
        pessoaFisicaRepository.save(pessoaFisica);
    }

    public List<PessoaFisicaDTO> findAllPessoaFisica(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return pessoaFisicaRepository
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

        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findByEmpresaAndEstado(empresa, dto.uf());

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
        PessoaFisica pessoaFisica = validadeId.validate(dto.id(), pessoaFisicaRepository);

        pessoaFisica.setEmail(dto.email());
        pessoaFisica.setTelefone(dto.telefone());

        repository.save(pessoaFisica);
        pessoaFisicaRepository.save(pessoaFisica);
    }

    @Transactional
    public void updateEnderecoPessoaFisica(UpdateEnderecoPessoaFisicaDTO dto) throws Exception {
        PessoaFisica pessoaFisica = validadeId.validate(dto.id(), pessoaFisicaRepository);
        validateCep.validate(dto.cep());

        Endereco endereco = viaCep.viaCep(dto.cep());

        pessoaFisica.setCep(dto.cep());
        pessoaFisica.setRua(endereco.logradouro());
        pessoaFisica.setBairro(endereco.bairro());
        pessoaFisica.setCidade(endereco.localidade());
        pessoaFisica.setEstado(endereco.uf());
        pessoaFisica.setNumero(dto.numero());

        repository.save(pessoaFisica);
        pessoaFisicaRepository.save(pessoaFisica);
    }

    @Transactional
    public void deletePessoaFisica(DeletePessoaFisicaDTO dto){
        Empresa empresa = validadeId.validate(dto.empresaId(), empresaRepository);
        PessoaFisica pessoaFisica = validadeId.validate(dto.id(), pessoaFisicaRepository);

        if(!pessoaFisica.getEmpresa().getId().equals(empresa.getId())){
            throw new PessoaFisicaNotFoundEmpresaException();
        }

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(pessoaFisica);
        pessoaFisicaRepository.delete(pessoaFisica);
    }

    @Transactional
    public void createPessoaJuridica(CreatePessoaJuridicaDTO dto) throws Exception {
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        validateCnpj.validate(dto.cnpj());

        DadosReceita dadosReceita = receitaWS.consultaCnpj(dto.cnpj());

        validateCep.validate(dadosReceita.cep());

        PessoaJuridica pessoaJuridica = getPessoaJuridica(dto, dadosReceita, empresa);

        repository.save(pessoaJuridica);
        pessoaJuridicaRepository.save(pessoaJuridica);
    }

    private static PessoaJuridica getPessoaJuridica(CreatePessoaJuridicaDTO dto, DadosReceita dadosReceita, Empresa empresa) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome(dto.nome());
        pessoaJuridica.setEmail(dto.email());
        pessoaJuridica.setCnpj(dto.cnpj());
        pessoaJuridica.setTelefone(dadosReceita.telefone());

        pessoaJuridica.setCep(dadosReceita.cep());
        pessoaJuridica.setRua(dadosReceita.logradouro());
        pessoaJuridica.setBairro(dadosReceita.bairro());
        pessoaJuridica.setCidade(dadosReceita.municipio());
        pessoaJuridica.setEstado(dadosReceita.uf());
        pessoaJuridica.setNumero(dto.numero());

        pessoaJuridica.setEmpresa(empresa);
        return pessoaJuridica;
    }

    public List<PessoaJuridicaDTO> findAll(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return pessoaJuridicaRepository
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

        List<PessoaJuridica> pessoaJuridicaList = pessoaJuridicaRepository.findByEmpresaAndEstado(empresa, dto.uf());

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
        PessoaJuridica pessoaJuridica = validadeId.validate(dto.id(), pessoaJuridicaRepository);

        pessoaJuridica.setEmail(dto.email());
        pessoaJuridica.setTelefone(dto.telefone());

        repository.save(pessoaJuridica);
        pessoaJuridicaRepository.save(pessoaJuridica);
    }

    @Transactional
    public void updateEnderecoPessoaJuridica(UpdateEnderecoPessoaJuridicaDTO dto) throws Exception {
        PessoaJuridica pessoaJuridica = validadeId.validate(dto.id(), pessoaJuridicaRepository);
        validateCep.validate(dto.cep());

        Endereco endereco = viaCep.viaCep(dto.cep());

        pessoaJuridica.setCep(dto.cep());
        pessoaJuridica.setRua(endereco.logradouro());
        pessoaJuridica.setBairro(endereco.bairro());
        pessoaJuridica.setCidade(endereco.localidade());
        pessoaJuridica.setEstado(endereco.uf());
        pessoaJuridica.setNumero(dto.numero());

        repository.save(pessoaJuridica);
        pessoaJuridicaRepository.save(pessoaJuridica);
    }

    @Transactional
    public void deletePessoaJuridica(DeletePessoaJuridicaDTO dto){
        Empresa empresa = validadeId.validate(dto.empresaId(), empresaRepository);
        PessoaJuridica pessoaJuridica = validadeId.validate(dto.id(), pessoaJuridicaRepository);

        if(!pessoaJuridica.getEmpresa().getId().equals(empresa.getId())){
            throw new PessoaJuridicaNotFoundEmpresaException();
        }

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(pessoaJuridica);
        pessoaJuridicaRepository.delete(pessoaJuridica);
    }
}
