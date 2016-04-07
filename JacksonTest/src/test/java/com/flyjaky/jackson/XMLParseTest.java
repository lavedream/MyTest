package com.flyjaky.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.flyjaky.jackson.model.User;
import com.flyjaky.jackson.model.UserList;


public class XMLParseTest {

	
	
	
	
	/**
	 *@author liushuaic
	 *@date 2016-04-07 14:17
	 *xml 转为json  需要中间类
	 * */
	@Test
	public void testXMLToJSON(){
		
		String xmlContent="<xml>"
				+ "<userList>"
				+ "<user><userId>用户1</userId><userName>用户1</userName></user>"
				+ "<user><userId>用户2</userId><userName>用户2</userName></user>"
				+ "</userList>"
				+ "</xml>";
		
		XmlMapper xmlMapper=new XmlMapper();
		try {
			JsonNode jsonNode=xmlMapper.readTree(xmlContent);
			System.out.println(jsonNode);
			UserList userList=xmlMapper.readValue(xmlContent, UserList.class);
			ObjectMapper objectMapper =new ObjectMapper();
			System.out.println(objectMapper.writeValueAsString(userList));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @author liushuaic
	 * @date 2016-04-07 14:17
	 * 
	 * **/
	@Test
	public void testObjectTOXML(){
		try{
			
			XmlMapper xmlMapper=new XmlMapper();
			UserList ul=new UserList();
			List<User> us=new ArrayList<User>();
			User user=new User();
			user.setUserId("1");
			user.setUserName("1");
			User user2=new User();
			user2.setUserId("2");
			user2.setUserName("2");
			us.add(user);
			us.add(user2);
			
			ul.setUserList(us);
			
			String xmlContent=xmlMapper.writeValueAsString(ul);
			
			System.out.println(xmlContent);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testXMLTOObject(){
	
		String xmlContent="<UserList ><userList><userList><userId>1</userId><userName>1</userName></userList><userList><userId>2</userId><userName>2</userName></userList></userList></UserList>";
		
		XmlMapper xmlMapper=new XmlMapper();
		
		try {
//			JsonNode jsonNode=xmlMapper.readTree(xmlContent);
//			System.out.println(jsonNode);
			UserList ul=xmlMapper.readValue(xmlContent, UserList.class);
			System.out.println(ul);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
