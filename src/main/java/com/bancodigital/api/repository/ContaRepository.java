package com.bancodigital.api.repository;

import com.bancodigital.api.model.Conta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContaRepository {
    private final List<Conta> contas = new ArrayList<>();
    private Long nextId = 1L;

    public List<Conta> listarTodas() {
        return contas;
    }

    public Optional<Conta> buscarPorId(Long id) {
        return contas.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Conta salvar(Conta conta) {
        conta.setId(nextId++);
        contas.add(conta);
        return conta;
    }

    public void encerrarConta(Long id) {
        // if / throws
        buscarPorId(id).ifPresent(conta -> conta.setAtiva(false));
          // .orElse(ResponseEntity.notFound().build());
    }
}