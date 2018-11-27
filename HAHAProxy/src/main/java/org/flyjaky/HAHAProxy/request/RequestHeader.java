package org.flyjaky.HAHAProxy.request;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;

import org.apache.commons.lang3.ArrayUtils;
import org.flyjaky.HAHAProxy.util.ByteUtils;

public class RequestHeader {

	/**
	 * length 4
	 */
	private byte[] magic = new byte[4];

	/**
	 * length 2
	 */
	private byte[] version = new byte[2];

	/**
	 * length 1
	 */
	private byte[] command = new byte[1];

	/**
	 * length 1 空位
	 */
	private byte[] spacePosition = new byte[1];

	/**
	 * 0000
	 */
	private byte[] status = new byte[4];

	/**
	 * length 4 AgentID
	 */
	private byte[] agentId = new byte[4];

	/**
	 * length4
	 * 
	 */
	private byte[] sequence = new byte[4];

	/**
	 * length 4 身体长度
	 */
	private byte[] reqestLength = new byte[4];

	/**
	 * length32 uuid
	 **/
	private byte[] uuid = new byte[32];

	/**
	 * @author liushuaic
	 * @desc 获取请求头的所有字节数组
	 **/
	public byte[] getBytes() {
		byte[] magicVersion = ArrayUtils.addAll(magic, version);
		byte[] mvc = ArrayUtils.addAll(magicVersion, command);
		byte[] mvcs = ArrayUtils.addAll(mvc, spacePosition);
		byte[] mvcsatus = ArrayUtils.addAll(mvcs, status);
		byte[] mvcsa = ArrayUtils.addAll(mvcsatus, agentId);
		byte[] mvcsas = ArrayUtils.addAll(mvcsa, sequence);
		byte[] mvcsasb = ArrayUtils.addAll(mvcsas, reqestLength);
		byte[] mvcsasbu = ArrayUtils.addAll(mvcsasb, uuid);
		return mvcsasbu;
	}

	public byte[] getMagic() {
		return magic;
	}

	/**
	 * @author liushuaic
	 * @param magic length=4
	 */
	public void setMagic(byte[] magic) {
		if (null == magic) {
			throw new IllegalArgumentException("magic is null");
		}
		if (magic.length != 4) {
			throw new IllegalArgumentException("magic is null");
		}
		this.magic = magic;
	}

	public byte[] getVersion() {
		return version;
	}
	
	/**
	 * @param version 
	 * @length 2
	 * **/
	public void setVersion(byte[] version) {
		
		if (null == version) {
			throw new IllegalArgumentException("version is null");
		}
		if (version.length != 2) {
			throw new IllegalArgumentException("version is null");
		}
		this.version = version;
		
		
	}

	public byte[] getCommand() {
		return command;
	}

	/**
	 * @param command 
	 * @length 1
	 * **/
	public void setCommand(byte command) {
		this.command = new byte[] {command};
	}

	public byte[] getSpacePosition() {
		return spacePosition;
	}

	/**
	 * @param spacePosition
	 * @length 1
	 * @desc 补位
	 * */
	public void setSpacePosition(byte[] spacePosition) {
		this.spacePosition = spacePosition;
	}

	public byte[] getAgentId() {
		return agentId;
	}

	
	/**
	 * @param agentId
	 * @length 4
	 * */
	public void setAgentId(byte[] agentId) {
		this.agentId = agentId;
	}

	public byte[] getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 * @length 4
	 * */
	public void setSequence(byte[] sequence) {
		this.sequence = sequence;
	}

	public byte[] getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 * @length 32
	 * **/
	public void setUuid(byte[] uuid) {
		this.uuid = uuid;
	}

	public byte[] getStatus() {
		return status;
	}

	public void setStatus(byte[] status) {
		this.status = status;
	}

	public byte[] getReqestLength() {
		return reqestLength;
	}

	public void setReqestLength(byte[] reqestLength) {
		this.reqestLength = reqestLength;
	}
	
	
	public RequestHeader() {
		this.initDefaultRequestHeader();
	}
	
	public void initDefaultRequestHeader() {
		this.setMagic("".getBytes());
		this.setAgentId("".getBytes());
		this.setCommand((byte) 0);
		this.setSequence(ByteUtils.intToBytes(10));
		this.setSpacePosition(ByteUtils.intToBytes(0));
		this.setVersion(ByteUtils.intToByteArray((int) Calendar.getInstance().getTimeInMillis()));
		this.setStatus(status);
	}
	
	

	/**
	 * @author liushuaic
	 * @date 2016-07-08 19:09
	 * @desc 获取magicString
	 **/
	public String getMagicString() {
		return new String(this.magic);
	}

	/**
	 * @author liushuaic
	 * @date 2016-07-08 19:19
	 * @desc 获取响应状态
	 **/
	public int getStatusInt() {
		ByteBuffer lengthbuffer = ByteBuffer.wrap(this.status);
		int lengths = lengthbuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
		return lengths;
	}

	/**
	 * @author liushuaic
	 * @date 2016-07-08 19:48
	 * @desc 获取uuid String
	 **/
	public String getUuidString() {
		return new String(this.getUuid());
	}

	/**
	 * @author liushuaic
	 * @date 2016-07-08 20:28
	 * @desc 获取请求长度
	 **/
	public int getRequestLengthInt() {
		ByteBuffer lengthbuffer = ByteBuffer.wrap(this.reqestLength);
		int lengths = lengthbuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
		return lengths;
	}

	public void setBody(byte[] data) {
		this.setMagic(ArrayUtils.subarray(data, 0, 4));
		// this.sbyte[]magicArray = ArrayUtils.subarray(header, 0, 4);
		// 把byte数组转换成字符串
		// String magic = new String(magicArray,"utf-8");
		// log.info(".........magic:"+magic);
		this.setVersion(ArrayUtils.subarray(data, 4, 6));
		this.setCommand(ArrayUtils.subarray(data, 6, 7)[0]);
		this.setStatus(ArrayUtils.subarray(data, 8, 12));

		byte[] statusArray = ArrayUtils.subarray(data, 8, 12);
		int status = ByteUtils.byte2int(statusArray);
		// log.info("........status:"+status);
		this.setAgentId(ArrayUtils.subarray(data, 12, 16));
		this.setSequence(ArrayUtils.subarray(data, 16, 20));
		this.setReqestLength(ArrayUtils.subarray(data, 20, 24));
		this.setUuid(ArrayUtils.subarray(data, 24, 56));
	}

}
