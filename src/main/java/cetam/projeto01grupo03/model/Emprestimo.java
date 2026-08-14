package cetam.projeto01grupo03.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmprestimo")
    private Integer idEmprestimo;

    @ManyToOne
    @JoinColumn(name = "idAluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "idLivro", nullable = false)
    private Livro livro;

    @Column(name = "dataEmprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "dataDevolucao")
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusEmprestimo status;

    public Emprestimo() {}

    public Integer getIdEmprestimo() { return idEmprestimo; }
    public void setIdEmprestimo(Integer idEmprestimo) { this.idEmprestimo = idEmprestimo; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public StatusEmprestimo getStatus() { return status; }
    public void setStatus(StatusEmprestimo status) { this.status = status; }
}
