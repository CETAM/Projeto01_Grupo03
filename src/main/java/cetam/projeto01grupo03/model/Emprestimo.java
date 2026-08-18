package cetam.projeto01grupo03.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emprestimo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Livro livro;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "data_previsao_devolucao")
    private LocalDate dataPrevisaoDevolucao;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusEmprestimo status;

    @OneToOne(mappedBy = "emprestimo", cascade = CascadeType.ALL)
    private Multa multa;

    public Emprestimo() {}

    public Emprestimo(Long id, Aluno aluno, Livro livro, LocalDate dataEmprestimo, LocalDate dataPrevisaoDevolucao, LocalDate dataDevolucao, StatusEmprestimo status, Multa multa) {
        this.id = id;
        this.aluno = aluno;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevisaoDevolucao = dataPrevisaoDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
        this.multa = multa;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevisaoDevolucao() {
        return dataPrevisaoDevolucao;
    }

    public void setDataPrevisaoDevolucao(LocalDate dataPrevisaoDevolucao) {
        this.dataPrevisaoDevolucao = dataPrevisaoDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public Multa getMulta() {
        return multa;
    }

    public void setMulta(Multa multa) {
        this.multa = multa;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataPrevisaoDevolucao=" + dataPrevisaoDevolucao +
                ", dataDevolucao=" + dataDevolucao +
                ", status=" + status +
                '}';
    }
}
