package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import frontcontroller.CommonInterface;
import model.UnstoringService;
import vo.UnstoringVO;

public class UnstoringTrackingNumberInputController implements CommonInterface {

	@Override
	public String execute(Map<String, Object> data) throws Exception {
		// org.json.simple.* 라이브러리를 사용하는 경우
		
		HttpServletRequest request = (HttpServletRequest) data.get("request");
		UnstoringService service = new UnstoringService();
		
		/**** JSON 받는 방법 ****/
	    // 1. request로 문자열형식의 데이터를 받고 (★★★왜냐면 String으로 찍히더라고 JS 단에서)
		String jsonTrkNum = request.getParameter("jsonTrkNum");
		System.out.println("송장번호 버튼을 눌렀습니다.");
		System.out.println("jsonTrkNum : " + jsonTrkNum);
		
		// 2. 파싱 : String으로 온 놈을 파싱해서 JSON 객체에 담아주고 
	    JSONParser parser = new JSONParser();
	    JSONObject obj1 = (JSONObject) parser.parse(jsonTrkNum); 
		System.out.println("파싱한 obj1 : " + obj1);
		
		// 3. key를 줘서 { }를 먼저 한번 벗기고, JSONArray에 담아 (왜냐면 배열 형태니까) (★근데 그냥 배열에 담으면 에러 나더라고)
		JSONArray arr =  (JSONArray) obj1.get("key");
		System.out.println("arr : " + arr);
		
		// 4. 벗긴 그 JSONArray를 loop 
		List<UnstoringVO> listVO = new ArrayList<>();
		for(int i=0; i<arr.size(); i++) {
			// 각각이 {"number" : 1} , {"number" : 2} 이런 식으로 되고
			// 다시 JSON오브젝트 타입에 담고
			JSONObject obj2 = (JSONObject) arr.get(i); 
			System.out.println("obj2 : " + obj2);
			
			// 5. 마지막으로 키:값 형태로 값을 얻어내면 되나..?
			String unstoring_code = String.valueOf(obj2.get("number"));
			System.out.println(unstoring_code);
			
			// 6. code 수만큼 VO를 new해서 값을 세팅해주고
			UnstoringVO vo = new UnstoringVO();
			vo.setUnstoring_code(unstoring_code);
			
			// 7. List에 담아 (왜냐면 List 형태로 DAO에 보내야 하거든)
			listVO.add(vo);
		}
//		송장입력 로직 처리하게끔
//		service.trackingNumberInput(listVO);
	    
	    
	    String page = "redirect:/unstoring/unstoringList.do"; 
	    return page;
	}
	

}

