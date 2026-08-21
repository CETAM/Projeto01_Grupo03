package cetam.projeto01grupo03.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, unique = true, length = 50)
    private String matricula;

    @Column(length = 150)
    private String email;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Aluno() {}

    public Aluno(Long id, String nome, String matricula, String email, Boolean ativo, List<Emprestimo> emprestimos) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.ativo = ativo;
        this.emprestimos = emprestimos;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Boolean isAtivo() {
        return ativo;


    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", matricula='" + matricula + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
