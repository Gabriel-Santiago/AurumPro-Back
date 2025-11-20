package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.custo.CreateCustoDTO;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.repositories.componentes.CustoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustoServiceTest {

    @Mock
    private CustoRepository repository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private PropostaRepository propostaRepository;

    @InjectMocks
    private CustoService service;

    @Test
    void createCusto() {
        Long empresaId = 1L;
        Long propostaId = 2L;
        CreateCustoDTO dto = new CreateCustoDTO(
                empresaId,
                propostaId,
                "Custo Teste",
                new BigDecimal("1500.50"));

        Empresa empresa = new Empresa();
        empresa.setId(empresaId);
        empresa.setNome("Empresa Teste");

        Proposta proposta = new Proposta();
        proposta.setPropostaId(propostaId);

        Custo custoSalvo = new Custo();
        custoSalvo.setId(1L);
        custoSalvo.setNome(dto.nome());
        custoSalvo.setValor(dto.valor());
        custoSalvo.setEmpresa(empresa);
        custoSalvo.setProposta(proposta);

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(propostaRepository.findById(propostaId)).thenReturn(Optional.of(proposta));
        when(repository.save(any(Custo.class))).thenReturn(custoSalvo);

        service.createCusto(dto);

        verify(empresaRepository).findById(empresaId);
        verify(propostaRepository).findById(propostaId);
        verify(repository).save(any(Custo.class));

        verify(repository).save(argThat(custo ->
                custo.getNome().equals(dto.nome()) &&
                        custo.getValor().equals(dto.valor()) &&
                        custo.getEmpresa().equals(empresa) &&
                        custo.getProposta().equals(proposta)
        ));
    }
}