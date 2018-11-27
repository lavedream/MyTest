package org.flyjaky.HAHAProxy.request;

/**
 * @author liushuaic
 * @date 2016-06-25 16:28
 * @desc 灾备请求接口类
 *  定义基本的header
 *   body
 * */
public interface HAHARequest {
	
	
    RequestHeader requestHeader=null;
	
	byte[] requestBody=null;
 
	
	
	/**
	 * @author liushuaic
	 * @throws Exception 
	 * @date 2016-06-25 16:40
	 * @desc 获取字节
	 * */
	public byte[] getRequest() throws Exception;

}
