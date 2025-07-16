package com.alejandro.facturacion.controller;

import com.alejandro.facturacion.entity.Client;
import com.alejandro.facturacion.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // Crear cliente
    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        if (clientService.existsByEmail(client.getEmail()) ||
            clientService.existsByIdentificationNumber(client.getIdentificationNumber())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(clientService.saveClient(client));
    }

    // Listar todos los clientes
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
