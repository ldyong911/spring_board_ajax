package kr.or.ddit.posting.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.attach.model.AttachVo;
import kr.or.ddit.attach.service.IAttachService;
import kr.or.ddit.posting.model.PostingVo;
import kr.or.ddit.posting.service.IPostingService;
import kr.or.ddit.reply.model.ReplyVo;
import kr.or.ddit.reply.service.IReplyService;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.util.model.PageVo;

@Controller
public class PostingController{
	
	private Logger logger = LoggerFactory.getLogger(PostingController.class);
	
	@Resource(name="postingService")
	private IPostingService postingService;
	@Resource(name="attachService")
	private IAttachService attachService;
	@Resource(name="replyService")
	private IReplyService replyService;
	
	@RequestMapping(path="/posting", method=RequestMethod.GET)
	public String posting(String board_num, String page, String pageSize, Model model){
		//해당 게시판 번호를 파라미터로 받기
		Integer paramBoard_num = Integer.parseInt(board_num);

		//해당 게시판번호를 requset 속성에 설정
		model.addAttribute("board_num", paramBoard_num);
		
		//계층형 게시글을 조회한후 posting_level을 update해줘야함(페이징처리된 계층형 쿼리를 조회하려면 필수)
		//검색한 level로 posting_level을 세팅
		List<PostingVo> postingHierar = postingService.selectHierar(paramBoard_num);
		
		int resultUpdate = 0;
		for(PostingVo postingVo : postingHierar){
			String posting_level = postingVo.getPosting_level();
			
			postingVo.setPosting_level(posting_level);
			
			resultUpdate += postingService.updateLevel(postingVo);
		}
		logger.debug("resultUpdate : {}", resultUpdate);
		logger.debug("postingHierar.size() : {}", postingHierar.size());
		
		//page, pageSize에 해당하는 파라미터 받기 ==> pageVO
		//단, 파라미터가 없을경우 page : 1, pageSize : 10
		int paramPage = page == null ? 1 : Integer.parseInt(page);
		int paramPageSize = pageSize == null ? 10 : Integer.parseInt(pageSize);
		PageVo pageVo = new PageVo(paramPage, paramPageSize);
		
		//postingService 객체를 이용 postingPaging 조회
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board_num", paramBoard_num);
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		
		//해당 게시판에 해당하는 게시글을 계층형으로 페이징 조회
		Map<String, Object> resultMap = postingService.selectPostingPaging(map, paramBoard_num);
		
		//게시글 리스트
		List<PostingVo> postingList = (List<PostingVo>) resultMap.get("postingList");
		//게시글 수
		int postingCnt = (int)resultMap.get("postingCnt");
		
		//마지막페이지 구하기
		int lastPage = postingCnt/paramPageSize + (postingCnt%paramPageSize > 0 ? 1 : 0);
		//마지막페이지가 포함된 시작페이지 구하기
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		
		//시작 페이지 구하기
		//10의 배수로 끝나는 페이지는 10으로 나누면 몫이 증가하기 때문에 - 1 한후
		//10으로 나눈 몫을 다시 10으로 곱해주고 + 1해주면 예시로 12페이지든 13페이지든 시작이 11페이지가 됨
		int startPage = ((paramPage - 1) / 10) * 10 + 1; 
		//마지막 페이지 구하기
		//시작페이지 + 10 하고 -1 해주면 예시로 12페이지든 13페이지든 끝이 20페이지가 됨
		int endPage = startPage + 10 - 1;
		
		//request객체에 조회된 결과를 속성으로 설정
		model.addAttribute("postingList", postingList);
		model.addAttribute("postingCnt", postingCnt);
		model.addAttribute("pageSize", paramPageSize);
		model.addAttribute("page", paramPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		//posting/posting.jsp로 forward
		return "postingTiles";
	}
	
	@RequestMapping(path="/postingDetail", method=RequestMethod.GET)
	public String postingDetail(String posting_num, Model model, HttpServletRequest request){
		//새글 등록한후 상세화면으로 넘어올때 posting_num을 파라미터로 받아서 해당 게시글 조회
		Integer paramPosting_num = Integer.parseInt(posting_num);
		
		//해당 게시글 조회한후 request 속성에 설정
		PostingVo postingVo = postingService.selectPosting(paramPosting_num);
		model.addAttribute("postingVo", postingVo);
		
		//해당 게시글의 첨부파일 조회한후 있으면 속성에 설정
		List<AttachVo> attachList = attachService.selectAttachList(paramPosting_num);
		if(attachList != null){
			model.addAttribute("attachList", attachList);
		}
		
		//해당 게시글의 댓글 조회한후 있으면 속성에 설정
		List<ReplyVo> replyList = replyService.getAllReply(paramPosting_num);
		if(replyList != null){
			model.addAttribute("replyList", replyList);
		}
		
		//필요한 정보를 request 속성에 설정한후 postingDetail.jsp로 forward
		return "postingDetailTiles";
	}
	
	@RequestMapping(path="/postingInsert", method=RequestMethod.GET)
	public String postingInsertGet(String board_num, String parentposting_num, Model model){
		//board_num을 파라미터로 받아 request 속성에 설정
		String paramBoard_num = board_num;
		model.addAttribute("board_num", paramBoard_num);
		
		//답글인경우 부모글번호를 받아서 request 속성에 설정
		String paramParentposting_num = parentposting_num;
		if(paramParentposting_num != null){
			model.addAttribute("parentposting_num", paramParentposting_num);
		}
		
		//게시글 등록페이지로 forward
		return "postingInsertTiles";
	}
	
	@RequestMapping(path="/postingInsert", method=RequestMethod.POST)
	public String postingInsertPost(PostingVo postingVo,
									HttpServletRequest request, Model model,
									@RequestPart("attach")MultipartFile attach) throws Exception{
		
		//부모글 번호 세팅
		Integer parentposting_num = null;
		if(postingVo.getParentposting_num() == null || postingVo.getParentposting_num().equals("")){
			parentposting_num = null;
		}else{
			parentposting_num = postingVo.getParentposting_num();
		}
		
		//게시글 세팅
		Integer board_num = postingVo.getBoard_num();
		String posting_title = postingVo.getPosting_title();
		String posting_content = postingVo.getPosting_content();
		UserVo userVo = (UserVo) request.getSession().getAttribute("userVo");
		String posting_userid = userVo.getUserId();
		PostingVo paramPostingVo = new PostingVo(board_num, posting_title, posting_content, posting_userid, parentposting_num);
		
		int result1 = postingService.insertPosting(paramPostingVo);
		int result2 = 0;
		
		//파일이름과 저장경로 초기화
		String filename = "";
		String realFilename = "";
		
		//첨부파일을 등록한 경우
		if(attach.getSize() > 0){
			filename = attach.getOriginalFilename();
			realFilename = "d:\\picture\\" + UUID.randomUUID().toString();
			
			attach.transferTo(new File(realFilename));
			
			//파일명, uuid(저장경로) 세팅
			AttachVo attachVo = new AttachVo();
			attachVo.setFilename(filename);
			attachVo.setRealfilename(realFilename);
			attachVo.setPosting_num(paramPostingVo.getPosting_num()); //insert된 시퀀스 값가져와야함 
			
			result2 += attachService.insertAttach(attachVo);
		}
		
		//첨부파일을 올리지 않은 경우 filename, realFilename 모두 공백(위에서 초기화한값)
		
		logger.debug("attachResult : {}", result2);
		
		//정상 입력(성공)
		if(result1 == 1){
			//db에서 데이터를 조작하는 로직을 처리할때는 forward가 아니라 redirect를 사용해야함(새로고침시 최초요청 url로 다시 이동하기때문에)
			//redirect는 ContextPath를 써줘야하며 redirect는 get방식임
			return "redirect:" + request.getContextPath() + "/postingDetail?posting_num=" + paramPostingVo.getPosting_num();
		}
		//정상 입력(실패)
		else{
			return "postingInsertTiles";
		}
	}
	
	@RequestMapping(path="/postingUpdate", method=RequestMethod.GET)
	public String postingUpdateGet(String type, String posting_num, HttpServletRequest request, Model model){
		//타입과 게시글 번호를 파라미터로 받음
		String paramType = type;
		Integer paramPosting_num = Integer.parseInt(posting_num);
		
		//파라미터로 받은 게시글 번호로 해당 게시글 조회
		PostingVo postingVo = postingService.selectPosting(paramPosting_num);
		
		//해당 게시글의 첨부파일 조회
		List<AttachVo> attachList = attachService.selectAttachList(paramPosting_num);
		
		int result = 0;
		//타입이 1이면 수정
		if(paramType.equals("1")){
			//수정시 request 속성에 설정한후 postingUpdate.jsp로 forward
			model.addAttribute("postingVo", postingVo);
			model.addAttribute("attachList", attachList);
			return "postingUpdateTiles";
		}
		//타입이 2이면 삭제
		else if(paramType.equals("2")){
			PostingVo posting = new PostingVo();
			posting.setPosting_title(postingVo.getPosting_title());
			posting.setPosting_content(postingVo.getPosting_content());
			posting.setDelete_exist("Y");
			posting.setPosting_num(postingVo.getPosting_num());
			
			result = postingService.updatePosting(posting);
			
			//정상입력(성공) - 리다이렉트는 get방식으로 게시판번호를 넘겨줘야함
			if(result == 1){
				return "redirect:" + request.getContextPath() + "/posting?board_num=" + postingVo.getBoard_num();
			}
			//실패 - 다시 상세조회를 가야하므로 게시글번호를 넘겨줘야함
			else{
				//request.getRequestDispatcher("/posting/postingDetail?posting_num="+ posting_num).forward(request, response);
				
				//Tiles로 반환할때는 uri이 아니기때문에 파라미터값을 넘겨줘서 사용하고싶을때는 model 객체에 담아서 사용
				model.addAttribute("posting_num", paramPosting_num);
				return "postingDetailTiles";
			}
		}
		return "";
	}
	
	@RequestMapping(path="/postingUpdate", method=RequestMethod.POST)
	public String postingUpdatePost(String posting_num, PostingVo postingVo, String attach_num,
									HttpServletRequest request, Model model,
									@RequestPart("attach")MultipartFile attach) throws Exception{
		
		Integer paramPosting_num = Integer.parseInt(posting_num);
		String paramPosting_title = postingVo.getPosting_title();
		String paramPosting_content = postingVo.getPosting_content();
		
		//해당 게시글번호로 게시글 조회
		PostingVo paramPostingVo = postingService.selectPosting(paramPosting_num);
		
		//게시글 세팅
		paramPostingVo.setPosting_title(paramPosting_title);
		paramPostingVo.setPosting_content(paramPosting_content);
		
		int result = postingService.updatePosting(paramPostingVo);
		int result2 = 0;
		
		//기존의 첨부파일 삭제
		String attach_num_str = attach_num;
		if(attach_num_str != null){
			Integer paramAttach_num = Integer.parseInt(attach_num_str);
			attachService.deleteAttach(paramAttach_num);
		}
		
		//파일이름과 저장경로 초기화
		String filename = "";
		String realFilename = "";
		
		//첨부파일을 등록한 경우
		if(attach.getSize() > 0){
			filename = attach.getOriginalFilename();
			realFilename = "d:\\picture\\" + UUID.randomUUID().toString();
			
			attach.transferTo(new File(realFilename));
			
			//파일명, uuid(저장경로) 세팅
			AttachVo attachVo = new AttachVo();
			attachVo.setFilename(filename);
			attachVo.setRealfilename(realFilename);
			attachVo.setPosting_num(paramPostingVo.getPosting_num()); //insert된 시퀀스 값가져와야함 
			
			result2 += attachService.insertAttach(attachVo);
		}
		
		//첨부파일을 올리지 않은 경우 filename, realFilename 모두 공백(위에서 초기화한값)
		
		logger.debug("attachResult : {}", result2);
		
		//정상입력(성공) - 해당 게시글 상세조회로 넘어감
		if(result == 1){
			return "redirect:" + request.getContextPath() + "/postingDetail?posting_num=" + paramPostingVo.getPosting_num();
		}
		//실패 - 원래 화면을 다시 보여줌
		else{
			//request.getRequestDispatcher("/postingUpdate?type=1&posting_num=" + postingVo.getPosting_num()).forward(request, response);
			
			//Tiles로 반환할때는 uri이 아니기때문에 파라미터값을 넘겨줘서 사용하고싶을때는 model 객체에 담아서 사용
			model.addAttribute("type", "1");
			model.addAttribute("posting_num", postingVo.getPosting_num());
			return "postingUpdateTiles";
		}
	}

}