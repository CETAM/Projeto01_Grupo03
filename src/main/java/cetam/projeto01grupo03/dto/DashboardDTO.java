package cetam.projeto01grupo03.dto;

import cetam.projeto01grupo03.model.Emprestimo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DashboardDTO {

    // KPIs Globais
    private long totalLivros;
    private long totalExemplares;
    private long totalAlunos;
    private long totalEmprestimosAtivos;
    private long totalDevolucoesHoje;
    private long totalEmprestimosAtrasados;
    private long totalEmprestimosDevolvidos;
    private BigDecimal totalMultasPendentesValor;
    private long totalMultasPendentesQtd;
    private double taxaCirculacao;

    // Listas Operacionais
    private List<Emprestimo> ultimosEmprestimos = new ArrayList<>();
    private List<Emprestimo> emprestimosCriticosAtrasados = new ArrayList<>();
    private List<LivroMaisEmprestadoDTO> topLivros = new ArrayList<>();

    // Dados para Gráficos
    private List<String> diasGraficoLabels = new ArrayList<>();
    private List<Long> emprestimosPorDia = new ArrayList<>();
    private List<Long> devolucoesPorDia = new ArrayList<>();
    private List<String> categoriasLabels = new ArrayList<>();
    private List<Long> categoriasValores = new ArrayList<>();

    public DashboardDTO() {
        this.totalMultasPendentesValor = BigDecimal.ZERO;
    }

    public long getTotalLivros() {
        return totalLivros;
    }

    public void setTotalLivros(long totalLivros) {
        this.totalLivros = totalLivros;
    }

    public long getTotalExemplares() {
        return totalExemplares;
    }

    public void setTotalExemplares(long totalExemplares) {
        this.totalExemplares = totalExemplares;
    }

    public long getTotalAlunos() {
        return totalAlunos;
    }

    public void setTotalAlunos(long totalAlunos) {
        this.totalAlunos = totalAlunos;
    }

    public long getTotalEmprestimosAtivos() {
        return totalEmprestimosAtivos;
    }

    public void setTotalEmprestimosAtivos(long totalEmprestimosAtivos) {
        this.totalEmprestimosAtivos = totalEmprestimosAtivos;
    }

    public long getTotalDevolucoesHoje() {
        return totalDevolucoesHoje;
    }

    public void setTotalDevolucoesHoje(long totalDevolucoesHoje) {
        this.totalDevolucoesHoje = totalDevolucoesHoje;
    }

    public long getTotalEmprestimosAtrasados() {
        return totalEmprestimosAtrasados;
    }

    public void setTotalEmprestimosAtrasados(long totalEmprestimosAtrasados) {
        this.totalEmprestimosAtrasados = totalEmprestimosAtrasados;
    }

    public long getTotalEmprestimosDevolvidos() {
        return totalEmprestimosDevolvidos;
    }

    public void setTotalEmprestimosDevolvidos(long totalEmprestimosDevolvidos) {
        this.totalEmprestimosDevolvidos = totalEmprestimosDevolvidos;
    }

    public BigDecimal getTotalMultasPendentesValor() {
        return totalMultasPendentesValor;
    }

    public void setTotalMultasPendentesValor(BigDecimal totalMultasPendentesValor) {
        this.totalMultasPendentesValor = totalMultasPendentesValor;
    }

    public long getTotalMultasPendentesQtd() {
        return totalMultasPendentesQtd;
    }

    public void setTotalMultasPendentesQtd(long totalMultasPendentesQtd) {
        this.totalMultasPendentesQtd = totalMultasPendentesQtd;
    }

    public double getTaxaCirculacao() {
        return taxaCirculacao;
    }

    public void setTaxaCirculacao(double taxaCirculacao) {
        this.taxaCirculacao = taxaCirculacao;
    }

    public List<Emprestimo> getUltimosEmprestimos() {
        return ultimosEmprestimos;
    }

    public void setUltimosEmprestimos(List<Emprestimo> ultimosEmprestimos) {
        this.ultimosEmprestimos = ultimosEmprestimos;
    }

    public List<Emprestimo> getEmprestimosCriticosAtrasados() {
        return emprestimosCriticosAtrasados;
    }

    public void setEmprestimosCriticosAtrasados(List<Emprestimo> emprestimosCriticosAtrasados) {
        this.emprestimosCriticosAtrasados = emprestimosCriticosAtrasados;
    }

    public List<LivroMaisEmprestadoDTO> getTopLivros() {
        return topLivros;
    }

    public void setTopLivros(List<LivroMaisEmprestadoDTO> topLivros) {
        this.topLivros = topLivros;
    }

    public List<String> getDiasGraficoLabels() {
        return diasGraficoLabels;
    }

    public void setDiasGraficoLabels(List<String> diasGraficoLabels) {
        this.diasGraficoLabels = diasGraficoLabels;
    }

    public List<Long> getEmprestimosPorDia() {
        return emprestimosPorDia;
    }

    public void setEmprestimosPorDia(List<Long> emprestimosPorDia) {
        this.emprestimosPorDia = emprestimosPorDia;
    }

    public List<Long> getDevolucoesPorDia() {
        return devolucoesPorDia;
    }

    public void setDevolucoesPorDia(List<Long> devolucoesPorDia) {
        this.devolucoesPorDia = devolucoesPorDia;
    }

    public List<String> getCategoriasLabels() {
        return categoriasLabels;
    }

    public void setCategoriasLabels(List<String> categoriasLabels) {
        this.categoriasLabels = categoriasLabels;
    }

    public List<Long> getCategoriasValores() {
        return categoriasValores;
    }

    public void setCategoriasValores(List<Long> categoriasValores) {
        this.categoriasValores = categoriasValores;
    }

}
