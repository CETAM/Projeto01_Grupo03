package cetam.projeto01grupo03.dto;

import java.math.BigDecimal;

public class ResumoFinanceiroDTO {

    private BigDecimal totalMultasGeradas;
    private BigDecimal totalMultasPagas;
    private BigDecimal totalMultasPendentes;
    private Long quantidadeMultasPagas;
    private Long quantidadeMultasPendentes;

    public ResumoFinanceiroDTO(){
        this.totalMultasGeradas = BigDecimal.ZERO;
        this.totalMultasPagas = BigDecimal.ZERO;
        this.totalMultasPendentes = BigDecimal.ZERO;
        this.quantidadeMultasPagas = 0L;
        this.quantidadeMultasPendentes = 0L;
    }

    public ResumoFinanceiroDTO(BigDecimal totalMultasGeradas, BigDecimal totalMultasPagas, BigDecimal totalMultasPendentes, Long quantidadeMultasPagas, Long quantidadeMultasPendentes){
        this.totalMultasGeradas = totalMultasGeradas != null ? totalMultasGeradas : BigDecimal.ZERO;
        this.totalMultasPagas = totalMultasPagas != null ? totalMultasPagas : BigDecimal.ZERO;
        this.totalMultasPendentes = totalMultasPendentes != null ? totalMultasPendentes : BigDecimal.ZERO;
        this.quantidadeMultasPagas = quantidadeMultasPagas != null ? quantidadeMultasPagas : 0L;
        this.quantidadeMultasPendentes = quantidadeMultasPendentes != null ? quantidadeMultasPendentes : 0L;
    }

    public BigDecimal getTotalMultasGeradas() {
        return totalMultasGeradas;
    }

    public void setTotalMultasGeradas(BigDecimal totalMultasGeradas) {
        this.totalMultasGeradas = totalMultasGeradas;
    }

    public BigDecimal getTotalMultasPagas() {
        return totalMultasPagas;
    }

    public void setTotalMultasPagas(BigDecimal totalMultasPagas) {
        this.totalMultasPagas = totalMultasPagas;
    }

    public BigDecimal getTotalMultasPendentes() {
        return totalMultasPendentes;
    }

    public void setTotalMultasPendentes(BigDecimal totalMultasPendentes) {
        this.totalMultasPendentes = totalMultasPendentes;
    }

    public Long getQuantidadeMultasPagas() {
        return quantidadeMultasPagas;
    }

    public void setQuantidadeMultasPagas(Long quantidadeMultasPagas) {
        this.quantidadeMultasPagas = quantidadeMultasPagas;
    }

    public Long getQuantidadeMultasPendentes() {
        return quantidadeMultasPendentes;
    }

    public void setQuantidadeMultasPendentes(Long quantidadeMultasPendentes) {
        this.quantidadeMultasPendentes = quantidadeMultasPendentes;
    }
}
