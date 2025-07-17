package com.alejandro.facturacion.controller;

import com.alejandro.facturacion.entity.Client;
import com.alejandro.facturacion.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de clientes.
 * Proporciona endpoints para crear, listar, obtener y eliminar clientes.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Crea un nuevo cliente.
     * @param client Cliente a crear
     * @return Cliente creado o error si ya existe email o identificación
     */
    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        if (clientService.existsByEmail(client.getEmail()) ||
            clientService.existsByIdentificationNumber(client.getIdentificationNumber())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(clientService.saveClient(client));
    }

    /**
     * Obtiene la lista de todos los clientes.
     * @return Lista de clientes
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    /**
     * Obtiene un cliente por su ID.
     * @param id ID del cliente
     * @return Cliente encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un cliente por su ID.
     * @param id ID del cliente
     * @return 204 No Content si se elimina correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
