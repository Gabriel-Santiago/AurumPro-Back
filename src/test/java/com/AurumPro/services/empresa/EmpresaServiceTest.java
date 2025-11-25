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
import com.AurumPro.exceptions.empresa.CnpjExistException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.ValidadeId;
import com.AurumPro.utils.ValidateCep;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository repository;

    @Mock
    private ReceitaWS receitaWS;

    @Mock
    private ValidateCep validateCep;

    @Mock
    private ValidadeId validadeId;

    @Mock
    private ViaCep viaCep;

    @InjectMocks
    private EmpresaService service;

    private Empresa createMockEmpresa(Long id) {
        Empresa empresa = new Empresa();
        empresa.setId(id);
        empresa.setEmail("empresa@teste.com");
        empresa.setNome("Empresa Teste LTDA");
        empresa.setCnpj("12345678000195");
        empresa.setInscricaoMunicipal("123456");
        empresa.setResponsavel("João Silva");
        empresa.setTelefone("11999999999");
        empresa.setCep("01414001");
        empresa.setRua("Rua Teste");
        empresa.setBairro("Bairro Teste");
        empresa.setCidade("São Paulo");
        empresa.setEstado("SP");
        empresa.setNumero("123");
        empresa.setSenha("senha123");
        return empresa;
    }

    @Test
    void createEmpresa_whenValidData() throws Exception {
        CreateEmpresaDTO dto = new CreateEmpresaDTO(
                "12345678000195",
                "senha123",
                "João Silva",
                "123456"
        );

        DadosReceita dadosReceita = new DadosReceita(
                "12345678000195",
                "Empresa Teste LTDA",
                "01414001",
                "Rua Teste",
                "Bairro Teste",
                "São Paulo",
                "SP",
                "123",
                "empresa@teste.com",
                "11999999999"
        );

        when(repository.existsByCnpj(dto.cnpj())).thenReturn(false);
        when(receitaWS.consultaCnpj(dto.cnpj())).thenReturn(dadosReceita);
        doNothing().when(validateCep).validate(dadosReceita.cep());
        when(repository.save(any(Empresa.class))).thenAnswer(invocationOnMock -> {
           Empresa empresa = invocationOnMock.getArgument(0);
           empresa.setId(1L);
           return empresa;
        });

        assertDoesNotThrow(() -> service.createEmpresa(dto));

        verify(repository).existsByCnpj(dto.cnpj());
        verify(receitaWS).consultaCnpj(dto.cnpj());
        verify(validateCep).validate(dadosReceita.cep());
        verify(repository).save(any(Empresa.class));
    }

    @Test
    void createEmpresa_whenCnpjAlreadyExists() {
        CreateEmpresaDTO dto = new CreateEmpresaDTO(
                "12345678000195",
                "senha123",
                "João Silva",
                "123456"
        );

        when(repository.existsByCnpj(dto.cnpj())).thenReturn(true);

        assertThrows(CnpjExistException.class, () -> service.createEmpresa(dto));

        verify(repository).existsByCnpj(dto.cnpj());
        verify(repository, never()).save(any(Empresa.class));
    }

    @Test
    void findAll_shouldReturnList() {
        Empresa empresa1 = createMockEmpresa(1L);
        Empresa empresa2 = createMockEmpresa(2L);
        List<Empresa> empresaList = List.of(empresa1, empresa2);

        when(repository.findAll()).thenReturn(empresaList);

        List<EmpresaDTO> empresaDTOList = service.findAll();

        assertNotNull(empresaDTOList);
        assertEquals(2, empresaDTOList.size());

        EmpresaDTO dto = empresaDTOList.getFirst();
        assertEquals(empresa1.getId(), dto.id());
        assertEquals(empresa1.getEmail(), dto.email());
        assertEquals(empresa1.getNome(), dto.nome());

        verify(repository).findAll();
    }

    @Test
    void findAll_shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<EmpresaDTO> empresaDTOList = service.findAll();

        assertNotNull(empresaDTOList);
        assertTrue(empresaDTOList.isEmpty());

        verify(repository).findAll();
    }

    @Test
    void findById_whenEmpresaExists() {
        Long id = 1L;
        Empresa empresa = createMockEmpresa(id);

        when(repository.findById(id)).thenReturn(Optional.of(empresa));

        EmpresaDTO empresaDTO = service.findById(id);

        assertNotNull(empresaDTO);
        assertEquals(empresa.getId(), empresaDTO.id());
        assertEquals(empresa.getEmail(), empresaDTO.email());
        assertEquals(empresa.getNome(), empresaDTO.nome());
        assertEquals(empresa.getCnpj(), empresaDTO.cnpj());

        verify(repository).findById(id);
    }

    @Test
    void findById_whenEmpresaNotExists() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmpresaNotFoundException.class, () -> service.findById(id));

        verify(repository).findById(id);
    }

    @Test
    void updateCepEmpresa_whenCepValid() throws Exception {
        UpdateCepEmpresaDTO dto = new UpdateCepEmpresaDTO(1L, "01414001", "123");
        Empresa empresa = createMockEmpresa(1L);
        Endereco endereco = new Endereco("01414001","Rua Nova", "Bairro Novo", "São Paulo", "SP");

        when(validadeId.validate(dto.id(), repository)).thenReturn(empresa);
        doNothing().when(validateCep).validate(dto.cep());
        when(viaCep.viaCep(dto.cep())).thenReturn(endereco);
        when(repository.save(empresa)).thenReturn(empresa);

        service.updateCepEmpresa(dto);

        assertEquals(dto.cep(), empresa.getCep());
        assertEquals(endereco.logradouro(), empresa.getRua());
        assertEquals(endereco.bairro(), empresa.getBairro());
        assertEquals(endereco.localidade(), empresa.getCidade());
        assertEquals(endereco.uf(), empresa.getEstado());
        assertEquals(dto.numero(), empresa.getNumero());

        verify(validadeId).validate(dto.id(), repository);
        verify(validateCep).validate(dto.cep());
        verify(viaCep).viaCep(dto.cep());
        verify(repository).save(empresa);
    }

    @Test
    void updateTelefoneEmpresa() {
        UpdateTelefoneEmpresaDTO dto = new UpdateTelefoneEmpresaDTO(1L, "11988887777");
        Empresa empresa = createMockEmpresa(1L);

        when(validadeId.validate(dto.id(), repository)).thenReturn(empresa);
        when(repository.save(empresa)).thenReturn(empresa);

        service.updateTelefoneEmpresa(dto);

        assertEquals(dto.telefone(), empresa.getTelefone());
        verify(validadeId).validate(dto.id(), repository);
        verify(repository).save(empresa);
    }

    @Test
    void updateEmailEmpresa() {
        UpdateEmailEmpresaDTO dto = new UpdateEmailEmpresaDTO(1L, "novoemail@teste.com");
        Empresa empresa = createMockEmpresa(1L);

        when(validadeId.validate(dto.id(), repository)).thenReturn(empresa);
        when(repository.save(empresa)).thenReturn(empresa);

        service.updateEmailEmpresa(dto);

        assertEquals(dto.email(), empresa.getEmail());
        verify(validadeId).validate(dto.id(), repository);
        verify(repository).save(empresa);
    }

    @Test
    void deleteEmpresa_whenCorrectPassword() {
        DeleteEmpresaDTO dto = new DeleteEmpresaDTO(1L, "senha123");
        Empresa empresa = createMockEmpresa(1L);

        when(validadeId.validate(dto.id(), repository)).thenReturn(empresa);
        doNothing().when(repository).delete(empresa);

        service.deleteEmpresa(dto);

        verify(validadeId).validate(dto.id(), repository);
        verify(repository).delete(empresa);
    }

    @Test
    void deleteEmpresa_whenIncorrectPassword() {
        DeleteEmpresaDTO dto = new DeleteEmpresaDTO(1L, "senhaErrada");
        Empresa empresa = createMockEmpresa(1L);

        when(validadeId.validate(dto.id(), repository)).thenReturn(empresa);

        assertThrows(SenhaEmpresaIncorretException.class, () -> service.deleteEmpresa(dto));

        verify(validadeId).validate(dto.id(), repository);
        verify(repository, never()).delete(any());
    }
}