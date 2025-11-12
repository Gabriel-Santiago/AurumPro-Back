package com.AurumPro.services.empresa;

import com.AurumPro.apis.DadosReceita;
import com.AurumPro.apis.ReceitaWS;
import com.AurumPro.apis.ViaCep;
import com.AurumPro.dtos.empresa.CreateEmpresaDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        empresa.setRua(dadosReceita.logradouro());
        empresa.setBairro(dadosReceita.bairro());
        empresa.setCidade(dadosReceita.municipio());
        empresa.setEstado(dadosReceita.uf());
        empresa.setNumero(Integer.valueOf(dadosReceita.numero()));

        empresa.setNome(dadosReceita.nome());

        repository.save(empresa);
    }
}
