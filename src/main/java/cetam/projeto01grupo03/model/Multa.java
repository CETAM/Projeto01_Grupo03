package cetam.projeto01grupo03.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "multas")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_multa")
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_geracao")
    private LocalDate dataGeracao;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusMulta status;

    @OneToOne
    @JoinColumn(name = "id_emprestimo", nullable = false)
    private Emprestimo emprestimo;

    public Multa() {}

    public Multa(Long id, BigDecimal valor, LocalDate dataGeracao, LocalDate dataPagamento, StatusMulta status, Emprestimo emprestimo) {
        this.id = id;
        this.valor = valor;
        this.dataGeracao = dataGeracao;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.emprestimo = emprestimo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public StatusMulta getStatus() {
        return status;
    }

    public void setStatus(StatusMulta status) {
        this.status = status;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public long getDiasAtraso() {
        if (emprestimo == null || emprestimo.getDataPrevisaoDevolucao() == null) {
            return 0;
        }
        LocalDate dataFim = emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao() : LocalDate.now();
        if (dataFim.isAfter(emprestimo.getDataPrevisaoDevolucao())) {
            return java.time.temporal.ChronoUnit.DAYS.between(emprestimo.getDataPrevisaoDevolucao(), dataFim);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "id=" + id +
                ", valor=" + valor +
                ", dataGeracao=" + dataGeracao +
                ", status=" + status +
                '}';
    }
}
