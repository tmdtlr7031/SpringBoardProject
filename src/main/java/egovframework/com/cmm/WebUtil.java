package egovframework.com.cmm;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import egovframework.com.cmm.util.StringUtil;

/**
 * 교차접속 스크립트 공격 취약성 방지(파라미터 문자열 교체)
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    	--------    ---------------------------
 *   2011.10.10  한성곤          최초 생성
 *
 * </pre>
 */

public class WebUtil {

	public static String m_FileName = "log";
    private static FileWriter objfile = null;

	public static String clearXSSMinimum(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}

		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		return returnValue;
	}

	public static String clearXSSMaximum(String value) {
		String returnValue = value;
		returnValue = clearXSSMinimum(returnValue);

		returnValue = returnValue.replaceAll("%00", null);

		returnValue = returnValue.replaceAll("%", "&#37;");

		// \\. => .

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\
		returnValue = returnValue.replaceAll("\\./", ""); // ./
		returnValue = returnValue.replaceAll("%2F", "");

		return returnValue;
	}

	public static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}

	/**
	 * 행안부 보안취약점 점검 조치 방안.
	 *
	 * @param value
	 * @return
	 */
	public static String filePathReplaceAll(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\\\", "");
		returnValue = returnValue.replaceAll("\\.\\.", ""); // ..
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}

	public static String filePathWhiteList(String value) {
		return value; // TODO
	}

	 public static boolean isIPAddress(String str) {
		Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

		return ipPattern.matcher(str).matches();
    }

	 public static String removeCRLF(String parameter) {
		 return parameter.replaceAll("\r", "").replaceAll("\n", "");
	 }

	 public static String removeSQLInjectionRisk(String parameter) {
		 return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("%", "").replaceAll(";", "").replaceAll("-", "").replaceAll("\\+", "").replaceAll(",", "");
	 }

	 public static String removeOSCmdRisk(String parameter) {
		 return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll(";", "");
	 }

	 public static String urlEncoding(String value) {
			if (value == null || value.trim().equals("")) {
				return "";
			}

			String returnValue = value;

			returnValue = returnValue.replace("&", "%26");
			returnValue = returnValue.replace("/", "%2F");
			returnValue = returnValue.replace(":", "%3A");
			returnValue = returnValue.replace("?", "%3F");
			returnValue = returnValue.replace("=", "%3D");
			return returnValue;
		}

		public static String urlDecoding(String value) {
			if (value == null || value.trim().equals("")) {
				return "";
			}

			String returnValue = value;

			returnValue = returnValue.replace("%26", "&");
			returnValue = returnValue.replace("%2F", "/");
			returnValue = returnValue.replace("%3A", ":");
			returnValue = returnValue.replace("%3F", "?");
			returnValue = returnValue.replace("%3D", "=");
			return returnValue;
		}

		public static String urlArgumentMake(String value) {
			if (value == null || value.trim().equals("")) {
				return "";
			}

			String returnValue = value;

			if(returnValue.indexOf("?") < 0){
				returnValue = returnValue + "?";
			}else{
				returnValue = returnValue + "&";
			}
			return returnValue;
		}

	/**
     * 전화번호 표시
     * @param regno    전화번호
     * @return
     */
    public static String fnTelNo(String telno) {
		String sValue    = "";
		String pattern     = "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})"; // 핸드폰/일반전화번호
		String pattern15xx = "^(1544|1566|1577|1588|1644|1688)-?([0-9]{4})";                  // 15XX 번호

		if ( telno != null ) {
			if ( "".equals(telno) ) {
				sValue = "";
			} else {
				Pattern telNoPattern = Pattern.compile(pattern);

				Matcher matcher = telNoPattern.matcher(telno);

				if ( matcher.matches() ) {
					sValue = matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
				} else {
					telNoPattern = Pattern.compile(pattern15xx);
					matcher = telNoPattern.matcher(telno);

					if ( matcher.matches() ) {
						sValue = matcher.group(1) + "-" + matcher.group(2);
					} else {
						sValue = telno;
					}
				}
			}
		} else {
			sValue = "";
		}

		return sValue;
	}

    /**
	 * 일자를 YYYYMMDD로 만들기
	 * @param day
	 * @return
	 */
	public static String getDateString(Calendar day) {
		String str = "" + day.get(Calendar.YEAR); // 년;

		int thisMonth = day.get(Calendar.MONTH) + 1; // 월 ( 0 ~ 11 )= 0;

		if ( thisMonth < 10 ) {
			str += "0";
		}

		str += "" + thisMonth ;

		int thisDate = day.get(Calendar.DATE) ; // 월 ( 0 ~ 11 )= 0;

		if ( thisDate < 10 ) {
			str += "0";
		}

		str += "" + thisDate ;

		return str;
	}

    /**
	 * 일자를 yyyy-mm-dd 로 만들기
	 * @param day
	 * @return
	 */
	public static String getMinusDateString(Calendar day) {
		String str = "" + day.get(Calendar.YEAR); // 년;

		str += "-";

		int thisMonth = day.get(Calendar.MONTH) + 1; // 월 ( 0 ~ 11 )= 0;

		if ( thisMonth < 10 ) {
			str += "0";
		}

		str += "" + thisMonth ;

		int thisDate = day.get(Calendar.DATE) ; // 월 ( 0 ~ 11 )= 0;

		str += "-";

		if ( thisDate < 10 ) {
			str += "0";
		}

		str += "" + thisDate ;

		return str;
	}


	/**
	 * 현재일 yyyy-mm-dd 얻기
	 * @return
	 */
	public static String getTodayDateString() {
		// 캘린더 인스턴스 생성
		Calendar day = Calendar.getInstance();

		String str = "" + day.get(Calendar.YEAR); // 년;

		int thisMonth = day.get(Calendar.MONTH) + 1; // 월 ( 0 ~ 11 )= 0;

		if ( thisMonth < 10 ) {
			str += "0";
		}

		str += "-" + thisMonth ;

		int thisDate = day.get(Calendar.DATE) ; // 월 ( 0 ~ 11 )= 0;

		if ( thisDate < 10 ) {
			str += "0";
		}

		str += "-" + thisDate ;

		return str;
	}

	/**
	 * 현재일 얻기
	 * @return
	 */
	public static String geTodayString() {
		// 캘린더 인스턴스 생성
		Calendar day = Calendar.getInstance();

		return getDateString(day);
	}

	/**
	 * 요일명 가져오기
	 * @param day
	 * @return
	 */
	public static String getDayName(int day) {
		String str = "";

		if ( day == 1 ) {
			str = "일";
		} else if ( day == 2 ) {
			str = "월";
		} else if ( day == 3 ) {
			str = "화";
		} else if ( day == 4 ) {
			str = "수";
		} else if ( day == 5 ) {
			str = "목";
		} else if ( day == 6 ) {
			str = "금";
		} else if ( day == 7 ) {
			str = "토";
		}

		return str;
	}

	/**
	 * 월
	 * @param month
	 * @return
	 */
	public static int getMonthNum(int month) {
		return month + 1 ;
	}

	/**
	 * 자리수 맞추기 함수
	 * @param src
	 * @param padChar
	 * @param padLength
	 * @return
	 */
    public static String fnLpad(String src, String padChar, int padLength) {
		String padString = "";
		String sValue    = "";

		int srcLength = src.length();

		sValue = src;

		if ( srcLength >= padLength ) {
			sValue = src;
		} else {
			for ( int i = 0; i < padLength - srcLength; i++ ) {
				padString += padChar;
			}
		}

		sValue = padString + sValue;

		return sValue;
	}
    public static String getDate() {
  	   DecimalFormat df = new DecimalFormat("00");
  	   Calendar calendar = Calendar.getInstance();


  	   String year = Integer.toString(calendar.get(Calendar.YEAR)); //년도를 구한다
  	   String month = df.format(calendar.get(Calendar.MONTH) + 1); //달을 구한다
  	   String day = df.format(calendar.get(Calendar.DATE)); //날짜를 구한다

  	   String hour = ""; //시간을 구한다
  	   if( calendar.get(Calendar.AM_PM) == Calendar.PM){
  	      hour = df.format(calendar.get(Calendar.HOUR)+12); //Calendar.PM이면 12를 더한다
  	   } else {
  	      hour = df.format(calendar.get(Calendar.HOUR));
  	   }

  	   String minute = df.format(calendar.get(Calendar.MINUTE)); //분을 구한다
  	   String second = df.format(calendar.get(Calendar.SECOND)); //초를 구한다
  	   String date = year + month + day + hour + minute + second;

  	   int iDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); //요일을 구한다

  	   String strDayOfWeek = "";
  	   switch(iDayOfWeek){
  	      case 1:
  	         strDayOfWeek = "일요일";
  	         break;
  	      case 2:
  	         strDayOfWeek = "월요일";
  	         break;
  	      case 3:
  	         strDayOfWeek = "화요일";
  	         break;
  	      case 4:
  	         strDayOfWeek = "수요일";
  	         break;
  	      case 5:
  	         strDayOfWeek = "목요일";
  	         break;
  	      case 6:
  	         strDayOfWeek = "금요일";
  	         break;
  	      case 7:
  	         strDayOfWeek = "토요일";
  	         break;

  	      }
  	 //  date = date;// + strDayOfWeek;
  	   return date;
  	}

    /**
     * 시간차이 구하는 함수
     * @param begin
     * @param end
     * @return
     * @throws ParseException 
     */
    public static Integer diffOfDate_Time(String begin, String end) throws ParseException {

	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date beginDate = formatter.parse(begin);
	    Date endDate = formatter.parse(end);

	    long diff = endDate.getTime() - beginDate.getTime();
	    long diffDays = diff / (60 * 60 * 1000);

	    return new Integer((int)(diffDays));
	}

    /**
     * 시간차이 구하는 함수
     * @param begin
     * @param end
     * @return
     * @throws ParseException 
     */
    public static String diffOf_Time(String begin, String end) throws ParseException {

	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date beginDate = formatter.parse(begin);
	    Date endDate = formatter.parse(end);

	    long diff = endDate.getTime() - beginDate.getTime();
	    long diffTime = diff / (60 * 60 * 1000);
	    int diffDate = new Integer((int)(diff / (24 * 60 * 60 * 1000)));
	    int diffTimeMod = new Integer((int)(diffTime))%24;
	    String diffResult = "";

	    if(diffDate < 1){
	    	diffResult = "1";
	    }else{
	    	if(diffTimeMod < 1){
	    		diffResult = String.valueOf(diffDate);
		    }else if(diffTimeMod < 10 && diffTimeMod > 0){
		    	diffResult = String.valueOf(diffDate) +"."+ String.valueOf(diffTimeMod);
		    }else{
		    	diffResult = String.valueOf(diffDate+1);
		    }
	    }
	    return diffResult;
	}

	public static boolean isEmpty(String strData) {
		boolean flag = false;

		if ( strData != null && !"".equals(strData) ) {
			flag = false;
		} else {
			flag = true;
		}

		return flag;
	}

	/**
	 * 복호화 함수
	 * @param paramValue
	 * @param gubun
	 * @return
	 */
	public static String requestReplace (String paramValue, String gubun) {
        String result = "";

        if (paramValue != null) {

        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");

        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
        	}

        	result = paramValue;

        }
        return result;
	}


	/**
	 * nullcheck
	 * @param str, Defaultvalue
	 * @return
	 */

	 public static String nullcheck(String str,  String Defaultvalue )
	 {
	 	 String ReturnDefault = "" ;
	      if (str == null)
	      {
	          ReturnDefault =  Defaultvalue ;
	      }
	      else if (str == "" )
	     {
	          ReturnDefault =  Defaultvalue ;
	      }
	      else
	      {
	   	   		ReturnDefault = str ;
	      }
		   return ReturnDefault ;
	 }

	/**
	 * 자리수 맞추기 함수
	 * @param src
	 * @param padChar
	 * @param padLength
	 * @return
	 */
    public static boolean fnGetHoliday(String sdate, int thisYear, int thisMonth) {
		String padString = String.valueOf(thisYear) + WebUtil.fnLpad (String.valueOf(WebUtil.getMonthNum(thisMonth)), "0", 2);
		boolean sValue    = false;
		if( sdate.indexOf(padString) != -1){
			sValue = true;
		}

		return sValue;
	}
    /**
     * 숫자인지 검사
     *
     * @param String
     * @return
     */
	public static boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String cleanXSS(String s) {
		if (s != null && s.length() > 0) {
			s = s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			s = s.replaceAll("\"", "&quot;").replaceAll("'", "&#39;");
		}
		return s;
	}

	public static String restoreXSS(String s) {
		if (s != null && s.length() > 0) {
			s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
			s = s.replaceAll("&quot;", "\"").replaceAll("&#39;", "'");
		}
		return s;
	}


	/***************************
     * Logging Method          *
     ***************************/
