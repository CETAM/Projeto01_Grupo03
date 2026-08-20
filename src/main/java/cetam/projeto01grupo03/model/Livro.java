package cetam.projeto01grupo03.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livro")
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 20, unique = true)
    private String isbn;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Integer quantidadeExemplares = 1;

    @Column(nullable = false)
    private Boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "id_editora", nullable = false)
    private Editora editora;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos = new ArrayList<>();


    public Livro() {}

    public Livro(Long id, String titulo, String isbn, Integer ano, Integer quantidadeExemplares, Boolean disponivel, Autor autor, Editora editora, List<Emprestimo> emprestimos) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.ano = ano;
        this.quantidadeExemplares = quantidadeExemplares != null ? quantidadeExemplares : 1;
        this.disponivel = disponivel != null ? disponivel : true;
        this.autor = autor;
        this.editora = editora;
        this.emprestimos = emprestimos;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getQuantidadeExemplares() {
        return quantidadeExemplares;
    }

    public void setQuantidadeExemplares(Integer quantidadeExemplares) {
        this.quantidadeExemplares = quantidadeExemplares;
        if (this.quantidadeExemplares != null && this.quantidadeExemplares <= 0) {
            this.disponivel = false;
        }
    }

    public Boolean getDisponivel() {
        return disponivel;
    }


    public Boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", ano=" + ano +
                ", disponivel=" + disponivel +
                '}';
    }
}
