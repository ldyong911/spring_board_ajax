package kr.or.ddit.posting.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.posting.model.PostingVo;

@Repository("postingDao")
public class PostingDaoImpl implements IPostingDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * Method : getAllPosting
	 * 작성자 : lee
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 전체 게시글 조회
	 */
	@Override
	public List<PostingVo> getAllPosting(Integer board_num) {
		List<PostingVo> postingList = sqlSessionTemplate.selectList("posting.getAllPosting", board_num);
		return postingList;
	}

	/**
	 * Method : selectHierar
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 해당 게시판 전체 게시글 계층형으로 조회
	 */
	@Override
	public List<PostingVo> selectHierar(Integer board_num) {
		List<PostingVo> postingList = sqlSessionTemplate.selectList("posting.selectHierar", board_num);
		return postingList;
	}

	/**
	 * Method : insertPosting
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param postingVo
	 * @return
	 * Method 설명 : 게시글 등록
	 */
	@Override
	public int insertPosting(PostingVo postingVo) {
		int result = sqlSessionTemplate.insert("posting.insertPosting", postingVo);
		return result;
	}

	/**
	 * Method : updatePosting
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param postingVo
	 * @return
	 * Method 설명 : 게시글 수정
	 */
	@Override
	public int updatePosting(PostingVo postingVo) {
		int result = sqlSessionTemplate.update("posting.updatePosting", postingVo);
		return result;
	}
	
	/**
	 * Method : selectPostingPaging
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 게시글 계층형으로 페이징 조회
	 */
	@Override
	public List<PostingVo> selectPostingPaging(Map<String, Object> map) {
		List<PostingVo> postingList = sqlSessionTemplate.selectList("posting.selectPostingPaging", map);
		return postingList;
	}

	/**
	 * Method : getPostingCnt
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 해당 게시판 전체 게시글 수 조회
	 */
	@Override
	public int getPostingCnt(Integer board_num) {
		int result = sqlSessionTemplate.selectOne("posting.getPostingCnt", board_num);
		return result;
	}

	/**
	 * Method : updateLevel
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param postingVo
	 * @return
	 * Method 설명 : 게시글 level 업데이트
	 */
	@Override
	public int updateLevel(PostingVo postingVo) {
		int result = sqlSessionTemplate.update("posting.updateLevel", postingVo);
		return result;
	}

	/**
	 * Method : selectPosting
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param posting_num
	 * @return
	 * Method 설명 : 해당 게시글 조회
	 */
	@Override
	public PostingVo selectPosting(Integer posting_num) {
		PostingVo postingVo = sqlSessionTemplate.selectOne("posting.selectPosting", posting_num);
		return postingVo;
	}
}