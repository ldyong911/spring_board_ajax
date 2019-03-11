package kr.or.ddit.posting.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.posting.model.PostingVo;

public interface IPostingService {

	/**
	 * Method : getAllPosting
	 * 작성자 : lee
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 해당 게시판 전체 게시글 조회
	 */
	List<PostingVo> getAllPosting(Integer board_num);
	
	/**
	 * Method : selectHierar
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 해당 게시판 전체 게시글 계층형으로 조회
	 */
	List<PostingVo> selectHierar(Integer board_num);
	
	/**
	 * Method : insertPosting
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param postingVo
	 * @return
	 * Method 설명 : 게시글 등록
	 */
	int insertPosting(PostingVo postingVo);
	
	/**
	 * Method : updatePosting
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param postingVo
	 * @return
	 * Method 설명 : 게시글 수정
	 */
	int updatePosting(PostingVo postingVo);
	
	/**
	 * Method : selectPostingPaging
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @param board_num
	 * @return
	 * Method 설명 : 게시글 계층형으로 페이징 조회(해당 게시판 전체 게시글 수 또한 여기서 조회)
	 */
	Map<String, Object> selectPostingPaging(Map<String, Object> map, Integer board_num);
	
	/**
	 * Method : updateLevel
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param postingVo
	 * @return
	 * Method 설명 : 게시글 level 업데이트
	 */
	int updateLevel(PostingVo postingVo);
	
	/**
	 * Method : selectPosting
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param posting_num
	 * @return
	 * Method 설명 : 해당 게시글 조회
	 */
	PostingVo selectPosting(Integer posting_num);
}