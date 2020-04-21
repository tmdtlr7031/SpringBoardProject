package nss.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ReflectionUtils;

import egovframework.com.cmm.util.StringUtil;

public class XlsxUtil{

	final static Logger logger = LoggerFactory.getLogger(XlsxUtil.class);

	private List data = null;
	private String[] topRows = null;
	private String[] keyArray = null;
	private Workbook wb;
	private String sheetName = null;
	private String filePath = null;
	private String saveFileName = null;
	private String orgFileName = null;
	private Sheet newSheet = null;
	private boolean combineYn = false;
	private boolean titleYn = false;
	private String title = null;
	private String ListInnerType = null;
	private String resultTypeFlag = null;
	private HttpServletResponse res = null;
	int SHEET_ROW_LIMIT = 1048576;
    XSSFCellStyle headerStyle = null;
    XSSFCellStyle titleStyle = null;
    XSSFCellStyle contentsStyle = null;
    Font contentsFont = null;
    Font titleFont = null;
    Font headerFont = null;
    XSSFRichTextString headerRich = null;
    int totalRow = 0;
    int sheetCnt = 0;
    
    public XlsxUtil(){
    	this.wb = new XSSFWorkbook();
    	titleFont = wb.createFont();
    	headerFont = wb.createFont();
    	contentsFont = wb.createFont();
    	headerStyle = (XSSFCellStyle) wb.createCellStyle();
    	titleStyle = (XSSFCellStyle) wb.createCellStyle();
    	contentsStyle = (XSSFCellStyle) wb.createCellStyle();
    }
    
    public void init(String sheetName,List data,String[] topRows, String[] keyArray, 
			String filePath, String saveFileName, boolean titleYn, String title, HttpServletResponse res, boolean combineYn) throws InvalidFormatException, IOException {
    	String orgFileName = String.valueOf(System.currentTimeMillis())+".xlsx";		// 서버에 저장될 엑셀파일명
		File fPath = new File(filePath);
		if(!fPath.exists()){
			if(!fPath.mkdirs()) {
				throw new IOException();
			}
		}
		
		// 데이터가 없는 경우 건너뛴다
		if(data.size() != 0){
			setListInnerType(String.valueOf(data.get(0).getClass()).substring(String.valueOf(data.get(0).getClass()).lastIndexOf(".")));
			setResultTypeFlag(ListInnerType.toLowerCase().indexOf("vo")>0?"vo":"hm");
		}
		
		setSheetName(sheetName);
		setData(data);
		setTopRows(topRows);
		setKeyArray(keyArray);
		setFilePath(filePath + orgFileName);
		setSaveFileName(saveFileName);
		setOrgFileName(orgFileName);
		setRes(res);
		setCombineYn(combineYn);
		setTitleYn(titleYn);
		setTitle(title);
	}
    
    public void make() throws Exception{
    	makeProc();
    	downloadFunc();
    }
    
    public void makeProc() throws Exception{
		titleMake();
    	headerMake();
    	contentsMake();
    }
    public void titleMake() throws Exception{
    	Row titleRow = null;
    	Cell titleCell = null;
    	if(titleYn){
    		if(totalRow == 0 || totalRow == SHEET_ROW_LIMIT){
    			newSheet = wb.createSheet(sheetName+(++sheetCnt));
    			for(int j=0; j<2; j++){
    				titleRow = newSheet.createRow(totalRow);
    				for(int i=0; i<keyArray.length; i++){
    					initTitleStyle();
    					titleCell = titleRow.createCell(i);
    					titleCell.setCellValue(StringUtil.evl(title,""));
    					titleCell.setCellStyle(titleStyle);
    				}
    				totalRow++;
    			}
    			newSheet.addMergedRegion(new CellRangeAddress(0, 1, 0, keyArray.length-1));
    		}
    	}
    }
    
