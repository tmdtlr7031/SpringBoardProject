/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package nss.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertVOUtil {

    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertVOUtil.class);

    public static void main(String[] args) throws Exception{

        getVoToInput(new Object());
    }
    /**
     *
     * @param list
     * @return
     */
    public static List<Object> convertVO(Object vo, List<Map<String, Object>> list) throws Exception{

        List<Object> objList = new ArrayList<Object>();

        for(Map<String, Object> map : list){
            objList.add(getHashMapToVo(vo, map ,false));
        }

        return objList;
    }


    public static String getVoToInput(Object setBean) throws Exception{

        String VOstring = "";
        StringBuffer sb = new StringBuffer();

        try {


            VOstring = ToStringBuilder.reflectionToString(setBean, CmsToStringStyle.URL_PARAMETER_STYLE);

            Pattern p = Pattern.compile("\\[.*\\]");
            Matcher m = p.matcher(VOstring);


            String inputs = m.replaceAll("");
            String[] input = inputs.split("abc@=-=@def");

            for(String in : input){

                String[] attrs = in.split("[=]");
                
                if(attrs.length == 2 )
                    sb.append("<input type='hidden' name='").append(attrs[0]).append("' value='").append(attrs[1]).append("' />");
                
            }

            System.out.println(sb.toString());
        } catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getVoToKeyValue(Object setBean) throws Exception{

        String VOstring = "";
        StringBuffer sb = new StringBuffer();

        try {


            VOstring = ToStringBuilder.reflectionToString(setBean, CmsToStringStyle.URL_PARAMETER_STYLE);

            Pattern p = Pattern.compile("\\[.*\\]");
            Matcher m = p.matcher(VOstring);


            String inputs = m.replaceAll("");
            String[] input = inputs.split("abc@=-=@def");
            int i = 0;
            for(String in : input){

                String[] attrs = in.split("[=]");
                if(attrs.length == 2 ){
                	if(i != 0){
                		sb.append(",");
                	}
                	sb.append(attrs[0]+"="+attrs[1]);
                	i ++ ;
                }
            }

            System.out.println(sb.toString());
        } catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return sb.toString();
    }
    
    
    public static Object getHashMapToVo(Object setBean, Map<String, Object> map,  boolean useTrim) throws Exception{

        Method[] methods = null;
        Class<?>[] parameters = null; // 메소드의 파라미터
        Object[] callParameter = null;
        String mName = null; // 메소드이름

        //List를 위해 신규 Object 생성
        Object newObj = Class.forName(setBean.getClass().getName()).newInstance();


        try {
            methods = newObj.getClass().getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                // 메소드 이름을 조회
                mName = methods[i].getName();

                // 메소드 타입 조회
                parameters = methods[i].getParameterTypes();
                // set 메서드인지 확인
                if (mName.indexOf("set") == 0 && mName.length() > 3 && parameters.length == 1) {
                    // 메소드 이름이 set 으로 시작하고 파라미터의 개수가 1개인 경우
                    // 프로퍼티 이름을 구한다.
                    String propertyName = mName.substring(3, 4).toLowerCase() + ((mName.length() > 4) ? mName.substring(4) : "");

                    // 프로퍼티 이름과 동일한 이름의 파라미터값을 구한다.
                    String paramValue = (String) map.get(propertyName);

                    if (paramValue != null && !"".equals(paramValue)) {
                        try {
                            if (parameters[0] == String.class) {
                                callParameter = new Object[]{useTrim ? paramValue.trim() : paramValue};
                            } else if (parameters[0] == int.class) {
                                paramValue = StringUtil.getConvert(paramValue, "0");
                                callParameter = new Object[]{new Integer(paramValue)};
                            } else if (parameters[0] == long.class) {
                                paramValue = StringUtil.getConvert(paramValue, "0");
                                callParameter = new Object[]{new Long((long) NumberUtil.toDouble(paramValue, 0.0))};
                            } else if (parameters[0] == double.class) {
                                paramValue = StringUtil.getConvert(paramValue, "0");
                                callParameter = new Object[]{new Double(paramValue)};
                            } else if (parameters[0] == float.class) {
                                paramValue = StringUtil.getConvert(paramValue, "0");
                                callParameter = new Object[]{new Float(paramValue)};
                            } else if (parameters[0] == Boolean.class) {
                                callParameter = new Object[]{new Boolean(paramValue)};
                            }

                            if (callParameter != null) {
                                // 메소드를 호출한다.
                                methods[i].invoke(newObj, callParameter);
                            }
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage(), e);
                            e.printStackTrace();
                            // 예외가 발생하면 단순히 넘어간다.
                            // 예를 들어, int 타입의 프로퍼티에 'abc'와 같은 문자열을 값으로 사용한 경우는 기본값 그대로 사용한다.
                        }
                    }

                }
            }
        } catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return newObj;
    }
}
