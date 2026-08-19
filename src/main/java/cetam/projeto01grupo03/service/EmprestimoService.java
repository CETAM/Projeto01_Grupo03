package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Aluno;
import cetam.projeto01grupo03.model.Emprestimo;
import cetam.projeto01grupo03.model.Livro;
import cetam.projeto01grupo03.model.StatusEmprestimo;
import cetam.projeto01grupo03.repository.AlunoRepository;
import cetam.projeto01grupo03.repository.EmprestimoRepository;
import cetam.projeto01grupo03.repository.LivroRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    private final LivroRepository livroRepository;

    private final AlunoRepository alunoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository, AlunoRepository alunoRepository) { this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.alunoRepository = alunoRepository;
    }

    public List<Emprestimo> listarTodos(StatusEmprestimo status) {
        if (status != null) {
            return emprestimoRepository.findByStatus(status);
        }
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado com o ID: " + id));
    }

    public Emprestimo realizarEmprestimo(Long alunoId, Long livroId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado com o ID: " + alunoId));
        if (!Boolean.TRUE.equals(aluno.getAtivo()))
        {
            throw new IllegalStateException("O aluno" + aluno.getNome() + " está inativo e não pode realizar empréstimos.");
        }

        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado com o ID: " + livroId));

        if (!Boolean.TRUE.equals(livro.getDisponivel())) {
            throw new IllegalStateException("O livro" + livro.getTitulo() + "não está disponível para empréstimo.");

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setAluno(aluno);
            emprestimo.setLivro(livro);

            emprestimo.setDataEmprestimo((LocalDate.now());
            emprestimo.setDataPrevisaoDevolucao(LocalDate.now().plusDays(14));

            emprestimo.setStatus(StatusEmprestimo.ATIVO);

            livro.setDisponivel(false);
            livroRepository.save(livro);

            return
                    emprestimoRepository.save(emprestimo);
            }

    }
    }
}
