package kr.or.ddit.reply.dao;

import java.util.List;

import kr.or.ddit.reply.model.ReplyVo;

public interface IReplyDao {
	/**
	 * Method : insertReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param replyVo
	 * @return
	 * Method 설명 : 댓글 등록
	 */
	int insertReply(ReplyVo replyVo);
	
	/**
	 * Method : updateReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param replyVo
	 * @return
	 * Method 설명 : 댓글 수정
	 */
	int updateReply(ReplyVo replyVo);
	
	/**
	 * Method : selectReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param reply_num
	 * @return
	 * Method 설명 : 댓글 조회
	 */
	ReplyVo selectReply(Integer reply_num);
	
	/**
	 * Method : getAllReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param posting_num
	 * @return
	 * Method 설명 : 해당 게시글 댓글 전체 조회
	 */
	List<ReplyVo> getAllReply(Integer posting_num);
}