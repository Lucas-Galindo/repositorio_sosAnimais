package sosanimais.com.example.app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.TransferenciaService;
import sosanimais.com.example.app.model.Transfere_to_Baia;
import sosanimais.com.example.app.model.Transferencia;
import sosanimais.com.example.app.model.objetosAux.FiltrosTransferencia;
import sosanimais.com.example.app.model.util.Erro;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/transferencias")
public class TransferenciaController {


    //O que é a transferencia?
    /*
     * 1 - A transferencia é primeiro registrar dados da propria entidade, como data,
     * Transfereionario e criar um id.
     *
     * 2 - Fluxo tambem precisa adicionar a baia, animal, Transfereionario e id da transferencia
     * dentro da associativa
     *
     *
     *
     * */


    TransferenciaService transfereService = new TransferenciaService();

    @PostMapping(path = "/",consumes = "application/json")
    public ResponseEntity<Object> salvarTransferencia(@RequestBody Transferencia elemento) { // correto
        Transferencia aux = transfereService.salvarTransferencia(elemento);
        if (aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro salvar registro de Transferencia"));
    }

    @PostMapping(path="/salvarDadosTransf/",consumes = "application/json")
    public ResponseEntity<Object> salvarDadosAssociativa(@RequestBody Transfere_to_Baia elemento) { // correto

        boolean aux = transfereService.salvarDados(elemento);
        if (aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body(new Erro("Erro salvar dados de Transação"));
    }


    @GetMapping("/busca-transfere-data/{data}") // correto
    public ResponseEntity<Object> getTransfereData(@PathVariable Date data) {
        Transferencia aux = transfereService.getRegistroData(data);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Transferencia"));
    }

    @GetMapping("/busca-func/{mat}") // correto
    public ResponseEntity<Object> getTransfereData(@PathVariable int mat) {
        Transferencia aux = transfereService.getRegistroFunc(mat);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Transferencia"));
    }

    @GetMapping("/{id}") // correto
    public ResponseEntity<Object> getTransfereId(@PathVariable Long id) {
        Transferencia aux = transfereService.getId(id);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Transferencia"));
    }

    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getTransfereLista() { //coreto
        List<Transferencia> lista = transfereService.getAll("");
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Transferencia"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getTransfereLista(@PathVariable String filtro) {
        List<Transferencia> lista = transfereService.getAll(filtro);
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Transferencia"));
    }


    @GetMapping("/{id}/detalhes")
    public ResponseEntity<Object> getDetalhesTransferencia(@PathVariable Long id){
        List<Transferencia> aux = transfereService.pesquisaTransfere(id);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao recuperar detalhes da transferencia"));

    }

    @GetMapping("/pesquisa")
    public ResponseEntity<Object> pesquisarTransferencias(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal,
            @RequestParam(required = false) int matFunc,
            @RequestParam(required = false) String nomeFunc,
            @RequestParam(required = false) String categoriaBaias
    ){
        FiltrosTransferencia filtros = new FiltrosTransferencia(dataInicial,dataFinal,matFunc,nomeFunc,categoriaBaias);
        Transferencia aux = transfereService.pesquisaDetalhesTransfere(filtros);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Nao foi possivel realizar a pesquisa"));

    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Transferencia entidade) { //correto
        boolean aux = transfereService.atualizar(entidade);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Transferencia"));
    }

  

}
