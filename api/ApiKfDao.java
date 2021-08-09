package egovframework.ag.homepage.api.service;

import java.util.List;
import java.util.Map;

import egovframework.ag.common.annotation.KFMapper;
import egovframework.ag.homepage.own.service.FilmVO;
import egovframework.ag.homepage.sample.service.ApiXmlVo;
import egovframework.ag.homepage.sample.service.EducationVo;
import egovframework.ag.homepage.sample.service.ExhibitionVo;

@KFMapper
public interface ApiKfDao {
	public List<Map<String, Object>> selectPublishPageList(Map<String, Object> paramMap) throws Exception;

	public int selectPublishListCount(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectKofaSchedulePageList(Map<String, Object> paramMap) throws Exception;

	public int selectKofaScheduleListCount(Map<String, Object> paramMap) throws Exception;

	public List<ApiXmlVo> selectApiKofaScheduleList(Map<String, Object> paramMap) throws Exception;	
	public List<EducationVo> selectApiEducationList(Map<String,Object> paramMap ) throws Exception;
	public List<ExhibitionVo> selectApiExhibitionList(Map<String,Object> paramMap ) throws Exception;

	public List<EducationVo> selectApiEducationDefault(Map<String, Object> paramMap);
	// 교육일정 데이터 list 갯수를 구합니다.
	public int getDataCountEdu(String eduState);

	public List<ExhibitionVo> selectApiExhibitionDefault(Map<String, Object> paramMap);
	// 전시일정 데이터 list 갯수를 구합니다. 
	public int getDataCountExhib(String exhbnState);


	

}
