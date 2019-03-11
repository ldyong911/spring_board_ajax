package kr.or.ddit.reply.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.reply.model.ReplyVo;
import kr.or.ddit.reply.service.IReplyService;
import kr.or.ddit.user.model.UserVo;

@Controller
public class ReplyController{
	
	private Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Resource(name="replyService")
	private IReplyService replyService;
       
	@RequestMapping(path="/reply", method=RequestMethod.POST)
	public String replyPost(String type, String posting_num, String reply_content, String reply_num,
							HttpServletRequest request){
		//타입과 게시글번호, 게시글내용을 파라미터로 받고, session에서 userVo 객체를 가져옴
		String paramType = type;
		Integer paramPosting_num = Integer.parseInt(posting_num);
		String paramReply_content = reply_content;
		UserVo userVo = (UserVo) request.getSession().getAttribute("userVo");
		
		int result = 0;
		//타입이 1이면 등록
		if(paramType.equals("1")){
			ReplyVo replyVo = new ReplyVo();
			replyVo.setReply_content(paramReply_content);
			replyVo.setPosting_num(paramPosting_num);
			replyVo.setReply_userid(userVo.getUserId());
			replyVo.setReply_admin(userVo.getUserId());
			result = replyService.insertReply(replyVo);
		}
		//타입이 2이면 삭제
		else if(paramType.equals("2")){
			//댓글번호를 파라미터로 받아서 댓글 조회
			Integer paramReply_num = Integer.parseInt(reply_num);
			ReplyVo replyVo = replyService.selectReply(paramReply_num);
			
			replyVo.setDelete_exist("Y");
			result = replyService.updateReply(replyVo);
		}
		
		//result 결과가 성공이든 실패든 해당 게시글 상세화면으로 넘어감
		//정상입력(성공) - 해당 게시글 상세조회로 넘어감
		return "redirect:" + request.getContextPath() + "/postingDetail?posting_num=" + paramPosting_num;
	}
	
	@RequestMapping(path="/replyUpdate", method=RequestMethod.GET)
	public String replyUpdateGet(String reply_num, Model model){
		//댓글번호를 파라미터로 받아서 댓글 조회
		Integer paramReply_num = Integer.parseInt(reply_num);
		ReplyVo replyVo = replyService.selectReply(paramReply_num);
		
		//댓글을 request 속성에 설정
		model.addAttribute("replyVo", replyVo);
		
		return "replyUpdateTiles";
	}
	
	@RequestMapping(path="/replyUpdate", method=RequestMethod.POST)
	public String replyUpdatePost(String reply_num, String Reply_content, Model model, HttpServletRequest request){
		//댓글번호를 파라미터로 받아서 댓글 조회
		Integer paramReply_num = Integer.parseInt(reply_num);
		ReplyVo replyVo = replyService.selectReply(paramReply_num);
		
		String paramReply_content = Reply_content;
		replyVo.setReply_content(paramReply_content);
		
		int result = replyService.updateReply(replyVo);;
		
		//게시글 번호를 조회한후
		Integer posting_num = replyVo.getPosting_num();
		
		//성공시 게시글 상세화면으로
		if(result == 1){
			return "redirect:" + request.getContextPath() + "/postingDetail?posting_num=" + posting_num;
		}
		//실패시
		else{
			//request.getRequestDispatcher("/replyUpdate?reply_num=" + reply_num).forward(request, response);
			
			//Tiles로 반환할때는 uri이 아니기때문에 파라미터값을 넘겨줘서 사용하고싶을때는 model 객체에 담아서 사용
			model.addAttribute(reply_num, paramReply_num);
			return "replyUpdateTiles?reply_num=" + paramReply_num;
		}
	}
}