package com.wx.test;

import org.junit.Test;

import com.wx.utils.IpUtil;

public class IPTest {
	@Test
	public void ipTest() throws Exception{
		IpUtil.getAddressByIp("219.136.134.157");
	}
}
