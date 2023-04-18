package org.fsb.FlixFlow.Controllers;

public class ActorRoleDisplay {
    private String urlImage;
    private String actorName;
    private String roleType;

    public ActorRoleDisplay(String urlImage, String actorName, String roleType) {
        this.urlImage = urlImage;
        this.actorName = actorName;
        this.roleType = roleType;
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

   
}
