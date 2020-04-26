package com.cloud.mina.component.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.cloud.mina.unit_a.sportpackage.PackageData;

/**
 * 解码器的抽象父类
 * 
 * @author changyaobin
 *
 */
public abstract class PacketFilterComponent implements Component {

	public static Logger log = Logger.getLogger(PacketFilterComponent.class);
	public List<Component> list = new ArrayList<Component>();

	public void add(Component t) {
		this.list.add(t);
	}

	public void remove(Component t) {
		this.list.add(t);
	}

	/**
	 * 解析iobuffer中的数据，看起是否符合要求
	 * 
	 * @param buffer
	 * @return
	 */
	public abstract boolean check(IoBuffer buffer);

	// 迭代模式：叠加递归算法进行编码
	public PackageData getDataFromBuffer(IoBuffer buffer) {
		return createTreeData(buffer);
	}

	private PackageData createTreeData(IoBuffer buffer) {
		// 没有子节点，该节点为叶子节点，直接生成data
		if (list.size() == 0) {
			return generateRealPackageData(buffer);
		}
		// 非叶子节点，调用叶子节点的方法生成data
		Iterator<Component> iterator = list.iterator();
		while (iterator.hasNext()) {
			PacketFilterComponent filter = (PacketFilterComponent) iterator.next();
			if (filter.check(buffer)) {
				return filter.getDataFromBuffer(buffer);
			}
		}
		return null;
	}

	public List<Component> getList() {
		return list;
	}

	public void setList(List<Component> list) {
		this.list = list;
	}

}
