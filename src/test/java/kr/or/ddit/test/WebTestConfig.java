package kr.or.ddit.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//**Tiles 사용시 tomcat runtime 추가해야 컨트롤러 테스트 코드가 오류가 나지 않음
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:kr/or/ddit/config/spring/servlet-context.xml",
						"classpath:kr/or/ddit/config/spring/application-context.xml",
						"classpath:kr/or/ddit/config/spring/context-datasource.xml"})
@WebAppConfiguration //스프링 컨테이너를 만들때 WebApplicationContext로 생성, 미적용시 applicationContext로 생성
public class WebTestConfig {
	@Autowired
	private WebApplicationContext context;
	protected MockMvc mockMvc;
	
	// @BeforeClass(static --> 사용빈도가 떨어짐)
	// @Before - @Test - @After
	// .......
	// @AfterClass(static --> 사용빈도가 떨어짐)
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

}