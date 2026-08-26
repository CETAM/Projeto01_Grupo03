package cetam.projeto01grupo03.repository;

import cetam.projeto01grupo03.model.ConfigSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigSistemaRepository extends JpaRepository<ConfigSistema, Long> {
}
