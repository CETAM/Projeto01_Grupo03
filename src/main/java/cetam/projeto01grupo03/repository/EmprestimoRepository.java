package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository <Emprestimo, Long> {
}
