package com.ejemplo.microservicio_venta.controller;

import com.ejemplo.microservicio_venta.mapper.VentaMapper;
import com.ejemplo.microservicio_venta.dto.VentaDTO;
import com.ejemplo.microservicio_venta.dto.VentaUpdateDTO;
import com.ejemplo.microservicio_venta.model.Venta;
import com.ejemplo.microservicio_venta.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// Import para HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Controlador REST para gestionar las operaciones CRUD de Venta.
 * 
 * Utiliza inyección de dependencias para obtener los servicios y mappers necesarios.
 */
@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    // Servicio para lógica de negocio relacionada a ventas
    private final VentaService ventaService;

    // Mapper para convertir entre entidad Venta y DTOs
    private final VentaMapper ventaMapper;

    /**
     * Constructor con inyección de dependencias.
     * @param ventaService Servicio para manejar operaciones de ventas
     * @param ventaMapper Mapper para transformar entidades y DTOs
     */
    public VentaController(VentaService ventaService, VentaMapper ventaMapper) {
        this.ventaService = ventaService;
        this.ventaMapper = ventaMapper;
    }

    /**
     * Crear una nueva venta.
     * @param venta La venta recibida en el cuerpo de la petición (validada)
     * @return Respuesta con la venta creada y código HTTP 201
     */
    @PostMapping
    public ResponseEntity<Venta> crearVenta(@Valid @RequestBody Venta venta) {
        Venta saved = ventaService.save(venta);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * Obtener todas las ventas.
     * @return Lista de ventas con código HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.findAll();
        return ResponseEntity.ok(ventas);
    }

    /**
     * Obtener una venta por su ID.
     * @param id Identificador de la venta
     * @return Venta encontrada o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        Optional<Venta> ventaOpt = ventaService.findById(id);
        return ventaOpt.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualizar una venta por su ID.
     * @param id Identificador de la venta a actualizar
     * @param dto DTO con los datos para actualización (validado)
     * @return DTO actualizado con enlace HATEOAS o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(
        @PathVariable Long id,
        @Valid @RequestBody VentaUpdateDTO dto) {

        Optional<Venta> ventaOpt = ventaService.findById(id);
        if (ventaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Usamos la instancia inyectada para mapear el DTO a la entidad existente
        Venta ventaActualizada = ventaMapper.toEntity(dto, ventaOpt.get());

        Venta ventaGuardada = ventaService.save(ventaActualizada);

        // Convertimos la entidad guardada a DTO
        VentaDTO response = ventaMapper.toDTO(ventaGuardada);

        // Agregamos enlace HATEOAS para obtener la venta actualizada
        response.add(linkTo(methodOn(VentaController.class).obtenerVentaPorId(id)).withSelfRel());

        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar una venta por su ID.
     * @param id Identificador de la venta a eliminar
     * @return Respuesta sin contenido o 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        Optional<Venta> ventaOpt = ventaService.findById(id);
        if (ventaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Calcular ganancias según un tipo dado.
     * @param tipo Tipo de ganancia a calcular
     * @return Ganancias calculadas
     */
    @GetMapping("/ganancias")
    public ResponseEntity<BigDecimal> calcularGanancias(@RequestParam("tipo") String tipo) {
        BigDecimal ganancias = ventaService.calcularGanancias(tipo);
        return ResponseEntity.ok(ganancias);
    }
}
