package com.AurumPro.services.empresa;

import com.AurumPro.api.DadosReceita;
import com.AurumPro.api.Endereco;
import com.AurumPro.api.ReceitaWS;
import com.AurumPro.api.ViaCep;
import com.AurumPro.dtos.empresa.CreateEmpresaDTO;
import com.AurumPro.dtos.empresa.DeleteEmpresaDTO;
import com.AurumPro.dtos.empresa.EmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateCepEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateEmailEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateTelefoneEmpresaDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.ValidadeId;
import com.AurumPro.utils.ValidateCep;
import com.AurumPro.utils.ValidateCnpj;
import com.AurumPro.utils.ValidateEmpresaCnpjExist;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService implements UserDetailsService {

    private final EmpresaRepository repository;
    private final ReceitaWS receitaWS;
    private final ValidateCep validateCep;
    private final ValidateCnpj validateCnpj;
    private final ValidateEmpresaCnpjExist validateEmpresaCnpjExist;
    private final ValidadeId validadeId;
    private final ViaCep viaCep;

    public EmpresaService(EmpresaRepository repository,
                          ReceitaWS receitaWS,
                          ValidateCep validateCep,
                          ValidateCnpj validateCnpj,
                          ValidateEmpresaCnpjExist validateEmpresaCnpjExist,
                          ValidadeId validadeId,
                          ViaCep viaCep) {
        this.repository = repository;
        this.receitaWS = receitaWS;
        this.validateCep = validateCep;
        this.validateCnpj = validateCnpj;
        this.validateEmpresaCnpjExist = validateEmpresaCnpjExist;
        this.validadeId = validadeId;
        this.viaCep = viaCep;
    }

    @Transactional
    public void createEmpresa(CreateEmpresaDTO dto) throws Exception {
        validateCnpj.validate(dto.cnpj());
        validateEmpresaCnpjExist.validate(dto.cnpj());

        DadosReceita dadosReceita = receitaWS
                .consultaCnpj(dto.cnpj());

        validateCep.validate(dadosReceita.cep());

        Empresa empresa = new Empresa();
        empresa.setCnpj(dto.cnpj());
        empresa.setSenha(dto.senha());
        empresa.setResponsavel(dto.responsavel());
        empresa.setInscricaoMunicipal(dto.inscricaoMunicipal());

        empresa.setEmail(dadosReceita.email());
        empresa.setTelefone(dadosReceita.telefone());

        empresa.setCep(dadosReceita.cep());

        empresa.setRua(dadosReceita.logradouro());
        empresa.setBairro(dadosReceita.bairro());
        empresa.setCidade(dadosReceita.municipio());
        empresa.setEstado(dadosReceita.uf());
        empresa.setNumero(dadosReceita.numero());

        empresa.setNome(dadosReceita.nome());

        repository.save(empresa);
    }

    public List<EmpresaDTO> findAll(){
        return repository
                .findAll()
                .stream()
                .map(dto -> new EmpresaDTO(
                        dto.getId(),
                        dto.getEmail(),
                        dto.getNome(),
                        dto.getCnpj(),
                        dto.getInscricaoMunicipal(),
                        dto.getResponsavel(),
                        dto.getTelefone(),
                        dto.getCep(),
                        dto.getRua(),
                        dto.getBairro(),
                        dto.getCidade(),
                        dto.getEstado(),
                        dto.getNumero()
                ))
                .toList();
    }

    public EmpresaDTO findById(Long id){
        return repository
                .findById(id)
                .map(dto -> new EmpresaDTO(
                        dto.getId(),
                        dto.getEmail(),
                        dto.getNome(),
                        dto.getCnpj(),
                        dto.getInscricaoMunicipal(),
                        dto.getResponsavel(),
                        dto.getTelefone(),
                        dto.getCep(),
                        dto.getRua(),
                        dto.getBairro(),
                        dto.getCidade(),
                        dto.getEstado(),
                        dto.getNumero()
                ))
                .orElseThrow(EmpresaNotFoundException::new);
    }

    @Transactional
    public void updateCepEmpresa(UpdateCepEmpresaDTO dto) throws Exception {
        Empresa empresa = validadeId.validate(dto.id(), repository);
        validateCep.validate(dto.cep());

        Endereco endereco = viaCep.viaCep(dto.cep());

        empresa.setCep(dto.cep());
        empresa.setRua(endereco.logradouro());
        empresa.setBairro(endereco.bairro());
        empresa.setCidade(endereco.localidade());
        empresa.setEstado(endereco.uf());
        empresa.setNumero(dto.numero());
    }

    @Transactional
    public void updateTelefoneEmpresa(UpdateTelefoneEmpresaDTO dto){
        Empresa empresa = validadeId.validate(dto.id(), repository);

        empresa.setTelefone(dto.telefone());
    }

    @Transactional
    public void updateEmailEmpresa(UpdateEmailEmpresaDTO dto){
        Empresa empresa = validadeId.validate(dto.id(), repository);

        empresa.setEmail(dto.email());
    }

    @Transactional
    public void deleteEmpresa(DeleteEmpresaDTO dto){
        Empresa empresa = validadeId.validate(dto.id(), repository);

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(empresa);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Empresa não encontrada"));
    }
}
