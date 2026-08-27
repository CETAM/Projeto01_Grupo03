package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Aluno;
import cetam.projeto01grupo03.model.StatusEmprestimo;
import cetam.projeto01grupo03.repository.AlunoRepository;
import cetam.projeto01grupo03.repository.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final EmprestimoRepository emprestimoRepository;

    public AlunoService(AlunoRepository alunoRepository, EmprestimoRepository emprestimoRepository) {
        this.alunoRepository = alunoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Aluno> listarTodos(String nome) {
        if (nome != null && !nome.isBlank()) {
            return alunoRepository.findByNomeContainingIgnoreCase(nome.trim());
        }
        return alunoRepository.findAll();
    }

    public List<Aluno> listarAtivos() {
        List<Aluno> ativos = alunoRepository.findByAtivoTrue();
        if (ativos.isEmpty()) {
            return alunoRepository.findAll().stream()
                    .filter(a -> a.getAtivo() == null || Boolean.TRUE.equals(a.getAtivo()))
                    .toList();
        }
        return ativos;
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o ID: " + id));
    }

    @Transactional
    public Aluno salvar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do aluno é obrigatório.");
        }
        if (aluno.getMatricula() == null || aluno.getMatricula().trim().isEmpty()) {
            throw new IllegalArgumentException("A matrícula do aluno é obrigatória.");
        }

        aluno.setNome(aluno.getNome().trim());
        aluno.setMatricula(aluno.getMatricula().trim());
        if (aluno.getEmail() != null) {
            aluno.setEmail(aluno.getEmail().trim().toLowerCase());
            if (aluno.getEmail().isEmpty()) {
                aluno.setEmail(null);
            }
        }

        // Validação de unicidade da matrícula
        if (aluno.getId() == null) {
            if (alunoRepository.existsByMatricula(aluno.getMatricula())) {
                throw new IllegalArgumentException("Já existe um aluno cadastrado com a matrícula '" + aluno.getMatricula() + "'.");
            }
        } else {
            if (alunoRepository.existsByMatriculaAndIdNot(aluno.getMatricula(), aluno.getId())) {
                throw new IllegalArgumentException("Já existe outro aluno cadastrado com a matrícula '" + aluno.getMatricula() + "'.");
            }
        }

        // Validação de unicidade do e-mail (se preenchido)
        if (aluno.getEmail() != null) {
            if (aluno.getId() == null) {
                if (alunoRepository.existsByEmailIgnoreCase(aluno.getEmail())) {
                    throw new IllegalArgumentException("Já existe um aluno cadastrado com o e-mail '" + aluno.getEmail() + "'.");
                }
            } else {
                if (alunoRepository.existsByEmailIgnoreCaseAndIdNot(aluno.getEmail(), aluno.getId())) {
                    throw new IllegalArgumentException("Já existe outro aluno cadastrado com o e-mail '" + aluno.getEmail() + "'.");
                }
            }
        }

        if (aluno.getAtivo() == null) {
            aluno.setAtivo(true);
        }

        return alunoRepository.save(aluno);
    }

    @Transactional
    public Aluno alternarStatus(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setAtivo(aluno.getAtivo() == null || !aluno.getAtivo());
        return alunoRepository.save(aluno);
    }

    @Transactional
    public void excluir(Long id) {
        Aluno aluno = buscarPorId(id);

        boolean possuirEmprestimoEmAberto = emprestimoRepository.findByAlunoId(id).stream()
                .anyMatch(e -> e.getStatus() == StatusEmprestimo.ATIVO || e.getStatus() == StatusEmprestimo.ATRASADO);

        if (possuirEmprestimoEmAberto) {
            throw new IllegalStateException("Não é possivel excluir o aluno '" + aluno.getNome() + "' porque ele possui empréstimos em aberto no momento.");
        }

        alunoRepository.delete(aluno);
    }
}
