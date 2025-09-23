package com.ejemplo.microservicio_venta.service;

import com.ejemplo.microservicio_venta.model.Venta;
import com.ejemplo.microservicio_venta.model.ItemVenta;
import com.ejemplo.microservicio_venta.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    // Crear o actualizar una venta
    public Venta save(Venta venta) {
        venta.setFechaVenta(LocalDateTime.now());
        // Asociar cada ItemVenta a la Venta para mantener la relación bidireccional
        if (venta.getItems() != null) {
            venta.getItems().forEach(item -> item.setVenta(venta));
        }

        // Calcular el total sumando subtotales de items
        BigDecimal total = (venta.getItems() == null || venta.getItems().isEmpty())
                ? BigDecimal.ZERO
                : venta.getItems().stream()
                    .map(ItemVenta::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

        venta.setTotal(total);

        return ventaRepository.save(venta);
    }

    // Obtener todas las ventas
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    // Buscar venta por id
    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }

    // Eliminar venta por id
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }

    // Calcular ganancias por tipo: diaria, mensual, anual
    public BigDecimal calcularGanancias(String tipo) {
        LocalDateTime inicio;
        LocalDateTime fin;
        LocalDateTime ahora = LocalDateTime.now();

        switch (tipo.toLowerCase()) {
            case "diaria":
                inicio = ahora.toLocalDate().atStartOfDay();
                fin = inicio.plusDays(1);
                break;
            case "mensual":
                inicio = ahora.withDayOfMonth(1).toLocalDate().atStartOfDay();
                fin = inicio.plusMonths(1);
                break;
            case "anual":
                inicio = ahora.withDayOfYear(1).toLocalDate().atStartOfDay();
                fin = inicio.plusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Tipo de ganancia no válido: " + tipo);
        }

        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(inicio, fin);
        return ventas.stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
