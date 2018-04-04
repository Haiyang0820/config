package spring.listener;

import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.DocumentException;

import spring.Dom4jForXml;
import spring.applicationcontext.SpringApplicationContext;
import spring.bean.InstanceBean;
import spring.beanfactory.SpringBeanFactory;

public class SpringListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent sce) {
		try {
			//��ȡweb.xml������Ϊ��ʼ���������õ���Ҫ���ص�xml�ľ���·��
			String filePath=getFilePath("configLoaction",sce);
			//������Ҫ�ķ����������س�ʼ����ʵ�����Ϳ���
			refresh(filePath,sce);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void refresh(String filePath,ServletContextEvent sce) throws Exception {
		//����xml���ʼ��IOC
		SpringBeanFactory beanFactory = Dom4jForXml.resolveXml(filePath);
		//ʵ����bean������ע��
		InstanceBean.instance(beanFactory);
		//����Context��beanFactory
		initContext(beanFactory,sce);
	}

	private void initContext(SpringBeanFactory beanFactory, ServletContextEvent sce) {
		SpringApplicationContext context=new SpringApplicationContext();
		//����SpringApplicationContext��SpringBeanFactory����
		context.setSpringBeanFactory(beanFactory);
		ServletContext servletContext = sce.getServletContext();
		//��SpringApplicationContext���õ�servlet��������
		servletContext.setAttribute("SpringApplicationContext", context);
	}

	private String getFilePath(String name,ServletContextEvent sce) {
		String config = sce.getServletContext().getInitParameter(name);
		URL url = null;
		if(config.startsWith("classpath")) {
			url = SpringListener.class.getClassLoader().getResource(config.split(":")[1]);
		}else {
			url = SpringListener.class.getClassLoader().getResource(config);
		}
		return url.getPath();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
