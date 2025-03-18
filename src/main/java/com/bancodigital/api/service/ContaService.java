package com.bancodigital.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bancodigital.api.model.Conta;
import com.bancodigital.api.repository.ContaRepository;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    public Conta cadastrarConta(Conta conta) {
        conta.setAtiva(true);
        conta.setDataAbertura(LocalDate.now());
        return contaRepository.salvar(conta);
    }

    public List<Conta> listarContas() {
        return contaRepository.listarTodas();
    }

    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.buscarPorId(id);
    }

    public void encerrarConta(Long id) {
        contaRepository.encerrarConta(id);
    }

    public Conta realizarDeposito(Long id, double valor) {
        Optional<Conta> contaOpt = contaRepository.buscarPorId(id);
        if (contaOpt.isPresent() && valor > 0) {
            Conta conta = contaOpt.get();
            conta.setSaldo(conta.getSaldo() + valor);
            return conta;
        }
        throw new IllegalArgumentException("Conta não encontrada ou valor inválido");
    }

    public Conta realizarSaque(Long id, double valor) {
        Optional<Conta> contaOpt = contaRepository.buscarPorId(id);
        if (contaOpt.isPresent() && valor > 0) {
            Conta conta = contaOpt.get();
            if (conta.getSaldo() >= valor) {
                conta.setSaldo(conta.getSaldo() - valor);
                return conta;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo Insulficiente");
            }
        }
        throw new IllegalArgumentException("Conta não encontrada");
    }
}