package org.fsb.FlixFlow.Controllers;

public class ActorRoleDisplay {
	private int actorid;
	private String actorName;
	private String roleType;
	private String urlImage;

	public ActorRoleDisplay(String urlImage, String actorName, String roleType, int actorid) {
		this.urlImage = urlImage;
		this.actorName = actorName;
		this.roleType = roleType;
		this.actorid = actorid;
	}

	public int getActorid() {
		return actorid;
	}

	public String getActorName() {
		return actorName;
	}

	public String getRoleType() {
		return roleType;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setActorid(int actorid) {
		this.actorid = actorid;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

}
