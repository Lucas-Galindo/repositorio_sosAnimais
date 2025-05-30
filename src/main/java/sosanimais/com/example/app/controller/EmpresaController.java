package sosanimais.com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.DAL.EmpresaDAL;
import sosanimais.com.example.app.model.entity.Empresa;

import java.util.Optional;


@RestController
@RequestMapping("/apis/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaDAL empresaDAL;


    @GetMapping("/buscar-por-cnpj/{cnpj}")
    public ResponseEntity<Empresa> buscarPorCnpj(@PathVariable String cnpj) {
        Empresa empresa = empresaDAL.buscarPorCnpj(cnpj);
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/info")
    public Empresa getOngInfo() {
        return new Empresa(1, 1000, "21.543.257/0001-60", "SOS Animais Pirapozinho", "SOS Animais", "ONG de proteção animal", "(18) 99685-6964");
    }


    @PostMapping("/cadastro")
    public ResponseEntity<Object> cadastro(@RequestBody Empresa empresa) {
        boolean sucesso = empresaDAL.save(empresa);
        if (sucesso)
            return ResponseEntity.ok(empresa);
        else
            return ResponseEntity.badRequest().body("Erro ao salvar empresa.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obterEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaDAL.get(id);
        if (empresa != null)
            return ResponseEntity.ok(empresa);
        else
            return ResponseEntity.notFound().build();
    }


    @GetMapping("/buscar-por-cep/{cep}")
    public ResponseEntity<Empresa> buscarPorCep(@PathVariable String cep) {
        Empresa empresa = empresaDAL.buscarPorCep(cep);
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/verificar")
    public ResponseEntity<Empresa> verificarEmpresa() {
        Empresa empresa = empresaDAL.buscarUnicaEmpresa();
        if (empresa != null)
            return ResponseEntity.ok(empresa);
        else
            return ResponseEntity.noContent().build();
    }


}
