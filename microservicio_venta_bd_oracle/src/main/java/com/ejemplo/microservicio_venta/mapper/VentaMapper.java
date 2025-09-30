package com.ejemplo.microservicio_venta.mapper;

import com.ejemplo.microservicio_venta.model.Venta;
import com.ejemplo.microservicio_venta.dto.VentaDTO;
import com.ejemplo.microservicio_venta.dto.VentaUpdateDTO;
import org.springframework.stereotype.Component;

/**
 * Clase encargada de mapear entre las entidades {@link Venta} y los objetos de transferencia de datos (DTOs).
 */
@Component
public class VentaMapper {

    /**
     * Convierte un {@link VentaUpdateDTO} en una entidad {@link Venta} existente.
     * Protege contra dto nulo: si dto es null, devuelve la venta sin cambios.
     *
     * @param dto            El DTO con los datos a actualizar (puede ser no nulo).
     * @param ventaExistente La entidad {@link Venta} que se actualizará (no debe ser null).
     * @return La entidad {@link Venta} actualizada.
     */
    public Venta toEntity(VentaUpdateDTO dto, Venta ventaExistente) {
        if (ventaExistente == null) {
            // No hay entidad a actualizar; devolvemos null por seguridad (el controlador/servicio debe manejarlo).
            return null;
        }
        if (dto == null) {
            // Si no hay dto, no hacemos cambios.
            return ventaExistente;
        }

        // Actualizamos solo los campos que vienen en el DTO
        ventaExistente.setFechaVenta(dto.getFechaVenta());
        ventaExistente.setTotal(dto.getTotal());
        return ventaExistente;
    }

    /**
     * Convierte una entidad {@link Venta} en un {@link VentaDTO}.
     *
     * Comportamiento:
     * - Si la entidad {@code venta} es null, se devuelve un {@code VentaDTO} vacío (no null).
     *   Esto evita que controladores que hacen directamente {@code dto.add(...)} lancen NPE.
     * - Si algún campo de la entidad es null, el DTO reflejará ese null (el controlador/tests
     *   pueden validar/gestionar esto, p.ej. verificar que id != null antes de construir links).
     *
     * Nota: devolver un DTO vacío evita fallos inmediatos, pero lo ideal es que el flujo
     * de negocio no intente mapear entidades nulas: validar en el servicio/controlador.
     *
     * @param venta La entidad {@link Venta} a convertir.
     * @return El DTO {@link VentaDTO} con los datos de la entidad (nunca null).
     */
    public VentaDTO toDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();

        // Si la entidad es null, devolvemos un DTO vacío (no null) para evitar NPE al añadir links.
        if (venta == null) {
            return dto;
        }

        // Mapear campos cuando existan
        dto.setId(venta.getId());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setTotal(venta.getTotal());
        // No seteamos tipo porque no está en Venta (si en el futuro existe, mapear aquí)

        return dto;
    }
}
