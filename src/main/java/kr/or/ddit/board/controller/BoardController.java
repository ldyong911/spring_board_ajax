package kr.or.ddit.board.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.service.IBoardService;

@Controller
public class BoardController{
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Resource(name="boardService")
	private IBoardService boardService;
	
	@RequestMapping(path="/board", method=RequestMethod.GET)
	public String boardGet(HttpServletRequest request, Model model){
		//게시판 전체 조회
		List<BoardVo> boardList = boardService.getAllBoard();
		
		//전체게시판리스트를 session 속성에 저장
		request.getSession().setAttribute("boardList", boardList);
		
		// localhost/board 요청시 /board/board.jsp로 보냄
		return "boardTiles";
	}
	
	@RequestMapping(path="/board", method=RequestMethod.POST)
	public String boardPost(String type,
							String board_name_ins, String use_exist_ins,
							String board_num_upd, String board_name_upd, String use_exist_upd,
							HttpServletRequest request){
		
		String paramType = type;

		BoardVo boardVo = null;
		int result = 0;
		//type이 1이면 등록
		if(paramType.equals("1")){
			String board_name = board_name_ins;
			String use_exist = use_exist_ins;
			
			boardVo = new BoardVo();
			boardVo.setBoard_name(board_name);
			boardVo.setUse_exist(use_exist);
			result = boardService.insertBoard(boardVo);
		}
		//type이 2이면 수정
		else if(paramType.equals("2")){
			Integer board_num = board_num_upd == null ? null : Integer.parseInt(board_num_upd);
			String board_name = board_name_upd;
			String use_exist = use_exist_upd;
			
			boardVo = boardService.selectBoard(board_num);
			boardVo.setBoard_name(board_name);
			boardVo.setUse_exist(use_exist);
			result = boardService.updateBoard(boardVo);
		}
		
		if(result == 1){
			return "redirect:" + "/board"; //루트path 지정해야하지만 contextPath에 관한 리스너 등록시 자동으로 붙여진다!!!
		}else{
			return "boardTiles";
		}
	}

}