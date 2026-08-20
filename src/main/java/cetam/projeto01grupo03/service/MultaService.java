package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Multa;
import cetam.projeto01grupo03.repository.MultaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class MultaService {

    private final MultaRepository multaRepository;

    public MultaService(MultaRepository multaRepository) {
        this.multaRepository = multaRepository;
    }

    public List<Multa> listarTodas(String termo) {
        if (termo != null && !termo.isBlank()) {
            return multaRepository.findByEmprestimoAlunoNomeContainingIgnoreCaseOrEmprestimoAlunoMatriculaContainingIgnoreCase(termo.trim(), termo.trim());
        }
        return multaRepository.findAll();
    }

    public List<Multa> listarPorAluno(Long alunoId) {
        return  multaRepository.findByEmprestimoAlunoId(alunoId);
    }

    public Multa buscarPorId(Long id){
        return multaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Multa não encontrada com o ID: " + id ));
    }

    @Transactional
    public Multa pagarMulta(Long id){
        Multa multa = buscarPorId(id);
        if (multa.getStatus() == StatusMulta.PAGO) {
            throw new IllegalStateException("Esta multa já foi liquidada anteriormente.");
        }
        multa.setStatus(StatusMulta.PAGO);
        multa.setDataPagamento(LocalDate.now());
        return multaRepository.save(multa);
    }

    public BigDecimal calcularTotalRecebido() {
        return multaRepository.findByStatus(StatusMulta.PAGO).stream()
                .map(Multa::getValor)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long contarAlunosEmAtraso() {
        return multaRepository.findByStatus(StatusMulta.PENDENTE).stream()
                .map(m -> m.getEmprestimo() != null && m.getEmprestimo(). getAluno() != null ? m.getEmprestimo().getAluno().getId() : null)
                .filter(Objects::nonNull)
                .distinct()
                .count();
    }

    @Transactional
    public Multa salvar(Multa multa) {
        return multaRepository.save(multa);
    }
}
