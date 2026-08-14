package cetam.projeto01grupo03.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "editoras")
public class Editora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEditora")
    private Integer idEditora;

    @Column(nullable = false, length = 200)
    private String nome;

    @OneToMany(mappedBy = "editora", cascade = CascadeType.ALL)
    private List<Livro> livros = new ArrayList<>();

    public Editora() {}

    public Editora(Integer idEditora, String nome) {
        this.idEditora = idEditora;
        this.nome = nome;
    }

    public Integer getIdEditora() { return idEditora; }
    public void setIdEditora(Integer idEditora) { this.idEditora = idEditora; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Livro> getLivros() { return livros; }
    public void setLivros(List<Livro> livros) { this.livros = livros; }
}
