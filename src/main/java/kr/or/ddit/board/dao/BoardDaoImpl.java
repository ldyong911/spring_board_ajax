package kr.or.ddit.board.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.board.model.BoardVo;

@Repository("boardDao")
public class BoardDaoImpl implements IBoardDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * Method : getAllBoard
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 게시판 조회
	 */
	@Override
	public List<BoardVo> getAllBoard() {
		List<BoardVo> boardList = sqlSessionTemplate.selectList("board.getAllBoard");
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
		BoardVo boardVo = sqlSessionTemplate.selectOne("board.selectBoard", board_num);		
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
		int result = sqlSessionTemplate.insert("board.insertBoard", boardVo);
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
		int result = sqlSessionTemplate.update("board.updateBoard", boardVo);
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
		int result = sqlSessionTemplate.delete("board.deleteBoard", board_num);
		return result;
	}
}