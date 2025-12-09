package com.AurumPro.services.proposta;

import com.AurumPro.dtos.cliente.ClienteDTO;
import com.AurumPro.dtos.empresa.ColaboradorDTO;
import com.AurumPro.dtos.empresa.EmpresaDTO;
import com.AurumPro.dtos.proposta.PropostaDocumentoDTO;
import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.empresa.Colaborador;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.exceptions.cliente.ClienteNotFoundException;
import com.AurumPro.exceptions.empresa.ColaboradorNotFoundException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.proposta.PropostaNotFoundException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.componentes.CustoRepository;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.empresa.ColaboradorRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.repositories.proposta.ItemPropostaRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DocumentoService {

    private final PropostaRepository propostaRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final ServicoRepository servicoRepository;
    private final CustoRepository custoRepository;
    private final ItemPropostaRepository itemPropostaRepository;

    public DocumentoService(PropostaRepository propostaRepository,
                            ClienteRepository clienteRepository,
                            EmpresaRepository empresaRepository,
                            ColaboradorRepository colaboradorRepository,
                            ServicoRepository servicoRepository,
                            CustoRepository custoRepository,
                            ItemPropostaRepository itemPropostaRepository) {
        this.propostaRepository = propostaRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.servicoRepository = servicoRepository;
        this.custoRepository = custoRepository;
        this.itemPropostaRepository = itemPropostaRepository;
    }

    public PropostaDocumentoDTO getDadosParaDocumento(Long propostaId){
        Proposta proposta = propostaRepository
                .findByPropostaId(propostaId)
                .orElseThrow(PropostaNotFoundException::new);

        Cliente cliente = clienteRepository
                .findById(proposta.getCliente().getId())
                .orElseThrow(ClienteNotFoundException::new);

        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getResponsavel(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getIdade(),
                cliente.getCpf(),
                cliente.getCnpj(),
                cliente.getCep(),
                cliente.getEstado(),
                cliente.getCidade(),
                cliente.getBairro(),
                cliente.getRua(),
                cliente.getTipoPessoa()
        );

        Empresa empresa = empresaRepository
                .findById(proposta.getEmpresa().getId())
                .orElseThrow(EmpresaNotFoundException::new);

        EmpresaDTO empresaDTO = new EmpresaDTO(
                empresa.getId(),
                empresa.getEmail(),
                empresa.getNome(),
                empresa.getCnpj(),
                empresa.getInscricaoMunicipal(),
                empresa.getResponsavel(),
                empresa.getTelefone(),
                empresa.getCep(),
                empresa.getRua(),
                empresa.getBairro(),
                empresa.getCidade(),
                empresa.getEstado(),
                empresa.getNumero()
        );

        ColaboradorDTO colaboradorDTO = null;
        if (proposta.getColaborador() != null) {
            Colaborador colaborador = colaboradorRepository
                    .findById(proposta.getColaborador().getId())
                    .orElseThrow(ColaboradorNotFoundException::new);

            colaboradorDTO = new ColaboradorDTO(
                    colaborador.getId(),
                    colaborador.getNome(),
                    colaborador.getTelefone(),
                    colaborador.getFuncao()
            );
        }

        List<Long> custoList = custoRepository
                .findByPropostaId(propostaId);

        List<ItemProposta> itemPropostaList = itemPropostaRepository
                .findByPropostaId(propostaId);

        List<Long> servicoList = itemPropostaList
                .stream()
                .map(item -> item.getServico().getId())
                .toList();

        List<Long> microServicoList = itemPropostaList
                .stream()
                .map(item -> item.getMicroServico().getId())
                .toList();

        return new PropostaDocumentoDTO(
                propostaId,
                proposta.getValorTotal(),
                clienteDTO,
                empresaDTO,
                colaboradorDTO,
                servicoList,
                microServicoList,
                custoList
        );
    }

    public byte[] gerarDocumentoWord(PropostaDocumentoDTO dto){
        try (InputStream templateStream = getClass().getResourceAsStream("/templates/Proposta.docx")) {
            assert templateStream != null;
            try (XWPFDocument doc = new XWPFDocument(templateStream);
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                gerarConteudoDocumento(doc, dto);

                doc.write(out);
                return out.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar documento Word", e);
        }
    }

    private String gerarTextoServicos(PropostaDocumentoDTO dto) {

        StringBuilder sb = new StringBuilder();

        // Buscar itens direto da proposta
        List<ItemProposta> itens = itemPropostaRepository.findByPropostaId(dto.id());

        Map<Long, List<ItemProposta>> itensPorServico = itens.stream()
                .collect(Collectors.groupingBy(item -> item.getServico().getId()));

        for (Map.Entry<Long, List<ItemProposta>> entry : itensPorServico.entrySet()) {

            Long servicoId = entry.getKey();
            Servico servico = servicoRepository.findById(servicoId).orElse(null);
            if (servico == null) continue;

            sb.append("• ").append(servico.getNome()).append("\n");

            for (ItemProposta item : entry.getValue()) {
                MicroServico micro = item.getMicroServico();
                sb.append("   - ").append(micro.getNome())
                        .append(" (R$ ").append(item.getValorTotal()).append(")\n");
            }

            sb.append("\n");
        }

        return sb.toString().trim();
    }


    private String gerarTextoCustos(PropostaDocumentoDTO dto) {

        if (dto.custoList() == null || dto.custoList().isEmpty())
            return "Nenhum custo adicional.";

        StringBuilder sb = new StringBuilder();

        for (Long id : dto.custoList()) {
            Custo c = custoRepository.findById(id).orElse(null);
            if (c == null) continue;

            sb.append("• ").append(c.getNome())
                    .append(" — R$ ").append(c.getValor()).append("\n");
        }

        return sb.toString().trim();
    }

    private void substituirVariaveis(XWPFDocument doc, Map<String, String> variaveis) {

        // Parágrafos
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            replaceInParagraph(paragraph, variaveis);
        }

        // Tabelas
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph par : cell.getParagraphs()) {
                        replaceInParagraph(par, variaveis);
                    }
                }
            }
        }
    }

    private void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> variaveis) {

        StringBuilder sb = new StringBuilder();

        // Concatena runs
        for (XWPFRun run : paragraph.getRuns()) {
            sb.append(run.getText(0));
        }

        String texto = sb.toString();

        // Substitui todas as variáveis
        for (Map.Entry<String, String> e : variaveis.entrySet()) {
            texto = texto.replace(e.getKey(), e.getValue());
        }

        // Remove todos os runs e recria o texto limpo
        int runCount = paragraph.getRuns().size();
        for (int i = runCount - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }

        XWPFRun newRun = paragraph.createRun();
        newRun.setText(texto);
    }

    private void gerarConteudoDocumento(XWPFDocument doc, PropostaDocumentoDTO dto) {

        Map<String, String> variaveis = new HashMap<>();

        ClienteDTO cliente = dto.cliente();
        EmpresaDTO empresa = dto.empresa();

        // ==== CLIENTE PF OU PJ =====

        boolean clientePF = cliente.tipoPessoa().name().equalsIgnoreCase("PF");

        if (clientePF) {
            variaveis.put("${cliente.responsavel}", "");
            variaveis.put("${documentoCliente}", cliente.cpf()); // CPF
        } else {
            variaveis.put("${cliente.responsavel}", cliente.responsavel());
            variaveis.put("${documentoCliente}", cliente.cnpj()); // CNPJ
        }

        variaveis.put("${cliente.nome}", cliente.nome());
        variaveis.put("${cliente.telefone}", cliente.telefone());
        variaveis.put("${cliente.email}", cliente.email());


        // ==== EMPRESA =====

        variaveis.put("${empresa.nome}", empresa.nome());
        variaveis.put("${empresa.cnpj}", empresa.cnpj());
        variaveis.put("${empresa.responsavel}", empresa.responsavel());
        variaveis.put("${empresa.rua}", empresa.rua());
        variaveis.put("${empresa.bairro}", empresa.bairro());
        variaveis.put("${empresa.numero}", empresa.numero());
        variaveis.put("${empresa.cidade}", empresa.cidade());
        variaveis.put("${empresa.estado}", empresa.estado());
        variaveis.put("${empresa.telefone}", empresa.telefone());


        // ==== SERVIÇOS E MICRO =====
        variaveis.put("${servicosLista}", gerarTextoServicos(dto));


        // ==== CUSTOS =====
        variaveis.put("${custosLista}", gerarTextoCustos(dto));


        // ==== INVESTIMENTO =====
        variaveis.put("${proposta.valorTotal}", dto.valorTotal().toString());


        // ==== LOCAL E DATA =====
        variaveis.put("${cidadeUfEmpresa}", empresa.cidade() + "/" + empresa.estado());
        variaveis.put("${dataCriacaoFormatada}",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


        // ==== CONSULTOR (SE NULL USA RESPONSÁVEL DA EMPRESA) =====
        String consultor = (dto.colaborador() != null)
                ? dto.colaborador().nome()
                : empresa.responsavel();

        variaveis.put("${consultorOuResponsavel}", consultor);


        // ==== APLICAR NO DOCUMENTO =====
        substituirVariaveis(doc, variaveis);
    }

    public String gerarConteudoVisual(PropostaDocumentoDTO dto) {
        StringBuilder sb = new StringBuilder();

        sb.append("TERMO DE PRESTAÇÃO DE SERVIÇOS\n\n");

        // 1. Dados do Cliente
        sb.append("1. Dados do Cliente Contratante\n");
        boolean clientePF = dto.cliente().tipoPessoa().name().equalsIgnoreCase("PF");

        if (!clientePF && dto.cliente().responsavel() != null) {
            sb.append("• Nome do Responsável: ").append(dto.cliente().responsavel()).append("\n");
        }

        sb.append("• Nome: ").append(dto.cliente().nome()).append("\n");

        String documentoCliente = clientePF ? dto.cliente().cpf() : dto.cliente().cnpj();
        sb.append("• CNPJ/CPF: ").append(documentoCliente).append("\n");
        sb.append("• Telefone: ").append(dto.cliente().telefone()).append("\n");
        sb.append("• E-mail: ").append(dto.cliente().email()).append("\n\n");

        // 2. Dados da Empresa
        sb.append("2. Dados da Empresa Contratada\n");
        sb.append("Empresa: ").append(dto.empresa().nome()).append("\n");
        sb.append("CNPJ: ").append(dto.empresa().cnpj()).append("\n");
        sb.append("Responsável: ").append(dto.empresa().responsavel()).append("\n");
        sb.append("Endereço: ").append(dto.empresa().rua()).append(", ")
                .append(dto.empresa().bairro()).append(", ")
                .append(dto.empresa().numero()).append(". ")
                .append(dto.empresa().cidade()).append("/")
                .append(dto.empresa().estado()).append("\n");
        sb.append("Telefone: ").append(dto.empresa().telefone()).append("\n\n");

        // 3. Serviços
        sb.append("3. Serviços e Subserviços Contratados\n");
        sb.append(gerarTextoServicos(dto)).append("\n\n");

        // 4. Custos
        sb.append("4. Custos adicionais\n");
        sb.append(gerarTextoCustos(dto)).append("\n\n");

        // 5. Investimento
        sb.append("5. Investimento\n");
        sb.append("• Valor proposto: R$ ").append(dto.valorTotal()).append("\n");
        sb.append("• Forma de pagamento: ___________________________\n");
        sb.append("• PIX para pagamento: \n\n");

        // 6. Condições
        sb.append("6. Condições Gerais\n");
        sb.append("O início do atendimento será a partir do dia: _________________________.\n");
        sb.append(dto.empresa().cidade()).append("/")
                .append(dto.empresa().estado()).append(", ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n\n\n");

        // Assinaturas
        sb.append("Cliente: ___________________________________________\n");
        String consultor = (dto.colaborador() != null)
                ? dto.colaborador().nome()
                : dto.empresa().responsavel();
        sb.append("Consultor(a): ").append(consultor).append("\n");

        return sb.toString();
    }
}
