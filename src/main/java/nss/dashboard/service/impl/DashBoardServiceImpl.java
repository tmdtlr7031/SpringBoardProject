package nss.dashboard.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.WebUtil;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.StringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jxl.Sheet;
import jxl.Workbook;
import nss.dashboard.mappers.DashBoardMapper;
import nss.dashboard.service.DashBoardService;
import nss.dashboard.service.DashBoardVO;

// <FIXME> EgovAbstractServiceImpl 역할이...
@Service("dashboardService")
public class DashBoardServiceImpl extends EgovAbstractServiceImpl implements DashBoardService{

	// mapper inject (@Service에서 주입해서 쓰자)
	@Resource(name="dashBoardMapper")
	DashBoardMapper dashBoardMapper;
	
	@Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
	
	
	
	@Override
	public List<DashBoardVO> selectTestList(DashBoardVO dashBoardVO) {
		return dashBoardMapper.selectTestList(dashBoardVO);
	}

	@Override
	public int selectTestListCnt(DashBoardVO dashBoardVO) {
		return dashBoardMapper.selectTestListCnt(dashBoardVO);
	}

	/*
	 * 
	 * - 트랜젝션 테스트를 위해 일부러 발생시키는 에러(thows exception)는 정상적인 트랜젝션 테스트를 할 수 없다. 따라서 로직으로 일부러 에러를 내보자
	 *   ( map은 형체크를 안하기 때문에 cast를 만족시키더라도 numberfomat에러를 낼수있으니 간단하게 에러 만들 수 있다!) 
	 *   
	 * - 트랜젝션과 try~catch는 다름. 이는  context-transaction에 Exception시 롤백이 명시 되어있기 때문에 Impl에서 try~catch를 쓰면 롤백이 안된다? 이건 틀림.
	 *   트랜젝션은 트랜젝션이고 try~catch는 개발자가 예측되는 에러 상황에 대한 처리를 할 때 쓰는 것이기 때문에 구별해서 생각하는게 맞다.  
	 * 
	 * - throws를 하던 try~catch로 잡던 개발 방법론의 차이. throws를 안해줘도 결국 "프레임워크"가 최후에는 에러를 받음. 하지만 무분별하게 throws를 해주는건
	 *   바람직하지 않다는게 내 생각. 사용자 관점에서 무작정 에러페이지를 보여주면 뭐때문인지 원인도 모르기 떄문..따라서 아래와 같이 에러시 파일을 지우기, 에러메시지 출력 등
	 *   에러 상황에 대한 후처리가 있는 경우 try~catch로 작성하고 "되도록"이면 해당 메서드 내의 에러는 본인이 처리하는게 좋다고 생각 (회피하지말고)   
	 * 
	 * */ 
	@Override
	public Map<String, Object> insertDashBoardExcelUpload(Map<String, MultipartFile> files) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		DashBoardVO paramVO = new DashBoardVO();
		List<FileVO> fileList = null;
		
