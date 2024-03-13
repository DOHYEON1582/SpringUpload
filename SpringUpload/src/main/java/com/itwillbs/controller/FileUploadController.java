package com.itwillbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping(value = "/file/*")
public class FileUploadController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public void uploadFormGET() throws Exception{
		logger.debug("uploadFormGET() 호출");
		
		// /file/form.jsp 호출 
		
	}
	
	// 파일 업로드
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public void fileUploadPOST(MultipartHttpServletRequest multiRequest, Model model) throws Exception{
		logger.debug(" fileUploadPOST() 호출");
		
		multiRequest.setCharacterEncoding("UTF-8");
		// 전달 정보 (정보 + 파일) 저장
		// 1) 폼태그 정보 
		
		// 전달 받은 정보 저장하는  map
		Map map = new HashMap();
		
		Enumeration enu = multiRequest.getParameterNames(); // 파일을 제외한 나머지 정보(파일이랑 같이 보낸 이름,아이디...등)
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multiRequest.getParameter(name);
			
			logger.debug(" name : "+name+", value : "+value);
			map.put(name, value);
		}
		logger.debug("  "+map);
		
		// 파일에 대한 처리
		List<String> fileList = fileProcess(multiRequest);
		
		map.put("fileList", fileList);
		logger.debug("  "+map);
		
		model.addAttribute("map", map);
		
	}// fileUploadPOST()
	
	
	private List<String> fileProcess(MultipartHttpServletRequest multiRequest) throws IOException{
		logger.debug(" fileProcess() - 파일 업로드 처리 시작");
		
		// 업로드 파일의 정보를 저장
		List<String> fileList = new ArrayList<String>(0);
		
		Iterator<String> fileNames = multiRequest.getFileNames(); // 파일의 파라메터명을 가져온다
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multiRequest.getFile(fileName);
			String oFileName = mFile.getOriginalFilename();
			logger.debug(" fileName : "+fileName+", oFileName : "+oFileName);
			
			fileList.add(oFileName); // 파일의 이름을 저장
			
			// 파일 업로드 처리
			File file = new File("D:\\springUpload\\"+fileName);
			
			if(mFile.getSize() != 0) { // 첨부파일이 있는지 없는지 체크
				if( !file.exists()) { // exists() => 해당파일이 있을때 true, 없을때 false
					// => 해당 파일이 없을때
					if(file.getParentFile().mkdirs()) {
						file.createNewFile();
					}
				}// exists()
				
				//mFile.transferTo(file);
				mFile.transferTo(new File("D:\\springUpload\\"+oFileName));
			}// getSize
		}// while
		
		logger.debug(" 파일 업로드 처리 끝 ");
		
		return fileList;
	}
	
	// 파일 다운로드 : 업로드 해놓은 파일의 위치, 다운로드할 파일 이름
	@RequestMapping(value = "/download",method = RequestMethod.GET)
	public void fileDownlaodGET(@RequestParam("fileName") String fileName,
								HttpServletResponse resp) throws Exception{
		logger.debug("fileDownlaodGET() 호출");
		
		String downLoadPath = "D:\\springUpload\\";
		logger.debug(" 다운로드 할 fileName : "+fileName);
		
		// 다운로드 할 파일
		File file = new File(downLoadPath+fileName);
		
		// 데이터(첨부파일)를 전송하는 통로
		OutputStream out = resp.getOutputStream();
		
		// 모든 파일의 다운로드 형태를 통일
		resp.setHeader("Cache-Control", "no-cache");
		resp.addHeader("Content-disposition", "attachment; fileName="+(URLEncoder.encode(fileName,"UTF-8")));
		
		/********************************************************************************************************************************/
		// 썸네일 만들기 (Thumbnailator repository)
		
		int lastIdx = fileName.lastIndexOf(".");
		
		String thumbFileName = fileName.substring(0,lastIdx);
		logger.debug("thumbFileName : "+thumbFileName);
		
		// 썸네일 파일 생성
		//File thumbNail = new File(downLoadPath+"\\thumbNail\\"+thumbFileName+".png");
		
		
		
		
		// 썸네일 이미지 바로 화면에 출력
		if(file.exists()) {
			//thumbNail.getParentFile().mkdirs();
			//Thumbnails.of(file).size(50, 50).outputFormat("png").toFile(thumbNail);
			
			Thumbnails.of(file).size(50, 50).outputFormat("png").toOutputStream(out);;
		}
		
		byte[] buffer = new byte[1024*8]; // 8KB
		out.write(buffer);
		out.close();
		
		/********************************************************************************************************************************/
		
//		// 파일데이터를 읽기
//		FileInputStream fis = new FileInputStream(file);
//		
//		byte[] buffer = new byte[1024*8]; // 8KB
//		
//		int data = 0;
//		while((data = fis.read(buffer)) != -1) { // -1 파일의 끝(EOF)
//			// 다운로드 출력
//			out.write(buffer,0,data);
//		}
		
//		out.flush(); // 버퍼의 여백을 공백으로 채움
//		out.close();
//		fis.close();
 		
		logger.debug(" 파일 다운로드 완료");
		
	}
	
	
	
	
	
	
	
}// controller
