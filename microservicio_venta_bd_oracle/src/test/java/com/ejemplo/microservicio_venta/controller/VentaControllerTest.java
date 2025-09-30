package com.ejemplo.microservicio_venta.controller;

// Importamos el modelo ItemVenta para crear ítems de prueba
import com.ejemplo.microservicio_venta.model.ItemVenta;

// Importamos el modelo Venta para crear objetos de prueba
import com.ejemplo.microservicio_venta.model.Venta;

// Importamos el servicio que vamos a mockear para evitar usar la base real
import com.ejemplo.microservicio_venta.service.VentaService;

// Importamos el mapper que se inyecta en el controlador y también lo mockearemos
import com.ejemplo.microservicio_venta.mapper.VentaMapper;

// Anotación de JUnit 5 para marcar un método de prueba
import org.junit.jupiter.api.Test;

// Mockito para crear mocks y definir comportamientos simulados
import org.mockito.Mockito;

// Anotación para testeo enfocado en la capa web (controladores) de Spring Boot
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

// Anotación para inyectar mocks en el contexto Spring
import org.springframework.boot.test.mock.mockito.MockBean;

// Permite simular peticiones HTTP en pruebas de controladores
import org.springframework.test.web.servlet.MockMvc;

// Anotación para inyectar MockMvc automáticamente
import org.springframework.beans.factory.annotation.Autowired;

// Define tipo de contenido JSON en peticiones/respuestas HTTP
import org.springframework.http.MediaType;

// Para crear listas y colecciones de prueba
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Clases para manejar fechas y dinero en las ventas
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Importamos métodos estáticos para construir y validar peticiones MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Clase de test enfocada en el controlador VentaController.
 * Se usa @WebMvcTest para cargar solo el contexto necesario para testear
 * la capa web (controladores, filtros, etc.), sin levantar toda la aplicación.
 */
@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    /**
     * MockMvc se usa para simular peticiones HTTP sin necesidad de levantar el servidor real.
     * Spring lo inyecta automáticamente.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Se crea un mock del servicio de ventas para controlar la lógica de negocio
     * durante las pruebas, evitando acceder a la base real.
     */
    @MockBean
    private VentaService ventaService;

    /**
     * Se crea un mock del mapper para evitar lógica compleja en el test unitario
     * del controlador, enfocándonos solo en el flujo web.
     */
    @MockBean
    private VentaMapper ventaMapper;

    /**
     * Test para el endpoint GET /api/ventas que devuelve todas las ventas.
     * Aquí simulamos que el servicio retorna dos ventas predefinidas,
     * cada una con sus respectivos ítems, y calculamos el total
     * en base al subtotal de esos ítems, tal como lo haría el servicio real.
     */
    @Test
    void listarVentas_retornaLista() throws Exception {
        // Creamos un ItemVenta para la primera venta
        ItemVenta item1 = new ItemVenta();
        item1.setNombreProducto("Producto A");
        item1.setCantidad(3); // 3 unidades
        item1.setPrecioUnitario(new BigDecimal("50.00")); // $50.00 c/u
        // Subtotal esperado: 3 * 50.00 = 150.00

        // Creamos la primera venta y le asignamos el item
        Venta v1 = new Venta();
        v1.setId(1L);
        v1.setFechaVenta(LocalDateTime.of(2025, 9, 25, 10, 0));
        v1.setItems(List.of(item1)); // Asignamos el ítem
        item1.setVenta(v1); // Relación bidireccional

        // Creamos un ItemVenta para la segunda venta
        ItemVenta item2 = new ItemVenta();
        item2.setNombreProducto("Producto B");
        item2.setCantidad(5); // 5 unidades
        item2.setPrecioUnitario(new BigDecimal("50.00")); // $50.00 c/u
        // Subtotal esperado: 5 * 50.00 = 250.00

        // Creamos la segunda venta y le asignamos el ítem
        Venta v2 = new Venta();
        v2.setId(2L);
        v2.setFechaVenta(LocalDateTime.of(2025, 9, 26, 12, 30));
        v2.setItems(List.of(item2));
        item2.setVenta(v2); // Relación bidireccional

        // Preparamos una lista con las dos ventas simuladas
        List<Venta> ventas = Arrays.asList(v1, v2);

        // Indicamos que cuando se llame a ventaService.findAll() devuelva nuestra lista simulada
        Mockito.when(ventaService.findAll()).thenReturn(ventas);

        // Simulamos una petición HTTP GET al endpoint /api/ventas y verificamos el resultado
        mockMvc.perform(get("/api/ventas")
                .accept(MediaType.APPLICATION_JSON)) // Esperamos un JSON en la respuesta
                .andExpect(status().isOk()) // Código HTTP 200 OK
                // Validamos que el primer objeto JSON tenga id=1 y total=150.00
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].total").value(150.00))
                // Validamos que el segundo objeto JSON tenga id=2 y total=250.00
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].total").value(250.00));
    }

    /**
     * Test para el endpoint GET /api/ventas/{id} que devuelve una venta por su ID.
     * Simulamos que existe una venta con id=1.
     */
    @Test
    void obtenerVentaPorId_retornaVenta() throws Exception {
        Long id = 1L;
        System.out.println("=== ESTO VIENE DEL TEST DE MOCK ===");
        // Creamos un ItemVenta con cantidad y precio para calcular subtotal
        ItemVenta item1 = new ItemVenta();
        item1.setCantidad(3);
        item1.setPrecioUnitario(new BigDecimal("50.00"));

        // Creamos la venta simulada con datos fijos
        Venta venta = new Venta();
        venta.setId(id);
        venta.setFechaVenta(LocalDateTime.of(2025, 9, 25, 10, 0));
        venta.setItems(List.of(item1)); // Asignamos el ítem
        item1.setVenta(venta); // Relación bidireccional

        // Debug: imprimimos el total calculado automáticamente
        System.out.println("TOTAL esperado = " + venta.getTotal());

        // Indicamos que al llamar a ventaService.findById(id) devuelva Optional con la venta creada
        Mockito.when(ventaService.findById(id)).thenReturn(Optional.of(venta));

        // Simulamos petición GET a /api/ventas/1 y validamos la respuesta
        mockMvc.perform(get("/api/ventas/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Código HTTP 200 OK
                .andExpect(jsonPath("$.id").value(id)) // El id en el JSON debe ser 1
                .andExpect(jsonPath("$.total").value(150.00)); // El total debe ser 150.00
    }

    /**
     * Test para el endpoint GET /api/ventas/{id} cuando la venta no existe.
     * Se espera un código HTTP 404 Not Found.
     */
    @Test
    void obtenerVentaPorId_noExiste_retorna404() throws Exception {
        Long id = 99L;

        // Simulamos que no existe venta con id=99, retornando Optional.empty()
        Mockito.when(ventaService.findById(id)).thenReturn(Optional.empty());

        // Simulamos petición GET con id inexistente y validamos que se recibe 404
        mockMvc.perform(get("/api/ventas/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // HTTP 404 Not Found esperado
    }
}
