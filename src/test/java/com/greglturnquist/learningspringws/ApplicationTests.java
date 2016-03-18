package com.greglturnquist.learningspringws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.greglturnquist.Server;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Server.class)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
