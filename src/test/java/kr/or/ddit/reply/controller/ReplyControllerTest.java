package kr.or.ddit.reply.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.test.WebTestConfig;
import kr.or.ddit.user.model.UserVo;

public class ReplyControllerTest extends WebTestConfig{
	
	@Test
	public void testReplyPost() throws Exception {
		/***Given***/
		UserVo userVo = new UserVo();
		userVo.setUserId("brown");
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(post("/reply").sessionAttr("userVo", userVo)
															.param("type", "1")
															.param("posting_num", "223")
															.param("reply_content", "답글 내용 테스트")).andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		String viewName = mav.getViewName();
		
		/***Then***/
		assertEquals("redirect:" + mvcResult.getRequest().getContextPath() + "/postingDetail?posting_num=" + 223, viewName);
	}

}