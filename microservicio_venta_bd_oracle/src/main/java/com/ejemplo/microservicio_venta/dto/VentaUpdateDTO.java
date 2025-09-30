package com.ejemplo.microservicio_venta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) utilizado para la actualización de una venta.
 * Este objeto es recibido en el controlador para actualizar una venta existente.
 */
public class VentaUpdateDTO {

    /**
     * Fecha y hora en que se realizó la venta.
     * Se utiliza LocalDateTime para almacenar tanto la fecha como la hora.
     */
    @NotNull
    private LocalDateTime fechaVenta;

    /**
     * Tipo de venta (por ejemplo: "contado", "crédito").
     * Se utiliza String para representar el tipo de venta.
     */
    @NotBlank
    private String tipo;

    /**
     * Monto total de la venta.
     * Se utiliza BigDecimal para manejar valores monetarios con precisión.
     */
    @NotNull
    private BigDecimal total;

    // Getters y setters

    /**
     * Obtiene la fecha y hora de la venta.
     * @return fecha y hora de la venta.
     */
    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    /**
     * Establece la fecha y hora de la venta.
     * @param fechaVenta fecha y hora de la venta.
     */
    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * Obtiene el tipo de la venta.
     * @return tipo de la venta.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la venta.
     * @param tipo tipo de la venta.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el monto total de la venta.
     * @return monto total de la venta.
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Establece el monto total de la venta.
     * @param total monto total de la venta.
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
