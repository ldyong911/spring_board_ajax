package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.board.model.BoardVo;

public interface IBoardDao {
	/**
	 * Method : getAllBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 게시판 조회
	 */
	List<BoardVo> getAllBoard();
	
	/**
	 * Method : selectBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 게시판 조회
	 */
	BoardVo selectBoard(Integer board_num);
	
	/**
	 * Method : insertBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param boardVo
	 * @return
	 * Method 설명 : 게시판 등록
	 */
	int insertBoard(BoardVo boardVo);
	
	/**
	 * Method : updateBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param boardVo
	 * @return
	 * Method 설명 : 게시판 수정
	 */
	int updateBoard(BoardVo boardVo);
	
	/**
	 * Method : deleteBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 게시판 삭제
	 */
	int deleteBoard(Integer board_num);
}