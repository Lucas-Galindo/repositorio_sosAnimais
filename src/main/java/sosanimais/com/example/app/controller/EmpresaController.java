package sosanimais.com.example.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.DAL.EmpresaDAL;
import sosanimais.com.example.app.model.Endereco;
import sosanimais.com.example.app.model.entity.Empresa;

import java.util.Optional;

@RestController
@RequestMapping(value=("/apis/empresa"))
public class EmpresaController {

    @Autowired
    private EmpresaDAL endereco;

    // Endpoint para adicionar um novo endereço

    public Empresa getOngInfo() {
        Empresa info = new Empresa(1, 1000, "21.543.257/0001-60", "SOS animais Pirapozinho","descricao","(18) 99685-6964");
       return info;
    }


    @PostMapping ("/apis/endereco")
    public String adicionarEndereco(@RequestBody Endereco endereco) throws InterruptedException {
        endereco.wait(endereco.getNumero());
        return "Endereço adicionado com sucesso!";
    }

    // Endpoint para listar o endereço
    @GetMapping("/endereco/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable Long id) {
        Endereco end = end.findById(id);
        return end.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para buscar um endereço pelo CEP
    @GetMapping("/endereco/{cep}")
    public Endereco buscarEnderecoPorCep(@PathVariable String cep) {
        return endereco.stream()
                .filter(e -> e.getCep().equals(cep))
                .findFirst()
                .orElse(null);
    }
}
