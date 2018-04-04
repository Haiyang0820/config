package spring.beanfactory;

import java.util.HashMap;

import spring.bean.BeanHashMap;
//����Ioc��������������map
public class SpringBeanFactory {
//ʵ����Bean map��BeanHashMap��ʼ������map
private  HashMap<String,Object> beanMap
=(HashMap<String, Object>) BeanHashMap.getBeanMap();
//bean����map,BeanHashMap��ʼ������map
private  HashMap<String,Object> beanDefinitionMap
=(HashMap<String, Object>) BeanHashMap.getBeanDefinitionMap();
	
	public  Object getBean(String name) {
		return beanMap.get(name);
	}
	
	public  Object beanDefinitionMap(String name) {
		return beanDefinitionMap.get(name);
	}

	public HashMap<String, Object> getBeanMap() {
		return beanMap;
	}

	public HashMap<String, Object> getBeanDefinitionMap() {
		return beanDefinitionMap;
	}



	


	
	
	
}
