package kr.or.ddit.attach.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.posting.model.PostingVo;
import kr.or.ddit.test.WebTestConfig;

public class AttachControllerTest extends WebTestConfig{

	@Test
	public void testAttachGet() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(get("/attach").param("attach_num", "22")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		
		String viewName = mav.getViewName();
		String attach_num = (String) mav.getModel().get("attach_num");
		/***Then***/
		assertEquals("attachView", viewName);
		assertEquals("22", attach_num);
	}
	
	@Test
	public void testAttachDeleteGet() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(get("/attachDelete").param("posting_num", "223")
																.param("attach_num", "22")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		
		String viewName = mav.getViewName();
		PostingVo postingVo = (PostingVo) mav.getModel().get("postingVo");
		
		/***Then***/
		assertEquals("postingUpdateTiles", viewName);
		assertNotNull(postingVo);
	}

}