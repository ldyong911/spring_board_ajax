package kr.or.ddit.reply.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.reply.model.ReplyVo;

@Repository("replyDao")
public class ReplyDaoImpl implements IReplyDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * Method : insertReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param replyVo
	 * @return
	 * Method 설명 : 댓글 등록
	 */
	@Override
	public int insertReply(ReplyVo replyVo) {
		int result = sqlSessionTemplate.insert("reply.insertReply", replyVo);
		return result;
	}

	/**
	 * Method : updateReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param replyVo
	 * @return
	 * Method 설명 : 댓글 수정
	 */
	@Override
	public int updateReply(ReplyVo replyVo) {
		int result = sqlSessionTemplate.update("reply.updateReply", replyVo);
		return result;
	}

	/**
	 * Method : selectReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param reply_num
	 * @return
	 * Method 설명 : 댓글 조회
	 */
	@Override
	public ReplyVo selectReply(Integer reply_num) {
		ReplyVo replyVo = sqlSessionTemplate.selectOne("reply.selectReply", reply_num);
		return replyVo;
	}

	/**
	 * Method : getAllReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param posting_num
	 * @return
	 * Method 설명 : 댓글 전체 조회
	 */
	@Override
	public List<ReplyVo> getAllReply(Integer posting_num) {
		List<ReplyVo> replyList = sqlSessionTemplate.selectList("reply.getAllReply", posting_num);
		return replyList;
	}

}