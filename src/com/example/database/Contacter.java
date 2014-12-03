package com.example.database;
import java.util.ArrayList;


public class Contacter {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((groupIdList == null) ? 0 : groupIdList.hashCode());
		result = prime * result
				+ ((groupList == null) ? 0 : groupList.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacter other = (Contacter) obj;
		if (groupIdList == null) {
			if (other.groupIdList != null)
				return false;
		} else if (!groupIdList.equals(other.groupIdList))
			return false;
		if (groupList == null) {
			if (other.groupList != null)
				return false;
		} else if (!groupList.equals(other.groupList))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	public String name;
	public String phone;
	public ArrayList<String >  groupList;
	public ArrayList<Long> groupIdList;
	public long id;
	public Contacter(String name,String phone,ArrayList<String >groupList,long id){
		this.name=name;
		this.phone=phone;
		this.groupList=groupList;
		this.id=id;
		this.groupIdList=new ArrayList<Long>();
	}
	public Contacter(String name,String phone,ArrayList<String >groupList,long id,ArrayList<Long> gidList){
		this.name=name;
		this.phone=phone;
		this.groupList=groupList;
		this.id=id;
		this.groupIdList=groupIdList;
		
		
	}
}
