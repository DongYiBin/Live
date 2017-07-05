/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.common.page;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import com.mz.live.utils.LogicUtil;
import com.mz.live.utils.StringUtil;



/**
 * 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。 利用拦截器实现Mybatis分页的原理：
 * 要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象
 * ，Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句
 * 是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手
 * 。在Mybatis中Statement语句是通过RoutingStatementHandler对象的
 * prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法
 * ，然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用
 * StatementHandler对象的prepare方法，即调用invocation.proceed()。
 * 对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少
 * ，这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设
 * 置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。
 *
 *
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	private static final Log log = LogFactory.getLog(PageInterceptor.class);

	private static final String DIALECT_MYSQL = "mysql";
	private static final String DIALECT_ORACLE = "oracle";
	private static final String DIALECT_SQLSERVER = "sqlserver";
	private static final String DB_TYPE = "dbType";// 数据库类型，不同的数据库有不同的分页方法

	private String dbType;

	/**
	 * 拦截后要执行的方法
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		// 在RoutingStatementHandler里面所有StatementHandler接口方法的实现都是调用的delegate对应的方法。
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation
				.getTarget();
		// 通过反射获取到当前RoutingStatementHandler对象的delegate属性
		StatementHandler delegate = (StatementHandler) ReflectUtil
				.getFieldValue(handler, "delegate");
		// 获取到当前StatementHandler的
		// boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
		// RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
		BoundSql boundSql = delegate.getBoundSql();
		// 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
		Object obj = boundSql.getParameterObject();
		// 这里我们简单的通过传入的是Page对象就认定它是需要进行分页操作的。
		PageParam<?> page = getPageParam(obj);
		if (page != null) {
			// 通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
			MappedStatement mappedStatement = (MappedStatement) ReflectUtil
					.getFieldValue(delegate, "mappedStatement");
			// 拦截到的prepare方法参数是一个Connection对象
			Connection connection = (Connection) invocation.getArgs()[0];
			// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
			String sql = boundSql.getSql();
			// 给当前的page参数对象设置总记录数
			this.setTotalRow(page, mappedStatement, connection);
			// 获取分页Sql语句
			String pageSql = this.getPageSql(page, sql);
			// 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
			ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
		}

		return invocation.proceed();
	}

	private PageParam<?> getPageParam(Object obj) {
		if (obj instanceof PageParam<?>) {
			return (PageParam<?>) obj;
		}
		if (obj instanceof HashMap) {
			HashMap<?, ?> map = (HashMap<?, ?>) obj;
			for (Entry<?, ?> e : map.entrySet()) {
				if (e.getValue() instanceof PageParam<?>) {
					return (PageParam<?>) e.getValue();
				}
			}
		}

		return null;
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 设置注册拦截器时设定的属性
	 */
	public void setProperties(Properties properties) {
		this.dbType = properties.getProperty(DB_TYPE);
	}

	/**
	 * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
	 * 
	 * @param pageParam
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return
	 */
	private String getPageSql(PageParam<?> pageParam, String sql) {
		if (DIALECT_MYSQL.equalsIgnoreCase(dbType)) {
			return getMysqlPageSql(pageParam, sql);
		} else if (DIALECT_ORACLE.equalsIgnoreCase(dbType)) {
			return getOraclePageSql(pageParam, sql);
		} else if (DIALECT_SQLSERVER.equalsIgnoreCase(dbType)) {
			return getSqlServerPageSql(pageParam, sql);
		}

		return sql;
	}

	/**
	 * 给当前的参数对象page设置总记录数
	 * 
	 * @param page
	 *            Mapper映射语句对应的参数对象
	 * @param mappedStatement
	 *            Mapper映射语句
	 * @param connection
	 *            当前的数据库连接
	 */
	private void setTotalRow(PageParam<?> page,
			MappedStatement mappedStatement, Connection connection) {
		// 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
		// delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		// 获取到我们自己写在Mapper映射语句中对应的Sql语句
		String sql = boundSql.getSql();
		// 通过查询Sql语句获取到对应的计算总记录数的sql语句
		String totalRowSql = this.getTotalRowSql(sql);
		// 通过BoundSql获取对应的参数映射
		List<ParameterMapping> totalRowParams = this.getTotalRowParams(
				boundSql.getParameterMappings(), totalRowSql);
		// 利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
		BoundSql totalBoundSql = new BoundSql(
				mappedStatement.getConfiguration(), totalRowSql,
				totalRowParams, page);
		// 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
		ParameterHandler parameterHandler = new DefaultParameterHandler(
				mappedStatement, page, totalBoundSql);
		// 通过connection建立一个countSql对应的PreparedStatement对象。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(totalRowSql);
			// 通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters(pstmt);
			// 之后就是执行获取总记录数的Sql语句和获取结果了。
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int rowTotal = rs.getInt(1);// 返回总数
				page.setDataTotal(rowTotal);// 设置总记录数
			}
		} catch (SQLException e) {
			log.error("set total row error", e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				log.error("set total row error", e);
			}
		}
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 */
	private String getTotalRowSql(String sql) {
		int f = StringUtil.getFromWord(sql);
		if (f <= 0) {
			return null;
		}
		return "select count(*) " + sql.substring(f);
	}

	private List<ParameterMapping> getTotalRowParams(
			List<ParameterMapping> parameterMappings, String countSql) {
		int size = parameterMappings.size();
		int count = this.getCount(countSql, "?");

		List<ParameterMapping> paramMappings = new ArrayList<ParameterMapping>();
		if (size >= count && count > 0) {
			paramMappings.addAll(parameterMappings.subList(size - count, size));
		}
		return paramMappings;
	}

	private int getCount(String str, String key) {
		if (LogicUtil.isNotNullAndEmpty(str)
				&& LogicUtil.isNotNullAndEmpty(key)) {
			int c = 0;
			int b = -1;
			int i = str.indexOf(key, b);
			while (i > -1) {
				c++;
				b = i + key.length();
				i = str.indexOf(key, b);
			}
			return c;
		}
		return 0;
	}

	/** 获取每页开始行号 */
	private int getStartOfPage(PageParam<?> pageParam) {
		int pageNo = pageParam.getPageNo();
		if (pageNo == 0) {
			pageNo = 1;// 页数是从第一页是从1开始计算的
		}
		return (pageNo - 1) * pageParam.getPageSize();
	}

	/**
	 * 获取Mysql数据库的分页查询语句
	 */
	private String getMysqlPageSql(PageParam<?> pageParam, String sql) {
		int offset = getStartOfPage(pageParam);
		int pageSize = pageParam.getPageSize();

		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		return sql + " limit " + offset + "," + pageSize;
	}

	/**
	 * 获取Oracle数据库的分页查询语句
	 */
	private String getOraclePageSql(PageParam<?> pageParam, String sql) {
		int offset = getStartOfPage(pageParam);
		int endrow = pageParam.getPageNo() * pageParam.getPageSize();

		// 命名为table1、table2是为了避免与入参中的sql 别名冲突
		return "select * from (select rownum rn, t.* from (" + sql
				+ ") table1 where rownum <= " + (endrow)
				+ ") table2 where table2.rn > " + offset;
	}

	/**
	 * 获取SqlServer数据库的分页查询语句
	 */
	private String getSqlServerPageSql(PageParam<?> pageParam, String sql) {
		int offset = getStartOfPage(pageParam);
		int pageSize = pageParam.getPageSize();

		// 命名为table1、table2是为了避免与入参中的sql 别名冲突
		return "select top " + pageSize + " from (" + sql
				+ ") table1 where table1.id not in (select top " + offset
				+ " table2.id from (" + sql + ") table2)";
	}

	/**
	 * 利用反射进行操作的一个工具类
	 */
	private static class ReflectUtil {
		/**
		 * 利用反射获取指定对象的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标属性的值
		 */
		public static Object getFieldValue(Object obj, String fieldName) {
			Field field = getField(obj, fieldName);
			if (field == null) {
				return null;
			}

			Object result = null;
			try {
				field.setAccessible(true);
				result = field.get(obj);
			} catch (IllegalArgumentException e) {
				log.error("illegal argument", e);
			} catch (IllegalAccessException e) {
				log.error("illegal access", e);
			}

			return result;
		}

		/**
		 * 利用反射获取指定对象里面的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标字段
		 */
		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz
					.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException e) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
				}
			}
			return field;
		}

		/**
		 * 利用反射设置指定对象的指定属性为指定的值
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @param fieldValue
		 *            目标值
		 */
		public static void setFieldValue(Object obj, String fieldName,
				String fieldValue) {
			Field field = getField(obj, fieldName);
			if (field == null) {
				return;
			}
			try {
				field.setAccessible(true);
				field.set(obj, fieldValue);
			} catch (IllegalArgumentException e) {
				log.error("illegal argument", e);
			} catch (IllegalAccessException e) {
				log.error("illegal access", e);
			}
		}
	}
}
