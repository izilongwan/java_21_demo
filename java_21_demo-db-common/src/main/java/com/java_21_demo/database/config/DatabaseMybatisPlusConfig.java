package com.java_21_demo.database.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.java_21_demo.database.entity.BaseEntity;
import com.java_21_demo.database.util.DatabaseThreadLocalUtil;

@Configuration
public class DatabaseMybatisPlusConfig implements MetaObjectHandler {
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		// 分页插件 (必须指定数据库类型)
		mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		// 乐观锁插件 (对应实体类加 @Version 注解)
		mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

		return mybatisPlusInterceptor;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void insertFill(MetaObject metaObject) {
		HashMap<String, String> map = DatabaseThreadLocalUtil.get(
				DatabaseThreadLocalUtil.SYSTEM_KEY,
				HashMap.class);
		Optional.ofNullable(map)
				.map(m -> m.get(BaseEntity.Fields.createUser))
				.ifPresent(u -> this.strictInsertFill(metaObject, BaseEntity.Fields.createUser, String.class, u));
		Optional.ofNullable(map)
				.map(m -> m.get(BaseEntity.Fields.updateUser))
				.ifPresent(u -> this.strictInsertFill(metaObject, BaseEntity.Fields.updateUser, String.class, u));

		this.strictInsertFill(metaObject, BaseEntity.Fields.createTime, LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, BaseEntity.Fields.updateTime, LocalDateTime.class, LocalDateTime.now());
	}

	@Override
	public void updateFill(MetaObject metaObject) {

		Optional.ofNullable(DatabaseThreadLocalUtil.get(
				DatabaseThreadLocalUtil.SYSTEM_KEY,
				HashMap.class))
				.map(m -> m.get(BaseEntity.Fields.updateUser))
				.map(String::valueOf)
				.ifPresent(u -> this.strictUpdateFill(metaObject, BaseEntity.Fields.updateUser, String.class, u));

		this.strictUpdateFill(metaObject, BaseEntity.Fields.updateTime, LocalDateTime.class, LocalDateTime.now());
	}
}
