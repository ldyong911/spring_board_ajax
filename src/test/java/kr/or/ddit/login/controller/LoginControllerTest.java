package kr.or.ddit.login.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.test.WebTestConfig;
import kr.or.ddit.user.model.UserVo;

public class LoginControllerTest extends WebTestConfig{

	@Test
	public void testLoginView() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(get("/login")).andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();

		/***Then***/
		assertEquals("login/login", viewName);
	}
	
	@Test
	public void testLoginProcess_success() throws Exception{
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(post("/login")
									.param("userId", "brown")
									.param("pass", "brown1234")).andReturn();
		
		//session userVo
		//main
		MockHttpServletRequest req = mvcResult.getRequest();
		HttpSession session = req.getSession();
		UserVo userVo = (UserVo)session.getAttribute("userVo");
		
		//view name
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();
		
		/***Then***/
		assertEquals("mainTiles", viewName);
		assertEquals("브라운", userVo.getUserNm());

	}
	
	@Test
	public void testLoginProcess_fail() throws Exception{
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(post("/login")
									.param("userId", "brown")
									.param("pass", "brown1235")).andReturn();
		
		//view name
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();
		
		/***Then***/
		assertEquals("login/login", viewName);
	}

}