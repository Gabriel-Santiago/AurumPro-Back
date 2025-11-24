package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.microServico.CreateMicroServicoDTO;
import com.AurumPro.dtos.componentes.microServico.MicroServicoDTO;
import com.AurumPro.dtos.componentes.microServico.UpdateValoresMicroServicoDTO;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.componentes.MicroServicoNotFoundEmpresaException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.componentes.MicroServicoRepository;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MicroServicoService {

    private final EmpresaRepository empresaRepository;
    private final MicroServicoRepository repository;
    private final ServicoRepository servicoRepository;

    public MicroServicoService(EmpresaRepository empresaRepository,
                               MicroServicoRepository repository,
                               ServicoRepository servicoRepository) {
        this.empresaRepository = empresaRepository;
        this.repository = repository;
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public void createMicroServico(CreateMicroServicoDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        Servico servico = servicoRepository
                .findById(dto.servicoId())
                .orElseThrow(MicroServicoNotFoundEmpresaException::new);

        MicroServico microServico = new MicroServico();
        microServico.setNome(dto.nome());
        microServico.setValorHora(dto.valorHora());
        microServico.setQtdHora(dto.qtdHora());
        microServico.setDescricao(dto.descricao());

        BigDecimal valorTotal = dto.valorHora().multiply(dto.qtdHora());
        microServico.setValorTotal(valorTotal);

        microServico.setEmpresa(empresa);
        microServico.setServico(servico);

        repository.save(microServico);
    }

    public List<MicroServicoDTO> findAll(Long servicoId){
        List<MicroServico> microServicoList = repository.findByServicoId(servicoId);

        return microServicoList
                .stream()
                .map(dto -> new MicroServicoDTO(
                        dto.getNome()
                ))
                .toList();
    }

    @Transactional
    public void updateMicroServico(UpdateValoresMicroServicoDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.empresaId())
                .orElseThrow(EmpresaNotFoundException::new);

        MicroServico microServico = repository
                .findById(dto.id())
                .orElseThrow(MicroServicoNotFoundEmpresaException::new);

        if (!microServico.getEmpresa().getId().equals(empresa.getId())){
            throw new MicroServicoNotFoundEmpresaException();
        }

        microServico.setValorTotal(dto.valorHora());
        microServico.setQtdHora(dto.qtdHora());

        BigDecimal valorTotal = dto.valorHora().multiply(dto.qtdHora());

        microServico.setValorTotal(valorTotal);
    }
}
