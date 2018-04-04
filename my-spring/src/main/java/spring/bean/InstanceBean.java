package spring.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import spring.beanfactory.SpringBeanFactory;


public class InstanceBean {

	
	public static void instance(SpringBeanFactory beanFactory) throws Exception {
		HashMap<String,Object> map 
		     = (HashMap<String, Object>) beanFactory.getBeanMap();
		HashMap<String, Object> beanDefinitionMap 
		     = (HashMap<String, Object>) beanFactory.getBeanDefinitionMap();
		//��ȡbeanDefinitionMap�����е�ֵ����class��������ʵ����
		Set<Entry<String, Object>> entrySet = beanDefinitionMap.entrySet();
		//ʵ�������е�Bean
		instanceBeansAndInject(entrySet,false,map);
		//����set����ע��
		instanceBeansAndInject(entrySet,true,map);
		
	}
	//ʵ������ע�룬��spring ����ע�벻ͬ
	private static void instanceBeansAndInject(Set<Entry<String, Object>> entrySet, boolean flag,
			HashMap<String,Object> map) throws Exception {
		//����class��ֵ�����÷������ʵ����
		for (Entry<String, Object> entry : entrySet) {
			BeanInfo value = (BeanInfo) entry.getValue();
			String clazz = value.getClazz();
			Class<?> entryClazz = Class.forName(clazz);
			Object newInstance = entryClazz.newInstance();
			//flagΪfalseֻ����ʵ����������û��ע�룬ʵ����UserController��
			//UserService,����UserController��userService������Ϊnull
			if(flag==false) {
				map.put(value.getId(), newInstance);
			}else {
				//flagΪtrue��������ע��(����set����)
				Method[] methods = entryClazz.getMethods();
				for (Method method : methods) {
					String name = method.getName();
					//����set��������ע��
					if(name.startsWith("set")) {
						//��ȡҪ�����Ķ���
						Object object = map.get(getBeanNameInMap(method.getParameters()));
						if(object==null) {
							continue;
						}
						//ע�룬ִ��set����,ע���UserController��userService������
						//�Ѿ�����ֵ
						method.invoke(newInstance, object);
						//��ʵ����bean��map��put��ע���Ķ���
						map.put(value.getId(), newInstance);
					}
				}
			}
			
		}
		
	}
	//�������÷����ȡ��set�����Ĳ������ͣ�ת��ΪBean��name
	private static String getBeanNameInMap(Parameter[] parameters) {
		String qianStr="";
		String houStr="";
		for (Parameter parameter : parameters) {
			Class<?> type = parameter.getType();
			houStr=type.getSimpleName().substring(1);
			qianStr=type.getSimpleName().substring(0, 1);
		}
		return qianStr.toLowerCase()+houStr;
	}

}
;