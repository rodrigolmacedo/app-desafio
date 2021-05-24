package desafio.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TelefoneTest {

	@Test
	void testeIntanciaModel() {
		Assertions.assertDoesNotThrow(()->{
			Telefone telefone = new Telefone();
			
			Integer ddd = 21;
			String numero = "21214444";
			String tipo = "tipo";
			
			Telefone telefoneComConstrutor = new Telefone(ddd, numero, tipo);
			telefoneComConstrutor.setId(1L);
			
			assertEquals(ddd, telefoneComConstrutor.getDdd());
			assertEquals(numero, telefoneComConstrutor.getNumero());
			assertEquals(tipo, telefoneComConstrutor.getTipo());
			
			
			telefoneComConstrutor.toString();
			
			telefone.setId(1L);
			telefone.setDdd(ddd);
			telefone.setNumero(numero);
			telefone.setTipo(tipo);
			
			telefone.equals(telefoneComConstrutor);
			telefone.equals(telefone);
			telefone.equals(null);
			
			
			assertEquals(telefone.hashCode(), telefoneComConstrutor.hashCode());
			
			assertTrue(telefoneComConstrutor.equals(telefone));
			
		});
	}

}
