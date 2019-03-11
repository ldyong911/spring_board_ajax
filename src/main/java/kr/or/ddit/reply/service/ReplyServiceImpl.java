package kr.or.ddit.reply.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.reply.dao.IReplyDao;
import kr.or.ddit.reply.model.ReplyVo;

@Service("replyService")
public class ReplyServiceImpl implements IReplyService{

	@Resource(name="replyDao")
	private IReplyDao replyDao;

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
		int result = replyDao.insertReply(replyVo);
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
		int result = replyDao.updateReply(replyVo);
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
		ReplyVo replyVo = replyDao.selectReply(reply_num);
		return replyVo;
	}

	/**
	 * Method : getAllReply
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param posting_num
	 * @return
	 * Method 설명 : 해당 게시글 댓글 전체 조회
	 */
	@Override
	public List<ReplyVo> getAllReply(Integer posting_num) {
		List<ReplyVo> replyList = replyDao.getAllReply(posting_num);
		return replyList;
	}

}