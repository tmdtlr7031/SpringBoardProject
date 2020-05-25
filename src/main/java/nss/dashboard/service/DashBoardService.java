package nss.dashboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface DashBoardService {
	
	public List<DashBoardVO> selectTestList(DashBoardVO dashBoardVO);

	public int selectTestListCnt(DashBoardVO dashBoardVO);
	
	public Map<String, Object> insertDashBoardExcelUpload(Map<String, MultipartFile> files);

}
