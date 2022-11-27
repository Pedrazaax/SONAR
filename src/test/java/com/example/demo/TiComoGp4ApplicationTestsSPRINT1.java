package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.exceptions.ContraseniaIncorrectaException;
import com.example.exceptions.IlegalNumberException;
import com.example.exceptions.IncompleteFormException;
import com.example.exceptions.InvalidEmailException;
import com.example.exceptions.NonAdminValidationException;
import com.example.exceptions.PerfilBloqueadoException;
import com.example.exceptions.UnexistentUser;
import com.example.exceptions.YaEnUsoException;
import com.example.model.Plato;
import com.example.model.Restaurante;
import com.example.model.Rider;
import com.example.model.TokenSession;
import com.example.model.Usuario;
import com.example.repository.RestauranteRepository;
import com.example.repository.RiderRepository;
import com.example.repository.UsuarioRepository;
import com.example.service.LoginService;
import com.example.service.PlatoService;
import com.example.service.RegisterService;
import com.example.service.RestauranteService;
import com.example.service.RiderService;
import com.example.service.UsuarioService;

@SpringBootTest
class TiComoGp4ApplicationTestsSPRINT1 {

	@Autowired
	private RegisterService regService;
	@Autowired
	private LoginService logService;
	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private RiderRepository riderRepo;
	@Autowired
	private RestauranteRepository restauranteRepo;
	@Autowired
	private PlatoService platoServ;
	@Autowired
	private UsuarioService userServ;
	@Autowired
	private RestauranteService restauranteService;

	private Usuario usuario, usuarioG;
	private Rider rider, riderG;
	private TokenSession token, tokenG;
	private Plato plato, platoG;
	private Restaurante restaurante, restauranteG;
	private URL url = null;

	// --------------------------------------------------------------------------------------------------------
	// USUARIOS REGISTRO
	// --------------------------------------------------------------------------------------------------------

	/**
	 * INTRODUCCIÓN DE USUARIO CON EL MISMO EMAIL
	 */
	@Test
	void testMismoEmail()
			throws YaEnUsoException, ContraseniaIncorrectaException, IlegalNumberException, InvalidEmailException {
		usuario = new Usuario("12345678H", "Usuario", "usuario@usuario.com", "8Caracteres", "8Caracteres", 5,
				"Ciudad Real", "623534253");
		try {
			usuarioG = regService.signUpUsuario(usuario);
		} catch (YaEnUsoException e) {
			assertNull(usuarioG);
		}
	}

	/**
	 * INTRODUCCIÓN CORRECTA
	 */
	@Test
	void testRegistroCorrecto()
			throws YaEnUsoException, ContraseniaIncorrectaException, IlegalNumberException, InvalidEmailException {
		usuario = new Usuario("12345677G", "Pruebecita", "pruebecita@pruebecita.com", "8Caracteres", "8Caracteres", 5,
				"Ciudad Real", "623534253");
		usuarioG = regService.signUpUsuario(usuario);
		assertEquals(usuarioG, usuario);
		userRepo.deleteById("12345677G");
	}

	/**
	 * INTRODUCCIÓN DE USUARIO CON MALA CONTRASEÑA
	 */
	@Test
	void testMalaContraseña()
			throws YaEnUsoException, ContraseniaIncorrectaException, IlegalNumberException, InvalidEmailException {
		usuario = new Usuario("45353457", "Pablo", "quetal@gmail.com", "245", "MalaContraseña", 1, "Ciudad Real",
				"644265476");
		try {
			usuarioG = regService.signUpUsuario(usuario);
		} catch (ContraseniaIncorrectaException e) {
			assertNull(usuarioG);
		}
	}

	// --------------------------------------------------------------------------------------------------------
	// RIDERS REGISTRO
	// --------------------------------------------------------------------------------------------------------

	/**
	 * INTRODUCCIÓN DE RIDER CON EL MISMO EMAIL //
	 */
	@Test
	void testMismoEmailRider()
			throws YaEnUsoException, ContraseniaIncorrectaException, IlegalNumberException, InvalidEmailException {
		rider = new Rider("05985017A", "Pablo", "demo@gmail.com", "8Caracteres", "8Caracteres", 5, "Audi", "14315FAS",
				"carnet", "685345313", 2);
		try {
			riderG = regService.signUpRider(rider);
		} catch (YaEnUsoException e) {
			assertNull(riderG);
		}
	}

	@Test
	void testRegistroCorrectoRider()
			throws YaEnUsoException, ContraseniaIncorrectaException, IlegalNumberException, InvalidEmailException {
		rider = new Rider("11111111S", "Prueba", "pruebaaa@p.com", "8Caracteres", "8Caracteres", 5, "Prueba", "Prueba",
				"Prueba", "685345313", 2);
		riderG = regService.signUpRider(rider);
		assertEquals(riderG, rider);
		riderRepo.deleteById("11111111S");
	}

