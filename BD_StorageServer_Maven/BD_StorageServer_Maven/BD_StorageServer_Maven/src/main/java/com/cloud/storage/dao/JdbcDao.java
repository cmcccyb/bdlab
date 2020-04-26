package com.cloud.storage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * jdbcDao层接口
 * 
 * @author Administrator
 *
 */
public interface JdbcDao {
	public String monitor();

	public void execute(final String sql);

	public int delete(final String sql);

	/**
	 * add the sql
	 * 
	 * @param sql
	 * @return
	 */
	public int add(final String sql);

	/**
	 * update the sql
	 * 
	 * @param sql
	 * @return
	 */
	public int update(final String sql);

	/**
	 * batch execute sql
	 * 
	 * @param sql
	 * @return
	 */
	public int[] batchUpdate(final String sql[]);

	/**
	 * query for list by sql
	 * 
	 * @param sql
	 * @return
	 */
	public List<?> queryForList(String sql);

	/**
	 * query for list by sql and elementType
	 * 
	 * @param <T>
	 * @param sql
	 * @param elementType
	 * @return
	 */
	public <T> List<T> queryForList(String sql, Class<T> elementType);

	/**
	 * query for rowset by sql
	 * 
	 * @param sql
	 * @return
	 */
	public SqlRowSet queryForRowSet(String sql);

	/**
	 * simple query
	 * 
	 * @param sql
	 * @return int value
	 */
	public int queryForInt(String sql);

	/**
	 * simple query
	 * 
	 * @param sql
	 * @return long value
	 */
	public long queryForLong(String sql);

	/**
	 * simple query
	 * 
	 * @param sql
	 * @return map object
	 */
	@SuppressWarnings("rawtypes")
	public Map queryForMap(String sql);

	/**
	 * query for object
	 * 
	 * @param sql
	 * @param requiredType
	 * @return
	 */
	public Object queryForObject(String sql, Class<?> requiredType);

	/**
	 * get count
	 * 
	 * @param sql
	 * @return
	 */
	public int getCount(String sql);

	/**
	 * execute update
	 * 
	 * @param sql
	 * @return true if success
	 */
	public boolean executeUpdate(String sql);

	/**
	 * execute delete
	 * 
	 * @param sql
	 * @return
	 */
	public int executeDelete(String sql);

	/**
	 * get map data with key is column'name value is column's value
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getData(String sql);

	/**
	 * get page data
	 * 
	 * @param sql
	 * 
	 * @param pageno
	 *            page number
	 * @param pagesize
	 *            page size
	 * @return
	 */
	public List<HashMap<String, String>> getScollData(String sql, int pageno, int pagesize);

	/**
	 * get obs data
	 * 
	 * @param appType
	 * @param conceptDescribe
	 * @param startTime
	 * @param endTime
	 * @param isMq
	 * @return
	 */
	public List<HashMap<String, String>> getObsData(String appType, String conceptDescribe, String startTime,
			String endTime, String isMq);

	/**
	 * just for hospital
	 * 
	 * @param sql
	 * @param changeSecond
	 * @return
	 */
	public String getJson(String sql);
}
