package com.AurumPro.services.cliente;

import com.AurumPro.api.DadosReceita;
import com.AurumPro.api.Endereco;
import com.AurumPro.api.ReceitaWS;
import com.AurumPro.api.ViaCep;
import com.AurumPro.dtos.cliente.pj.CreatePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.DeletePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.FindPessoaJuridicaByUfDTO;
import com.AurumPro.dtos.cliente.pj.PessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.UpdateEmailAndTelefonePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.pj.UpdateEnderecoPessoaJuridicaDTO;
import com.AurumPro.entities.base.ClienteEntity;
import com.AurumPro.entities.cliente.PessoaJuridica;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.cliente.PessoaJuridicaNotFoundEmpresaException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.ValidateCep;
import com.AurumPro.utils.ValidateCnpj;
import com.AurumPro.utils.ValidadeId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaJuridicaService {

    private final ClienteRepository repository;
    private final EmpresaRepository empresaRepository;
    private final ReceitaWS receitaWS;
    private final ValidateCep validateCep;
    private final ValidateCnpj validateCnpj;
    private final ValidadeId validadeId;
    private final ViaCep viaCep;

    public PessoaJuridicaService(ClienteRepository repository,
                                 EmpresaRepository empresaRepository,
                                 ReceitaWS receitaWS,
                                 ValidateCep validateCep,
                                 ValidateCnpj validateCnpj,
                                 ValidadeId validadeId,
                                 ViaCep viaCep) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
        this.receitaWS = receitaWS;
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

        validateCnpj.validate(dto.cnpj());

        DadosReceita dadosReceita = receitaWS.consultaCnpj(dto.cnpj());

        validateCep.validate(dadosReceita.cep());

        PessoaJuridica pessoaJuridica = getPessoaJuridica(dto, dadosReceita, empresa);

        repository.save(pessoaJuridica);
    }

    public List<PessoaJuridicaDTO> findAll(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return repository
                .findAllPessoaJuridica(empresaId)
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

        List<PessoaJuridica> pessoaJuridicaList = repository.findPessoaJuridicaByEmpresaAndEstado(empresa, dto.uf());

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
        ClienteEntity pessoaJuridica = validatePessoaJuridica(dto.id());

        pessoaJuridica.setEmail(dto.email());
        pessoaJuridica.setTelefone(dto.telefone());
    }

    @Transactional
    public void updateEnderecoPessoaJuridica(UpdateEnderecoPessoaJuridicaDTO dto) throws Exception {
        ClienteEntity pessoaJuridica = validatePessoaJuridica(dto.id());
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
        ClienteEntity pessoaJuridica = validatePessoaJuridica(dto.id());

        if(!pessoaJuridica.getEmpresa().getId().equals(empresa.getId())){
            throw new PessoaJuridicaNotFoundEmpresaException();
        }

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(pessoaJuridica);
    }

    private PessoaJuridica validatePessoaJuridica(Long id) {
        ClienteEntity c = validadeId.validate(id, repository);
        if (!(c instanceof PessoaJuridica pj)) {
            throw new PessoaJuridicaNotFoundEmpresaException();
        }
        return pj;
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
}
