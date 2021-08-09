package egovframework.ag.homepage.sample.service;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;

@XmlRootElement(name = "EducationVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class EducationVo {
	private String eduTitle;
	private String State ;
	private String imageURL ;
	private String schedule ;
	private String eduTarget ;
	private String TraineesNum ;
	private String Fee ;
	private String applPeriod ;
	private String eduSDate ;
	private String eduEDate ;
	private String GroupYN ;
	private String URL;
	public EducationVo() {
		
	}
	


	public String getEduTitle() {
		return eduTitle;
	}
	public void setEduTitle(String eduTitle) {
		this.eduTitle = eduTitle;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		this.State = state;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getEduTarget() {
		return eduTarget;
	}
	public void setEduTarget(String eduTarget) {
		this.eduTarget = eduTarget;
	}
	public String getTraineesNum() {
		return TraineesNum;
	}
	public void setTraineesNum(String traineesNum) {
		this.TraineesNum = traineesNum;
	}
	public String getFee() {
		return Fee;
	}
	public void setFee(String fee) {
		this.Fee = fee;
	}
	public String getApplPeriod() {
		return applPeriod;
	}
	public void setApplPeriod(String applPeriod) {
		this.applPeriod = applPeriod;
	}
	public String getEduSDate() {
		return eduSDate;
	}
	public void setEduSDate(String eduSDate) {
		this.eduSDate = eduSDate;
	}
	public String getEduEDate() {
		return eduEDate;
	}
	public void setEduEDate(String eduEDate) {
		this.eduEDate = eduEDate;
	}
	public String getGroupYN() {
		return GroupYN;
	}
	public void setGroupYN(String groupYN) {
		this.GroupYN = groupYN;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		this.URL = uRL;
	}

	
	
	@JsonBackReference
	private List<EducationVo> xmlList;
	public List<EducationVo> getXmlList() {
		return Collections.unmodifiableList(xmlList);
	}
	public void setXmlList(List<EducationVo> resultList) {
		this.xmlList = Collections.unmodifiableList(resultList);
	}
	
	

}
