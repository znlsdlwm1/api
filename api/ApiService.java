package egovframework.ag.homepage.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.ag.common.vo.PageListVo;
import egovframework.ag.homepage.own.service.FilmVO;
import egovframework.ag.homepage.sample.service.ApiXmlVo;
import egovframework.ag.homepage.sample.service.EducationVo;
import egovframework.ag.homepage.sample.service.ExhibitionVo;

@Service
public class ApiService {

	@Autowired
	private ApiDao apiDao;

	@Autowired
	private ApiKfDao apiKfDao;




	/**
	 * 파일데이터 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public PageListVo<Map<String,Object>> selectApiFileListInfo(Map<String, Object> paramMap) throws Exception {
		return new PageListVo<Map<String,Object>>(apiDao.selectApiFileListCount(paramMap), apiDao.selectApiFilePageList(paramMap));
	}

	/**
	 * 파일데이터 상세 정보
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectApiFileDataInfo(Map<String, Object> paramMap) throws Exception {
		return  apiDao.selectApiFileDataInfo(paramMap);
	}

	/**
	 * API 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public PageListVo<Map<String,Object>> selectApiListInfo(Map<String, Object> paramMap) throws Exception {
		return new PageListVo<Map<String,Object>>(apiDao.selectApiListCount(paramMap), apiDao.selectApiPageList(paramMap));
	}



	/**
	 * 파일데이터 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
/*	public PageListVo<Map<String,Object>> selectMovieDetailList(Map<String, Object> paramMap) throws Exception {
		return new PageListVo<Map<String,Object>>(apiDao.selectMovieDetailListCount(paramMap), apiDao.selectMovieDetailPageList(paramMap));
	}*/

	/**
	 * 영화기본정보 목록리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public PageListVo<Map<String,Object>> selectMovieInfoList(Map<String, Object> paramMap) throws Exception {
		return new PageListVo<Map<String,Object>>(apiDao.selectMovieInfoListCount(paramMap), apiDao.selectMovieInfoPageList(paramMap));
	}

	/**
	 * 소장자료 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public PageListVo<Map<String,Object>> selectOwnDataList(Map<String, Object> paramMap) throws Exception {
		return new PageListVo<Map<String,Object>>(apiDao.selectOwnDataListCount(paramMap), apiDao.selectOwnDataPageList(paramMap));
	}

	/**
	 * kofa상영일정 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public PageListVo<Map<String,Object>> selectKofaScheduleList(Map<String, Object> paramMap) throws Exception {
		return new PageListVo<Map<String,Object>>(apiKfDao.selectKofaScheduleListCount(paramMap), apiKfDao.selectKofaSchedulePageList(paramMap));
	}

	/**
	 * Dcinema 리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public PageListVo<Map<String,Object>> selectDcinemaList(Map<String, Object> paramMap) throws Exception {
		return new PageListVo<Map<String,Object>>(apiDao.selectDcinemaListCount(paramMap), apiDao.selectDcinemaPageList(paramMap));
	}

	/**
	 * 발간물 목록리스트 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public PageListVo<Map<String,Object>> selectPublishList(Map<String, Object> paramMap) throws Exception {
		return new PageListVo<Map<String,Object>>(apiKfDao.selectPublishListCount(paramMap), apiKfDao.selectPublishPageList(paramMap));
	}


	/**
	 * API 상세 정보
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectApiInfo(Map<String, Object> paramMap) throws Exception {
		return  apiDao.selectApiInfo(paramMap);
	}

	/**
	 * API 사용자신청상태 상세 정보
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectUserApiInfo(Map<String, Object> paramMap) throws Exception {
		return  apiDao.selectUserApiInfo(paramMap);
	}

	public int insertApiApplication(Map<String, String> param) throws Exception{
		return apiDao.insertApiApplication(param);
	}


	/**
	 * API 조회수 증가
	 * @param upStoryDivSeq
	 * @return
	 * @throws Exception
	 */
	public int updateApiReadCount(Map<String, Object> paramMap) throws Exception {
		return apiDao.updateApiReadCount(paramMap);
	}


	/**
	 * API 파일 다운 로그
	 * @param upStoryDivSeq
	 * @return
	 * @throws Exception
	 */
	public int insertApiFileLog(Map<String, Object> paramMap) throws Exception {
		return apiDao.insertApiFileLog(paramMap);
	}


	/**
	 * API 사용 로그
	 * @param upStoryDivSeq
	 * @return
	 * @throws Exception
	 */
	public int insertApiUseLog(Map<String, Object> paramMap) throws Exception {
		return apiDao.insertApiUseLog(paramMap);
	}

	/**
	 * 영화상세정보통계 등록 파일 조회
	 * @param fileSeq
	 * @param fileTypeCd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectApiApplicationInfo(Map<String, Object> paramMap) throws Exception {
		return apiDao.selectApiApplicationInfo(paramMap);
	}


	/**
	 * API 금일 사용 트래픽
	 * @param upStoryDivSeq
	 * @return
	 * @throws Exception
	 */
	public Long selectTodayTrafficCnt(String keycode) throws Exception {
		return apiDao.selectTodayTrafficCnt(keycode);
	}

	/**
	 * KOFA 상영일정 API 목록
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<ApiXmlVo> selectApiKofaScheduleList(Map<String, Object> paramMap) throws Exception {
		return  apiKfDao.selectApiKofaScheduleList(paramMap);
	}
	
	/**
	 * 교육일정 API : 2021-08-02 소명소프트 by 주환
	 * @param paramMap
	 * @method selectApiEducationList()
	 * @return
	 * @throws Exception
	 */
	
	public List<EducationVo> selectApiEducationList(Map<String, Object> paramMap) throws Exception {
		return  apiKfDao.selectApiEducationList(paramMap);
	}
	
	/**
	 * 교육일정 API 요청변수가 없는경우  : 2021-08-02 소명소프트 by 주환
	 * @param paramMap
	 * @method selectApiEducationList()
	 * @return
	 * @throws Exception
	 */
	
	public List<EducationVo> selectApiEducationDefault(Map<String, Object> paramMap) throws Exception {
		return  apiKfDao.selectApiEducationDefault(paramMap);
	}
	/**
	 * 전시일정 API 요청변수가 없는경우  : 2021-08-02 소명소프트 by 주환
	 * @param paramMap
	 * @method selectApiExhibitionList()
	 * @return
	 * @throws Exception
	 */
	public List<ExhibitionVo> selectApiExhibitionDefault(Map<String, Object> paramMap) throws Exception {
		return  apiKfDao.selectApiExhibitionDefault(paramMap);
	}
	
	
	/**
	 * 전시일정 API : 2021-08-02 소명소프트 by 주환
	 * @param paramMap
	 * @method selectApiExhibitionList()
	 * @return
	 * @throws Exception
	 */
	public List<ExhibitionVo> selectApiExhibitionList(Map<String, Object> paramMap) throws Exception {
		return  apiKfDao.selectApiExhibitionList(paramMap);
	}
	/**
	 * 교육일정 DataList 의 총 갯수를 가져옵니다.
	 * @param eduState
	 * @return
	 */
	public int getDataCountEdu(String eduState) {
		
		return apiKfDao.getDataCountEdu(eduState);
	}
	
	/**
	 * 전시일정 DataList 의 총 갯수를 가져옵니다.
	 * @param exhbnState
	 * @return
	 */
	public int getDataCountExhib(String exhbnState) {
		
		return apiKfDao.getDataCountExhib(exhbnState);
	}

	
	
	
}