    public void headerMake() throws Exception{
    	Row headerRow = null;
    	Cell headerCell = null;
    	if(!titleYn){
    		newSheet = wb.createSheet(sheetName+(++sheetCnt));
    	}
    	headerRow = newSheet.createRow(++totalRow); // 4행생성
    	int cellNum = 0;
    	// 셀병합 사용되지 않는 부분
    	if (!combineYn){
    		for(String topRow : topRows) {
    			initHeaderStyle();
    			headerCell = headerRow.createCell(cellNum++);
    			headerCell.setCellValue(getHeaderRich(topRow));
    			headerCell.setCellStyle(headerStyle);
    			newSheet.autoSizeColumn(cellNum);
    			newSheet.setColumnWidth(cellNum, (newSheet.getColumnWidth(cellNum)+2000));
    		}
    		totalRow++;
    		
    	} else { // 병합되는부분
    		
    		/* 통계 */
    		if (saveFileName.contains("Stat")){
    			int cnt = 0; // 행 추가 시점으로 사용
    			
    			/**************************** 20200110 기관명 추가 ****************************/
    			// CellRangeAddress(3, 4, 0, 0) => CellRangeAddress(행시작, 행끝, 열시작, 열끝)
    			
        		newSheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0)); // 일자   
        		newSheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1)); // 기관명
    			newSheet.addMergedRegion(new CellRangeAddress(3, 4, 2, 2)); // 송신대상
    			newSheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 5)); // 송신성공
    			newSheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 7));	// 송신실패
    			/**************************** 20200110 기관명 추가 ****************************/
    			
    			/******************************** AS-IS ********************************/
    			/*newSheet.addMergedRegion(new CellRangeAddress(3, 4, 2, 2)); // 기관명*/
    			/*newSheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));*/ // 송신대상
    			/*newSheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 4));*/ // 송신성공
    			/*newSheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 6));*/ // 송신실패
    			/******************************** AS-IS ********************************/
    			
    			for (String topRow : topRows) {
    				
    				// 행 추가 후 병합된 곳 스타일 맞춰주기 + 송신실패 병합된 셀 마지막 부분 스타일
    				if ((totalRow == 4 && cellNum == 0) ){
    					for (int i = 0; i < 3; i++) {
    						initHeaderStyle();
    						headerCell = headerRow.createCell(cellNum++);
    						headerCell.setCellStyle(headerStyle);
    					}
    				}
    				
    				// 실제로 엑셀에 그려주는 부분
    				initHeaderStyle();
    				headerCell = headerRow.createCell(cellNum++); 
        			headerCell.setCellValue(getHeaderRich(topRow)); // 실제로 값 넣어주는 부분
        			headerCell.setCellStyle(headerStyle);
        			newSheet.autoSizeColumn(cellNum);
        			newSheet.setColumnWidth(cellNum, (newSheet.getColumnWidth(cellNum)+2000));
        			
        			
        			// 헤드 밀어주기
    				if (totalRow == 3 && cellNum == 4 ) {
    					for (int i = 0; i < 2; i++) {
    						headerCell = headerRow.createCell(cellNum++);
    						headerCell.setCellStyle(headerStyle);
						}
    				}				
        			
        			// 마지막으로 병합된 셀 스타일 맞춰주기용
        			if(totalRow == 3 && cellNum == 7){ //6
        				headerCell = headerRow.createCell(cellNum++); 
            			headerCell.setCellStyle(headerStyle);
            			newSheet.autoSizeColumn(cellNum);
            			newSheet.setColumnWidth(cellNum, (newSheet.getColumnWidth(cellNum)+2000));
        			}
        			
        			cnt++; // 병합된 컬럼 수
        			
        			if (cnt == 5) {	
        				headerRow = newSheet.createRow(++totalRow);
        				cellNum = 0; 
        				
    				}							     
        		}
        		totalRow++;
        		
    		} else {
    			
    			/* 송신예약관리 */
        		int cnt = 0; // 행 추가 시점으로 사용
        		newSheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0)); // no
    			newSheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1)); // 센터명
    			newSheet.addMergedRegion(new CellRangeAddress(3, 4, 2, 2)); // 통지서
    			newSheet.addMergedRegion(new CellRangeAddress(3, 4, 3, 3)); // 상태
    			newSheet.addMergedRegion(new CellRangeAddress(3, 4, 4, 4)); // 송신대상
    			newSheet.addMergedRegion(new CellRangeAddress(3, 4, 5, 5)); // 접수일시
    			newSheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 7)); // 대상입수
    			
        		for(String topRow : topRows) {
        			
        			// 행 추가 후 병합된 곳 스타일 맞춰주기 
    				if (totalRow == 4 && cellNum == 0){
    					for (int i = 0; i < 6; i++) {
    						initHeaderStyle();
    						headerCell = headerRow.createCell(cellNum++);
    						headerCell.setCellStyle(headerStyle);
    					}
    				}
        			
        			initHeaderStyle();
        			headerCell = headerRow.createCell(cellNum++);
        			headerCell.setCellValue(getHeaderRich(topRow));
        			headerCell.setCellStyle(headerStyle);
        			newSheet.autoSizeColumn(cellNum);
        			newSheet.setColumnWidth(cellNum, (newSheet.getColumnWidth(cellNum)+2000));
        			cnt++;
        			
        			// 마지막으로 병합된 셀 스타일 맞춰주기용
        			if(totalRow == 3 && cellNum == 7){
        				headerCell = headerRow.createCell(cellNum++); 
            			headerCell.setCellStyle(headerStyle);
            			newSheet.autoSizeColumn(cellNum);
            			newSheet.setColumnWidth(cellNum, (newSheet.getColumnWidth(cellNum)+2000));
        			}
        			
        			if (cnt == 7) {
        				headerRow = newSheet.createRow(++totalRow);
        				cellNum = 0;
    				}
        		}
        		totalRow++;
			}
    		
		}
    	
    }
    
    public void contentsMake() throws Exception{
    	Row contentsRow = null;
    	Cell contentsCell = null;
    	
    	// 데이터가 없는 경우
    	if(data.size() == 0){
    		try {
    			contentsRow = newSheet.createRow(totalRow++);
    			
    			if (saveFileName.contains("Stat")){ 
    				newSheet.addMergedRegion(new CellRangeAddress(5, 5, 0, topRows.length-3)); // 통계부분	
    			}else {
    				newSheet.addMergedRegion(new CellRangeAddress(4, 4, 0, topRows.length-1)); // 병합사용 안하는 부분 (수신거부관리, 송신완료현황)
				}
    			for(int i=0; i<keyArray.length; i++){
    				initContentsStyle();
	    			contentsCell = contentsRow.createCell(i);
	    			contentsCell.setCellValue("목록이 없습니다.");
	    			contentsCell.setCellStyle(contentsStyle);
    			}
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    	}
    	
    	for(Object entry : data){
    		if(totalRow == SHEET_ROW_LIMIT){
    			totalRow = 0;
    			titleMake();
    	    	headerMake();
    		}
	    	try {
				contentsRow = newSheet.createRow(totalRow++);
				for(int i=0; i<keyArray.length; i++){
					initContentsStyle();
					if("vo".equals(resultTypeFlag)){
						Field field = ReflectionUtils.findField(entry.getClass(), JdbcUtils.convertUnderscoreNameToPropertyName(keyArray[i]));
						field.setAccessible(true);
						contentsCell = contentsRow.createCell(i);
						contentsCell.setCellValue(StringUtil.evl(field.get(entry),""));
					}else {
						HashMap<String,Object> hm = (HashMap<String,Object>)entry;
						contentsCell = contentsRow.createCell(i);
						contentsCell.setCellValue(StringUtil.evl(hm.get(keyArray[i]),""));
					}
					contentsCell.setCellStyle(contentsStyle);
					newSheet.autoSizeColumn(i);
		    		newSheet.setColumnWidth(i, (newSheet.getColumnWidth(i)+2000));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    }
    
    public void downloadFunc() throws Exception{
    	File f = new File(filePath);
		FileInputStream is = null;
		
		
		 try{
			 OutputStream fileOut=new FileOutputStream(filePath);
		 
	        	wb.write(fileOut);
				
				String mimeType = null;
				if (mimeType == null) {
					mimeType = "application/x-msdownload";
				}
				res.setContentType(mimeType);
				res.setHeader("Content-Disposition",
						String.format("inline; filename=\"" + new String(saveFileName.getBytes("UTF-8"), "ISO-8859-1") + "\""));
				res.setContentLength((int) f.length());
				
				is = FileUtils.openInputStream(f);
				IOUtils.copy(is, res.getOutputStream());
				res.flushBuffer();
				if(fileOut != null) fileOut.close();
	     }catch (IOException ioe) {
	    	 	logger.debug("defaultMake error message : {}",ioe.getMessage());
	     }finally {
	    	if(is != null) is.close();
    		if(f.exists()){
    			if(!f.delete()){
    				throw new IOException();
    			}
    		}
		}
    }
    
	public XSSFRichTextString getHeaderRich(String value){
		headerRich = new XSSFRichTextString(value);
		headerRich.applyFont(headerFont);
		return headerRich;
	}

	public void initTitleStyle(){
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
	}
	
	public void initHeaderStyle(){
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLACK.index);
		headerFont.setFontName("맑은 고딕");
		headerStyle.setFont(headerFont);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}
	
	public void initContentsStyle(){
		contentsFont.setColor(IndexedColors.BLACK.index);
		contentsFont.setFontName("맑은 고딕");
		contentsStyle.setFont(contentsFont);
		
		// 데이터가 없는 경우 가운데 정렬
		if (data.size() != 0){
			contentsStyle.setAlignment(HorizontalAlignment.LEFT);
		}else {
			contentsStyle.setAlignment(HorizontalAlignment.CENTER);
		}
		
		contentsStyle.setBorderBottom(BorderStyle.THIN);
		contentsStyle.setBorderLeft(BorderStyle.THIN);
		contentsStyle.setBorderRight(BorderStyle.THIN);
		contentsStyle.setBorderTop(BorderStyle.THIN);
	}
	
	public boolean isCombineYn() {
		return combineYn;
	}

	public void setCombineYn(boolean combineYn) {
		this.combineYn = combineYn;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public String[] getTopRows() {
		return topRows;
	}

	public void setTopRows(String[] topRows) {
		this.topRows = topRows;
	}

	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}

	public Workbook getWb() {
		return wb;
	}

	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getOrgFileName() {
		return orgFileName;
	}

	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}

	public Sheet getNewSheet() {
		return newSheet;
	}

	public void setNewSheet(Sheet newSheet) {
		this.newSheet = newSheet;
	}

	public boolean isTitleYn() {
		return titleYn;
	}

	public void setTitleYn(boolean titleYn) {
		this.titleYn = titleYn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getListInnerType() {
		return ListInnerType;
	}

	public void setListInnerType(String listInnerType) {
		ListInnerType = listInnerType;
	}

	public String getResultTypeFlag() {
		return resultTypeFlag;
	}

	public void setResultTypeFlag(String resultTypeFlag) {
		this.resultTypeFlag = resultTypeFlag;
	}

	public HttpServletResponse getRes() {
		return res;
	}

	public void setRes(HttpServletResponse res) {
		this.res = res;
	}

	public int getSHEET_ROW_LIMIT() {
		return SHEET_ROW_LIMIT;
	}

	public void setSHEET_ROW_LIMIT(int sHEET_ROW_LIMIT) {
		SHEET_ROW_LIMIT = sHEET_ROW_LIMIT;
	}

	public XSSFCellStyle getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(XSSFCellStyle headerStyle) {
		this.headerStyle = headerStyle;
	}

	public XSSFCellStyle getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(XSSFCellStyle titleStyle) {
		this.titleStyle = titleStyle;
	}

	public XSSFCellStyle getContentsStyle() {
		return contentsStyle;
	}

	public void setContentsStyle(XSSFCellStyle contentsStyle) {
		this.contentsStyle = contentsStyle;
	}

	public Font getContentsFont() {
		return contentsFont;
	}

	public void setContentsFont(Font contentsFont) {
		this.contentsFont = contentsFont;
	}

	public Font getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	public Font getHeaderFont() {
		return headerFont;
	}

	public void setHeaderFont(Font headerFont) {
		this.headerFont = headerFont;
	}

	public XSSFRichTextString getHeaderRich() {
		return headerRich;
	}

	public void setHeaderRich(XSSFRichTextString headerRich) {
		this.headerRich = headerRich;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getSheetCnt() {
		return sheetCnt;
	}

	public void setSheetCnt(int sheetCnt) {
		this.sheetCnt = sheetCnt;
	}
	
	
	
	
}
