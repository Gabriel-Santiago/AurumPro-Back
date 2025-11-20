package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.microServico.CreateMicroServicoDTO;
import com.AurumPro.dtos.componentes.microServico.MicroServicoDTO;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.repositories.componentes.MicroServicoRepository;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MicroServicoServiceTest {

    @Mock
    private MicroServicoRepository repository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private MicroServicoService service;

    @Test
    void createMicroServico() {
        Long empresaId = 1L;
        Long servicoId = 2L;
        CreateMicroServicoDTO dto = new CreateMicroServicoDTO(
                empresaId,
                servicoId,
                "Desenvolvimento API",
                new BigDecimal("100.00"),
                new BigDecimal("10.0")
        );

        Empresa empresa = new Empresa();
        empresa.setId(empresaId);

        Servico servico = new Servico();
        servico.setId(servicoId);

        BigDecimal valorTotalEsperado = new BigDecimal("1000.00");

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(servicoRepository.findById(servicoId)).thenReturn(Optional.of(servico));
        when(repository.save(any(MicroServico.class))).thenAnswer(invocation ->
            invocation.getArgument(0));

        service.createMicroServico(dto);

        verify(empresaRepository).findById(empresaId);
        verify(servicoRepository).findById(servicoId);

        ArgumentCaptor<MicroServico> microServicoCaptor = ArgumentCaptor.forClass(MicroServico.class);

        verify(repository).save(microServicoCaptor.capture());

        MicroServico savedMicroServico = microServicoCaptor.getValue();

        assertEquals(dto.nome(), savedMicroServico.getNome());
        assertEquals(0, dto.valorHora().compareTo(savedMicroServico.getValorHora()));
        assertEquals(0, dto.qtdHora().compareTo(savedMicroServico.getQtdHora()));
        assertEquals(0, valorTotalEsperado.compareTo(savedMicroServico.getValorTotal()));
        assertEquals(empresa, savedMicroServico.getEmpresa());
        assertEquals(servico, savedMicroServico.getServico());
    }

    @Test
    void findAll() {
        Long servicoId = 1L;

        MicroServico microServico1 = new MicroServico();
        microServico1.setId(1L);
        microServico1.setNome("Desenvolvimento Backend");

        MicroServico microServico2 = new MicroServico();
        microServico2.setId(2L);
        microServico2.setNome("Desenvolvimento Frontend");

        List<MicroServico> microServicos = List.of(microServico1, microServico2);

        when(repository.findByServicoId(servicoId)).thenReturn(microServicos);

        List<MicroServicoDTO> result = service.findAll(servicoId);

        assertNotNull(result);
        assertEquals(2, result.size());

        MicroServicoDTO dto1 = result.get(0);
        assertEquals("Desenvolvimento Backend", dto1.nome());

        MicroServicoDTO dto2 = result.get(1);
        assertEquals("Desenvolvimento Frontend", dto2.nome());

        verify(repository).findByServicoId(servicoId);
    }
}