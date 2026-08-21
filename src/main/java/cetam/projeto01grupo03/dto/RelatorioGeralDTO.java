package cetam.projeto01grupo03.dto;

public class RelatorioGeralDTO {

    private long totalLivros;
    private long totalAlunos;
    private long totalEmprestimosAtivos;
    private long totalEmprestimosAtrasados;
    private long totalEmprestimosDevolvidos;
    private long totalMultasPendentes;

    public RelatorioGeralDTO() {
    }

    public RelatorioGeralDTO(long totalLivros, long totalAlunos, long totalEmprestimosAtivos, long totalEmprestimosAtrasados, long totalEmprestimosDevolvidos, long totalMultasPendentes) {
        this.totalLivros = totalLivros;
        this.totalAlunos = totalAlunos;
        this.totalEmprestimosAtivos = totalEmprestimosAtivos;
        this.totalEmprestimosAtrasados = totalEmprestimosAtrasados;
        this.totalEmprestimosDevolvidos = totalEmprestimosDevolvidos;
        this.totalMultasPendentes = totalMultasPendentes;
    }

    public long getTotalLivros() {
        return totalLivros;
    }

    public void setTotalLivros(long totalLivros) {
        this.totalLivros = totalLivros;
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

    public long getTotalMultasPendentes() {
        return totalMultasPendentes;
    }

    public void setTotalMultasPendentes(long totalMultasPendentes) {
        this.totalMultasPendentes = totalMultasPendentes;
    }
}
