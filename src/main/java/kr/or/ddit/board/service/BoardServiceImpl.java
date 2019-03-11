package kr.or.ddit.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.model.BoardVo;

@Service("boardService")
public class BoardServiceImpl implements IBoardService{
	@Resource(name="boardDao")
	private IBoardDao boardDao;
	
	/**
	 * Method : getAllBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 게시판 조회
	 */
	@Override
	public List<BoardVo> getAllBoard() {
		List<BoardVo> boardList = boardDao.getAllBoard();
		return boardList;
	}
	
	/**
	 * Method : selectBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 게시판 조회
	 */
	@Override
	public BoardVo selectBoard(Integer board_num) {
		BoardVo boardVo = boardDao.selectBoard(board_num);
		return boardVo;
	}

	/**
	 * Method : insertBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param boardVo
	 * @return
	 * Method 설명 : 게시판 등록
	 */
	@Override
	public int insertBoard(BoardVo boardVo) {
		int result = boardDao.insertBoard(boardVo);
		return result;
	}

	/**
	 * Method : updateBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param boardVo
	 * @return
	 * Method 설명 : 게시판 수정
	 */
	@Override
	public int updateBoard(BoardVo boardVo) {
		int result = boardDao.updateBoard(boardVo);
		return result;
	}

	/**
	 * Method : deleteBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 게시판 삭제
	 */
	@Override
	public int deleteBoard(Integer board_num) {
		int result = boardDao.deleteBoard(board_num);
		return result;
	}
}