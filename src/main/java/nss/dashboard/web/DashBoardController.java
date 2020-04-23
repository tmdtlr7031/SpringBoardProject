package nss.dashboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;


@Controller
public class DashBoardController {
	
	@RequestMapping(value="/dashboard/selectDashBoardList.do")
	public String selectDashBoardList(Model model) {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("LoginVO", loginVO);
		return "/dashboard/selectDashBoardList";
	}
	
}
