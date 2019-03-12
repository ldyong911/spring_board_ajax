package kr.or.ddit.posting.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.test.WebTestConfig;
import kr.or.ddit.user.model.UserVo;

public class PostingControllerTest extends WebTestConfig{

	@Test
	public void testPostingGet() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(get("/posting").param("board_num", "2")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		
		String viewName = mav.getViewName();
		int page = (int) mav.getModel().get("page");
		int pageSize = (int) mav.getModel().get("pageSize");
		
		/***Then***/
		assertEquals("postingTiles", viewName);
		assertEquals(1, page);
		assertEquals(10, pageSize);
	}
	
	@Test
	public void testPostingDetailGet() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(get("/postingDetail").param("posting_num", "73")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		
		String viewName = mav.getViewName();
		
		/***Then***/
		assertEquals("postingDetailTiles", viewName);
	}
	
	@Test
	public void testPostingInsertGet() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(get("/postingInsert").param("board_num", "2")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		
		String viewName = mav.getViewName();
		String board_num = (String) mav.getModel().get("board_num");
		
		/***Then***/
		assertEquals("postingInsertTiles", viewName);
		assertEquals("2", board_num);
	}
	
	@Test
	public void testPostingInsertPost() throws Exception {
		/***Given***/
		UserVo userVo = new UserVo();
		userVo.setUserId("brown");
		
		File attacFile = new File("D:\\레인저스사진\\real.jpg");
		FileInputStream fis = new FileInputStream(attacFile);
		MockMultipartFile file = new MockMultipartFile("attach", "real.jpg", "image/jpg", fis);
		MvcResult mvcResult = mockMvc.perform(fileUpload("/postingInsert").file(file)
																		.sessionAttr("userVo", userVo)
																		.param("board_num", "2")
																		.param("posting_title", "와나나나")
																		.param("posting_content", "테스트입니다")
																		.param("posting_userid", "brown")
																		.param("parentposting_num", "210")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		
		/***When***/
		String viewName = mav.getViewName();
		
		/***Then***/
		assertNotEquals("postingInsertTiles", viewName);
	}

}