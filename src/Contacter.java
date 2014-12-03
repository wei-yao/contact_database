import java.util.ArrayList;


public class Contacter {
	String name;
	String phone;
	ArrayList<String >  groupList;
	ArrayList<Long> groupIdList;
	long id;
	public Contacter(String name,String phone,ArrayList<String >groupList,long id){
		this.name=name;
		this.phone=phone;
		this.groupList=groupList;
		this.id=id;
	}
	public Contacter(String name,String phone,ArrayList<String >groupList,long id,ArrayList<Long> gidList){
		this.name=name;
		this.phone=phone;
		this.groupList=groupList;
		this.id=id;
		this.groupIdList=groupIdList;
		
		
	}
}
