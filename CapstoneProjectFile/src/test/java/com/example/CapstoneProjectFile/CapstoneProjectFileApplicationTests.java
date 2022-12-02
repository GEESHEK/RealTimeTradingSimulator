package com.example.CapstoneProjectFile;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CapstoneProjectFileApplicationTests {



	@Test
	void testContextLoads(ConfigurableApplicationContext context) {
		assertThat(context).isNotNull();

	}

}
