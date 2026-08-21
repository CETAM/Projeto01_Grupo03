package cetam.projeto01grupo03.dto;

public class LivroMaisEmprestadoDTO {

    private String titulo;
    private String autor;
    private String categoria;
    private Long totalEmprestimos;

    public LivroMaisEmprestadoDTO() {
    }

    public LivroMaisEmprestadoDTO(String titulo, String autor, String categoria, Long totalEmprestimos) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.totalEmprestimos = totalEmprestimos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getTotalEmprestimos() {
        return totalEmprestimos;
    }

    public void setTotalEmprestimos(Long totalEmprestimos) {
        this.totalEmprestimos = totalEmprestimos;
    }
}
