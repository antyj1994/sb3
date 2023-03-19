package com.aarci.sb3;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class Sb3ApplicationTests {

	@Value("${server.port}")
	private String port;

	@Test
	void serverLoads() {
		log.info("ciao");
		assertEquals(8080, Integer.parseInt(this.port));
	}

}
