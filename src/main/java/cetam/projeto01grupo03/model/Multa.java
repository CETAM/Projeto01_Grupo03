package cetam.projeto01grupo03.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "multas")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMulta")
    private Integer idMulta;

    @OneToOne
    @JoinColumn(name = "idEmprestimo", nullable = false)
    private Emprestimo emprestimo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusMulta status;

    public Multa() {}

    public Integer getIdMulta() { return idMulta; }
    public void setIdMulta(Integer idMulta) { this.idMulta = idMulta; }

    public Emprestimo getEmprestimo() { return emprestimo; }
    public void setEmprestimo(Emprestimo emprestimo) { this.emprestimo = emprestimo; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public StatusMulta getStatus() { return status; }
    public void setStatus(StatusMulta status) { this.status = status; }
}
