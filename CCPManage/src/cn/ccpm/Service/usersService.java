package cn.ccpm.Service;

import cn.ccpm.beans.users;
import cn.ccpm.dao.usersDao;
import cn.ccpm.dao.usersDaoImp;

public class usersService {
	public String login(users u) throws Exception
	{
		usersDaoImp ud=new usersDao();
		users user=ud.selectUsersById(u.getUsersname());
		if(user==null)
		return "error";
		else
		{
			if(!user.getPassword().equals(u.getPassword()))
			{
				return "error";
			}
		}
		return "success";
	}

}
