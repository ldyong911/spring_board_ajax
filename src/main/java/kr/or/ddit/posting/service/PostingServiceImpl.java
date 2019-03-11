package kr.or.ddit.posting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.posting.dao.IPostingDao;
import kr.or.ddit.posting.model.PostingVo;

@Service("postingService")
public class PostingServiceImpl implements IPostingService{
	
	@Resource(name="postingDao")
	private IPostingDao postingDao;
	
	/**
	 * Method : getAllPosting
	 * 작성자 : lee
	 * 변경이력 :
	 * @param board_num
	 * @return
	 * Method 설명 : 해당 게시판 전체 게시글 조회
	 */
	@Override
	public List<PostingVo> getAllPosting(Integer board_num) {
		List<PostingVo> postingList = postingDao.getAllPosting(board_num);
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
		List<PostingVo> postingList = postingDao.selectHierar(board_num);
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
		int result = postingDao.insertPosting(postingVo);
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
		int result = postingDao.updatePosting(postingVo);
		return result;
	}


	/**
	 * Method : selectPostingPaging
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @param board_num
	 * @return
	 * Method 설명 : 게시글 계층형으로 페이징 조회(해당 게시판 전체 게시글 수 또한 여기서 조회)
	 */
	@Override
	public Map<String, Object> selectPostingPaging(Map<String, Object> map, Integer board_num) {
		//결과값을 두개 담아서 return 하기위해 Map 객체 사용
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("postingList", postingDao.selectPostingPaging(map));
		resultMap.put("postingCnt", postingDao.getPostingCnt(board_num));
		
		return resultMap;
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
		int result = postingDao.updateLevel(postingVo);
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
		PostingVo postingVo = postingDao.selectPosting(posting_num);
		return postingVo;
	}

}