package com.AurumPro.services.proposta;

import com.AurumPro.dtos.proposta.CreateItemPropostaDTO;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.repositories.componentes.MicroServicoRepository;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.proposta.ItemPropostaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemPropostaServiceTest {

    @Mock
    private ItemPropostaRepository repository;

    @Mock
    private MicroServicoRepository microServicoRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ItemPropostaService service;

    @Test
    void createItemProposta() {
        Servico servico = new Servico();
        servico.setId(10L);

        when(servicoRepository.findById(10L)).thenReturn(Optional.of(servico));

        MicroServico m1 = new MicroServico();
        m1.setId(1L);
        m1.setValorTotal(new BigDecimal("100"));

        MicroServico m2 = new MicroServico();
        m2.setId(2L);
        m2.setValorTotal(new BigDecimal("200"));

        MicroServico m3 = new MicroServico();
        m3.setId(3L);
        m3.setValorTotal(new BigDecimal("300"));

        CreateItemPropostaDTO dto = new CreateItemPropostaDTO(
                servico.getId(),
                List.of(m1.getId(), m2.getId(), m3.getId())
        );

        when(microServicoRepository.findAllById(List.of(1L, 2L, 3L)))
                .thenReturn(List.of(m1, m2, m3));

        service.createItemProposta(dto);

        verify(repository, times(3)).save(any(ItemProposta.class));

        ArgumentCaptor<ItemProposta> captor = ArgumentCaptor.forClass(ItemProposta.class);
        verify(repository, times(3)).save(captor.capture());

        List<ItemProposta> itensSalvos = captor.getAllValues();

        assertEquals(3, itensSalvos.size());

        assertEquals(servico, itensSalvos.get(0).getServico());
        assertEquals(servico, itensSalvos.get(1).getServico());
        assertEquals(servico, itensSalvos.get(2).getServico());

        assertEquals(new BigDecimal("100"), itensSalvos.get(0).getValorTotal());
        assertEquals(new BigDecimal("200"), itensSalvos.get(1).getValorTotal());
        assertEquals(new BigDecimal("300"), itensSalvos.get(2).getValorTotal());
    }
}