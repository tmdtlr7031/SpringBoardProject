package nss.dashboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DashBoardController {
	
	@RequestMapping(value="/dashboard/selectDashBoardList.do")
	public String selectDashBoardList(Model model) {
		return "/dashboard/selectDashBoardList";
	}
	
}
