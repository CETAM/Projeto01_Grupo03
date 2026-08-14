package cetam.projeto01grupo03.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLivro")
    private Integer idLivro;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false)
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "idAutor", nullable = false)
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "idEditora", nullable = false)
    private Editora editora;

    public Livro() {}

    public Integer getIdLivro() { return idLivro; }
    public void setIdLivro(Integer idLivro) { this.idLivro = idLivro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }
}
