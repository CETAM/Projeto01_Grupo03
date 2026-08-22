package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.dto.CategoriaDistribuicaoDTO;
import cetam.projeto01grupo03.dto.DashboardDTO;
import cetam.projeto01grupo03.dto.LivroMaisEmprestadoDTO;
import cetam.projeto01grupo03.dto.RelatorioGeralDTO;
import cetam.projeto01grupo03.dto.ResumoFinanceiroDTO;
import cetam.projeto01grupo03.model.Emprestimo;
import cetam.projeto01grupo03.model.Multa;
import cetam.projeto01grupo03.model.StatusEmprestimo;
import cetam.projeto01grupo03.model.StatusMulta;
import cetam.projeto01grupo03.repository.AlunoRepository;
import cetam.projeto01grupo03.repository.EmprestimoRepository;
import cetam.projeto01grupo03.repository.LivroRepository;
import cetam.projeto01grupo03.repository.MultaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    private final EmprestimoRepository emprestimoRepository;
    private final MultaRepository multaRepository;
    private final LivroRepository livroRepository;
    private final AlunoRepository alunoRepository;
    private final EmprestimoService emprestimoService;

    public RelatorioService(EmprestimoRepository emprestimoRepository,
                            MultaRepository multaRepository,
                            LivroRepository livroRepository,
                            AlunoRepository alunoRepository,
                            EmprestimoService emprestimoService) {
        this.emprestimoRepository = emprestimoRepository;
        this.multaRepository = multaRepository;
        this.livroRepository = livroRepository;
        this.alunoRepository = alunoRepository;
        this.emprestimoService = emprestimoService;
    }

    @Transactional
    public DashboardDTO obterDadosDashboard() {

        emprestimoService.listarTodos(null);

        DashboardDTO dto = new DashboardDTO();

        long totalLivros = livroRepository.count();
        long totalExemplares = livroRepository.sumTotalExemplares();
        long totalAlunos = alunoRepository.count();
        long totalAtivos = emprestimoRepository.countByStatus(StatusEmprestimo.ATIVO);
        long totalAtrasados = emprestimoRepository.countByStatus(StatusEmprestimo.ATRASADO);
        long totalDevolvidos = emprestimoRepository.countByStatus(StatusEmprestimo.DEVOLVIDO);
        long totalDevolucoesHoje =  emprestimoRepository.countByDataPrevisaoDevolucaoAndStatusNot(LocalDate.now(), StatusEmprestimo.DEVOLVIDO);

        dto.setTotalLivros(totalLivros);
        dto.setTotalExemplares(totalExemplares);
        dto.setTotalAlunos(totalAlunos);
        dto.setTotalEmprestimosAtivos(totalAtivos);
        dto.setTotalEmprestimosAtrasados(totalAtrasados);
        dto.setTotalEmprestimosDevolvidos(totalDevolvidos);
        dto.setTotalDevolucoesHoje(totalDevolucoesHoje);

        BigDecimal multasPendentesValor = multaRepository.sumValorByStatus(StatusMulta.PENDENTE);
        long multasPendentesQtd = multaRepository.countByStatus(StatusMulta.PENDENTE);
        dto.setTotalMultasPendentesValor(multasPendentesValor);
        dto.setTotalMultasPendentesQtd(multasPendentesQtd);

        if (totalExemplares > 0) {
            double taxa = (totalAtivos * 100.0) / totalExemplares;
            dto.setTaxaCirculacao(Math.round(taxa * 10.0) / 10.0);
        } else {
            dto.setTaxaCirculacao(0.0);
        }

        dto.setUltimosEmprestimos(emprestimoRepository.findAllByOrderByIdDesc(PageRequest.of(0, 5)));
        List<Emprestimo> todosAtrasados = emprestimoRepository.findByStatus(StatusEmprestimo.ATRASADO);
        dto.setEmprestimosCriticosAtrasados(todosAtrasados.stream().limit(5).toList());
        dto.setTopLivros(obterLivrosMaisEmprestados(5));

        List<String> diasLabels = new ArrayList<>();
        List<Long> emprestimosPorDia = new ArrayList<>();
        List<Long> devolucoesPorDia = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM");

        LocalDate hoje = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate dia = hoje.minusDays(i);
            diasLabels.add(dia.format(dtf));
            emprestimosPorDia.add(emprestimoRepository.countByDataEmprestimo(dia));
            devolucoesPorDia.add(emprestimoRepository.countByDataDevolucao(dia));
        }
        dto.setDiasGraficoLabels(diasLabels);
        dto.setEmprestimosPorDia(emprestimosPorDia);
        dto.setDevolucoesPorDia(devolucoesPorDia);

        List<CategoriaDistribuicaoDTO> categorias = livroRepository.findDistribuicaoPorCategoria();
        List<String> catLabels = new ArrayList<>();
        List<Long> catValores = new ArrayList<>();
        for (CategoriaDistribuicaoDTO cat : categorias.stream().limit(6).toList()) {
            catLabels.add(cat.getCategoria());
            catValores.add(cat.getQuantidade());
        }
        dto.setCategoriasLabels(catLabels);
        dto.setCategoriasValores(catValores);

        return dto;
    }

    @Transactional(readOnly = true)
    public RelatorioGeralDTO obterResumoGeral() {
        long totalLivros = livroRepository.count();
        long totalAlunos = alunoRepository.count();
        long totalAtivos = emprestimoRepository.countByStatus(StatusEmprestimo.ATIVO);
        long totalAtrasados = emprestimoRepository.countByStatus(StatusEmprestimo.ATRASADO);
        long totalDevolvidos = emprestimoRepository.countByStatus(StatusEmprestimo.DEVOLVIDO);
        long totalMultasPendentes = multaRepository.countByStatus(StatusMulta.PENDENTE);

        return new RelatorioGeralDTO(
                totalLivros,
                totalAlunos,
                totalAtivos,
                totalAtrasados,
                totalDevolvidos,
                totalMultasPendentes
        );
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> filtrarEmprestimos(LocalDate dataInicio, LocalDate dataFim, StatusEmprestimo status) {
        if (dataInicio != null && dataFim != null) {
            if (status != null) {
                return emprestimoRepository.findByDataEmprestimoBetweenAndStatus(dataInicio, dataFim, status);
            }
            return emprestimoRepository.findByDataEmprestimoBetween(dataInicio, dataFim);
        }

        if (status != null) {
            return emprestimoRepository.findByStatus(status);
        }

        return emprestimoRepository.findAll();
    }

    @Transactional
    public List<Emprestimo> obterEmprestimosAtrasados() {
        // Atualiza status de eventuais empréstimos que venceram
        emprestimoService.listarTodos(null);
        return emprestimoRepository.findByStatus(StatusEmprestimo.ATRASADO);
    }

    @Transactional(readOnly = true)
    public List<LivroMaisEmprestadoDTO> obterLivrosMaisEmprestados(int limite) {
        int limitVal = (limite > 0) ? limite : 10;
        return emprestimoRepository.findLivrosMaisEmprestados(PageRequest.of(0, limitVal));
    }

    @Transactional(readOnly = true)
    public ResumoFinanceiroDTO obterResumoFinanceiro(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio != null && dataFim != null) {
            BigDecimal totalGerado = multaRepository.sumTotalValorBetween(dataInicio, dataFim);
            BigDecimal totalPago = multaRepository.sumValorByStatusAndDataGeracaoBetween(StatusMulta.PAGO, dataInicio, dataFim);
            BigDecimal totalPendente = multaRepository.sumValorByStatusAndDataGeracaoBetween(StatusMulta.PENDENTE, dataInicio, dataFim);
            long qtdPago = multaRepository.countByStatusAndDataGeracaoBetween(StatusMulta.PAGO, dataInicio, dataFim);
            long qtdPendente = multaRepository.countByStatusAndDataGeracaoBetween(StatusMulta.PENDENTE, dataInicio, dataFim);

            return new ResumoFinanceiroDTO(totalGerado, totalPago, totalPendente, qtdPago, qtdPendente);
        }

        BigDecimal totalGerado = multaRepository.sumTotalValor();
        BigDecimal totalPago = multaRepository.sumValorByStatus(StatusMulta.PAGO);
        BigDecimal totalPendente = multaRepository.sumValorByStatus(StatusMulta.PENDENTE);
        long qtdPago = multaRepository.countByStatus(StatusMulta.PAGO);
        long qtdPendente = multaRepository.countByStatus(StatusMulta.PENDENTE);

        return new ResumoFinanceiroDTO(totalGerado, totalPago, totalPendente, qtdPago, qtdPendente);
    }

    @Transactional(readOnly = true)
    public List<Multa> filtrarMultas(LocalDate dataInicio, LocalDate dataFim, StatusMulta status) {
        if (dataInicio != null && dataFim != null) {
            if (status != null) {
                return multaRepository.findByDataGeracaoBetweenAndStatus(dataInicio, dataFim, status);
            }
            return multaRepository.findByDataGeracaoBetween(dataInicio, dataFim);
        }

        if (status != null) {
            return multaRepository.findByStatus(status);
        }

        return multaRepository.findAll();
    }
}
