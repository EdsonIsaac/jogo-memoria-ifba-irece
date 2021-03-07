package br.edu.ifba.jogomemoria;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JogomemoriaApplicationTests {

	@Test
	void contextLoads() {
	}

	@BeforeAll
	public static void setHeadlessMode () {
		System.setProperty("java.awt.headless", "false");
	}
}