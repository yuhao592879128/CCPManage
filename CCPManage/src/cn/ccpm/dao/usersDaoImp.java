package cn.ccpm.dao;

import java.util.List;

import cn.ccpm.beans.users;

public interface usersDaoImp {
	public users selectUsersById(String id) throws Exception;
}
