package com.jt.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
//定义公共的pojo对象
@Data
@Accessors(chain = true)
public class BasePojo implements Serializable{
	private static final long serialVersionUID = 5732336033430986767L;
	private Date created;
	private Date updated;   //列表排序时按修改时间排序，所以在新增时需要设置此值
}
