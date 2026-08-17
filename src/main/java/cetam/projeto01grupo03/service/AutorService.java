package cetam.projeto01grupo03.service;

import cetam.projeto01grupo03.model.Autor;
import cetam.projeto01grupo03.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarTodos (){
        return autorRepository.findAll();
    }

    public Autor criar(Autor autor){
        return autorRepository.save(autor);
    }
}
