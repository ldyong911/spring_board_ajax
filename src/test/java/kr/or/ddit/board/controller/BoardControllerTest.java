package kr.or.ddit.board.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.test.WebTestConfig;

public class BoardControllerTest extends WebTestConfig{

	@Test
	public void testBoardGet() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(get("/board")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		
		String viewName = mav.getViewName();
		List<BoardVo> boardList = (List<BoardVo>) mvcResult.getRequest().getSession().getAttribute("boardList");

		/***Then***/
		assertEquals("boardTiles", viewName);
		assertTrue(boardList.size() > 3);
	}
	
	@Test
	public void testBoardPost_success() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(post("/board").param("type", "1")
															.param("board_name_ins", "뚜둔")
															.param("use_exist_ins", "Y")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();

		/***Then***/
		assertEquals("redirect:" + mvcResult.getRequest().getContextPath() + "/board", viewName);
	}
	
//	@Test //sql 크기수 오류로 해당부분에 try~catch해줘야함
	public void testBoardPost_fail() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(post("/board").param("type", "1")
															.param("board_name_ins", "뚜둔")
															.param("use_exist_ins", "YYYYY")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();

		/***Then***/
		assertEquals("boardTiles", viewName);
	}

}
