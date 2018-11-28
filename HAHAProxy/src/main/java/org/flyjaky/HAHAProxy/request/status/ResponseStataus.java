package org.flyjaky.HAHAProxy.request.status;

/**
 * 
 * @author liushuaic
 * @date 2018-11-27 17:26
 * @desc 响应状态码
 * 
 * 
 * */
public class ResponseStataus {

	public enum status{
		ok(0,"response ok"),error(1,"response ok"),busy(2,"busy ok")
		,requesttimeout(3,"request timeout"),responsetimeout(4,"response timeout"),systemerror(5,"system error");
		int code;String message;
		private status(int code,String message){
			this.code=code;
			this.message=message;
		}
	}
	
}
