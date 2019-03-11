package kr.or.ddit.attach.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.attach.model.AttachVo;

@Repository("attachDao")
public class AttachDaoImpl implements IAttachDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * Method : insertAttach
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param attachVo
	 * @return
	 * Method 설명 : 첨부파일 등록
	 */
	@Override
	public int insertAttach(AttachVo attachVo) {
		int result = sqlSessionTemplate.insert("attach.insertAttach", attachVo);
		return result;
	}

	/**
	 * Method : updateAttach
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param attachVo
	 * @return
	 * Method 설명 : 첨부파일 수정
	 */
	@Override
	public int updateAttach(AttachVo attachVo) {
		int result = sqlSessionTemplate.update("attach.updateAttach", attachVo);
		return result;
	}

	/**
	 * Method : selectAttachList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param posting_num
	 * @return
	 * Method 설명 : 해당 게시글 첨부파일 전체 조회
	 */
	@Override
	public List<AttachVo> selectAttachList(Integer posting_num) {
		List<AttachVo> attachList = sqlSessionTemplate.selectList("attach.selectAttachList", posting_num);
		return attachList;
	}

	/**
	 * Method : selectAttach
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param attach_num
	 * @return
	 * Method 설명 : 첨부파일 조회
	 */
	@Override
	public AttachVo selectAttach(Integer attach_num) {
		AttachVo attachVo = sqlSessionTemplate.selectOne("attach.selectAttach", attach_num);
		return attachVo;
	}

	/**
	 * Method : deleteAttach
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param posting_num
	 * @return
	 * Method 설명 : 해당 게시글 첨부파일 전체 삭제
	 */
	@Override
	public int deleteAttach(Integer posting_num) {
		int result = sqlSessionTemplate.delete("attach.deleteAttach", posting_num);
		return result;
	}

}