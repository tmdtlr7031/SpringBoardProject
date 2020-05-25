package egovframework.com.cmm.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.WebUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.StringUtil;


/**
 * 파일 다운로드를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.3.25  이삼섭          최초 생성
 *
 * Copyright (C) 2009 by MOPAS  All right reserved.
 * </pre>
 */
@Controller
public class FileDownloadController {

    private static final Logger LOG = Logger.getLogger(FileDownloadController.class.getName());
    
    @RequestMapping(value = "/mng/downloadExcel.do")
	public void downloadExcel( HttpServletRequest request, HttpServletResponse response) {
		String filePath = "";
		String filePathType = "";
		String fileName = "";
		String saveName = "";
		String fileSeq = "";
		String errorMsg = "";
		String example_no = "";
		String category_code="";
		String seq="";
		InputStream in  = null;
		OutputStream os = null;
		
		try {
			filePath = request.getParameter("filePath");
			filePathType = request.getParameter("filePathType");
			fileName = request.getParameter("fileName");
			saveName = request.getParameter("maskName");
			example_no = request.getParameter("file_example_no");
			category_code = request.getParameter("category_code");
			seq = request.getParameter("file_seq");
			
			filePath = filePath == null ? "" : filePath;
			filePathType = filePathType == null ? "" : filePathType;
			
//			filePath = CommonUtil.getDownloadPath(filePathType)+filePath;
			filePath = EgovProperties.getProperty("Globals.filePath")+"excel/";

			errorMsg = "파일 다운로드 실패 하였습니다.";
			
			filePath = filePath.replace("%2f", "/");
			saveName = saveName.replace("%2f", "/");
			
			if( filePath.contains("./") || filePath.contains("../") || saveName.contains("./") || saveName.contains("../") ){
				errorMsg = "올바른 방법이 아닙니다.";
				throw new IOException();
			}

			if(fileName == null || saveName == null){
				
				errorMsg = "파일이 존재하지 않습니다.";
				throw new IOException();
			}
			
			saveName = WebUtil.filePathReplaceAll(saveName);
			
			File file = new File(filePath, saveName);
			if(file == null || !file.isFile()){
				
				errorMsg = "파일이 존재하지 않습니다.";
				throw new IOException();
			}
			
			in = new FileInputStream(file);
		    
		    response.reset() ; 
		    
		    if( ( request.getHeader("user-agent") ).indexOf("MSIE 5.5") != -1 || ( request.getHeader("user-agent") ).indexOf("Trident") != -1 ) {
		        response.setHeader("Content-Type", "doesn/matter; charset=UTF-8");
		        response.setHeader("Content-Disposition", "attachment;filename=" + 
		        					StringUtil.evl(java.net.URLEncoder.encode(fileName, "UTF-8")).replace("%28", "(").replace("%29", ")").replaceAll("\\+", "%20") + ";");
		    } else {
		    	response.setHeader("Content-Disposition", "attachment;filename=" + 
    					StringUtil.evl("\"" + new String(fileName.getBytes("UTF-8"), "8859_1").replaceAll("\\+", "%20") + "\"") + ";");
			}
		    
		    response.setHeader("Content-Transfer-Encoding", "binary;"); 
		    response.setHeader("Content-Length", "" + file.length() );
		    response.setHeader("Pragma", "no-cache;");
		    response.setHeader("Expires", "-1;"); 
		    
//		    out.clear();
//		    out = pageContext.pushBody();
		    os = response.getOutputStream();
		    
		    byte b[] = new byte[4096];
		    
		    int i = 0;
		    
		    while ( ( i = in.read(b) ) > 0 ) {
		        os.write(b, 0, i);
		    }
		   	os.flush();
			
		} catch(IOException e) {
			System.err.println("IOException");
//			out.print("<!doctype html><html lang='ko'><head><title>download</title><meta charset='utf-8'>");
//			out.print("<script type=\"text/javascript\">alert(\""+errorMsg+"\");history.back();</script>");
//			out.print("</head><body></body></html>");
		} finally {
			if(in != null) {
				try{ in.close(); } catch(IOException e) { System.err.println("IOException"); }
			}
			if(os != null) {
				try{ os.close(); } catch(IOException e) { System.err.println("IOException"); }
			}
		}
		
	}

}



