package sosanimais.com.example.app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.TransferenciaService;
import sosanimais.com.example.app.model.Transfere_to_Baia;
import sosanimais.com.example.app.model.Transferencia;
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


    /*
    Json enviado para ca ta com esses elementos
    const transferenciaData = {
                // id será gerado automaticamente no backend
                data: new Date(dataInput.value).toISOString(),
                matTransfere: parseInt(matriculaInput.value),
                // Dados adicionais para contexto (não necessariamente enviados ao backend)
                animalId: animalInput.value.trim(),
                baiaDestino: selectedBaiaId,
                baiaNome: selectedBaiaName,
                tipoBaia: selectedBaiaType
            };

     */

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


    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Transferencia entidade) { //correto
        boolean aux = transfereService.atualizar(entidade);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Transferencia"));
    }

  

}
