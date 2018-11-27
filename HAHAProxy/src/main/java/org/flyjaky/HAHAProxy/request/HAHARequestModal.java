package org.flyjaky.HAHAProxy.request;

import org.apache.commons.lang3.ArrayUtils;
import org.flyjaky.HAHAProxy.util.ByteUtils;

/**
 * @author liushuaic
 * @date 2016-06-26 16:03
 * @desc 请求模型
 * **/
public abstract class HAHARequestModal implements HAHARequest{
	
//	// 请求的头是固定的
//	/**
//	 * @author liushuaic
//	 * @date 2016-06-25 16:49
//	 * @desc 请求头
//	 * byte[4] magic   逻辑头
//	 * byte[2] version 版本号
//	 * byte[1] commnd  请求
//	 * 【byte[1] reserved 保留位】
//	 * byte[4] status 状态码
//	 * byte[4] agentId
//	 * byte[4] sequence  现在设置为0
//	 * byte[4] length	 请求总长度
//	 * byte[4] 
//	 * */
//	public byte[] header=new byte[56];
	
	public RequestHeader requestHeader;
	
	public byte[] requestBody;

	/**
	 * @author liushuaic
	 * @throws Exception 
	 * @date 2016-06-25 16:45
	 * @desc 设置请求头
	 * 
	 * @throws Exception  请求长度不正确  请求长度为56位
	 * **/
	public void setHeader(RequestHeader requestHeader) throws Exception {
		if(requestHeader.getBytes().length==56){
			this.requestHeader=requestHeader;
		}else{
			throw new Exception("请求长度不对！");
		}
		
	}

	/**
	 * @author liushuaic
	 * @throws Exception 
	 * @date 2016-06-26 11:10
	 * @desc 获取请求内容
	 * **/
	public byte[] getRequest() throws Exception {
		
		if(null == this.requestBody){
			requestBody=new byte[0];
		}
		this.requestHeader.setReqestLength(ByteUtils.toLH(this.requestHeader.getBytes().length+this.requestBody.length));
		//组装新的请求
		byte[] body=ArrayUtils.addAll(this.requestHeader.getBytes(), this.requestBody);
		return body;
	}

	public RequestHeader getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}
	

}
