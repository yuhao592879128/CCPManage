package cn.ccpm.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.ccpm.beans.party;
import cn.ccpm.utils.JDBCUtils;
public class partyDao implements partyDaoImp{
	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
	public void add(party p)throws Exception
	{
			String sql = "insert into party value(null,?,?,?,?,?,?,null)";
			queryRunner.update(sql,p.getName(),p.getIdcard(),p.getAddress(),p.getSalary()
					,p.getRole(),p.getImg());
	}
	public List<party> showAllPart()throws Exception
	{
		String sql = "select * from party";
		List<party> p = queryRunner.query(sql,
				new BeanListHandler<party>(party.class));
		return p;
	}
	public party findPartyById(String id)throws Exception
	{
			String sql = "select * from party where id=?";
	        return 	(party)queryRunner.query(sql,
					new BeanListHandler<party>(party.class));
	}
	 public void delParty(String id)throws Exception
	 {
			String sql = "delete from party where id = ?";
			queryRunner.update(sql, id);
	 }

}
