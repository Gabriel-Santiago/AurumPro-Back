package com.AurumPro.services.cliente;

import com.AurumPro.api.DadosReceita;
import com.AurumPro.api.Endereco;
import com.AurumPro.api.ReceitaWS;
import com.AurumPro.api.ViaCep;
import com.AurumPro.dtos.cliente.ClienteDTO;
import com.AurumPro.dtos.cliente.CreatePessoaFisicaDTO;
import com.AurumPro.dtos.cliente.CreatePessoaJuridicaDTO;
import com.AurumPro.dtos.cliente.DeleteClienteDTO;
import com.AurumPro.dtos.cliente.FindTipoPessoabyUfDTO;
import com.AurumPro.dtos.cliente.UpdateEmailAndTelefoneClienteDTO;
import com.AurumPro.dtos.cliente.UpdateEnderecoClienteDTO;
import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.enums.TipoPessoa;
import com.AurumPro.exceptions.cliente.ClienteNotAssociatedToEmpresaException;
import com.AurumPro.exceptions.cliente.ClienteNotFoundException;
import com.AurumPro.exceptions.cliente.PessoaJuridicaNotAssociatedToEmpresaException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.empresa.SenhaEmpresaIncorretException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final CalculateAge calculateAge;
    private final ClienteRepository repository;
    private final EmpresaRepository empresaRepository;
    private final ReceitaWS receitaWS;
    private final ValidateCep validateCep;
    private final ValidateCpf validateCpf;
    private final ValidateCnpj validateCnpj;
    private final ValidateClienteCnpjExist validateClienteCnpjExist;
    private final ValidadeId validadeId;
    private final ViaCep viaCep;

    public ClienteService(CalculateAge calculateAge,
                          ClienteRepository repository,
                          EmpresaRepository empresaRepository,
                          ReceitaWS receitaWS,
                          ValidateCep validateCep,
                          ValidateCpf validateCpf,
                          ValidateCnpj validateCnpj,
                          ValidateClienteCnpjExist validateClienteCnpjExist,
                          ValidadeId validadeId,
                          ViaCep viaCep) {
        this.calculateAge = calculateAge;
        this.repository = repository;
        this.empresaRepository = empresaRepository;
        this.receitaWS = receitaWS;
        this.validateCep = validateCep;
        this.validateCpf = validateCpf;
        this.validateCnpj = validateCnpj;
        this.validateClienteCnpjExist = validateClienteCnpjExist;
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

        Cliente pessoaFisica = new Cliente();
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
        pessoaFisica.setTipoPessoa(TipoPessoa.PF);

        repository.save(pessoaFisica);
    }

    @Transactional
    public void createPessoaJuridica(CreatePessoaJuridicaDTO dto) throws Exception {
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        validateCnpj.validate(dto.cnpj());
        validateClienteCnpjExist.validate(dto.cnpj());

        DadosReceita dadosReceita = receitaWS.consultaCnpj(dto.cnpj());

        validateCep.validate(dadosReceita.cep());

        Cliente pessoaJuridica = getPessoaJuridica(dto, dadosReceita, empresa);

        repository.save(pessoaJuridica);
    }

    public List<ClienteDTO> findAllCliente(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return repository
                .findAll()
                .stream()
                .map(dto -> new ClienteDTO(
                        dto.getId(),
                        dto.getResponsavel(),
                        dto.getNome(),
                        dto.getEmail(),
                        dto.getTelefone(),
                        dto.getIdade(),
                        dto.getCpf(),
                        dto.getCnpj(),
                        dto.getCep(),
                        dto.getEstado(),
                        dto.getCidade(),
                        dto.getBairro(),
                        dto.getRua(),
                        dto.getTipoPessoa()
                ))
                .toList();
    }

    public List<ClienteDTO> findClienteByTipoPessoaAndUf(FindTipoPessoabyUfDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        List<Cliente> clienteList = repository.findTipoPessoaByEstado(empresa, dto.uf(), dto.tipoPessoa());

        return clienteList
                .stream()
                .map(c -> new ClienteDTO(
                        c.getId(),
                        c.getResponsavel(),
                        c.getNome(),
                        c.getEmail(),
                        c.getTelefone(),
                        c.getIdade(),
                        c.getCpf(),
                        c.getCnpj(),
                        c.getCep(),
                        c.getEstado(),
                        c.getCidade(),
                        c.getBairro(),
                        c.getRua(),
                        c.getTipoPessoa()
                ))
                .toList();
    }

    @Transactional
    public void updateEmailAndTelefoneCliente(UpdateEmailAndTelefoneClienteDTO dto){
        Cliente cliente = validarTipoPessoa(dto.id(), dto.tipoPessoa());

        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
    }

    @Transactional
    public void updateEnderecoCliente(UpdateEnderecoClienteDTO dto) throws Exception {
        Cliente cliente = validarTipoPessoa(dto.id(), dto.tipoPessoa());
        validateCep.validate(dto.cep());

        Endereco endereco = viaCep.viaCep(dto.cep());

        cliente.setCep(dto.cep());
        cliente.setRua(endereco.logradouro());
        cliente.setBairro(endereco.bairro());
        cliente.setCidade(endereco.localidade());
        cliente.setEstado(endereco.uf());
        cliente.setNumero(dto.numero());
    }

    @Transactional
    public void deletePessoa(DeleteClienteDTO dto){
        Cliente cliente = validarTipoPessoa(dto.id(), dto.tipoPessoa());
        Empresa empresa = validadeId.validate(dto.empresaId(), empresaRepository);

        if (!cliente.getEmpresa().getId().equals(empresa.getId())){
            throw new ClienteNotAssociatedToEmpresaException();
        }

        if(!empresa.getSenha().equals(dto.senha())){
            throw new SenhaEmpresaIncorretException();
        }

        repository.delete(cliente);
    }

    public ClienteDTO findById(Long id){
        return repository
                .findById(id)
                .map(dto -> new ClienteDTO(
                        dto.getId(),
                        dto.getResponsavel(),
                        dto.getNome(),
                        dto.getEmail(),
                        dto.getTelefone(),
                        dto.getIdade(),
                        dto.getCpf(),
                        dto.getCnpj(),
                        dto.getCep(),
                        dto.getEstado(),
                        dto.getCidade(),
                        dto.getBairro(),
                        dto.getRua(),
                        dto.getTipoPessoa()
                ))
                .orElseThrow(ClienteNotFoundException::new);
    }

    private static Cliente getPessoaJuridica(CreatePessoaJuridicaDTO dto, DadosReceita dadosReceita, Empresa empresa) {
        Cliente pessoaJuridica = new Cliente();
        pessoaJuridica.setEmail(dto.email());
        pessoaJuridica.setCnpj(dto.cnpj());
        pessoaJuridica.setNome(dadosReceita.nome());
        pessoaJuridica.setResponsavel(dto.responsavel());
        pessoaJuridica.setTelefone(dadosReceita.telefone());

        pessoaJuridica.setCep(dadosReceita.cep());
        pessoaJuridica.setRua(dadosReceita.logradouro());
        pessoaJuridica.setBairro(dadosReceita.bairro());
        pessoaJuridica.setCidade(dadosReceita.municipio());
        pessoaJuridica.setEstado(dadosReceita.uf());
        pessoaJuridica.setNumero(dto.numero());

        pessoaJuridica.setEmpresa(empresa);
        pessoaJuridica.setTipoPessoa(TipoPessoa.PJ);

        return pessoaJuridica;
    }

    private Cliente validarTipoPessoa(Long id, TipoPessoa tipoPessoa) {
        Cliente c = validadeId.validate(id, repository);

        if (c.getTipoPessoa() != tipoPessoa) {
            if (tipoPessoa == TipoPessoa.PF) {
                throw new ClienteNotAssociatedToEmpresaException();
            } else {
                throw new PessoaJuridicaNotAssociatedToEmpresaException();
            }
        }

        return c;
    }

}
