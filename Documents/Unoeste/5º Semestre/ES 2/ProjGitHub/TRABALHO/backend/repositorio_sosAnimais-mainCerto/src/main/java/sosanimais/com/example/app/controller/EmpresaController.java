package sosanimais.com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.EmpresaService;
import sosanimais.com.example.app.model.Endereco;
import sosanimais.com.example.app.model.entity.Empresa;
import sosanimais.com.example.app.model.util.Erro;

@RestController
@RequestMapping("/apis/empresa")
@CrossOrigin("*")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/buscar-por-cnpj/{cnpj}")
    public ResponseEntity<Empresa> buscarPorCnpj(@PathVariable String cnpj) {
        Empresa empresa = empresaService.buscarEmpresaPorCnpj(cnpj); // Corrigido para usar o service
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Empresa> getOngInfo() {
        Empresa empresa = empresaService.verificarEmpresaExistente();
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            // Retorna um objeto padrão se nenhuma empresa estiver cadastrada
            // O construtor de Endereco não tem bairro, cidade, estado
            Endereco enderecoFixo = new Endereco("Rua Exemplo Padrão", 0, "00000-000", "Padrão");
            // O construtor de Empresa com 8 args não inclui historia
            Empresa empresaPadrao = new Empresa(0, 0, "00.000.000/0000-00", "Nome Padrão ONG", "ONG Padrão",
                    enderecoFixo, "Descrição padrão da ONG.", "(00) 00000-0000");
            return ResponseEntity.status(HttpStatus.OK).body(empresaPadrao);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> cadastro(@RequestBody Empresa empresa) {
        if (empresa == null || empresa.getNome() == null || empresa.getNome().trim().isEmpty() ||
                empresa.getCnpj() == null || empresa.getCnpj().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("Nome e CNPJ da empresa são obrigatórios."));
        }
        // Validação básica do endereço. O objeto Endereco pode ser nulo ou ter campos nulos.
        // A lógica de formatação no DAL lidará com isso.
        if (empresa.getEndereco() == null || empresa.getEndereco().getRua() == null || empresa.getEndereco().getRua().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("A rua no endereço da empresa é obrigatória."));
        }

        boolean sucesso = empresaService.cadastro(empresa);
        if (sucesso) {
            // Após salvar/atualizar, busca a (única) empresa para retornar o estado mais recente.
            Empresa empresaSalva = empresaService.verificarEmpresaExistente();
            return ResponseEntity.ok(empresaSalva != null ? empresaSalva : empresa); // Retorna a salva ou a de entrada se algo falhar na busca pós-save
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao salvar empresa. Verifique os dados ou log do servidor."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaService.getId(id);
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro("Empresa não encontrada com o ID: " + id));
        }
    }

    @GetMapping("/buscar-por-cep/{cep}")
    public ResponseEntity<Empresa> buscarPorCep(@PathVariable String cep) {
        Empresa empresa = empresaService.buscarEmpresaPorCep(cep); // Corrigido para usar o service
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/verificar")
    public ResponseEntity<Empresa> verificarEmpresa() {
        Empresa empresa = empresaService.verificarEmpresaExistente();
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
