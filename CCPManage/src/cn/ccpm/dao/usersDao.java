package cn.ccpm.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.ccpm.beans.users;
import cn.ccpm.utils.JDBCUtils;

public class usersDao implements usersDaoImp{
	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
	public users selectUsersById(String id) throws Exception
	{
	    return (users) queryRunner.query("select * from users where usersname=?", new BeanHandler(users.class),id);
	}
	

}
