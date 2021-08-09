package egovframework.ag.homepage.api.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.ag.common.security.MemberDetails;
import egovframework.ag.common.util.DownloadVo;
import egovframework.ag.common.util.StringUtils;
import egovframework.ag.common.vo.PageListVo;
import egovframework.ag.common.web.ParamMap;
import egovframework.ag.homepage.api.service.ApiService;
import egovframework.ag.homepage.own.service.FilmVO;
import egovframework.ag.homepage.sample.service.ApiXmlVo;
import egovframework.ag.homepage.sample.service.EducationVo;
import egovframework.ag.homepage.sample.service.ExhibitionVo;
import egovframework.ag.homepage.sample.service.SampleXmlVo;

@Controller
@RequestMapping("/info/api")
public class ApiController {

	@Autowired
	private ApiService apiService;

	/**
	 * 리스트 메인
	 * - 리스트  메인
	 *
	 *
	 * @return
	 */
	@RequestMapping("/guide")
	public String mainView(Model model, HttpServletResponse res,ParamMap paramMap) throws Exception {

		model.addAttribute("paramMap", paramMap.getMap());
		return "/api/guide01";
	}

	@RequestMapping("/guide2")
	public String infoView2(Model model, HttpServletResponse res,ParamMap paramMap) throws Exception {

		model.addAttribute("paramMap", paramMap.getMap());
		return "/api/guide02";
	}

	@RequestMapping("/guide3")
	public String infoView3(Model model, HttpServletResponse res,ParamMap paramMap) throws Exception {

		model.addAttribute("paramMap", paramMap.getMap());
		return "/api/guide03";
	}


	/**
	 * API 파일데이터 리스트
	 *
	 *
	 * @return
	 */
	/*스키마 : KMDB 테이블 : TB_API  xml : apiMapper.xml */ 
	//
	@RequestMapping("/filedataList")
	public String selectApiFileListInfo(ParamMap paramMap, Model model) throws Exception {
		String pageSize = StringUtils.trimToEmpty(paramMap.getString("_pageSize"));
		if("".equals(pageSize)||"10".equals(pageSize)){
			paramMap.put("_pageSize", "10");
		}

		PageListVo<Map<String, Object>> pageListVo = apiService.selectApiFileListInfo(paramMap.getMap());

		int totalpage = (int) Math.ceil(pageListVo.getTotalCount()/ (double)10 );

        model.addAttribute("paramMap", paramMap.getMap());
        model.addAttribute("totalpage", totalpage);
        model.addAttribute("paging", paramMap.getPagingTagString(pageListVo.getTotalCount()));
        model.addAttribute("pageListVo", pageListVo);

		return "/api/fileDataList";
	}


	/**
	 * 영화기본정보  API  상세
	 **/
	/* 스키마: KOREAFILM 테이블 : TB_PROGRAM_MOVIE_CALENDAR(조인함) xml:apiKfMapper  */
	@RequestMapping("/filedataDetail/{apiSeq}")
	public String selectApiFileDataInfo(@PathVariable String apiSeq, ParamMap paramMap, Model model) throws Exception {

		paramMap.put("apiSeq", apiSeq);
        model.addAttribute("detailInfo", apiService.selectApiFileDataInfo(paramMap.getMap())); //API 파일데이터 정보

        // pageSize => 1페이지당 10개 데이터 뿌려주기 
        String pageSize = StringUtils.trimToEmpty(paramMap.getString("_pageSize"));
		if("".equals(pageSize)||"10".equals(pageSize)){
			paramMap.put("_pageSize", "10");
		}
		/*API_SEQ 
		 *  참조 : TB_API 
		 *  컬럼 : API_SEQ
		 *  
		 *  1 => 영화기본정보
		 *  2 => 한국영상자료원 소장자료목록DB
		 *  3 => 시네마테크KOFA 상영일정 
		 *  4 => 영상자료원 발간물 목록
		 *  5 => 디지털시네마파일정보 
		 *  6 => 영화상세정보 
		 *  
		 *  추가 항목 : 7 => 교육일정 정보 list 
		 *  추가 항목 : 8 => 전시일정 정보 list 
		 *  
		 *  
		 */
		String returnUrl = "";
		int totalpage = 0;
		PageListVo<Map<String, Object>> pageListVo = null;

		//PageListVo<Map<String, Object>> pageListVo = apiService.selectMovieDetailList(paramMap.getMap());
		
		if("1".equals(apiSeq) == true ){
		// selectMovieInfoList => 영화기본정보 목록리스트 조회
			pageListVo = apiService.selectMovieInfoList(paramMap.getMap());
			totalpage = (int) Math.ceil(pageListVo.getTotalCount()/ (double)10 );
			returnUrl = "/api/movieInfo";
		
		}else if("2".equals(apiSeq) == true){
		// selectOwnDataList => 소장자료 리스트 조회
			pageListVo = apiService.selectOwnDataList(paramMap.getMap());
			totalpage = (int) Math.ceil(pageListVo.getTotalCount()/ (double)10 );
			returnUrl = "/api/ownDataDetail";
		
		}else if("3".equals(apiSeq) == true){
		// selectKofaScheduleList => kofa상영일정 리스트 조회
			pageListVo = apiService.selectKofaScheduleList(paramMap.getMap());
			totalpage = (int) Math.ceil(pageListVo.getTotalCount()/ (double)10 );
			returnUrl = "/api/kofaScheduleDetail";
		
		}else if("4".equals(apiSeq) == true){
		// selectPublishList => 발간물 목록리스트 조회
			pageListVo = apiService.selectPublishList(paramMap.getMap());
			totalpage = (int) Math.ceil(pageListVo.getTotalCount()/ (double)10 );
			returnUrl = "/api/publishDataDetail";
	
		}else if("5".equals(apiSeq) == true){
		// selectDcinemaList => Dcinema 리스트 조회
			pageListVo = apiService.selectDcinemaList(paramMap.getMap());
			totalpage = (int) Math.ceil(pageListVo.getTotalCount()/ (double)10 );
			returnUrl = "/api/dcinemaDataDetail";
		}
		// API 조회수 증가 
		apiService.updateApiReadCount(paramMap.getMap());
		
        model.addAttribute("paramMap", paramMap.getMap());
        model.addAttribute("totalpage", totalpage);

		model.addAttribute("paging", paramMap.getPagingTagString(pageListVo.getTotalCount()));
	
        model.addAttribute("pageListVo", pageListVo);
       

		return returnUrl;
	}

