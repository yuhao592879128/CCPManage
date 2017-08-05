package cn.ccpm.dao;


import java.util.List;

import cn.ccpm.beans.party;

public interface partyDaoImp {
	public void add(party p)throws Exception;
	public List<party> showAllPart()throws Exception;
    public void delParty(String id)throws Exception;

}
