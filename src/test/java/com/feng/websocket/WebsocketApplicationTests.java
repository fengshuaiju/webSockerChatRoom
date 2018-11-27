package com.feng.websocket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
//测试类的注解，@SpringBootTest改为@WebMvcTest. 具体原因可以看官网.用springbootTest会启动整个Spring application context 但是没有启动server。这个时候自然websocket就会报错
//@SpringBootTest
@WebMvcTest
public class WebsocketApplicationTests {

	@Test
	public void contextLoads() {
	}

}
