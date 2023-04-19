package org.fsb.FlixFlow.Controllers;

public class ActorRoleDisplay {
    private String urlImage;
    private String actorName;
    private String roleType;
    private int actorid;
	public ActorRoleDisplay(String urlImage, String actorName, String roleType, int actorid) {
		this.urlImage = urlImage;
		this.actorName = actorName;
		this.roleType = roleType;
		this.actorid = actorid;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public int getActorid() {
		return actorid;
	}
	public void setActorid(int actorid) {
		this.actorid = actorid;
	}
	
	

    

   
}
