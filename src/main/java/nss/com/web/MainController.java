package nss.com.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
//import four.system.service.MenuVO;
import nss.uat.uia.service.EgovLoginService;

@Controller
public class MainController {

//    /** EgovMessageSource */
//    @Resource(name = "egovMessageSource")
//    EgovMessageSource egovMessageSource;
    
    /** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;

    @RequestMapping(value="/main.do")
    public String comMain(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//    	HttpSession session = request.getSession();
//    	LoginVO loginVO = (LoginVO) session.getAttribute("ADMIN");
//    	System.out.println("roleCode: "+loginVO.getRoleCode());
//    	System.out.println("id: "+loginVO.getUsrId());
    	
//        return "main";

    	// get방식처럼 url뒤에 &로 값이 넘어감
//    	redirectAttributes.addAttribute("menuOrderChk", 1002);
//    	redirectAttributes.addAttribute("prtMenuSeqChk", 1);
    	
    	return "redirect:/dashboard/selectDashBoardList.do";
    }

    @RequestMapping("/left.do")
    public String left(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String, Object> reqMap) throws IOException {
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        model.addAttribute("LoginVO",loginVO);
        
//        List<MenuVO> menues = loginService.selectAdminAuthMenuList(loginVO.getRoleCode());
//        model.addAttribute("menues", menues);
        model.addAttribute("currentMenuOdr", reqMap.get("menuOrderChk"));
        
        return "include/left";
    }
    
    @RequestMapping("/menuTopBar.do")
    public String menuTopBar(ModelMap model, HttpServletRequest request) {
        LoginVO loginVO = new LoginVO();
        loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        
//        loginVO = loginService.selectLastLoginTime(loginVO.getUsrId());
//        model.addAttribute("LoginVO",loginVO);
        
        return "include/menuTopBar";
    }

    @RequestMapping(value = "/authDeny.do")
	public String authDeny(ModelMap model) {
		
		model.addAttribute("message", "권한이 없습니다. 관리자에게 문의해 주세요.");
		model.addAttribute("function", " $(document).ready(function(){ history.back(-1); }); ");

		return "process";
	}
    
    @RequestMapping(value = "/accessDeny.do")
	public String accessDeny() {
		
		return "egovframework/error/accessDeny";
	}
    
    @RequestMapping(value = "/innerAccessDeny.do")
	public String innerAccessDeny() {
		
		return "egovframework/error/innerAccessDeny";
	}

    @RequestMapping(value = "/keepSession.do")
	public void sessionContinue(HttpServletRequest request){
        request.getSession(true);

    }
}