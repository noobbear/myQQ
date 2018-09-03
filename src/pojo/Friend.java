package pojo;

public class Friend {
	private int id;//自增id;
	private int myid;//我的id;
	private int fid;//好友id;
	private String inwhichGroup;//好友所在的分组
	public String getInwhichGroup() {
		return inwhichGroup;
	}
	public void setInwhichGroup(String inwhichGroup) {
		this.inwhichGroup = inwhichGroup;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMyid() {
		return myid;
	}
	public void setMyid(int myid) {
		this.myid = myid;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
}
