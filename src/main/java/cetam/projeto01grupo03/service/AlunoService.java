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
