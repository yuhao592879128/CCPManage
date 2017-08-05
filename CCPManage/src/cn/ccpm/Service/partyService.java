package cn.ccpm.Service;

import java.util.List;

import cn.ccpm.beans.party;
import cn.ccpm.dao.partyDao;
import cn.ccpm.dao.partyDaoImp;
import cn.ccpm.dao.usersDao;
import cn.ccpm.dao.usersDaoImp;

public class partyService {
	public String add(party p) throws Exception
	{
		if(p!=null)
		{
		partyDaoImp pd=new partyDao();
        pd.add(p);
		
        return "success";
		}
		else
		{
			  return "error";
		}
	}
	public List<party> show() throws Exception
	{
		partyDaoImp pd=new partyDao();
		List<party> p=pd.showAllPart();
		return p;
	}
	public void del(String id) throws Exception
	{
		partyDaoImp pd=new partyDao();
		pd.delParty(id);
	}
}
