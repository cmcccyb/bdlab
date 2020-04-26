package com.cloud.storage.daoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.cloud.storage.base.Domain.PhysicalCheckData;
import com.cloud.storage.dao.MongodbBaseDao;
import com.cloud.storage.dao.PhysicalCheckMongoDao;
import com.mongodb.BasicDBObject;

/**
 * 体检信息dao层接口实现类（用于操作Mongodb）
 * 
 * @author Administrator
 *
 */
@Repository
public class PhysicalCheckMongoDaoImpl extends MongodbBaseDao<PhysicalCheckData> implements PhysicalCheckMongoDao {

	@Override
	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return PhysicalCheckData.class;
	}

	@Autowired
	@Override
	protected void setMongoTemplate(@Qualifier("mongoTemplate") MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	public void savePhyCheckData(PhysicalCheckData physicalCheckData) {
		super.save(physicalCheckData);
	}

	@Override
	public Map<String, Object> getPhyCheckDataByUserId(Integer userId) {
		// mongodb 管道操作
		List<AggregationOperation> aggOperations = new ArrayList<AggregationOperation>();

		// 管道match过滤
		aggOperations.add(TypedAggregation.match(Criteria.where("patientId").is(userId)));
		// 表关联查询用户的信息
		aggOperations.add(TypedAggregation.lookup("patient", "patientId", "_id", "patientInfo"));
		// 切割lookup后的用户数组信息
		aggOperations.add(TypedAggregation.unwind("patientInfo", true));

		Aggregation agg = Aggregation.newAggregation(aggOperations);
		// 执行管道查询
		AggregationResults<BasicDBObject> aggregate = mongoTemplate.aggregate(agg, PhysicalCheckData.class,
				BasicDBObject.class);
		List<BasicDBObject> mappedResults = aggregate.getMappedResults();
		if (mappedResults != null && mappedResults.size() > 0) {
			return mappedResults.get(0);
		}
		return null;
	}
}
