package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.exceptions.IlegalNumberException;
import com.example.exceptions.MalEstadoPedidoException;
import com.example.exceptions.UnexistentUser;
import com.example.model.PedidoComanda;
import com.example.model.Valoracion;
import com.example.repository.PedidosRepository;
import com.example.service.RiderService;
import com.example.service.UsuarioService;

@SpringBootTest
class TiComoGp4ApplicationTestsSPRINT2 {

	private Valoracion valoracion, valoracionG;
	@Autowired
	private UsuarioService usuarioService;
	private Map<String, String> comanda;
	Optional<PedidoComanda> pedidoComanda;
	private PedidoComanda pedidoComandaG;
	@Autowired
	private RiderService riderService;
	@Autowired
	private PedidosRepository pedidoRepo;

	// --------------------------------------------------------------------------------------------------------
	// VALORACIÓN DE RESTAURANTES
	// --------------------------------------------------------------------------------------------------------

	/**
	 * VALORACIÓN DE RESTAURANTE INEXISTENTE
	 */
	@Test
	void valorarRestauranteInexistente() {
		valoracion = new Valoracion(4, "FALSO", "1234");
		try {
			valoracionG = usuarioService.valorarRestaurante(valoracion);
		} catch (Exception e) {
			assertNull(valoracionG);
		}
	}
	

	/**
	 * VALORACIÓN CORRECTA DE RESTAURANTE
	 */
	@Test
	void valorarRestaurante() throws UnexistentUser {
		valoracion = new Valoracion(4, "FALSO", "3");
		valoracionG = usuarioService.valorarRestaurante(valoracion);
		assertEquals(valoracion, valoracionG);

	}

	// --------------------------------------------------------------------------------------------------------
	// VALORACIÓN DE RIDERS
	// --------------------------------------------------------------------------------------------------------

	/**
	 * VALORACIÓN DE RIDER INEXISTENTE
	 */
	@Test
	void valorarRiderInexistente() {
		valoracion = new Valoracion(4, "FALSO", "1234");
		try {
			valoracionG = usuarioService.valorarRider(valoracion);
		} catch (UnexistentUser e) {
			assertNull(valoracionG);
		}
	}

	/**
	 * VALORACIÓN DE RIDER CORRECTA
	 */
	@Test
	void valorarRider() throws UnexistentUser {
		valoracion = new Valoracion(4, "FALSO", "12345674J");
		valoracionG = usuarioService.valorarRider(valoracion);
		assertEquals(valoracion, valoracionG);
	}

	// --------------------------------------------------------------------------------------------------------
	// REALIZACIÓN DE PEDIDOS
	// --------------------------------------------------------------------------------------------------------

	/**
	 * REALIZACIÓN DE PEDIDO CORRECTO
	 */
	@Test
	void realizarPedidoCorrecto() throws IlegalNumberException {
		comanda = new HashMap<String, String>();
		comanda.put("nifCliente", "44444444D");
		comanda.put("idRestaurante", "1");
		pedidoComandaG = usuarioService.realizarPedido(comanda);
		assertNotNull(pedidoComandaG);
		
	}

	// --------------------------------------------------------------------------------------------------------
	// SITUACION DEL RIDER ELIGIENDO PEDIDOS A REPARTIR
	// --------------------------------------------------------------------------------------------------------

	/**
	 * RIDER COGE PEDIDO INCORRECTO CON MAL ESTADO
	 */
	@Test
	void cogerPedidoIncorrecto() {
		pedidoComanda = pedidoRepo.findById("637c045236fb2b019dca7a84");
		try {
			pedidoComandaG =riderService.cambiarEstadoPedido(pedidoComanda.get());
		} catch (MalEstadoPedidoException e) {
			assertNull(pedidoComandaG);
		}	

	}
	
	/**
	 * RIDER COGE PEDIDO CORRECTO
	 */
	@Test
	void cogerPedidoCorrecto() throws MalEstadoPedidoException {
		pedidoComanda = pedidoRepo.findById("637c044336fb2b019dca7a83");
		pedidoComandaG =riderService.cambiarEstadoPedido(pedidoComanda.get());
		assertEquals("EnReparto", pedidoComandaG.getEstadoPedido());	
	}
	
	
	
	// --------------------------------------------------------------------------------------------------------
	// SITUACION DEL USUARIOS MARCANDO PEDIDOS COMO ENTREGADOS
	// --------------------------------------------------------------------------------------------------------
	
	/**
	 * USUARIO MARCA PEDIDO CORRECTO COMO ENTREGADO
	 */
	@Test
	void marcarPedidoEntregado() throws MalEstadoPedidoException, UnexistentUser {
		pedidoComanda = pedidoRepo.findById("637c045236fb2b019dca7a84");
		pedidoComandaG = usuarioService.cambiarEstadoPedido(pedidoComanda.get());
		assertEquals("Entregado", pedidoComandaG.getEstadoPedido());	
	}
	
	
	/**
	 * USUARIO MARCA PEDIDO ERRÓNEO COMO ENTREGADO
	 * @throws UnexistentUser 
	 */
	@Test
	void marcarPedidoEntregadoIncorrecto() throws UnexistentUser{
		pedidoComanda = pedidoRepo.findById("637c044336fb2b019dca7a83");
		try {
			pedidoComandaG = usuarioService.cambiarEstadoPedido(pedidoComanda.get());
		} catch (MalEstadoPedidoException e) {
			assertNull(pedidoComandaG);	
		}
	}
	
	
	
	

}
