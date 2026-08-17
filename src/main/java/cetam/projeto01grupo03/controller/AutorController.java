package cetam.projeto01grupo03.controller;

import cetam.projeto01grupo03.model.Autor;
import cetam.projeto01grupo03.repository.AutorRepository;
import cetam.projeto01grupo03.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private final AutorRepository autorRepository;
    private final AutorService autorService;

    public AutorController(AutorRepository autorRepository, AutorService autorService) {
        this.autorRepository = autorRepository;
        this.autorService = autorService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Autor>> listaTodos(){
        List<Autor> autor = autorRepository.findAll();
        if (!autor.isEmpty()){
            return new ResponseEntity<>(autor, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Autor> criar(@RequestBody Autor autor){
        try {
            Autor autorSalvo = autorService.criar(autor);
            return new ResponseEntity<>(autorSalvo, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao cadastrar autor: "+ e.getMessage());
        }
    }
}
