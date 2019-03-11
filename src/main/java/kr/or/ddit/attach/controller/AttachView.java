package kr.or.ddit.attach.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import kr.or.ddit.attach.model.AttachVo;
import kr.or.ddit.attach.service.IAttachService;

public class AttachView implements View{
	
	@Resource(name="attachService")
	private IAttachService attachService;

	@Override
	public String getContentType() {
		return "image";
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//첨부파일 다운로드 받기위해선 설정해줘야함 - 필수!!!
		response.setHeader("Content-Disposition", "attachment; filename=profile.png");
		response.setContentType("application/octet-stream");
		response.setContentType("image");
		
		//1.첨부파일번호 파라미터 확인
		String attach_num_str = (String) model.get("attach_num");
		Integer attach_num = Integer.parseInt(attach_num_str);
		
		//2.해당 첨부파일 번호로 첨부파일 정보 조회
		AttachVo attachVo = attachService.selectAttach(attach_num);
		
		//3-1.realFilename이 존재할 경우
		//3-1-1.해당 경로의 파일을 FileInputStream으로 읽는다
		FileInputStream fis;
		if(attachVo != null && attachVo.getRealfilename() != null){
			fis = new FileInputStream(new File(attachVo.getRealfilename()));
			response.setHeader("Content-Disposition", "attachment; filename=" + attachVo.getFilename());
		}
		
		//3-2.realFilename이 존재하지 않을 경우
		//3-2-1. /upload/noimg.png (application.getRealPath())
		else{
			ServletContext application = request.getServletContext();
			String noimgPath = application.getRealPath("/upload/noimg.png");
			fis = new FileInputStream(new File(noimgPath));
		}
		
		//4.FileInputStream을 response객체의 outputStream 객체에 write
		ServletOutputStream sos = response.getOutputStream();
		byte[] buff = new byte[512];
		int len = 0;
		while((len = fis.read(buff)) > -1){
			sos.write(buff);
		}
		
		sos.close();
		fis.close();
	}

}