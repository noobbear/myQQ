package pojo;
//�����û��ķ��鼰����
public class UserGroup {
	private String[] userGroup= new String[8];//���8��
	private int Group_num=0;
	public String[] getUserGroup() {//������������
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
