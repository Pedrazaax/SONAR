package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.exceptions.IlegalNumberException;
import com.example.exceptions.IncompleteFormException;
import com.example.exceptions.MalEstadoPedidoException;
import com.example.exceptions.PerfilBloqueadoException;
import com.example.exceptions.UnexistentUser;
import com.example.model.Facturas;
import com.example.model.PedidoComanda;
import com.example.model.Usuario;
import com.example.model.Valoracion;
import com.example.repository.FacturasRepository;
import com.example.repository.PedidosRepository;
import com.example.service.RestauranteService;
import com.example.service.RiderService;
import com.example.service.UsuarioService;
import com.itextpdf.layout.Document;

@SpringBootTest
class TiComoGp4ApplicationTestsSPRINT2 {

	private Valoracion valoracion, valoracionG;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RestauranteService restauranteService;
	private Optional<Facturas> facturaModel;
	private Map<String, String> comanda;
	Optional<PedidoComanda> pedidoComanda;
	private Map<String, Document> factura;
	private PedidoComanda pedidoComandaG;
	@Autowired
	private RiderService riderService;
	@Autowired
	private PedidosRepository pedidoRepo;
	@Autowired
	private FacturasRepository facturaRepo;
	private Optional<Usuario> usuario;
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
			pedidoComandaG = riderService.cambiarEstadoPedido(pedidoComanda.get());
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
		pedidoComandaG = riderService.cambiarEstadoPedido(pedidoComanda.get());
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
	 * 
	 * @throws UnexistentUser
	 */
	@Test
	void marcarPedidoEntregadoIncorrecto() throws UnexistentUser {
		pedidoComanda = pedidoRepo.findById("637c044336fb2b019dca7a83");
		try {
			pedidoComandaG = usuarioService.cambiarEstadoPedido(pedidoComanda.get());
		} catch (MalEstadoPedidoException e) {
			assertNull(pedidoComandaG);
		}
	}

	/**
	 * GENERAR FACTURA INCORRECTO
	 * 
	 * @throws IOException
	 * @throws IncompleteFormException
	 */
	@Test
	void restauranteGeneraFactura() throws Exception {
		pedidoComanda = pedidoRepo.findById("637c044336fb2b019dca7a83");
		facturaModel = facturaRepo.findById("637c044336fb2b019dca7a83");
		try {
			factura = restauranteService.restauranteGeneraFactura(pedidoComanda.get(), facturaModel.get());
		} catch (Exception e) {
			assertNull(factura);
		}
	}

	/**
	 * COGER PEDIDOS POR ID RESTAURANTE
	 */
	@Test
	void getPedidosById() throws Exception {
		List<PedidoComanda> totalPedidos = restauranteService.getPedidosByIdRestaurante("1", "entregado");
		assertNotNull(totalPedidos);

	}
	
	/**
	 * LOCK PERSON POR ADMINISTRADOR
	 * @throws PerfilBloqueadoException 
	 */
	@Test
	void lockPersonAdmin() throws PerfilBloqueadoException {
		usuario = usuarioService.findById("95748569A");
		Usuario usuarioG = usuarioService.lockPerson(usuario.get());
		assertEquals(0,usuarioG.getIntentos());
		usuarioService.unlockPerson(usuario.get());
	}
	
	/**
	 * UNLOCK PERSON POR ADMINISTRADOR
	 * @throws PerfilBloqueadoException 
	 */
	@Test
	void unlockPersonAdmin() throws PerfilBloqueadoException {
		usuario = usuarioService.findById("85746586S");
		Usuario usuarioG = usuarioService.unlockPerson(usuario.get());
		assertEquals(5,usuarioG.getIntentos());
		usuarioService.lockPerson(usuario.get());
	}
	
	

}
