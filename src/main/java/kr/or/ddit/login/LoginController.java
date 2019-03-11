package kr.or.ddit.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.util.KISA_SHA256;

@Controller
public class LoginController{
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name="userService")
	private IUserService userService;
	
	@Resource(name="boardService")
	private IBoardService boardService;
	
	@RequestMapping(path="/login", method=RequestMethod.GET)
	public String loginView(){
		return "login/login";
	}
	
	@RequestMapping(path="/login", method=RequestMethod.POST)
	public String loginProcess(String userId, String pass,
								HttpServletRequest request){
		String paramUserId = userId;
		String paramPass = pass;
		
		logger.debug("userId : {}", paramUserId);
		logger.debug("pass : {}", paramPass);
		
		//디비에 사용자가 존재하는지 조회
		UserVo userVo = userService.selectUser(paramUserId);
		logger.debug("userVo : {}", userVo);
		
		//db정보와 사용자 파라미터 아이디와 패스워드가 일치하는경우 -> main.jsp
		if(userVo != null && userVo.getUserId().equals(paramUserId) && userVo.getPass().equals(KISA_SHA256.encrypt(paramPass))){
			//사용자 정보를 session에 저장한다
			//userVo 객체는 session이 유지될 동안 다른 페이지에서 사용
			HttpSession session = request.getSession();
			session.setAttribute("userVo", userVo);
			
			session.setAttribute("boardList", boardService.getAllBoard());
			
			// localhost/main.jsp으로 보냄
			return "mainTiles";
		}
		//db정보와 사용자 파라미터 아이디와 패스워드가 일치하지않는경우 -> login.jsp
		else{
			return "login/login";
		}
	}
	
	@RequestMapping(path="/logout", method=RequestMethod.GET)
	public String logoutView(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("userVo");
		
		return "login/login";
	}
}