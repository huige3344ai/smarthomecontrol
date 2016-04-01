package com.smarthome.simple.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.smarthome.base.BaseAction;
import com.smarthome.simple.entity.Devices;
import com.smarthome.simple.entity.Home;
import com.smarthome.simple.entity.User;
import com.smarthome.simple.query.DevicesQuery;
import com.smarthome.simple.services.DevicesServices;
import com.smarthome.simple.services.UserServices;
import com.smarthome.util.DateUtil;
import com.smarthome.util.JSONSerializer;

public class DevicesAction extends
		BaseAction<Devices, DevicesQuery> {
	@Resource
	protected DevicesServices devicesService;
    @Resource
    protected UserServices userService;

    /**
     * 根据id获取对象  通过json方式返回
     */
	public void getDevice(){
		try {
			model=devicesService.get(query.getId());
			getResponse().getWriter().write(JSONSerializer.serialize(model).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    
    
	/**
	 * 设备查询 设备首页
	 */
	public String devicesList(){
	    page=devicesService.getCurrentPage(query,this.pageNum,this.numPerPage);  
        createPage(page);
        if(page.size()>0&&page.getPageNum()>page.getTotalPage())
        	return "direct_devicesList";
		return "devicesList";
        	
	}
	
	/**
	 * 获取所有用户
	 */
	public void getAllUserList(){
		try {
			List<User> list = userService.findAll();
			getResponse().getWriter().write(JSONSerializer.serialize(list).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 获取用户住所
	 */
	public void getHomeList(){
		try {
			List<Home> list = devicesService.findHomeByUid(query);
			getResponse().getWriter().write(JSONSerializer.serialize(list).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * 保存 设备
	 */
	public void saveDevices(){
		
		try {
			model.setRecordTime(DateUtil.getCurrDateStr());
			model.setStatus("0");
			devicesService.save(model);
			getResponse().getWriter().write(JSONSerializer.serialize(true).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * 更新 设备信息
	 */
	public void updateDevices(){
		try {

			Devices devices = devicesService.get(query.getId());
			
			devices.setDeviceNum(model.getDeviceNum());
			devices.setDeviceName(model.getDeviceName());
			devices.setUserId(model.getUserId());
			devices.setHomeId(model.getHomeId());
			devices.setStatus(model.getStatus());
			devicesService.update(devices);
			getResponse().getWriter().write(JSONSerializer.serialize(true).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除设备
	 */
	public void deleteDevices(){
		try {
			model = devicesService.get(query.getId());
			devicesService.delete(model);
			getResponse().getWriter().write(JSONSerializer.serialize(true).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
}