	/**
	 * INTRODUCCIÓN DE RIDER CON MALA CONTRASEÑA
	 */
	@Test
	void testMalaContraseñaRider()
			throws YaEnUsoException, ContraseniaIncorrectaException, IlegalNumberException, InvalidEmailException {
		rider = new Rider("1235123S", "Pablo", "asdfasg@r.com", "1234", "FallaContraseñaSegunda", 1, "Audi", "14315FAS",
				"carnet", "685345313", 2);
		try {
			riderG = regService.signUpRider(rider);
		} catch (ContraseniaIncorrectaException e) {
			assertNull(riderG);
		}
	}

////--------------------------------------------------------------------------------------------------------
////USUARIOS LOGIN
////--------------------------------------------------------------------------------------------------------
//
	/**
	 * // * LOGIN DE USUARIO CON EL PERFIL BLOQUEADO //
	 * 
	 * @throws NonAdminValidationException
	 */
	@Test
	void testLoginPerfilBloqueado() throws UnexistentUser, PerfilBloqueadoException, InvalidEmailException,
			YaEnUsoException, IlegalNumberException, ContraseniaIncorrectaException, IncompleteFormException,
			NonAdminValidationException {
		usuario = new Usuario("12345678S", "Prueba", "prueba@prueba.com", "8Caracteres", "8Caracteres", 0, "prueba",
				"687574635");
		try {
			token = logService.signIn(usuario);
		} catch (PerfilBloqueadoException e) {
			assertNull(token);
		}
	}

	/**
	 * LOGIN CORRECTO DE USUARIO
	 * 
	 * @throws NonAdminValidationException
	 */
	@Test
	void testLoginCorrecto()
			throws YaEnUsoException, ContraseniaIncorrectaException, IlegalNumberException, InvalidEmailException,
			UnexistentUser, PerfilBloqueadoException, IncompleteFormException, NonAdminValidationException {
		usuario = new Usuario("44444444D", "pruebon", "pruebon@pruebon.com", "8Caracteres", "8Caracteres", 5, "Pruebon",
				"678788978");
		token = new TokenSession("pruebon", "Usuario", "44444444D", "pruebon@pruebon.com");
		tokenG = logService.signIn(usuario);
		assertEquals(tokenG.toString(), token.toString());
	}

	/**
	 * GUARDAR PLATO POR PARTE DE UN ADMIN
	 * 
	 */
	@Test
	void guardarPlato() throws IncompleteFormException {
		plato = new Plato("Plato", "plato", 3.2, false, "3");
		platoG = platoServ.save(plato);
		assertEquals(plato, platoG);
	}

	/**
	 * GUARDAR PLATO INCORRECTO POR PARTE DE UN ADMIN
	 * 
	 */
	@Test
	void guardarPlatoIncorrecto() {
		plato = new Plato("Plato", "", 3.2, false, "3");
		try {
			platoG = platoServ.save(plato);
		} catch (IncompleteFormException e) {

			assertNull(platoG);
		}
	}

	/**
	 * UPDATE USUARIO CORRECTO
	 * 
	 */
	@Test
	void updateUsuario() throws IncompleteFormException, IlegalNumberException, ContraseniaIncorrectaException {
		usuario = new Usuario("12345678H", "Usuario", "usuario@usuario.com", "8Caracteres", "8Caracteres", 5,
				"Ciudad Real", "623534253");
		usuarioG = userServ.updateForm(usuario);
		assertEquals(usuario, usuarioG);
	}

	/**
	 * GUARDAR RESTAURANTE NUEVO POR PARTE DE ADMIN
	 * 
	 * @throws IncompleteFormException
	 * 
	 */
	@Test
	void guardarRestaurante() throws IncompleteFormException {
		restaurante = new Restaurante("prueba", "prueba", "prueba", "prueba@prueba.com", "prueba", url);
		try {
			restauranteG = restauranteService.saveRestaurante(restaurante);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(restaurante, restauranteG);
		restauranteRepo.delete(restaurante);

	}
	
	/**
	 * UPDATE RESTAURANTE POR PARTE DE ADMIN
	 * @throws IncompleteFormException 
	 * 
	 */
	@Test
	void updateRestaurante() throws IncompleteFormException {
		restaurante = new Restaurante("prueba", "prueba", "prueba", "prueba@prueba.com", "prueba", url);
		
		restauranteG = restauranteService.updateRestaurante(restaurante);
		
		assertEquals(restaurante, restauranteG);
		restauranteRepo.delete(restaurante);

	}
	
	/**
	 * UPDATE RESTAURANTE INCORRECTO POR PARTE DE ADMIN
	 * @throws IncompleteFormException 
	 * 
	 */
	@Test
	void updateRestauranteIncorrecto() {
		restaurante = new Restaurante("", "prueba", "prueba", "prueba@prueba.com", "prueba", url);
		try {
			restauranteG = restauranteService.updateRestaurante(restaurante);
		} catch (IncompleteFormException e) {
			assertNull(restauranteG);
		}

	}

}
