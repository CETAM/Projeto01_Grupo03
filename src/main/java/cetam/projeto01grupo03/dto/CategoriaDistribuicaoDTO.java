package cetam.projeto01grupo03.dto;

public class CategoriaDistribuicaoDTO {

    private String categoria;
    private Long quantidade;

    public CategoriaDistribuicaoDTO() {

    }

    public CategoriaDistribuicaoDTO(String categoria, Long quantidade) {
        this.categoria = categoria != null ? categoria : "Sem Categoria";
        this.quantidade = quantidade != null ? quantidade : 0L;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