//    public static void TraceLog(String log)
//    {
//        int i                 = 0;
//        String stPath         = "";
//        String stFileName     = "";
//
//        String m_PathName = log_path;
////로그 파일이 저장 될 경로 (요것만 바꿔주면 된답니다.)
//
//        stPath     = m_PathName;
//        stFileName = m_FileName;
//        SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");
//        SimpleDateFormat formatter2 = new SimpleDateFormat ("HH:mm:ss");
//        SimpleDateFormat formatter3 = new SimpleDateFormat ("yyyyMMdd");
//
//        String stDate = formatter1.format(new Date());
//        String stTime = formatter2.format(new Date());
//        String stDateFileNm = formatter3.format(new Date());
//        StringBuffer bufLogPath  = new StringBuffer();
//        String tmp = "";
//		bufLogPath.append(stPath);
//		bufLogPath.append(stFileName);
//		bufLogPath.append("_");
//		bufLogPath.append(stDateFileNm);
//		bufLogPath.append(".log") ;
//
//        StringBuffer bufLogMsg = new StringBuffer();
//        String bufLogMsgTmp = new String();
//		bufLogMsg.append("[");
//		bufLogMsg.append(stDate +" "+ stTime);
//		bufLogMsg.append("]\r\n");
//		bufLogMsg.append(log);
//
//		bufLogMsgTmp = "\r\n["+stDate  +" "+  stTime+"]"+log;
//        try{
//            objfile = new FileWriter(bufLogPath.toString(), true);
//            objfile.write(bufLogMsgTmp.toString());
//            objfile.write("\r\n");
//        }catch(IOException e){
//
//        } finally {
//            try{
//             objfile.close();
//            }catch(Exception e1){}
//        }
//    }

	/**
	 * 해당 파라미터에 욕이 포함되어있는지 검사
	 *
	 * @param String
	 * @return
	 */
	public static boolean isSwearWord(String s) {
			boolean result = false;
			s = StringUtil.isNullToString(s).trim();
			String swearWords = "10새||10새기||10새리||10세리||10쉐이||10쉑||10스||10쌔|| 10쌔기||10쎄||10알||10창||10탱||18것||18넘||18년||18노||18놈||18뇬||18럼||18롬||18새||18새끼||18색||18세끼||18세리||18섹"
				+"||18쉑||18스||18아||ㄱㅐ||ㄲㅏ||ㄲㅑ||ㄲㅣ||ㅅㅂㄹㅁ||ㅅㅐ||ㅆㅂㄹㅁ||ㅆㅍ||ㅆㅣ||ㅆ앙||ㅍㅏ||凸|| 갈보||갈보년||강아지||같은년||같은뇬||개같은||개구라||개년||개놈||개뇬||개대중||개독"
				+"||개돼중||개랄||개보지||개뻥||개뿔||개새||개새기||개새끼||개새키||개색기||개색끼||개색키||개색히||개섀끼||개세||개세끼||개세이||개소리||개쑈||개쇳기||개수작||개쉐||개쉐리||개쉐이||개쉑"
				+"||개쉽||개스끼||개시키||개십새기|| 개십새끼||개쐑||개씹||개아들||개자슥||개자지||개접||개좆||개좌식||개허접||걔새||걔수작||걔시끼||걔시키||걔썌||걸레||게색기||게색끼||광뇬||구녕||구라"
				+"||구멍||그년||그새끼||냄비||놈현||뇬||눈깔||뉘미럴||니귀미||니기미||니미||니미랄||니미럴||니미씹||니아배||니아베||니아비||니어매||니어메||니어미||닝기리||닝기미||대가리||뎡신||도라이"
				+"||돈놈||돌아이||돌은놈||되질래||뒈져||뒈져라||뒈진||뒈진다||뒈질|| 뒤질래||등신||디져라||디진다||디질래||딩시||따식||때놈||또라이||똘아이||똘아이||뙈놈||뙤놈||뙨넘||뙨놈||뚜쟁||띠바"
				+"||띠발||띠불||띠팔||메친넘||메친놈||미췬|| 미췬||미친||미친넘||미친년||미친놈||미친새끼||미친스까이||미틴||미틴넘||미틴년|| 미틴놈||바랄년||병자||뱅마||뱅신||벼엉신||병쉰||병신||부랄"
				+"||부럴||불알||불할||붕가||붙어먹||뷰웅||븅||븅신||빌어먹||빙시||빙신||빠가||빠구리||빠굴||빠큐||뻐큐||뻑큐||뽁큐||상넘이||상놈을||상놈의||상놈이||새갸||새꺄||새끼||새새끼||새키||색끼"
				+"||생쑈||세갸||세꺄||세끼||섹스||쇼하네||쉐||쉐기||쉐끼||쉐리||쉐에기||쉐키||쉑||쉣||쉨||쉬발||쉬밸||쉬벌||쉬뻘||쉬펄||쉽알||스패킹||스팽||시궁창||시끼||시댕||시뎅||시랄||시발||시벌"
				+"||시부랄||시부럴||시부리||시불||시브랄||시팍||시팔||시펄||신발끈||심발끈||심탱||십8||십라||십새||십새끼||십세||십쉐||십쉐이||십스키||십쌔||십창||십탱||싶알||싸가지||싹아지||쌉년||쌍넘"
				+"||쌍년||쌍놈||쌍뇬||쌔끼|| 쌕||쌩쑈||쌴년||썅||썅년||썅놈||썡쇼||써벌||썩을년||썩을놈||쎄꺄||쎄엑|| 쒸벌||쒸뻘||쒸팔||쒸펄||쓰바||쓰박||쓰발||쓰벌||쓰팔||씁새||씁얼||씌파||씨8|| 씨끼"
				+"||씨댕||씨뎅||씨바||씨바랄||씨박||씨발||씨방||씨방새||씨방세||씨밸||씨뱅||씨벌||씨벨||씨봉||씨봉알||씨부랄||씨부럴||씨부렁||씨부리||씨불||씨붕||씨브랄|| 씨빠||씨빨||씨뽀랄||씨앙||씨파"
				+"||씨팍||씨팔||씨펄||씸년||씸뇬||씸새끼||씹같||씹년||씹뇬||씹보지||씹새||씹새기||씹새끼||씹새리||씹세||씹쉐||씹스키||씹쌔||씹이||씹자지||씹질||씹창||씹탱||씹퇭||씹팔||씹할||씹헐||아가리"
				+"||아갈||아갈이||아갈통||아구창||아구통||아굴||얌마||양넘||양년||양놈||엄창||엠병||여물통||염병||엿같||옘병||옘빙||오입||왜년||왜놈||욤병||육갑||은년||을년||이년||이새끼||이새키||이스끼"
				+"||이스키||임마||자슥||잡것||잡넘||잡년||잡놈||저년||저새끼||접년||젖밥||조까||조까치||조낸||조또||조랭||조빠||조쟁이||조지냐||조진다||조찐||조질래||존나||존나게||존니||존만|| 존만한"
				+"||좀물||좁년||좆||좁밥||좃까||좃또||좃만||좃밥||좃이||좃찐||좆같||좆까||좆나||좆또||좆만||좆밥||좆이||좆찐||좇같||좇이||좌식||주글||주글래||주데이||주뎅||주뎅이||주둥아리||주둥이||주접"
				+"||주접떨||죽고잡||죽을래||죽통||쥐랄||쥐롤||쥬디||지랄||지럴||지롤||지미랄||짜식||짜아식||쪼다||쫍빱||찌랄||창녀||캐년||캐놈||캐스끼||캐스키||캐시키||탱구||팔럼||퍽큐||호로||호로놈"
				+"||호로새끼||호로색||호로쉑||호로스까이||호로스키||후라들||후래자식||후레||후뢰||씨ㅋ발||ㅆ1발||씌발||띠발||띄발||뛰발||띠ㅋ발||뉘뮈";
			if(s.matches(swearWords)) {
				result = true;
			}
			return result;
	}
	
}