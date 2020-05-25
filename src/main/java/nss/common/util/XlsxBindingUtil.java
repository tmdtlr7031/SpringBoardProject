package nss.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ReflectionUtils;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.StringUtil;
import nss.dashboard.service.DashBoardVO;

public class XlsxBindingUtil {
	
	final static Logger logger = LoggerFactory.getLogger(XlsxUtil.class);
	
	int SHEET_ROW_LIMIT = 1048576;
	int totalRow = 0;
	int sheetIdx = 0;
	
	int sheetCnt = 0;
	
	public void readFile(List<DashBoardVO> resultList, String[] keyArray, String fileIdx, HttpServletResponse res , String gubun, String title) throws Exception{
		try {
			
			String filePath = "";
			String sampleFileName = ""; 
			String fileName = "";
			
			filePath = EgovProperties.getProperty("Globals.filePath") + "excel/";
			sampleFileName = "템플릿테스트.xlsx";
			fileName = "test222.xlsx";
			
            FileInputStream fis = new FileInputStream(filePath + sampleFileName);
			
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            
            Font titleFont = workbook.createFont();
    		CellStyle titleStyle =  workbook.createCellStyle();
        	XSSFCell titleCell = null;
        	
        	/*
        	 *  SXSSF는 읽기를 못하기 떄문에 getRow등으로 접근 불가. 따라서 필요한 경우 XSSF로 셋팅 먼저해주고 생성.
        	 *  하지만 XSSF로 커스텀하는 부분이 있으면 속도가 느려짐. => 있을때와 없을때 차이는 3초정도?남.
        	 *  
        	 **/
        	
        	// 해당부분은 타이틀 부분 병합+값생성
			for(int j=0; j<2; j++){
				XSSFRow newRow = workbook.getSheetAt(sheetIdx).createRow(j);
				
				for(int i=0; i<keyArray.length; i++){
					titleFont.setBold(true);
            		titleFont.setColor(IndexedColors.BLACK.index);
            		titleFont.setFontHeightInPoints((short)13);
            		titleFont.setFontName("맑은 고딕");
            		titleFont.setItalic(true);
            		titleStyle.setFont(titleFont);
            		titleStyle.setAlignment(HorizontalAlignment.CENTER);
            		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            		titleStyle.setBorderBottom(BorderStyle.MEDIUM);
            		titleStyle.setBorderLeft(BorderStyle.MEDIUM);
            		titleStyle.setBorderRight(BorderStyle.MEDIUM);
            		titleStyle.setBorderTop(BorderStyle.MEDIUM);
					titleCell = newRow.createCell(i);
					titleCell.setCellValue(StringUtil.evl(title,""));
					titleCell.setCellStyle(titleStyle);
				}
				totalRow++;
			}
			workbook.getSheetAt(sheetIdx).addMergedRegion(new CellRangeAddress(0, 1, 0, keyArray.length-1));
            
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            List<XSSFSheet> sheetList = new ArrayList<XSSFSheet>();
//            
//            sheetList.add(sheet);
//            int sheetCount = (int) Math.ceil((double)resultList.size() / (double)(SHEET_ROW_LIMIT-4));
//            if(sheetCount >= 1){
//            	for(int i=0; i<sheetCount-1; i++){
//            		XSSFSheet newSheet = workbook.cloneSheet(0);
//            		sheetList.add(newSheet);
//            	}
//            }
            
            SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook, 1000);
            SXSSFRow newRow = null;
            
            if(resultList.size() == 0){
    			newRow = sxssfWorkbook.getSheetAt(sheetIdx).createRow(totalRow+4);
    			Font fontStyle = sxssfWorkbook.createFont();
        		CellStyle style = sxssfWorkbook.createCellStyle();
        		
        		sxssfWorkbook.getSheetAt(sheetIdx).addMergedRegion(new CellRangeAddress(4, 4, 0, keyArray.length)); // 병합사용 안하는 부분 (수신거부관리, 송신완료현황)
        		
        		for (int columnIndex = 0; columnIndex < keyArray.length; columnIndex++) {
        			fontStyle.setColor(IndexedColors.BLACK.index);
        			fontStyle.setFontName("맑은 고딕");
        			style.setFont(fontStyle);
        			style.setAlignment(HorizontalAlignment.CENTER);
        			style.setBorderBottom(BorderStyle.THIN);
        			style.setBorderLeft(BorderStyle.THIN);
        			style.setBorderRight(BorderStyle.THIN);
        			style.setBorderTop(BorderStyle.THIN);
        			SXSSFCell cell = newRow.createCell(columnIndex);
        			
        			cell.setCellValue("목록이 없습니다.");
        			cell.setCellStyle(style);
        		}
            }else{
            	
            	totalRow = 0;
            	CellStyle cellStyle = sxssfWorkbook.createCellStyle();
            	
            	for (int rowIndex = 0; rowIndex < resultList.size(); rowIndex++) {
            		
            		if(totalRow+4 >= SHEET_ROW_LIMIT){
            			totalRow = 0;
            			sheetIdx++;
            			newRow = sxssfWorkbook.getSheetAt(sheetIdx).createRow(totalRow+4);
            		}else{
            			newRow = sxssfWorkbook.getSheetAt(sheetIdx).createRow(totalRow+4);
            		}
            		
            		for (int columnIndex = 0; columnIndex < keyArray.length; columnIndex++) { // VO 객체 읽어서 값 넣어주는 부분
            			Object entry = resultList.get(rowIndex);
            			Field field = ReflectionUtils.findField(entry.getClass(), JdbcUtils.convertUnderscoreNameToPropertyName(keyArray[columnIndex]));
            			field.setAccessible(true);
            			
            			SXSSFCell cell = newRow.createCell(columnIndex);
            			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            			cellStyle.setBorderBottom(BorderStyle.THIN);
            			cellStyle.setBorderLeft(BorderStyle.THIN);
            			cellStyle.setBorderRight(BorderStyle.THIN);
            			cellStyle.setBorderTop(BorderStyle.THIN);
            			cell.setCellValue(StringUtil.evl(field.get(entry)));
            			cell.setCellStyle(cellStyle);
            		}
            		totalRow++;
            	}
            }
            
            
            File f = new File(filePath + fileName);
    		FileInputStream is = null;
    		try {
    			OutputStream fileOut=new FileOutputStream(filePath + fileName);
    			
    			sxssfWorkbook.write(fileOut);
    			
    			String mimeType = null;
    			if (mimeType == null) {
    				mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    			}
    			res.setContentType(mimeType);
    			res.setHeader("Content-Disposition",
    					String.format("inline; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\""));
    			res.setContentLength((int) f.length());
    			
    			is = FileUtils.openInputStream(f);
    			IOUtils.copy(is, res.getOutputStream());
    			res.flushBuffer();
    			fileOut.close();
    			is.close();
    			fis.close();
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				if(is != null) is.close();
	       		if(f.exists()){
	       			if(!f.delete()){
	       				throw new IOException();
	       			}
	       		}
	       		sxssfWorkbook.dispose();
			}
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
