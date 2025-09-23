package com.ejemplo.microservicio_venta.controller;

import com.ejemplo.microservicio_venta.model.Venta;
import com.ejemplo.microservicio_venta.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Crear nueva venta
    @PostMapping
    public ResponseEntity<Venta> crearVenta(@Valid @RequestBody Venta venta) {
        Venta saved = ventaService.save(venta);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.findAll();
        return ResponseEntity.ok(ventas);
    }

    // Obtener venta por id
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        Optional<Venta> ventaOpt = ventaService.findById(id);
        return ventaOpt.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar venta por id
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable Long id, @Valid @RequestBody Venta venta) {
        Optional<Venta> ventaOpt = ventaService.findById(id);
        if (ventaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        venta.setId(id);
        Venta updated = ventaService.save(venta);
        return ResponseEntity.ok(updated);
    }

    // Eliminar venta por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        Optional<Venta> ventaOpt = ventaService.findById(id);
        if (ventaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint que ya tenías para calcular ganancias (dependerá del service)
    @GetMapping("/ganancias")
    public ResponseEntity<BigDecimal> calcularGanancias(@RequestParam("tipo") String tipo) {
        BigDecimal ganancias = ventaService.calcularGanancias(tipo);
        return ResponseEntity.ok(ganancias);
    }
}