	//dataList 를 excelFile로 다운로드합니다. 
	@RequestMapping(value="/excel/{apiSeq}")
	public String downApiExcelInfo(@PathVariable String apiSeq, ParamMap paramMap, Model model, HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception {

		String pageSize = StringUtils.trimToEmpty(paramMap.getString("_pageSize"));
		if("".equals(pageSize)||"10".equals(pageSize)){
			paramMap.put("_pageSize", "100000");
		}
		paramMap.put("apiSeq", apiSeq);
	    paramMap.put("userId", "");
	    //auth 체크 , null 이 아닌경우 getUserid , getName 후 paramMap 에 put 
		if(auth != null && auth.getPrincipal() instanceof MemberDetails){
			MemberDetails userDetails = (MemberDetails) auth.getPrincipal();
			paramMap.put("userId", userDetails.getUserid());
			paramMap.put("userName", userDetails.getName());
		}

		String status = "F";

		PageListVo<Map<String, Object>> pageListVo = null;
		String headerNames[] = null;
		String headerKeys[] = null;
		if("1".equals(apiSeq) == true ){
			pageListVo = apiService.selectMovieInfoList(paramMap.getMap());
			headerNames = new String[]{"영화등록번호ID","영화등록번호NO","영화명","영문제명","원제명","유형","용도","장르","제작국가","제작년도","제작사","감독","각본","출연","독립영화여부","외부코드값","영화심의여부","대표영화심의일","대표영화심의번호","대표영화관람등급","대표개봉일","대표상영시간","키워드","줄거리","KMDBURL","최초등록일","최종수정일"};
			headerKeys = new String[]{"movieId","movieSeq","title","titleEng","orgTit","pattenNm","mediumNm","typeClss","nationClss","prodYear","compyClss","director","casts","scenario","casts","indieYn","extCode","filmcnsClss","cnsDate","cnsNo","levelNm","releaseDate","runtime","keywords","plot","KMDburl","createDate","adjustDate"};
		}else if("2".equals(apiSeq) == true){
			pageListVo = apiService.selectOwnDataList(paramMap.getMap());
			headerNames = new String[]{"자료구분","세부구분","자료관리번호","제목","감독저자","제작국가발행국가","제작년도발행년도","제작사출판사","매체","언어","상영시간","소장위치","KMDBURL","영화심의여부","대표개봉일","독립영화여부"};
			headerKeys = new String[]{"dataType","detailType","mNo","title","director","nationNm","prodYear","compyNm","media","langNm","runtime","location","kmdburl","filmcnsClss","releaseDate","indieYn"};
		}else if("3".equals(apiSeq) == true){
			pageListVo = apiService.selectKofaScheduleList(paramMap.getMap());
			headerNames = new String[]{"프로그램ID","프로그램명","프로그램시작일","프로그램종료일","영화ID","영화제목","영화제목영문","감독","제작년도","출연","러닝타임","상영매체","관람등급","줄거리","KMDB연결정보1","KMDB연결정보2","상영일","상영시간","상영장소","GV여부","강연여부"};
			headerKeys = new String[]{"cProgramId","cProgramName","cProgramStartdate","cProgramEnddate","cMovieId","cMovieName","cMovieNameEn","cDirector","cProductionYear","cActors","cRunningTime","cCodeSubName","cCodeSubName2","cSynopsys","cKmdbNation","cKmdbDataid","cMovieDate","cMovieTime","cCodeSubName3","cIsGv","cIsLecture"};
		}else if("4".equals(apiSeq) == true){
			pageListVo = apiService.selectPublishList(paramMap.getMap());
			headerNames = new String[]{"종류","발간물명","발간물구분","발간자","발행년도","소개","차례정보"};
			headerKeys = new String[]{"pKind","title","cSub","publisher","cYear","introduce","cIndex"};
		}else if("5".equals(apiSeq) == true){
			pageListVo = apiService.selectDcinemaList(paramMap.getMap());
			headerNames = new String[]{"DCINEMA_NO","유형","영화명","감독","제작사","제작년도","제작국가","영화심의여부","대표개봉일","독립영화여부","파일종류","파일형식","화면비","프레임레이트","컬러모드"};
			headerKeys = new String[]{"dcinemaNo","pattenNm","title","director","compyClss","prodYear","nationClss","filmcnsClssNm","releaseDate","indieYn","formClssNm","formatClssNm","pictureRatioNm","frameRateNm","colorModeNm"};
		}

		DownloadVo dVo = new DownloadVo();
		dVo.setFileName("kmdb_excel.xlsx");
		dVo.setHeaderNames(headerNames);
		dVo.setHeaderKeys(headerKeys);
		dVo.setDatas(pageListVo.getResultList());

		try {
			model.addAttribute("downloadFile", dVo);
			status = "S";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		paramMap.put("useType", "DOWN");
		paramMap.put("status", status);
		paramMap.put("filedownType", "SHEET");
		apiService.insertApiFileLog(paramMap.getMap());

		return "apiview";
	}
	//dataList 를 csv 파일로 다운로드합니다. 
	@RequestMapping(value="/csv/{apiSeq}")
	public String downApiCsvInfo(@PathVariable String apiSeq,ParamMap paramMap, Model model, HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception {

		PageListVo<Map<String, Object>> pageListVo = null;

		String pageSize = StringUtils.trimToEmpty(paramMap.getString("_pageSize"));
		if("".equals(pageSize)||"10".equals(pageSize)){
			paramMap.put("_pageSize", "100000");
		}
		paramMap.put("apiSeq", apiSeq);
	    paramMap.put("userId", "");
		if(auth != null && auth.getPrincipal() instanceof MemberDetails){
			MemberDetails userDetails = (MemberDetails) auth.getPrincipal();
			paramMap.put("userId", userDetails.getUserid());
			paramMap.put("userName", userDetails.getName());
		}

		String status = "F";

		String headerNames[] = null;
		String headerKeys[] = null;
		if("1".equals(apiSeq) == true ){
			pageListVo = apiService.selectMovieInfoList(paramMap.getMap());
			headerNames = new String[]{"영화등록번호ID","영화등록번호NO","영화명","영문제명","원제명","유형","용도","장르","제작국가","제작년도","제작사","감독","각본","출연","영화심의여부","대표영화심의일","대표영화심의번호","대표영화관람등급","대표개봉일","대표상영시간","키워드","줄거리","KMDBURL","최초등록일","최종수정일"};
			headerKeys = new String[]{"movieId","movieSeq","title","titleEng","orgTit","pattenNm","mediumNm","typeClss","nationClss","prodYear","compyClss","director","casts","scenario","casts","filmcnsClss","cnsDate","cnsNo","levelNm","releaseDate","runtime","keywords","plot","KMDburl","createDate","adjustDate"};
		}else if("2".equals(apiSeq) == true){
			pageListVo = apiService.selectOwnDataList(paramMap.getMap());
			headerNames = new String[]{"자료구분","세부구분","자료관리번호","제목","감독저자","제작국가발행국가","제작년도발행년도","제작사출판사","매체","언어","상영시간","소장위치","KMDBURL"};
			headerKeys = new String[]{"dataType","detailType","mNo","title","director","nationNm","prodYear","compyNm","media","langNm","runtime","location","kmdburl"};
		}else if("3".equals(apiSeq) == true){
			pageListVo = apiService.selectKofaScheduleList(paramMap.getMap());
			headerNames = new String[]{"프로그램ID","프로그램명","프로그램시작일","프로그램종료일","영화ID","영화제목","감독","제작년도","출연","러닝타임","상영매체","관람등급","줄거리","KMDB연결정보1","KMDB연결정보2","상영일","상영시간","상영장소","GV여부","강연여부"};
			headerKeys = new String[]{"cProgramId","cProgramName","cProgramStartdate","cProgramEnddate","cMovieId","cMovieName","cDirector","cProductionYear","cActors","cRunningTime","cCodeSubName","cCodeSubName2","cSynopsys","cKmdbNation","cKmdbDataid","cMovieDate","cMovieTime","cCodeSubName3","cIsGv","cIsLecture"};
		}else if("4".equals(apiSeq) == true){
			pageListVo = apiService.selectPublishList(paramMap.getMap());
			headerNames = new String[]{"종류","발간물명","발간물구분","발간자","발행년도","소개","차례정보"};
			headerKeys = new String[]{"pKind","title","cSub","publisher","cYear","introduce","cIndex"};
		}else if("5".equals(apiSeq) == true){
			pageListVo = apiService.selectDcinemaList(paramMap.getMap());
			headerNames = new String[]{"DCINEMA_NO","유형","영화명","감독","제작사","제작년도","제작국가","영화심의여부","파일종류","파일형식","화면비","프레임레이트","컬러모드"};
			headerKeys = new String[]{"dcinemaNo","pattenNm","title","director","compyClss","prodYear","nationClss","filmcnsClssNm","formClssNm","formatClssNm","pictureRatioNm","frameRateNm","colorModeNm"};
		}

		DownloadVo dVo = new DownloadVo();
		dVo.setFileName("kmdb_csv.csv");
		dVo.setHeaderNames(headerNames);
		dVo.setHeaderKeys(headerKeys);
		dVo.setDatas(pageListVo.getResultList());

		try {
			model.addAttribute("downloadFile", dVo);
			status = "S";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		paramMap.put("useType", "DOWN");
		paramMap.put("status", status);
		paramMap.put("filedownType", "CSV");
		apiService.insertApiFileLog(paramMap.getMap());

		model.addAttribute("downloadFile", dVo);

		return "apiview";
	}
	//dataList 를 json 형태로 다운로드합니다
	@RequestMapping(value="/json/{apiSeq}")
	public String downApiJsonInfo(@PathVariable String apiSeq,ParamMap paramMap, Model model, HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception {

		String pageSize = StringUtils.trimToEmpty(paramMap.getString("_pageSize"));
		if("".equals(pageSize)||"10".equals(pageSize)){
			paramMap.put("_pageSize", "100000");
		}
		paramMap.put("apiSeq", apiSeq);
	    paramMap.put("userId", "");
		if(auth != null && auth.getPrincipal() instanceof MemberDetails){
			MemberDetails userDetails = (MemberDetails) auth.getPrincipal();
			paramMap.put("userId", userDetails.getUserid());
			paramMap.put("userName", userDetails.getName());
		}

		String status = "F";


		PageListVo<Map<String, Object>> pageListVo = null;
		String headerNames[] = null;
		String headerKeys[] = null;
		if("1".equals(apiSeq) == true ){
			pageListVo = apiService.selectMovieInfoList(paramMap.getMap());
			headerNames = new String[]{"영화등록번호ID","영화등록번호NO","영화명","영문제명","원제명","유형","용도","장르","제작국가","제작년도","제작사","감독","각본","출연","영화심의여부","대표영화심의일","대표영화심의번호","대표영화관람등급","대표개봉일","대표상영시간","키워드","줄거리","KMDBURL","최초등록일","최종수정일"};
			headerKeys = new String[]{"movieId","movieSeq","title","titleEng","orgTit","pattenNm","mediumNm","typeClss","nationClss","prodYear","compyClss","director","casts","scenario","casts","filmcnsClss","cnsDate","cnsNo","levelNm","releaseDate","runtime","keywords","plot","KMDburl","createDate","adjustDate"};
		}else if("2".equals(apiSeq) == true){
			pageListVo = apiService.selectOwnDataList(paramMap.getMap());
			headerNames = new String[]{"자료구분","세부구분","자료관리번호","제목","감독저자","제작국가발행국가","제작년도발행년도","제작사출판사","매체","언어","상영시간","소장위치","KMDBURL"};
			headerKeys = new String[]{"dataType","detailType","mNo","title","director","nationNm","prodYear","compyNm","media","langNm","runtime","location","kmdburl"};
		}else if("3".equals(apiSeq) == true){
			pageListVo = apiService.selectKofaScheduleList(paramMap.getMap());
			headerNames = new String[]{"프로그램ID","프로그램명","프로그램시작일","프로그램종료일","영화ID","영화제목","영화제목영문","감독","제작년도","출연","러닝타임","상영매체","관람등급","줄거리","KMDB연결정보1","KMDB연결정보2","상영일","상영시간","상영장소","GV여부","강연여부"};
			headerKeys = new String[]{"cProgramId","cProgramName","cProgramStartdate","cProgramEnddate","cMovieId","cMovieName","cMovieNameEng","cDirector","cProductionYear","cActors","cRunningTime","cCodeSubName","cCodeSubName2","cSynopsys","cKmdbNation","cKmdbDataid","cMovieDate","cMovieTime","cCodeSubName3","cIsGv","cIsLecture"};
		}else if("4".equals(apiSeq) == true){
			pageListVo = apiService.selectPublishList(paramMap.getMap());
			headerNames = new String[]{"종류","발간물명","발간물구분","발간자","발행년도","소개","차례정보"};
			headerKeys = new String[]{"pKind","title","cSub","publisher","cYear","introduce","cIndex"};
		}else if("5".equals(apiSeq) == true){
			pageListVo = apiService.selectDcinemaList(paramMap.getMap());
			headerNames = new String[]{"DCINEMA_NO","유형","영화명","감독","제작사","제작년도","제작국가","영화심의여부","파일종류","파일형식","화면비","프레임레이트","컬러모드"};
			headerKeys = new String[]{"dcinemaNo","pattenNm","title","director","compyClss","prodYear","nationClss","filmcnsClssNm","formClssNm","formatClssNm","pictureRatioNm","frameRateNm","colorModeNm"};
		}

		DownloadVo dVo = new DownloadVo();
		dVo.setFileName("kmdb_json.json");
		dVo.setHeaderNames(headerNames);
		dVo.setHeaderKeys(headerKeys);
		dVo.setDatas(pageListVo.getResultList());

		try {
			model.addAttribute("downloadFile", dVo);
			status = "S";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		paramMap.put("useType", "DOWN");
		paramMap.put("status", status);
		paramMap.put("filedownType", "JSON");
		apiService.insertApiFileLog(paramMap.getMap());

		return "apiview";

	}
	

		
		
	
	/*
	 * [2021-08-04] 소명소프트 by 주환 
	 * apiSeq 3 => Kofa상영일정 list 
	 * apiSeq 7 => 교육일정 list 
	 * apiSeq 8 => 전시회 list 를 가져옵니다. 
	 * @Return : jsonView 
	 */
	 @RequestMapping(value="/{apiSeq}/api.json")
	    public String apiJson(@PathVariable String apiSeq,Model model, ParamMap paramMap, @RequestParam Map<String, String> param, HttpServletRequest request, HttpServletResponse res) throws Exception {
		 	String keycode =  StringUtils.getString(param.get("serviceKey"));
	        String resultStr = "";
	        /**
        	 * 설명 : apiSeq3 에 대한 데이터를 가져옵니다.
	         * 예시URL :  http://localhost:8083/info/api/3/api.json?serviceKey=DAJN2SDFM2684DJNSADN231&VAL_001=2017&VAL_002=09&VAL_003=22  
	         * 요청변수1 : val001 => 년도
	         * 요청변수2 : val002 => 월 
	         * 요청변수3 : val003 => 일 
	         * 요청변수4 : apiSeq => api번호
	         * 요청변수5 : serviceKey => api 유효 인증키 
	         * @return : jsonView
        	 */

	        if("3".equals(apiSeq)){
		      // apiSeq3 에 대한 요청변수들입니다. 
//		        String val001 = StringUtils.getString(param.get("VAL_001")); 
//		        String val002 = StringUtils.getString(param.get("VAL_002"));
//		        String val003 = StringUtils.getString(param.get("VAL_003"));
//		        String valDate = val001 + val002+val003;
	        	
	        	// val001 val002 val003을 StartDate로 한번에 날짜 출력
	        	// URL = http://localhost:8083/info/api/3/api.json?serviceKey=DAJN2SDFM2684DJNSADN231&StartDate=20170922
	        	String StartDate = StringUtils.getString(param.get("StartDate"));
	        	paramMap.put("apiSeq", apiSeq);
	  	        paramMap.put("useType", "API"); // useType 은 API 입니다.
	  	        paramMap.put("status", "F"); // api 상태값 (초기에 F 로 설정합니다)
	  	        paramMap.put("keycode", keycode);
	  	        paramMap.put("valDate", StartDate);
	            Map<String, Object> applicationInfoMap = apiService.selectApiApplicationInfo(paramMap.getMap());
	            Long trafficCnt = apiService.selectTodayTrafficCnt(keycode); // 해당 서비스키에 대한 트래픽 사용량 체크 
	            if("2".equals(StringUtils.getString(applicationInfoMap.get("status"))) && "3".equals(StringUtils.getString(applicationInfoMap.get("apiSeq")))  ){
	                if (trafficCnt > StringUtils.getLong(applicationInfoMap.get("trafficCount")) ){
	                    //sxv.setResultMsg("ERR-300");
	                    resultStr = "ERR-300"; //에러코드 -300
	                }else{
	                    paramMap.put("status", "S");
	                    //sxv.setResultMsg("INFO-000");
	                    resultStr = "INFO-000"; //성공코드 -000 
	                    List<ApiXmlVo> resultList= apiService.selectApiKofaScheduleList(paramMap.getMap()); // 리스트
	                    //System.out.println(resultList);
	                    //sxv.setXmlList(resultList);
	                    model.addAttribute("resultMsg", resultStr);
	                    model.addAttribute("resultList", resultList);
	                    paramMap.put("createId", StringUtils.getString(applicationInfoMap.get("createId")));
	    	            paramMap.put("applicationSeq", StringUtils.getLong(applicationInfoMap.get("applicationSeq")));
	    	            apiService.insertApiUseLog(paramMap.getMap());
	                }
	            }
	        }else if("7".equals(apiSeq)){
	        	/**
	        	 * 설명 : apiSeq7 에 대한 데이터를 가져옵니다.
		         * 예시URL : http://localhost:8083/info/api/7/api.json?serviceKey=ZM4589962P941TVOFP04&StartDate=20170812&eduState=04
		         * 요청변수1 : eduSdateY => 년도
		         * 요청변수2 : eduSdateM => 월 
		         * 요청변수3 : eduSdateD => 일 
		         * 요청변수4 : apiSeq => api번호
		         * 요청변수5 : serviceKey => api 유효 인증키 
		         * 요청변수6 : eduState => 상태(모집예정 / 모집중 / 마감임박 / 모집마감 / 교육마감) 
		         * @return : jsonView
	        	 */
	        	
	        	/**
	        	 * eduState 상태값 
	        	 * 01: 마감임박
	        	 * 02:모집중
	        	 * 03:모집예정
	        	 * 04:모집마감
	        	 * 05:교육마감 
	        	 * @return eduState
	        	 */
	        	// 날짜 요청변수는 필수값이 아닙니다. 
		        String eduState = StringUtils.getString(param.get("eduState"));
//		        String eduSdateY = StringUtils.getString(param.get("eduSdateY"));
//		        String eduSdateM = StringUtils.getString(param.get("eduSdateM")); 
//		        String eduSdateD = StringUtils.getString(param.get("eduSdateD"));
//		        String eduDate = eduSdateY+eduSdateM+eduSdateD;
		        
		        // eduSdateY eduSdateM eduSdateD을 StartDate로 한번에 날짜 출력
		        String StartDate = StringUtils.getString(param.get("StartDate"));
		        // 요청변수가 없는경우 (필수값이 아닌 요청변수가 없는경우) 해당 부분이 실행됩니다. 
		        // 예시 url => http://localhost:8083/info/api/7/api.json?serviceKey=ZM4589962P941TVOFP04&PageNum=1&eduState=04
		        if(StartDate.equals("")) {
		        	System.out.println("요청변수가없는 default 로직 실행! ");
		        	int listCount = apiService.getDataCountEdu(eduState); // 해당 조건의 데이터 갯수를 세줍니다. (교육일정 dataList count()) 
		        	System.out.println("dataCount ===> " + listCount);
		        	int PageNum = Integer.parseInt(request.getParameter("PageNum"));
		        	System.out.println("pageNum====> " + PageNum);
		        	//int PageNum = Integer.parseInt(getString(param.get("PageNum")));
		        	//paramMap.put("PageNum",PageNum);
		        	paramMap.put("eduState", eduState);
		        	paramMap.put("apiSeq", apiSeq);
		        	paramMap.put("useType", "API");
		        	paramMap.put("status", "F");
		        	paramMap.put("keycode", keycode);
		            Map<String, Object> applicationInfoMap = apiService.selectApiApplicationInfo(paramMap.getMap());
		            Long trafficCnt = apiService.selectTodayTrafficCnt(keycode);  //해당 서비스키에 대한 api 트래픽 사용량체크 
		            if("2".equals(StringUtils.getString(applicationInfoMap.get("status"))) && "7".equals(StringUtils.getString(applicationInfoMap.get("apiSeq")))  ){
		                if (trafficCnt > StringUtils.getLong(applicationInfoMap.get("trafficCount")) ){
		                    //sxv.setResultMsg("ERR-300");
		                    // 트래픽 량 초과! 
		                    resultStr = "ERR-300"; // 결과코드 -300
		                }else{
		                    paramMap.put("status", "S");
		                    resultStr = "INFO-000"; // 결과코드 -100  
		                    double itemPerPage = 10.0; // 페이지당 보여줄 결과 갯수 
		            		int itemFirstNum=((int)itemPerPage)*(PageNum-1)+1;  // 첫 번째 item 번호 (페이지별)
		            		int pageLastNum=(int)Math.ceil(listCount/itemPerPage);  // 마지막 페이지 수
		            		int LastPageItemNum=listCount-(pageLastNum-1)*((int)itemPerPage);  // 마지막 페이지 마지막item수
		            		int itemLastNum=0;
		            		if(PageNum<pageLastNum){
		            			itemLastNum=((int)itemPerPage)*PageNum;
		            		}else {
		            			itemLastNum=LastPageItemNum+(((int)itemPerPage)*(PageNum-1));
		            		}
		            		paramMap.put("itemFirstNum", itemFirstNum);
		            		paramMap.put("itemLastNum", itemLastNum);

		                    List<EducationVo> resultList= apiService.selectApiEducationDefault(paramMap.getMap()); // 교육 리스트 
		                    model.addAttribute("resultMsg", resultStr);
		                    model.addAttribute("resultList", resultList);
		                    paramMap.put("createId", StringUtils.getString(applicationInfoMap.get("createId")));
		    	            paramMap.put("applicationSeq", StringUtils.getLong(applicationInfoMap.get("applicationSeq")));
		    	            apiService.insertApiUseLog(paramMap.getMap());

		                }
		                
		            }
		        	
		        }else {
	        	paramMap.put("apiSeq", apiSeq);
	  	        paramMap.put("useType", "API");
	  	        paramMap.put("status", "F");
	        	paramMap.put("eduState",eduState);
	        	paramMap.put("keycode", keycode);
	        	paramMap.put("eduDate",StartDate);
	            Map<String, Object> applicationInfoMap = apiService.selectApiApplicationInfo(paramMap.getMap());
	            Long trafficCnt = apiService.selectTodayTrafficCnt(keycode);  //해당 서비스키에 대한 api 트래픽 사용량체크 
	            if("2".equals(StringUtils.getString(applicationInfoMap.get("status"))) && "7".equals(StringUtils.getString(applicationInfoMap.get("apiSeq")))  ){
	                if (trafficCnt > StringUtils.getLong(applicationInfoMap.get("trafficCount")) ){
	                    //sxv.setResultMsg("ERR-300");
	                    // 트래픽 량 초과! 
	                    resultStr = "ERR-300"; // 결과코드 -300
	                }else{
	                    paramMap.put("status", "S");
	                    resultStr = "INFO-000"; // 결과코드 -100  
	                    List<EducationVo> resultList= apiService.selectApiEducationList(paramMap.getMap()); // 교육 리스트 
	                    model.addAttribute("resultMsg", resultStr);
	                    model.addAttribute("resultList", resultList);
	                    paramMap.put("createId", StringUtils.getString(applicationInfoMap.get("createId")));
	    	            paramMap.put("applicationSeq", StringUtils.getLong(applicationInfoMap.get("applicationSeq")));
	    	            apiService.insertApiUseLog(paramMap.getMap());

	                }
	            }
	                
	            }
	          
	        }else if("8".equals(apiSeq)) {
	        	/**
	        	 * 설명 : apiSeq8 에 대한 데이터를 가져옵니다.
		         * 예시URL :  http://localhost:8083/info/api/8/api.json?serviceKey=ZM4589962P941T44FP04&StartDate=20160616&exhbnState=1 
		         * 요청변수1 : exhbnSdateY => 년도
		         * 요청변수2 : exhbnSdateM => 월 
		         * 요청변수3 : exhbnSdateD => 일 
		         * 요청변수4 : apiSeq => api번호
		         * 요청변수5 : serviceKey => api 유효 인증키 
		         * 요청변수6 : exhbnState => 상태(ex: 전시중) 
		         * @retrun : jsonView
	        	 */
	        	//apiSeq8 요청변수
		        String exhbnState = StringUtils.getString(param.get("exhbnState"));		
//		        String exhbnSdateY = StringUtils.getString(param.get("exhbnSdateY"));
//		        String exhbnSdateM = StringUtils.getString(param.get("exhbnSdateM")); 
//		        String exhbnSdateD = StringUtils.getString(param.get("exhbnSdateD"));
//		        String exhdnDate = exhbnSdateY+exhbnSdateM+exhbnSdateD;
		        
		        // exhbnSdateY exhbnSdateM exhbnSdateD을 StartDate로 한번에 날짜 출력
		        String StartDate = StringUtils.getString(param.get("StartDate"));
		        // 요청변수 exhbnState 상태값은 텍스트가 아닌 코드값으로 처리합니다.
		        /*
		        if(exhbnState.equals("지난전시")) {
		        	exhbnState = "0";
		        }
		        if(exhbnState.equals("전시중")) {
		        	exhbnState = "1";
		        }
		        if(exhbnState.equals("전시예정")) {
		        	exhbnState = "2";
		        }
		        */
		        // 요청변수가 없는경우 (필수값이 아닌 요청변수가 없는경우) 해당 부분이 실행됩니다. 
		        // 예시 url => http://localhost:8083/info/api/8/api.json?serviceKey=ZM4589962P941T44FP04&PageNum=2&exhbnState=1
		        if(StartDate.equals("")) {
		        	System.out.println("요청변수가없는 default 로직 실행! ");
		        	int listCount = apiService.getDataCountExhib(exhbnState); // 해당 조건의 데이터 갯수를 세줍니다.(전시일정 dataList count() )  
		        	System.out.println("dataCount ===> " + listCount);
		        	int PageNum = Integer.parseInt(request.getParameter("PageNum"));
		        	System.out.println("pageNum====> " + PageNum);
		        	//int PageNum = Integer.parseInt(getString(param.get("PageNum")));
		        	//paramMap.put("PageNum",PageNum);
		        	paramMap.put("exhbnState", exhbnState);
		        	paramMap.put("apiSeq", apiSeq);
		        	paramMap.put("useType", "API");
		        	paramMap.put("status", "F");
		        	paramMap.put("keycode", keycode);
		            Map<String, Object> applicationInfoMap = apiService.selectApiApplicationInfo(paramMap.getMap());
		            Long trafficCnt = apiService.selectTodayTrafficCnt(keycode);  //해당 서비스키에 대한 api 트래픽 사용량체크 
		            if("2".equals(StringUtils.getString(applicationInfoMap.get("status"))) && "8".equals(StringUtils.getString(applicationInfoMap.get("apiSeq")))  ){
		                if (trafficCnt > StringUtils.getLong(applicationInfoMap.get("trafficCount")) ){
		                    //sxv.setResultMsg("ERR-300");
		                    // 트래픽 량 초과! 
		                    resultStr = "ERR-300"; // 결과코드 -300
		                }else{
		                    paramMap.put("status", "S");
		                    resultStr = "INFO-000"; // 결과코드 -100  
		                    double itemPerPage = 10.0; // 페이지당 보여줄 결과 갯수 
		            		int itemFirstNum=((int)itemPerPage)*(PageNum-1)+1;  // 첫 번째 item 번호 (페이지별)
		            		int pageLastNum=(int)Math.ceil(listCount/itemPerPage);  // 마지막 페이지 수
		            		int LastPageItemNum=listCount-(pageLastNum-1)*((int)itemPerPage);  // 마지막 페이지 마지막item수
		            		int itemLastNum=0;
		            		if(PageNum<pageLastNum){
		            			itemLastNum=((int)itemPerPage)*PageNum;
		            		}else {
		            			itemLastNum=LastPageItemNum+(((int)itemPerPage)*(PageNum-1));
		            		}
		            		paramMap.put("itemFirstNum", itemFirstNum);
		            		paramMap.put("itemLastNum", itemLastNum);

		                    List<ExhibitionVo> resultList= apiService.selectApiExhibitionDefault(paramMap.getMap()); // 전시일정 list 
		                    model.addAttribute("resultMsg", resultStr);
		                    model.addAttribute("resultList", resultList);
		                    paramMap.put("createId", StringUtils.getString(applicationInfoMap.get("createId")));
		    	            paramMap.put("applicationSeq", StringUtils.getLong(applicationInfoMap.get("applicationSeq")));
		    	            apiService.insertApiUseLog(paramMap.getMap());

		                }
		                
		            }
		        	
		        }else {
	        	paramMap.put("apiSeq", apiSeq);
	  	        paramMap.put("useType", "API");
	  	        paramMap.put("status", "F");
	        	paramMap.put("exhbnState",exhbnState);
	        	paramMap.put("keycode", keycode);
	        	paramMap.put("exhdnDate",StartDate);
	        	  Map<String, Object> applicationInfoMap = apiService.selectApiApplicationInfo(paramMap.getMap());
		            Long trafficCnt = apiService.selectTodayTrafficCnt(keycode);  //해당 서비스키에 대한 api 트래픽 사용량체크 
		            if("2".equals(StringUtils.getString(applicationInfoMap.get("status"))) && "8".equals(StringUtils.getString(applicationInfoMap.get("apiSeq")))  ){
		                if (trafficCnt > StringUtils.getLong(applicationInfoMap.get("trafficCount")) ){
		                    //sxv.setResultMsg("ERR-300");
		                    // 트래픽 량 초과! 
		                    resultStr = "ERR-300"; // 결과코드 -300
		                }else{
		                    paramMap.put("status", "S");
		                    resultStr = "INFO-000"; // 결과코드 -100  
		                    List<ExhibitionVo> resultList= apiService.selectApiExhibitionList(paramMap.getMap()); // 전시 리스트
		                    model.addAttribute("resultMsg", resultStr);
		                    model.addAttribute("resultList", resultList);
		                    paramMap.put("createId", StringUtils.getString(applicationInfoMap.get("createId")));
		    	            paramMap.put("applicationSeq", StringUtils.getLong(applicationInfoMap.get("applicationSeq")));
		    	            apiService.insertApiUseLog(paramMap.getMap());

		                }
		            }
		                
		            }
	        }

	     
	        return "jsonView";
	    }

	//xml api 제공 주석  	
/*
	@RequestMapping(value="/{apiSeq}/api.xml", produces="application/xml")
	public @ResponseBody ApiXmlVo apiXml(@PathVariable String apiSeq, @RequestParam Map<String, String> param,Model model, HttpServletRequest request, ParamMap paramMap, HttpServletResponse res) throws Exception {

			String keycode =  StringUtils.getString(param.get("serviceKey"));
			//String keycode =  "DAJN2SDFM2684DJNSADN231";
			//String keycode = "ADM3123ASD5125KFM2310DM";

			paramMap.put("apiSeq", apiSeq);
			paramMap.put("useType", "API");
			paramMap.put("status", "F");
			paramMap.put("keycode", keycode);
			
//			2020-01-20 GOWIT 수정.
//		    VAL_001,VAL_002,VAL_003
			String val001 = StringUtils.getString(param.get("VAL_001"));
			String val002 = StringUtils.getString(param.get("VAL_002"));
			String val003 = StringUtils.getString(param.get("VAL_003"));
			String valDate = val001 + val002+val003;
			paramMap.put("valDate",valDate);
			
//			paramMap.put("val001",StringUtils.getString(param.get("val001")));
//			paramMap.put("val002",StringUtils.getString(param.get("val002")));
//			paramMap.put("val003",StringUtils.getString(param.get("val003")));
			ApiXmlVo sxv = new ApiXmlVo();

			if("3".equals(apiSeq)){
				Map<String, Object> applicationInfoMap = apiService.selectApiApplicationInfo(paramMap.getMap());
				Long trafficCnt = apiService.selectTodayTrafficCnt(keycode); //해당 서비스키에 대한 api 트래픽 사용량체크 
				if("2".equals(StringUtils.getString(applicationInfoMap.get("status"))) && "3".equals(StringUtils.getString(applicationInfoMap.get("apiSeq")))   ){
					if (trafficCnt > StringUtils.getLong(applicationInfoMap.get("trafficCount")) ){
						sxv.setResultMsg("ERR-300");
					}else{
						paramMap.put("status", "S");
						sxv.setResultMsg("INFO-000");
						List<ApiXmlVo> resultList= apiService.selectApiKofaScheduleList(paramMap.getMap()); //Film 리스트

						sxv.setXmlList(resultList);

						/*PageListVo<Map<String, Object>> pageListVo = null;

						String pageSize = StringUtils.trimToEmpty(paramMap.getString("_pageSize"));
						if("".equals(pageSize)||"10".equals(pageSize)){
							paramMap.put("_pageSize", "100000");
						}


						pageListVo = apiService.selectKofaScheduleList(paramMap.getMap());
						List<Map<String, Object>> privateList =  pageListVo.getResultList();

					    for (Map<String, Object> privateMap : privateList) {
					    	 sxv.setcProgramId(StringUtils.getString(privateMap.get("cProgramId")));
					    	 sxv.setcProgramName(StringUtils.getString(privateMap.get("cProgramName")));
					    	 sxv.setcProgramStartdate(StringUtils.getString(privateMap.get("cProgramStartdate")));
					    	 sxv.setcProgramEnddate(StringUtils.getString(privateMap.get("cProgramEnddate")));
					    	 sxv.setcMovieId(StringUtils.getString(privateMap.get("cMovieId")));
					    	 sxv.setcMovieName(StringUtils.getString(privateMap.get("cMovieName")));
					    	 sxv.setcDirector(StringUtils.getString(privateMap.get("cDirector")));
					    	 sxv.setcProductionYear(StringUtils.getString(privateMap.get("cProductionYear")));
					    	 sxv.setcActors(StringUtils.getString(privateMap.get("cActors")));
					    	 sxv.setcRunningTime(StringUtils.getString(privateMap.get("cRunningTime")));
					    	 sxv.setcCodeSubName(StringUtils.getString(privateMap.get("cCodeSubName")));
					    	 sxv.setcCodeSubName2(StringUtils.getString(privateMap.get("cCodeSubName2")));
					    	 sxv.setcSynopsys(StringUtils.getString(privateMap.get("cSynopsys")));
					    	 sxv.setcKmdbNation(StringUtils.getString(privateMap.get("cKmdbNation")));
					    	 sxv.setcKmdbDataid(StringUtils.getString(privateMap.get("cKmdbDataid")));
					    	 sxv.setcMovieDate(StringUtils.getString(privateMap.get("cMovieDate")));
					    	 sxv.setcMovieTime(StringUtils.getString(privateMap.get("cMovieTime")));
					    	 sxv.setcCodeSubName3(StringUtils.getString(privateMap.get("cCodeSubName3")));
					    	 sxv.setcIsGv(StringUtils.getString(privateMap.get("cIsGv")));
					    	 sxv.setcIsLecture(StringUtils.getString(privateMap.get("cIsLecture")));
					  
					     }*/
	 /*
					}
				}else{
					sxv.setResultMsg("INFO-100");
				}
				paramMap.put("createId", StringUtils.getString(applicationInfoMap.get("createId")));
				paramMap.put("applicationSeq", StringUtils.getLong(applicationInfoMap.get("applicationSeq")));
				apiService.insertApiUseLog(paramMap.getMap());
			}else{
				sxv.setResultMsg("ERR-300");
			}

		return sxv;
	}

*/

	/**
	 * API 리스트
	 *
	 *
	 * @return
	 */
	//api List 를 select 합니다.
	//table : TB_API 
	//column : API_SEQ , TITLE , DEPT , UPDATE_DATE

	@RequestMapping("/apiList")
	public String selectApiListInfo(ParamMap paramMap, Model model) throws Exception {
		String pageSize = StringUtils.trimToEmpty(paramMap.getString("_pageSize"));
		if("".equals(pageSize)||"10".equals(pageSize)){
			paramMap.put("_pageSize", "10");
		}
		//namespace : id = selectApiPageList / apiMapper.xml  
		PageListVo<Map<String, Object>> pageListVo = apiService.selectApiListInfo(paramMap.getMap());

		int totalpage = (int) Math.ceil(pageListVo.getTotalCount()/ (double)10 );

        model.addAttribute("paramMap", paramMap.getMap());
        model.addAttribute("totalpage", totalpage);
        model.addAttribute("paging", paramMap.getPagingTagString(pageListVo.getTotalCount()));
        model.addAttribute("pageListVo", pageListVo);

		return "/api/apiList";
	}


	/**
	 * API 상세
	 **/
	//api 상세정보를 select 합니다. 
	/*
	 *  EX)
	 *  title : 시네마테크kofa 상영일정
	 * 	DEPT  : 경영기획부 
	 *  PERSON : username
	 *  UPDATE_DATE : 2018-02-06 18:39:03
	 *  NOTE : NULL 
	 *  USE_AUTH : 저작권 출처표시 조건에 따라 이용할수있습니다. 
	 *  OP_CNT : 2
	 *  OP_AUTH_CNT : 0 
	 *  DEV_CNT : 3
	 *  DEV_AUTH_CNT : 0
	 */
	@RequestMapping("/apiDetail/{apiSeq}")
	public String selectApiInfo(@PathVariable String apiSeq, ParamMap paramMap, Model model, HttpServletResponse res, Authentication auth) throws Exception {

		paramMap.put("apiSeq", apiSeq);

        paramMap.put("userId", "");
		if(auth != null && auth.getPrincipal() instanceof MemberDetails){
			MemberDetails userDetails = (MemberDetails) auth.getPrincipal();
			paramMap.put("userId", userDetails.getUserid());
			paramMap.put("userName", userDetails.getName());
		}

		model.addAttribute("paramMap", paramMap.getMap());
		model.addAttribute("detailInfo", apiService.selectUserApiInfo(paramMap.getMap())); //사용자 API 상세정보 조회

		String returnurl = "/api/apiDetail";
		if( "6".equals(apiSeq)){
			returnurl = "/api/apiMovieDetail";
		}
		return returnurl;
	}


	/**
	 * API 인증키 신청
	 **/
	@RequestMapping("/application/{apiSeq}")
	public String selectApiApplication(@PathVariable String apiSeq, ParamMap paramMap, Model model, HttpServletResponse res, Authentication auth) throws Exception {

		paramMap.put("apiSeq", apiSeq);

        paramMap.put("userId", "");
		if(auth != null && auth.getPrincipal() instanceof MemberDetails){
			MemberDetails userDetails = (MemberDetails) auth.getPrincipal();
			paramMap.put("userId", userDetails.getUserid());
			paramMap.put("userName", userDetails.getName());
			paramMap.put("userEmail", userDetails.getMailaddr());
		}

		model.addAttribute("paramMap", paramMap.getMap());
		model.addAttribute("detailInfo", apiService.selectUserApiInfo(paramMap.getMap())); //사용자 API 상세정보 조회

		return "/api/apiApplicationForm";
	}


	/**
	 * API 인증키 신청 등록
	 **/
	@RequestMapping("/application/{apiSeq}/proc")
	@ResponseBody
	public Map<String, String> insertApiApplication(@PathVariable String apiSeq, @RequestParam Map<String, String> param, HttpServletRequest request, Model model, Authentication auth) throws Exception {
		param.put("apiSeq", apiSeq);

		Random rnd =new Random();

		StringBuffer buf =new StringBuffer();

		for(int i=0;i<20;i++){

		    // rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴. true일 시 랜덤 한 소문자를, false 일 시 랜덤 한 숫자를 StringBuffer 에 append 한다.

		    if(rnd.nextBoolean()){

		        buf.append((char)((int)(rnd.nextInt(26))+65));

		    }else{

		        buf.append((rnd.nextInt(10)));

		    }

		}

		param.put("keycode", buf.toString() );


		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", "true");
		try {

			param.put("createIp", request.getRemoteAddr());

    		if(auth != null && auth.getPrincipal() instanceof MemberDetails){
				MemberDetails userDetails = (MemberDetails) auth.getPrincipal();
        		param.put("userId", userDetails.getUserid());
        		param.put("userName", userDetails.getUsername());
        		param.put("userMail", userDetails.getMailaddr());
			}

    		apiService.insertApiApplication(param);
		} catch (Exception e) {
			resultMap.put("result", "false");
		}

		return resultMap;
	}

}