		// 등록하다가 에러나면 롤백할 생각말고 검증 후에 넣는게 더 낫다는 의견 수렴
		// 대량 등록같은 경우 한번에 Insert하는게 바람직. DB 접근할때마다 트랜젝션을 생성하고 커밋하는데 이 횟수를 한번으로 줄일 수 있기 때문. (oracle의 insert all 등을 이용) 
		try {
			fileList = fileUtil.parseFileInf(files, "", 0, "", "Globals.filePath", "");
			
			/*
			 *	큰 맥락으로 보면
			 *		xls : HSSFWorkbook -> HSSFSheet -> HSSFRow -> HSSFCell -> 값 획득
			 *		xlsx : XSSFWorkbook -> XSSFSheet -> XSSFRow -> XSSFCell -> 값 획득
			 *		확장자에 따라 다른 걸 써야함. 로직은 동일
			 *
			 *		API 문서에 따르면 Closeable이라 따로 close(); 안해도됨. implements에서 WorkBook 확인해보면 됨.		
			 *
			 *	참고 : https://javaslave.tistory.com/78 , https://cofs.tistory.com/80
			 **/
			
			for (int j = 0; j < fileList.size(); j++) { // 업로드파일 개수 2개이상일때 대비
				FileVO fvo = fileList.get(j);
				String saveName = fvo.getStreFileNm();
				
				saveName = WebUtil.filePathReplaceAll(saveName);
				FileInputStream fis= new FileInputStream(fvo.getFileStreCours()+saveName);
				
				HSSFWorkbook workbook = new HSSFWorkbook(fis); // 엑셀파일 전체를 담고 있는 객체
				HSSFSheet sheet = workbook.getSheetAt(0); // 0번째 sheet
				int totalRowCnt = sheet.getPhysicalNumberOfRows(); // 0번째 시트의 전체 행수
				
				for (int rowindex = 0; rowindex < totalRowCnt; rowindex++) {
					// 0번째는 헤더있는 곳이라 패쓰
					if (rowindex != 0) {
						HSSFRow curRow = sheet.getRow(rowindex); // 행읽기
						DashBoardVO vo = new DashBoardVO();
						
						if(curRow !=null){
							 // 해당 로우의 셀 탐색
							 for(int columnindex=0; columnindex<curRow.getPhysicalNumberOfCells(); columnindex++) { // 행의 정의된 셀 수
								 HSSFCell curCell = curRow.getCell(columnindex);
						         String value=""; // 셀 읽은 값 반환용
						         
								 // 셀이 비어있는 경우 다음 셀로 (-> 로직 완성 후 업로드하려는 엑셀에 빈값이 있는 경우 후처리를 어케할지 고민)
								 if(curCell == null) {
									 continue;
								 }else {
								 	//타입별로 내용 읽기
					                switch (curCell.getCellType()){
					                case FORMULA:
					                    value=curCell.getCellFormula();
					                    break;
					                case NUMERIC:
					                    value=String.valueOf((int)curCell.getNumericCellValue()); // int로 cast안하면 소수점첫째자리까지 나옴
					                    break;
					                case STRING:
					                    value=curCell.getStringCellValue();
					                    break;
					                case BLANK:
					                    value=curCell.getBooleanCellValue()+"";
					                    break;
					                case ERROR:
					                    value=curCell.getErrorCellValue()+"";
					                    break;
									default:
										value = new String();
										break;
					                }
								 }
								 
								 // VO에 입력
								 switch (columnindex) {
									case 0: // 이름
										vo.setUserName(value);
										break;
									case 1: // 상품
										vo.setOrderProduct(value);
										break;
									case 2: // 수량
										vo.setOrderCnt(Integer.parseInt(value));
										break;
									case 3: // 상태
										vo.setOrderStatus(value);
										break;
									case 4: // 주문번호
										vo.setOrderCode(Integer.parseInt(value));
										break;
								}
							 } // 셀 for(e)
							 paramVO.getParamList().add(vo);
						}
					}
				} // 행 for(e)
			} // 파일리스트 for(e)
			
			dashBoardMapper.insertDashBoardAll(paramVO);
			returnMap.put("status", "OK");
			returnMap.put("msg", "등록이 완료되었습니다.");
			
			//-------------------------------------------------- poi이용전 읽기(정상작동함)-------------------------------------------
			//========================================================================================================
//			for (int j = 0; j < fileList.size(); j++) {
//				FileVO fvo = fileList.get(j);
//				String saveName = fvo.getStreFileNm();
//				
//				saveName = WebUtil.filePathReplaceAll(saveName);
//				
//				File file = new File(fvo.getFileStreCours(), saveName);
//				
//				//엑셀파일 워크북 객체 생성(xls)
//				Workbook workbook = Workbook.getWorkbook(file);
//				
//				// 시트 지정
//				Sheet sheet = workbook.getSheet(0);
//
//				int endIdx = sheet.getColumn(1).length-1; // 타이틀 부분빼고 데이터 수
//				
//				// 실제 값이 있는 부분
//				for(int i=1;i<=endIdx;i++){
//					DashBoardVO vo = new DashBoardVO();
//					String name = StringUtil.isNullToString(sheet.getCell(0,i).getContents()); // 이름
//					String prouduct = StringUtil.isNullToString(sheet.getCell(1,i).getContents()); // 상품
//					String counts =  StringUtil.isNullToString(sheet.getCell(2,i).getContents()); // 수량
//					String status =  StringUtil.isNullToString(sheet.getCell(3,i).getContents()); // 상태
//					String ordernumber =  StringUtil.isNullToString(sheet.getCell(4,i).getContents()); // 주문번호
//
//					// 엑셀에 값이 다 있는 경우에만 등록
//					if(!"".equals(name) && !"".equals(prouduct) && !"".equals(counts) && !"".equals(status) && !"".equals(ordernumber)){
//						vo.setUserName(name);
//						vo.setOrderProduct(prouduct);
//						vo.setOrderCnt(Integer.parseInt(counts));
//						vo.setOrderStatus(status);
//						vo.setOrderCode(Integer.parseInt(ordernumber));
//						paramVO.getParamList().add(vo);
//					}else {
//						returnMap.put("status", "FAIL");
//						returnMap.put("msg", "엑셀에 빈값이 존재합니다. 다시 확인해주세요");
//						return returnMap;
//					}
//				}
//			}
//			dashBoardMapper.insertDashBoardAll(paramVO);
//			returnMap.put("status", "OK");
//			returnMap.put("msg", "등록이 완료되었습니다.");
			//========================================================================================================
			//-------------------------------------------------- poi이용전 읽기 -------------------------------------------
			
		} catch (Exception e) {
			// 에러 시 db는 롤백이 될꺼고 + 실제 파일 삭제 확인
			e.printStackTrace();
			returnMap.put("status", "FAIL");
			returnMap.put("msg", "예기치 못한 오류가 발생하여 등록에 실패했습니다.");
		} finally {
			// 업로드하고 오류 여부에 상관없이 실제 파일은 날리기 위해서 finally 이용
			for (int i = 0; i < fileList.size(); i++) {
				File file = new File(fileList.get(i).getFileStreCours()+fileList.get(i).getStreFileNm()); // 첨부파일 경로 + 물리파일명
			
		        if(file.exists()) {
		            file.delete();
		        }
			}
		}
		return returnMap;
	}
}
