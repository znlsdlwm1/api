package egovframework.ag.homepage.api.service;

import java.util.List;
import java.util.Map;

import egovframework.ag.common.annotation.KMDBMapper;

@KMDBMapper
public interface ApiDao {

	public List<Map<String, Object>> selectApiFilePageList(Map<String, Object> paramMap) throws Exception;

	public int selectApiFileListCount(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectApiPageList(Map<String, Object> paramMap) throws Exception;

	public int selectApiListCount(Map<String, Object> paramMap) throws Exception;

	public Map<String, Object> selectApiFileDataInfo(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectOwnDataPageList(Map<String, Object> paramMap) throws Exception;

	public int selectOwnDataListCount(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectDcinemaPageList(Map<String, Object> paramMap) throws Exception;

	public int selectDcinemaListCount(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectMovieInfoPageList(Map<String, Object> paramMap) throws Exception;

	public int selectMovieInfoListCount(Map<String, Object> paramMap) throws Exception;

	public Map<String, Object> selectApiInfo(Map<String, Object> paramMap) throws Exception;

	public Map<String, Object> selectUserApiInfo(Map<String, Object> paramMap) throws Exception;

	public int insertApiApplication(Map<String, String> paramMap) throws Exception;

	public int updateApiReadCount(Map<String, Object> paramMap) throws Exception;

	public int insertApiFileLog(Map<String, Object> paramMap) throws Exception;

	public int insertApiUseLog(Map<String, Object> paramMap) throws Exception;

	public Map<String, Object> selectApiApplicationInfo(Map<String, Object> paramMap) throws Exception;

	public Long selectTodayTrafficCnt(String keycode) throws Exception;

}
