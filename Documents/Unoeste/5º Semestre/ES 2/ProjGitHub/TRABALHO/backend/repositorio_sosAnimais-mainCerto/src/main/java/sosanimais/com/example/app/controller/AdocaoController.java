package sosanimais.com.example.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.AdocaoService;
import sosanimais.com.example.app.model.Adocao_Animal;
import sosanimais.com.example.app.model.util.Erro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/adocao")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

    @GetMapping
    public ResponseEntity<Object> getAllAdocao(@RequestParam(required = false) String filtro) {
        List<Adocao_Animal> adocao = new ArrayList<>();
        try {
            System.out.println("Filtro recebido: " + filtro);
            if (filtro != null && !filtro.isEmpty()) {
                adocao = adocaoService.buscarComFiltro(filtro);
            } else {
                adocao = adocaoService.buscarTodos();
            }
            System.out.println("Número de adoções encontradas: " + adocao.size());
        } catch (SQLException e) {
            System.out.println("Erro ao buscar adoções: " + e.getMessage());
            throw new RuntimeException(e);
        }
        if (!adocao.isEmpty()) {
            return ResponseEntity.ok(adocao);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Nenhuma adoção encontrada!"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAdById(@PathVariable Long id){//ok
        Adocao_Animal aux=adocaoService.buscarPorId(id);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Não foi possível encontrar a adoção de id: "+id));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> addAdocao(@RequestBody Adocao_Animal ad){//ok
        Adocao_Animal aux=adocaoService.salvarAdocao(ad);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao salvar a adoção"));
    }

    @PutMapping("/{id}")//ok
    public ResponseEntity<Object> updateAdocao(@PathVariable Long id, @RequestBody Adocao_Animal ad){
        ad.setAdoCod(id);
        Adocao_Animal aux=adocaoService.atualizarAdocao(ad);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar a adoção"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAdocao(@PathVariable Long id) {
        try {
            System.out.println("=== INÍCIO DO PROCESSO DE EXCLUSÃO DE ADOÇÃO ===");
            System.out.println("ID da adoção a ser excluída: " + id);
            
            Adocao_Animal aux = adocaoService.buscarPorId(id);
            System.out.println("Adoção encontrada: " + (aux != null));
            
            if (aux == null) {
                System.out.println("Adoção não encontrada no banco de dados!");
                return ResponseEntity.badRequest().body(new Erro("Adoção não encontrada!"));
            }
            
            System.out.println("Dados da adoção:");
            System.out.println("ID: " + aux.getAdoCod());
            System.out.println("Animal: " + aux.getAniCod());
            System.out.println("Adotante: " + aux.getAdoMat());
            System.out.println("Funcionário: " + aux.getFuncCod());
            
            boolean status = adocaoService.deletarAdocao(aux);
            System.out.println("Status da exclusão: " + status);
            
            if (status) {
                System.out.println("Adoção excluída com sucesso!");
                return ResponseEntity.ok("Adoção excluída com sucesso!");
            } else {
                System.out.println("Falha ao excluir adoção!");
                return ResponseEntity.badRequest().body(new Erro("Erro ao excluir a adoção!"));
            }
        } catch (Exception e) {
            System.out.println("ERRO durante a exclusão: " + e.getMessage());
            System.out.println("Stack trace do erro:");
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao processar a exclusão: " + e.getMessage()));
        }
    }
}
