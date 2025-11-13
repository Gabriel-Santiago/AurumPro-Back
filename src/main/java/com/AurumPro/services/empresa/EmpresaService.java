package com.AurumPro.services.empresa;

import com.AurumPro.apis.DadosReceita;
import com.AurumPro.apis.Endereco;
import com.AurumPro.apis.ReceitaWS;
import com.AurumPro.apis.ViaCep;
import com.AurumPro.dtos.empresa.CreateEmpresaDTO;
import com.AurumPro.dtos.empresa.DeleteEmpresaDTO;
import com.AurumPro.dtos.empresa.EmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateCepEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateEmailEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateTelefoneEmpresaDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.empresa.IdEmpresaNotFoundException;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.exceptions.endereco.CepIsEmptyException;
import com.AurumPro.exceptions.endereco.CepNotFoundException;
import com.AurumPro.exceptions.empresa.CnpjExistException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository repository;
    private final ReceitaWS receitaWS;
    private final ViaCep viaCep;

    public EmpresaService(EmpresaRepository repository,
                          ReceitaWS receitaWS,
                          ViaCep viaCep) {
        this.repository = repository;
        this.receitaWS = receitaWS;
        this.viaCep = viaCep;
    }

    @Transactional
    public void createEmpresa(CreateEmpresaDTO dto) throws Exception {
        if(repository.existsByCnpj(dto.cnpj())){
            throw new CnpjExistException();
        }

        DadosReceita dadosReceita = receitaWS
                .consultaCnpj(dto.cnpj());

        Empresa empresa = new Empresa();
        empresa.setCnpj(dto.cnpj());
        empresa.setSenha(dto.senha());
        empresa.setResponsavel(dto.responsavel());
        empresa.setInscricaoMunicipal(dto.inscricaoMunicipal());

        empresa.setEmail(dadosReceita.email());
        empresa.setTelefone(dadosReceita.telefone());

        empresa.setCep(dadosReceita.cep());

        validateCep(dadosReceita.cep());

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
        validateId(dto.id());
        validateCep(dto.cep());

        Endereco endereco = viaCep.viaCep(dto.cep());

        Empresa empresa = new Empresa();

        empresa.setCep(dto.cep());
        empresa.setRua(endereco.logradouro());
        empresa.setBairro(endereco.bairro());
        empresa.setCidade(endereco.localidade());
        empresa.setEstado(endereco.uf());
        empresa.setNumero(dto.numero());

        repository.save(empresa);
    }

    @Transactional
    public void updateTelefoneEmpresa(UpdateTelefoneEmpresaDTO dto){
        validateId(dto.id());

        Empresa empresa = new Empresa();

        empresa.setTelefone(dto.telefone());

        repository.save(empresa);
    }

    @Transactional
    public void updateEmailEmpresa(UpdateEmailEmpresaDTO dto){
        validateId(dto.id());

        Empresa empresa = new Empresa();

        empresa.setEmail(dto.email());

        repository.save(empresa);
    }

    @Transactional
    public void deleteEmpresa(DeleteEmpresaDTO dto){
        Empresa empresa = validateId(dto.id());

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(empresa);
    }

    private void validateCep(String cep){
        if(cep == null){
            throw new CepNotFoundException();
        }

        if(cep.trim().isEmpty()){
            throw new CepIsEmptyException();
        }
    }

    private Empresa validateId(Long id){
        return repository.findById(id)
                .orElseThrow(IdEmpresaNotFoundException::new);
    }
}
