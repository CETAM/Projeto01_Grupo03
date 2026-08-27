package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.dto.LivroMaisEmprestadoDTO;
import cetam.projeto01grupo03.model.Emprestimo;
import cetam.projeto01grupo03.model.StatusEmprestimo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByStatus(StatusEmprestimo status);

    List<Emprestimo> findByAlunoNomeContainingIgnoreCase(String nomeAluno);

    List<Emprestimo> findByAlunoNomeContainingIgnoreCaseOrAlunoMatriculaContainingIgnoreCase(String nomeAluno, String matricula);

    List<Emprestimo> findByAlunoId(Long alunoId);

    List<Emprestimo> findByLivroId(Long livroId);

    List<Emprestimo> findByStatusAndDataPrevisaoDevolucaoBefore(StatusEmprestimo status, LocalDate data);

    List<Emprestimo> findByDataEmprestimoBetween(LocalDate inicio, LocalDate fim);

    List<Emprestimo> findByDataEmprestimoBetweenAndStatus(LocalDate inicio, LocalDate fim, StatusEmprestimo status);

    long countByStatus(StatusEmprestimo status);

    long countByDataEmprestimo(LocalDate data);

    long countByDataDevolucao(LocalDate data);

    long countByDataPrevisaoDevolucaoAndStatusNot(LocalDate data, StatusEmprestimo status);

    List<Emprestimo> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT new cetam.projeto01grupo03.dto.LivroMaisEmprestadoDTO(e.livro.titulo, e.livro.autor.nome, e.livro.categoria.nome, COUNT(e)) " +
            "FROM Emprestimo e " +
            "GROUP BY e.livro.id, e.livro.titulo, e.livro.autor.nome, e.livro.categoria.nome " +
            "ORDER BY COUNT(e) DESC")
    List<LivroMaisEmprestadoDTO> findLivrosMaisEmprestados(Pageable pageable);

    @org.springframework.data.jpa.repository.Modifying
    @Query("UPDATE Emprestimo e SET e.status = :statusAtrasado WHERE e.status = :statusAtivo AND e.dataPrevisaoDevolucao < :hoje AND e.dataDevolucao IS NULL")
    void atualizarStatusEmprestimosVencidos(@org.springframework.data.repository.query.Param("statusAtrasado") StatusEmprestimo statusAtrasado,
                                           @org.springframework.data.repository.query.Param("statusAtivo") StatusEmprestimo statusAtivo,
                                           @org.springframework.data.repository.query.Param("hoje") LocalDate hoje);

    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucao IS NULL AND (e.status = 'ATRASADO' OR (e.status = 'ATIVO' AND e.dataPrevisaoDevolucao < :hoje)) ORDER BY e.dataPrevisaoDevolucao ASC")
    List<Emprestimo> findEmprestimosEmAtrasoEmAberto(@org.springframework.data.repository.query.Param("hoje") LocalDate hoje);
}
