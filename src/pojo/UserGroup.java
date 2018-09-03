package pojo;
//保存用户的分组及数量
public class UserGroup {
	private String[] userGroup= new String[8];//最多8组
	private int Group_num=0;
	public String[] getUserGroup() {//返回整个分组
		return userGroup;
	}
	public void setUserGroup(String[] userGroup) {
		this.userGroup = userGroup;
	}
	public int getGroup_num() {
		return Group_num;
	}
	public String Getagroup(int i) {
		return userGroup[i];
	}
	public void addGroup(String group){
		userGroup[Group_num++]=group;
	}
}
