package cetam.projeto01grupo03.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "configuracoes_sistema")
public class ConfigSistema {

    @Id
    @Column(name = "id_config")
    private Long id = 1L;

    // Regras de Circulação e Prazos
    @Column(name = "dias_prazo_emprestimo", nullable = false)
    private Integer diasPrazoEmprestimo = 14;

    @Column(name = "dias_prazo_renovacao", nullable = false)
    private Integer diasPrazoRenovacao = 14;

    @Column(name = "limite_livros_simultaneos", nullable = false)
    private Integer limiteLivrosSimultaneos = 3;

    @Column(name = "maximo_renovacoes_permitidas", nullable = false)
    private Integer maximoRenovacoesPermitidas = 2;

    // Regras Financeiras e Cobrança
    @Column(name = "valor_multa_por_dia", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMultaPorDia = new BigDecimal("2.00");

    @Column(name = "dias_tolerancia_atraso", nullable = false)
    private Integer diasToleranciaAtraso = 0;

    @Column(name = "bloquear_emprestimo_com_multa_pendente", nullable = false)
    private Boolean bloquearEmprestimoComMultaPendente = false;

    // Identidade Institucional e Relatórios
    @Column(name = "nome_instituicao", nullable = false, length = 150)
    private String nomeInstituicao = "Sistema de Biblioteca";

    @Column(name = "texto_rodape_relatorio", nullable = false, length = 255)
    private String textoRodapeRelatorio = "Sistema de Controle de Biblioteca - Relatório Oficial";

    public ConfigSistema() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiasPrazoEmprestimo() {
        return diasPrazoEmprestimo;
    }

    public void setDiasPrazoEmprestimo(Integer diasPrazoEmprestimo) {
        this.diasPrazoEmprestimo = diasPrazoEmprestimo;
    }

    public Integer getDiasPrazoRenovacao() {
        return diasPrazoRenovacao;
    }

    public void setDiasPrazoRenovacao(Integer diasPrazoRenovacao) {
        this.diasPrazoRenovacao = diasPrazoRenovacao;
    }

    public Integer getLimiteLivrosSimultaneos() {
        return limiteLivrosSimultaneos;
    }

    public void setLimiteLivrosSimultaneos(Integer limiteLivrosSimultaneos) {
        this.limiteLivrosSimultaneos = limiteLivrosSimultaneos;
    }

    public Integer getMaximoRenovacoesPermitidas() {
        return maximoRenovacoesPermitidas;
    }

    public void setMaximoRenovacoesPermitidas(Integer maximoRenovacoesPermitidas) {
        this.maximoRenovacoesPermitidas = maximoRenovacoesPermitidas;
    }

    public BigDecimal getValorMultaPorDia() {
        return valorMultaPorDia;
    }

    public void setValorMultaPorDia(BigDecimal valorMultaPorDia) {
        this.valorMultaPorDia = valorMultaPorDia;
    }

    public Integer getDiasToleranciaAtraso() {
        return diasToleranciaAtraso;
    }

    public void setDiasToleranciaAtraso(Integer diasToleranciaAtraso) {
        this.diasToleranciaAtraso = diasToleranciaAtraso;
    }

    public Boolean getBloquearEmprestimoComMultaPendente() {
        return bloquearEmprestimoComMultaPendente;
    }

    public void setBloquearEmprestimoComMultaPendente(Boolean bloquearEmprestimoComMultaPendente) {
        this.bloquearEmprestimoComMultaPendente = bloquearEmprestimoComMultaPendente;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getTextoRodapeRelatorio() {
        return textoRodapeRelatorio;
    }

    public void setTextoRodapeRelatorio(String textoRodapeRelatorio) {
        this.textoRodapeRelatorio = textoRodapeRelatorio;
    }
}
