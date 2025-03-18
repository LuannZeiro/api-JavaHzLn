package com.bancodigital.api.controller;

import com.bancodigital.api.model.Conta;
import com.bancodigital.api.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> cadastrarConta(@RequestBody Conta conta) {
        return ResponseEntity.ok(contaService.cadastrarConta(conta));
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listarContas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/depositar/{id}")
    // @PostMapping ("/deposito/{id}")
    public ResponseEntity<Conta> depositar(@PathVariable Long id, @RequestParam double valor) {
        return ResponseEntity.ok(contaService.realizarDeposito(id, valor));
    }

    @PutMapping("/sacar/{id}")
    // @PostMapping ("/saque/{id}")
    public ResponseEntity<Conta> sacar(@PathVariable Long id, @RequestParam double valor) {
        return ResponseEntity.ok(contaService.realizarSaque(id, valor));
    }
}
