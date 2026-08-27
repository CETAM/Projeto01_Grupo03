package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Emprestimo;
import cetam.projeto01grupo03.model.Livro;
import cetam.projeto01grupo03.model.Multa;
import cetam.projeto01grupo03.model.StatusEmprestimo;
import cetam.projeto01grupo03.model.StatusMulta;
import cetam.projeto01grupo03.repository.EmprestimoRepository;
import cetam.projeto01grupo03.repository.LivroRepository;
import cetam.projeto01grupo03.repository.MultaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
public class MultaService {

    private final MultaRepository multaRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public MultaService(MultaRepository multaRepository,
                        EmprestimoRepository emprestimoRepository,
                        LivroRepository livroRepository) {
        this.multaRepository = multaRepository;
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }

    public List<Multa> listarTodas(String termo) {
        if (termo != null && !termo.isBlank()) {
            return multaRepository.findByEmprestimoAlunoNomeContainingIgnoreCaseOrEmprestimoAlunoMatriculaContainingIgnoreCase(termo.trim(), termo.trim());
        }
        return multaRepository.findAll();
    }

    public List<Multa> listarPorAluno(Long alunoId) {
        return  multaRepository.findByEmprestimoAlunoId(alunoId);
    }

    public Multa buscarPorId(Long id){
        return multaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Multa não encontrada com o ID: " + id ));
    }

    @Transactional
    public Multa pagarMulta(Long id){
        Multa multa = buscarPorId(id);
        if (multa.getStatus() == StatusMulta.PAGO) {
            throw new IllegalStateException("Esta multa já foi liquidada anteriormente.");
        }
        multa.setStatus(StatusMulta.PAGO);
        multa.setDataPagamento(LocalDate.now());

        // Se o empréstimo associado ainda não estava registrado como devolvido, finaliza a devolução e repõe o exemplar
        Emprestimo emprestimo = multa.getEmprestimo();
        if (emprestimo != null) {
            if (emprestimo.getStatus() != StatusEmprestimo.DEVOLVIDO || emprestimo.getDataDevolucao() == null) {
                emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
                if (emprestimo.getDataDevolucao() == null) {
                    emprestimo.setDataDevolucao(LocalDate.now());
                }

                Livro livro = emprestimo.getLivro();
                if (livro != null) {
                    int qtdAtual = livro.getQuantidadeExemplares() != null ? livro.getQuantidadeExemplares() : 0;
                    livro.setQuantidadeExemplares(qtdAtual + 1);
                    livro.setDisponivel(true);
                    livroRepository.save(livro);
                }

                emprestimoRepository.save(emprestimo);
            }
        }

        return multaRepository.save(multa);
    }

    public BigDecimal calcularTotalPendente() {
        return multaRepository.findByStatus(StatusMulta.PENDENTE).stream()
                .map(Multa::getValor)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularTotalRecebido() {
        return multaRepository.findByStatus(StatusMulta.PAGO).stream()
                .map(Multa::getValor)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long contarAlunosEmAtraso() {
        return multaRepository.findByStatus(StatusMulta.PENDENTE).stream()
                .map(m -> m.getEmprestimo() != null && m.getEmprestimo(). getAluno() != null ? m.getEmprestimo().getAluno().getId() : null)
                .filter(Objects::nonNull)
                .distinct()
                .count();
    }

    @Transactional
    public Multa salvar(Multa multa) {
        return multaRepository.save(multa);
    }
}
