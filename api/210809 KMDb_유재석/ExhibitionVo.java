package egovframework.ag.homepage.sample.service;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;

@XmlRootElement(name = "ExhibitionVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExhibitionVo {
	private String exhbnTitle;
	private String exhbnTitleSub ;
	private String exhbnClass ;
	private String exhbnState ;
	private String ImageURL ;
	private String ExhbnPlace ;
	private String exhbnSDate ;
	private String exhbnEDate ;
	private String URL ;
	private String ExhbnDesc;
	
	
	public ExhibitionVo() {
		
	}

	

	
	public String getExhbnTitle() {
		return exhbnTitle;
	}
	public void setExhbnTitle(String exhbnTitle) {
		this.exhbnTitle = exhbnTitle;
	}
	public String getExhbnTitleSub() {
		return exhbnTitleSub;
	}
	public void setExhbnTitleSub(String exhbnTitleSub) {
		this.exhbnTitleSub = exhbnTitleSub;
	}
	public String getExhbnClass() {
		return exhbnClass;
	}
	public void setExhbnClass(String exhbnClass) {
		this.exhbnClass = exhbnClass;
	}
	public String getExhbnState() {
		return exhbnState;
	}
	public void setExhbnState(String exhbnState) {
		this.exhbnState = exhbnState;
	}
	public String getImageURL() {
		return ImageURL;
	}
	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}
	public String getExhbnPlace() {
		return ExhbnPlace;
	}
	public void setExhbnPlace(String exhbnPlace) {
		ExhbnPlace = exhbnPlace;
	}
	public String getExhbnSDate() {
		return exhbnSDate;
	}
	public void setExhbnSDate(String exhbnSDate) {
		this.exhbnSDate = exhbnSDate;
	}
	public String getExhbnEDate() {
		return exhbnEDate;
	}
	public void setExhbnEDate(String exhbnEDate) {
		this.exhbnEDate = exhbnEDate;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getExhbnDesc() {
		return ExhbnDesc;
	}
	public void setExhbnDesc(String exhbnDesc) {
		ExhbnDesc = exhbnDesc;
	}





	@JsonBackReference
	private List<ExhibitionVo> xmlList;
	public List<ExhibitionVo> getXmlList() {
		return Collections.unmodifiableList(xmlList);
	}
	public void setXmlList(List<ExhibitionVo> resultList) {
		this.xmlList = Collections.unmodifiableList(resultList);
	}

	
	

}
