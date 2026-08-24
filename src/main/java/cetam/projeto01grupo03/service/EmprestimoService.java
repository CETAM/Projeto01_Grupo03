package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.*;
import cetam.projeto01grupo03.repository.AlunoRepository;
import cetam.projeto01grupo03.repository.EmprestimoRepository;
import cetam.projeto01grupo03.repository.LivroRepository;
import cetam.projeto01grupo03.repository.MultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmprestimoService {

    private static final BigDecimal VALOR_MULTA_POR_DIA = new BigDecimal("2.00");

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final AlunoRepository alunoRepository;
    private final MultaRepository multaRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             LivroRepository livroRepository,
                             AlunoRepository alunoRepository,
                             MultaRepository multaRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.alunoRepository = alunoRepository;
        this.multaRepository = multaRepository;
    }

    @Transactional
    public List<Emprestimo> listarTodos(StatusEmprestimo status) {
        List<Emprestimo> lista = emprestimoRepository.findAll();
        LocalDate hoje = LocalDate.now();
        for (Emprestimo emp : lista) {
            if (emp.getStatus() == StatusEmprestimo.ATIVO && emp.getDataPrevisaoDevolucao() != null && hoje.isAfter(emp.getDataPrevisaoDevolucao())) {
                emp.setStatus(StatusEmprestimo.ATRASADO);
                emprestimoRepository.save(emp);
            }
        }
        if (status != null) {
            return emprestimoRepository.findByStatus(status);
        }
        return lista;
    }

    public List<Emprestimo> listarPorAluno(Long alunoId) {
        return emprestimoRepository.findByAlunoId(alunoId);
    }

    public List<Emprestimo> buscarPorNomeAluno(String termo) {
        if (termo != null && !termo.isBlank()) {
            return emprestimoRepository.findByAlunoNomeContainingIgnoreCaseOrAlunoMatriculaContainingIgnoreCase(termo.trim(), termo.trim());
        }
        return emprestimoRepository.findAll();
    }


    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado com o ID: " + id));
    }

    @Transactional
    public Emprestimo realizarEmprestimo(Long alunoId, Long livroId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado com o ID: " + alunoId));

        if (!Boolean.TRUE.equals(aluno.getAtivo())) {
            throw new IllegalStateException("O aluno " + aluno.getNome() + " está inativo e não pode realizar empréstimos.");
        }

        // Bloqueio se houver empréstimos em atraso
        boolean possuiEmprestimoAtrasado = emprestimoRepository.findByAlunoId(alunoId).stream()
                .anyMatch(e -> e.getStatus() == StatusEmprestimo.ATRASADO ||
                        (e.getStatus() == StatusEmprestimo.ATIVO && e.getDataPrevisaoDevolucao() != null && LocalDate.now().isAfter(e.getDataPrevisaoDevolucao())));
        if (possuiEmprestimoAtrasado) {
            throw new IllegalStateException("O aluno " + aluno.getNome() + " possui empréstimo(s) em atraso e não pode realizar novos empréstimos até regularizar a devolução.");
        }

        // Bloqueio se houver multas financeiras pendentes de quitação
        boolean possuiMultasPendentes = multaRepository.existsByEmprestimoAlunoIdAndStatus(alunoId, StatusMulta.PENDENTE);
        if (possuiMultasPendentes) {
            throw new IllegalStateException("O aluno " + aluno.getNome() + " possui multas pendentes de pagamento e não pode realizar novos empréstimos.");
        }

        // Limite máximo de 3 livros simultâneos
        long emprestimosEmAberto = emprestimoRepository.findByAlunoId(alunoId).stream()
                .filter(e -> e.getStatus() == StatusEmprestimo.ATIVO || e.getStatus() == StatusEmprestimo.ATRASADO)
                .count();
        if (emprestimosEmAberto >= 3) {
            throw new IllegalStateException("O aluno " + aluno.getNome() + " já possui 3 livros em aberto (limite máximo atingido).");
        }

        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado com o ID: " + livroId));

        int exemplaresDisponiveis = livro.getQuantidadeExemplares() != null ? livro.getQuantidadeExemplares() : 0;
        if (exemplaresDisponiveis <= 0 || !Boolean.TRUE.equals(livro.getDisponivel())) {
            throw new IllegalStateException("O livro '" + livro.getTitulo() + "' não possui exemplares disponíveis no momento.");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setAluno(aluno);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataPrevisaoDevolucao(LocalDate.now().plusDays(14));
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        // Decrementa exemplar
        livro.setQuantidadeExemplares(exemplaresDisponiveis - 1);
        if (livro.getQuantidadeExemplares() <= 0) {
            livro.setDisponivel(false);
        }
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo renovarEmprestimo(Long id) {
        Emprestimo emprestimo = buscarPorId(id);

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Não é possível renovar um empréstimo já devolvido.");
        }

        if (LocalDate.now().isAfter(emprestimo.getDataPrevisaoDevolucao())) {
            throw new IllegalStateException("Não é possível renovar um empréstimo com devolução atrasada. É necessário realizar a devolução e quitar multas pendentes.");
        }

        emprestimo.setDataPrevisaoDevolucao(emprestimo.getDataPrevisaoDevolucao().plusDays(14));
        emprestimo.setStatus(StatusEmprestimo.ATIVO);
        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = buscarPorId(emprestimoId);

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Este empréstimo já foi devolvido anteriormente.");
        }

        LocalDate dataDevolucao = LocalDate.now();
        emprestimo.setDataDevolucao(dataDevolucao);

        if (dataDevolucao.isAfter(emprestimo.getDataPrevisaoDevolucao())) {
            long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataPrevisaoDevolucao(), dataDevolucao);
            BigDecimal valorMulta = VALOR_MULTA_POR_DIA.multiply(BigDecimal.valueOf(diasAtraso));

            Multa multa = new Multa();
            multa.setValor(valorMulta);
            multa.setDataGeracao(dataDevolucao);
            multa.setStatus(StatusMulta.PENDENTE);
            multa.setEmprestimo(emprestimo);

            emprestimo.setMulta(multa);
            emprestimo.setStatus(StatusEmprestimo.ATRASADO);
        } else {
            emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        }

        Livro livro = emprestimo.getLivro();
        if (livro != null) {
            int qtdAtual = livro.getQuantidadeExemplares() != null ? livro.getQuantidadeExemplares() : 0;
            livro.setQuantidadeExemplares(qtdAtual + 1);
            livro.setDisponivel(true);
            livroRepository.save(livro);
        }

        return emprestimoRepository.save(emprestimo);
    }

    public long contarAtivos() {
        return emprestimoRepository.findByStatus(StatusEmprestimo.ATIVO).size();
    }

    public long contarAtrasados() {
        return emprestimoRepository.findByStatus(StatusEmprestimo.ATRASADO).size();
    }

    public long contarDevolucoesHoje() {
        LocalDate hoje = LocalDate.now();
        return emprestimoRepository.findAll().stream()
                .filter(e -> hoje.equals(e.getDataPrevisaoDevolucao()) && e.getStatus() != StatusEmprestimo.DEVOLVIDO)
                .count();
    }

    @Transactional
    public void excluir(Long id) {
        Emprestimo emprestimo = buscarPorId(id);
        if (emprestimo.getStatus() == StatusEmprestimo.ATIVO) {
            Livro livro = emprestimo.getLivro();
            if (livro != null) {
                int qtdAtual = livro.getQuantidadeExemplares() != null ? livro.getQuantidadeExemplares() : 0;
                livro.setQuantidadeExemplares(qtdAtual + 1);
                livro.setDisponivel(true);
                livroRepository.save(livro);
            }
        }
        emprestimoRepository.deleteById(id);
    }
}
