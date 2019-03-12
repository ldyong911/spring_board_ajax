package kr.or.ddit.attach.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.attach.model.AttachVo;
import kr.or.ddit.attach.service.IAttachService;
import kr.or.ddit.posting.model.PostingVo;
import kr.or.ddit.posting.service.IPostingService;

@Controller
public class AttachController{
	
	private Logger logger = LoggerFactory.getLogger(AttachController.class);
	
	@Resource(name="attachService")
	private IAttachService attachService;
	
	@Resource(name="postingService")
	private IPostingService postingService;
	
	@RequestMapping(path="/attach", method=RequestMethod.GET)
	public String attach(String attach_num, Model model){
		model.addAttribute("attach_num", attach_num);
		return "attachView";
	}
	
	@RequestMapping(path="/attachDelete", method=RequestMethod.GET)
	public String attachDelete(String posting_num, String attach_num,
								Model model){
		//게시글 번호와 첨부파일번호를 파라미터로 받음
		Integer paramPosting_num = Integer.parseInt(posting_num);
		Integer paramAttach_num = Integer.parseInt(attach_num);
		
		//기존의 첨부파일 삭제
		attachService.deleteAttach(paramAttach_num);
		
		//해당 게시글의 첨부파일 조회
		List<AttachVo> attachList = attachService.selectAttachList(paramPosting_num);
		
		//파라미터로 받은 게시글 번호로 해당 게시글 조회
		PostingVo postingVo = postingService.selectPosting(paramPosting_num);
		
		//수정시 request 속성에 설정한후 postingUpdate.jsp로 forward
		model.addAttribute("postingVo", postingVo);
		model.addAttribute("attachList", attachList);
		
		return "postingUpdateTiles";
	}
	
}