package com.somnus.cloud.provider;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MybatisGenerator
 * @Description: The class Mybatis generator.
 * @author Somnus
 * @date 2018年10月17日
 */
public class MybatisGenerator {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 *
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("/generator/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
}
