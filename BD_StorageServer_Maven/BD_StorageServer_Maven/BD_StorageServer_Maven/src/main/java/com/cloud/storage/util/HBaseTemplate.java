package com.cloud.storage.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.storage.base.Domain.HBaseColumn;
import com.cloud.storage.base.Domain.HQuery;

@Repository
public class HBaseTemplate {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private HbaseTemplate htemplate;

	/**
	 * 写数据
	 * 
	 * @param tableName
	 * @param action
	 * @return
	 */
	public Object execute(final HQuery query) {
		if (StringUtils.isBlank(query.getRow()) || query.getColumns().isEmpty()) {
			return null;
		}
		synchronized (Object.class) {
			return htemplate.execute(query.getTable(), new TableCallback<Object>() {
				@SuppressWarnings("deprecation")
				@Override
				public Object doInTable(HTableInterface table) throws Throwable {
					try {
						byte[] rowkey = query.getRow().getBytes();
						Put put = new Put(rowkey);
						if (query.getColumns() != null) {
							Iterator<HBaseColumn> iterator = query.getColumns().iterator();
							while (iterator.hasNext()) {
								HBaseColumn col = iterator.next();
								if (StringUtils.isNotBlank(col.getFamily())
										&& StringUtils.isNotBlank(col.getQualifier())
										&& StringUtils.isNotBlank(col.getValue())) {

									put.add(Bytes.toBytes(col.getFamily()), Bytes.toBytes(col.getQualifier()),
											Bytes.toBytes(col.getValue()));
								}
							}
							table.put(put);
						}
						System.out.println("data into hbase success! ");
					} catch (Exception e) {
						e.printStackTrace();
						logger.warn("==> hbase get object fail> " + query.getRow());
					}
					return null;
				}

			});
		}
	}

	/**
	 * 通过表名和key获取一行数据
	 * 
	 * @param tableName
	 * @param rowName
	 * @return
	 */
	public <T> T get(HQuery query, final Class<T> c) {

		if (StringUtils.isBlank(query.getTable()) || StringUtils.isBlank(query.getRow())) {
			return null;
		}

		return htemplate.get(query.getTable(), query.getRow(), new RowMapper<T>() {
			public T mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				T item = c.newInstance();
				JSONObject obj = new JSONObject();
				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {
						obj.put(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
								cell.getQualifierLength()),
								Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
					}
				} else {
					return null;
				}
				item = JSON.parseObject(obj.toJSONString(), c);
				return item;
			}
		});
	}

	/**
	 * 通过表名 key 和 列族 和列 获取一个数据
	 * 
	 * @param tableName
	 * @param rowName
	 * @param familyName
	 * @param qualifier
	 * @return
	 */
	public String getColumn(HQuery query) {

		if (StringUtils.isBlank(query.getTable()) || StringUtils.isBlank(query.getRow())
				|| StringUtils.isBlank(query.getFamily()) || StringUtils.isBlank(query.getQualifier())) {
			return null;
		}

		return htemplate.get(query.getTable(), query.getRow(), query.getFamily(), query.getQualifier(),
				new RowMapper<String>() {
					public String mapRow(Result result, int rowNum) throws Exception {
						List<Cell> ceList = result.listCells();
						String res = "";
						if (ceList != null && ceList.size() > 0) {
							for (Cell cell : ceList) {
								res = Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
										cell.getValueLength());
							}
						}
						return res;
					}
				});
	}

	/**
	 * 通过表名，开始行键和结束行键获取数据
	 * 
	 * @param HQuery
	 * @return
	 */
	public <T> List<T> find(HQuery query, final Class<T> c) {
		// 如果未设置scan,设置scan
		if (query.getScan() == null) {

			Scan scan = new Scan();
			FilterList localFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

			// 起止搜索
			if (StringUtils.isNotBlank(query.getStartRow()) && StringUtils.isNotBlank(query.getStopRow())) {
				scan.setStartRow(Bytes.toBytes(query.getStartRow()));
				scan.setStartRow(Bytes.toBytes(query.getStopRow()));
			}

			// 列匹配搜索
			if (StringUtils.isNotBlank(query.getFamily()) && StringUtils.isNotBlank(query.getQualifier())
					&& StringUtils.isNotBlank(query.getQualifierValue())) {
				SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(
						Bytes.toBytes(query.getFamily()), Bytes.toBytes(query.getQualifier()), CompareOp.EQUAL,
						Bytes.toBytes(query.getQualifierValue()));
				localFilterList.addFilter(singleColumnValueFilter);
				// query.setSearchEqualFilter(query.getFamily(),query.getQualifier(),query.getQualifierValue());
			}

			// 分页搜索
			if (query.getPageFilter() != null) {
				localFilterList.addFilter(query.getPageFilter());
			}
			scan.setFilter(localFilterList);
			query.setScan(scan);
		}

		// 设置缓存
		query.getScan().setCacheBlocks(false);
		query.getScan().setCaching(2000);

		return htemplate.find(query.getTable(), query.getScan(), new RowMapper<T>() {
			@Override
			public T mapRow(Result result, int rowNum) throws Exception {

				List<Cell> ceList = result.listCells();
				JSONObject obj = new JSONObject();
				T item = c.newInstance();
				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {
						// String row = Bytes.toString(cell.getRowArray(),
						// cell.getRowOffset(), cell.getRowLength());
						// String family = Bytes.toString(cell.getFamilyArray(),
						// cell.getFamilyOffset(),
						// cell.getFamilyLength());

						String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
								cell.getValueLength());

						String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
								cell.getQualifierLength());
						if (value.startsWith("[")) {
							obj.put(quali, JSONArray.parseArray(value));
						} else {
							obj.put(quali, value);
						}
					}
				}
				item = JSON.parseObject(obj.toJSONString(), c);
				return item;
			}

		});
	}

	// public void delete(HQuery query){
	// htemplate.delete(query.getTable(), query.getRow(), query.getFamily());
	// }
}
