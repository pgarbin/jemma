/**
 * This file is part of JEMMA - http://jemma.energy-home.org
 * (C) Copyright 2013 Telecom Italia (http://www.telecomitalia.it)
 *
 * JEMMA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) version 3
 * or later as published by the Free Software Foundation, which accompanies
 * this distribution and is available at http://www.gnu.org/licenses/lgpl.html
 *
 * JEMMA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License (LGPL) for more details.
 *
 */
package org.energy_home.jemma.ah.zigbee.zcl.cluster.eh;

import java.util.HashMap;
import java.util.Iterator;

import org.energy_home.jemma.ah.cluster.zigbee.eh.ApplianceControlClient;
import org.energy_home.jemma.ah.cluster.zigbee.eh.ApplianceControlServer;
import org.energy_home.jemma.ah.cluster.zigbee.eh.SignalStateResponse;
import org.energy_home.jemma.ah.cluster.zigbee.eh.WriteAttributeRecord;
import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IEndPointRequestContext;
import org.energy_home.jemma.ah.hac.ServiceClusterException;
import org.energy_home.jemma.ah.hac.IServiceCluster;
import org.energy_home.jemma.ah.hac.lib.EndPoint;
import org.energy_home.jemma.ah.internal.zigbee.ZclAttributeDescriptor;
import org.energy_home.jemma.ah.zigbee.IZclFrame;
import org.energy_home.jemma.ah.zigbee.ZCL;
import org.energy_home.jemma.ah.zigbee.ZclFrame;
import org.energy_home.jemma.ah.zigbee.ZigBeeDevice;
import org.energy_home.jemma.ah.zigbee.ZigBeeDeviceListener;
import org.energy_home.jemma.ah.zigbee.zcl.IZclAttributeDescriptor;
import org.energy_home.jemma.ah.zigbee.zcl.ZclException;
import org.energy_home.jemma.ah.zigbee.zcl.ZclValidationException;
import org.energy_home.jemma.ah.zigbee.zcl.lib.ZclServiceCluster;
import org.energy_home.jemma.ah.zigbee.zcl.lib.types.ZclAbstractDataType;
import org.energy_home.jemma.ah.zigbee.zcl.lib.types.ZclDataTypeBoolean;
import org.energy_home.jemma.ah.zigbee.zcl.lib.types.ZclDataTypeEnum8;
import org.energy_home.jemma.ah.zigbee.zcl.lib.types.ZclDataTypeI16;
import org.energy_home.jemma.ah.zigbee.zcl.lib.types.ZclDataTypeUI16;
import org.energy_home.jemma.ah.zigbee.zcl.lib.types.ZclDataTypeUI24;
import org.energy_home.jemma.ah.zigbee.zcl.lib.types.ZclDataTypeUI8;

public class ZclApplianceControlServer extends ZclServiceCluster implements ApplianceControlServer, ZigBeeDeviceListener {

	public final static short CLUSTER_ID = 27;
	final static HashMap attributesMapByName = new HashMap();
	final static HashMap attributesMapById = new HashMap();

	static {
		attributesMapByName.put(ZclApplianceControlServer.ATTR_StartTime_NAME, new ZclAttributeDescriptor(0,
				ZclApplianceControlServer.ATTR_StartTime_NAME, new ZclDataTypeUI16(), null, true, 1));
		attributesMapByName.put(ZclApplianceControlServer.ATTR_FinishTime_NAME, new ZclAttributeDescriptor(1,
				ZclApplianceControlServer.ATTR_FinishTime_NAME, new ZclDataTypeUI16(), null, true, 1));
		attributesMapByName.put(ZclApplianceControlServer.ATTR_RemainingTime_NAME, new ZclAttributeDescriptor(2,
				ZclApplianceControlServer.ATTR_RemainingTime_NAME, new ZclDataTypeUI16(), null, true, 1));
		attributesMapByName.put(ATTR_CycleTarget0_NAME, new ZclAttributeDescriptor(4,
				ATTR_CycleTarget0_NAME, new ZclDataTypeUI8(), null, true, 1));
		attributesMapByName.put(ATTR_CycleTarget1_NAME, new ZclAttributeDescriptor(5,
				ATTR_CycleTarget1_NAME, new ZclDataTypeUI8(), null, true, 1));
		attributesMapByName.put(ATTR_TemperatureTarget0_NAME, new ZclAttributeDescriptor(6,
				ATTR_TemperatureTarget0_NAME, new ZclDataTypeI16(), null, true, 1));
		attributesMapByName.put(ATTR_TemperatureTarget1_NAME, new ZclAttributeDescriptor(7,
				ATTR_TemperatureTarget1_NAME, new ZclDataTypeI16(), null, true, 1));
		attributesMapByName.put(ATTR_Spin_NAME, new ZclAttributeDescriptor(10, //spin attribute id: 0x000a
				ATTR_Spin_NAME, new ZclDataTypeI16(), null, true, 1));
		attributesMapByName.put(ATTR_SuperCoolMode_NAME, new ZclAttributeDescriptor(11, //SuperCoolMode attribute id: 0x000b
				ATTR_SuperCoolMode_NAME, new ZclDataTypeBoolean(), null, true, 1));
		attributesMapByName.put(ATTR_SuperFreezeMode_NAME, new ZclAttributeDescriptor(12, //SuperFreezeMode attribute id: 0x000c
				ATTR_SuperFreezeMode_NAME, new ZclDataTypeBoolean(), null, true, 1));
		attributesMapByName.put(ATTR_NormalMode_NAME, new ZclAttributeDescriptor(13, //NormalMode attribute id: 0x000d
				ATTR_NormalMode_NAME, new ZclDataTypeBoolean(), null, true, 1));
		attributesMapByName.put(ATTR_EcoMode_NAME, new ZclAttributeDescriptor(14, //EcoMode attribute id: 0x000e
				ATTR_EcoMode_NAME, new ZclDataTypeBoolean(), null, true, 1));
		attributesMapByName.put(ATTR_HolidayMode_NAME, new ZclAttributeDescriptor(15, //EcoMode attribute id: 0x000f
				ATTR_HolidayMode_NAME, new ZclDataTypeBoolean(), null, true, 1));
		attributesMapByName.put(ATTR_RemoteControl_NAME, new ZclAttributeDescriptor(18, //RemoteControl attribute id: 0x0012
				ATTR_RemoteControl_NAME, new ZclDataTypeBoolean(), null, true, 1));
		attributesMapByName.put(ATTR_IceParty_NAME, new ZclAttributeDescriptor(20, //IceParty attribute id: 0x0014
				ATTR_IceParty_NAME, new ZclDataTypeBoolean(), null, true, 1));

	}

	public ZclApplianceControlServer() throws ApplianceException {
		super();
	}

	public boolean notifyZclFrame(short clusterId, IZclFrame zclFrame) throws Exception {
		boolean handled;
		handled = super.notifyZclFrame(clusterId, zclFrame);
		if (handled) {
			return handled;
		}
		int commandId = zclFrame.getCommandId();
		if (zclFrame.isClientToServer()) {
			throw new ZclValidationException("invalid direction field");
		}
		IZclFrame responseZclFrame = null;
		ZigBeeDevice device = getZigBeeDevice();
		int statusCode = ZCL.SUCCESS;

		switch (commandId) {
		case 1:
			try {
				IServiceCluster[] serviceClusters = ((EndPoint) endPoint)
						.getPeerServiceClusters(ApplianceControlClient.class.getName());
				if (serviceClusters == null) {
					throw new ServiceClusterException("No appliances connected");
				} else if (serviceClusters.length == 0) {
					throw new ServiceClusterException("Service Clusters List is empty!!!");
				}
	
				short ApplianceStatus = ZclDataTypeEnum8.zclParse(zclFrame);
				short RemoteEnableFlags = ZclDataTypeUI8.zclParse(zclFrame);
				int ApplianceStatus2 = ZclDataTypeUI24.zclParse(zclFrame);
	
				for (int i = 0; i < serviceClusters.length; i++) {
					
						ApplianceControlClient o = (ApplianceControlClient) serviceClusters[i];
					
						o.execSignalStateNotification(ApplianceStatus, RemoteEnableFlags, ApplianceStatus2,
								endPoint.getDefaultRequestContext());
				}

			} catch (Throwable e) {
					// ignore the exeption
				e.printStackTrace();
			}


			responseZclFrame = null;
			break;

		default:
			throw new ZclException(ZCL.UNSUP_CLUSTER_COMMAND);
		}

		
		if (responseZclFrame == null) {
			if (!zclFrame.isDefaultResponseDisabled()) {
				responseZclFrame = getDefaultResponse(zclFrame, statusCode);
			}
			else {
				return true;
			}
		}
		if (!(responseZclFrame == null)) {
			device.post(ZclApplianceControlServer.CLUSTER_ID, responseZclFrame);
			return true;
		}
		return false;
	}

	protected int getClusterId() {
		return CLUSTER_ID;
	}

	protected IZclAttributeDescriptor getAttributeDescriptor(String name) {
		return ((IZclAttributeDescriptor) attributesMapByName.get(name));
	}

	protected IZclAttributeDescriptor getAttributeDescriptor(int id) {
		Iterator iterator = attributesMapByName.values().iterator();
		// FIXME: generate it and optimize!!!!
		for (; iterator.hasNext();) {
			IZclAttributeDescriptor attributeDescriptor = (IZclAttributeDescriptor) iterator.next();
			if (attributeDescriptor.zclGetId() == id)
				return attributeDescriptor;
		}
		return null;
	}

	public void execCommandExecution(short CommandId, IEndPointRequestContext context) throws ApplianceException,
			ServiceClusterException {
		int size = 0;
		size += ZclDataTypeEnum8.zclSize(CommandId);
		ZclFrame zclFrame = new ZclFrame(1, size);
		zclFrame.setCommandId(0);
		ZclDataTypeEnum8.zclSerialize(zclFrame, CommandId);
		issueExec(zclFrame, 11, context);
	}

	public SignalStateResponse execSignalState(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		ZclFrame zclFrame = new ZclFrame(1);
		zclFrame.setCommandId(1);
		IZclFrame zclResponseFrame = issueExec(zclFrame, 0, context);
		return (ZclSignalStateResponse.zclParse(zclResponseFrame));
	}

	public void execWriteFunctions(WriteAttributeRecord[] WriteAttributeRecords, IEndPointRequestContext context)
			throws ApplianceException, ServiceClusterException {
		int size = 255;
		ZclFrame zclFrame = new ZclFrame(1, size);
		zclFrame.setCommandId(2);

		for (int i = 0; i < WriteAttributeRecords.length; i++) {
			IZclAttributeDescriptor attributeDescriptor = getAttributeDescriptor(WriteAttributeRecords[i].name);
			zclFrame.appendUInt16(attributeDescriptor.zclGetId());
			ZclAbstractDataType zclDataType = attributeDescriptor.zclGetDataType();
			zclFrame.appendUInt8(zclDataType.zclGetDataType());
			zclDataType.zclObjectSerialize(zclFrame, WriteAttributeRecords[i].value);
		}

		zclFrame.shrink();
		issueExec(zclFrame, 11, context);
	}

	public void execOverloadPauseResume(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		ZclFrame zclFrame = new ZclFrame(1);
		zclFrame.setCommandId(3);
		issueExec(zclFrame, 11, context);
	}

	public void execOverloadPause(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		ZclFrame zclFrame = new ZclFrame(1);
		zclFrame.setCommandId(4);
		issueExec(zclFrame, 11, context);
	}

	public void execOverloadWarning(short WarningEvent, IEndPointRequestContext context) throws ApplianceException,
			ServiceClusterException {
		int size = 0;
		size += ZclDataTypeEnum8.zclSize(WarningEvent);
		ZclFrame zclFrame = new ZclFrame(1, size);
		zclFrame.setCommandId(5);
		ZclDataTypeEnum8.zclSerialize(zclFrame, WarningEvent);
		issueExec(zclFrame, 11, context);
	}

	protected IZclFrame parseSignalStateNotification(ApplianceControlClient o, IZclFrame zclFrame) throws ApplianceException,
			ServiceClusterException {
		short ApplianceStatus = ZclDataTypeEnum8.zclParse(zclFrame);
		short RemoteEnableFlags = ZclDataTypeUI8.zclParse(zclFrame);
		int ApplianceStatus2 = ZclDataTypeUI24.zclParse(zclFrame);
		o.execSignalStateNotification(ApplianceStatus, RemoteEnableFlags, ApplianceStatus2, endPoint.getDefaultRequestContext());
		return null;
	}

	public int getStartTime(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Integer objectResult = null;
			objectResult = ((Integer) getValidCachedAttributeObject(0, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult.intValue();
			}
		}
		IZclFrame zclFrame = readAttribute(0, context);
		int v = ZclDataTypeUI16.zclParse(zclFrame);
		setCachedAttributeObject(0, new Integer(v));
		return v;
	}

	public int getFinishTime(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Integer objectResult = null;
			objectResult = ((Integer) getValidCachedAttributeObject(1, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult.intValue();
			}
		}
		IZclFrame zclFrame = readAttribute(1, context);
		int v = ZclDataTypeUI16.zclParse(zclFrame);
		setCachedAttributeObject(1, new Integer(v));
		return v;
	}

	public int getRemainingTime(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Integer objectResult = null;
			objectResult = ((Integer) getValidCachedAttributeObject(2, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult.intValue();
			}
		}
		IZclFrame zclFrame = readAttribute(2, context);
		int v = ZclDataTypeUI16.zclParse(zclFrame);
		setCachedAttributeObject(2, new Integer(v));
		return v;
	}
	
	public short getCycleTarget0(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Short objectResult = null;
			objectResult = ((Short) getValidCachedAttributeObject(4, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult.shortValue();
			}
		}
		IZclFrame zclFrame = readAttribute(4, context);
		short v = ZclDataTypeUI8.zclParse(zclFrame);
		setCachedAttributeObject(4, new Short(v));
		return v;
	}
	
	public short getCycleTarget1(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Short objectResult = null;
			objectResult = ((Short) getValidCachedAttributeObject(5, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult.shortValue();
			}
		}
		IZclFrame zclFrame = readAttribute(5, context);
		short v = ZclDataTypeUI8.zclParse(zclFrame);
		setCachedAttributeObject(5, new Short(v));
		return v;
	}
	
	public int getTemperatureTarget0(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Integer objectResult = null;
			objectResult = ((Integer) getValidCachedAttributeObject(6, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult.intValue();
			}
		}
		IZclFrame zclFrame = readAttribute(6, context);
		int v = ZclDataTypeI16.zclParse(zclFrame);
		setCachedAttributeObject(6, new Integer(v));
		return v;
	}
	
	public int getTemperatureTarget1(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Integer objectResult = null;
			objectResult = ((Integer) getValidCachedAttributeObject(7, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult.intValue();
			}
		}
		IZclFrame zclFrame = readAttribute(7, context);
		int v = ZclDataTypeI16.zclParse(zclFrame);
		setCachedAttributeObject(7, new Integer(v));
		return v;
	}

	@Override
	public short getSpin(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Short objectResult = null;
			objectResult = ((Short) getValidCachedAttributeObject(10 //spin attribute ID: 0x000a
					, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult.shortValue();
			}
		}
		IZclFrame zclFrame = readAttribute(10, context);//spin attribute ID: 0x000a
		Integer v = ZclDataTypeUI16.zclParse(zclFrame);
		setCachedAttributeObject(10, v.shortValue());
		return v.shortValue();
	}

	@Override
	public boolean getEcoMode(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Boolean objectResult = null;
			objectResult = ((Boolean) getValidCachedAttributeObject(14 //EcoMode attribute ID: 0x000e
					, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult;
			}
		}
		IZclFrame zclFrame = readAttribute(14, context);//EcoMode attribute ID: 0x000e
		Boolean v = ZclDataTypeBoolean.zclParse(zclFrame);
		setCachedAttributeObject(14, v);
		return v;
	}

	@Override
	public boolean getNormalMode(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Boolean objectResult = null;
			objectResult = ((Boolean) getValidCachedAttributeObject(13 //NormalMode attribute ID: 0x000d
					, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult;
			}
		}
		IZclFrame zclFrame = readAttribute(13, context);//NormalMode attribute ID: 0x000d
		Boolean v = ZclDataTypeBoolean.zclParse(zclFrame);
		setCachedAttributeObject(13, v);
		return v;
	}

	@Override
	public boolean getHolidayMode(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Boolean objectResult = null;
			objectResult = ((Boolean) getValidCachedAttributeObject(15 //HolidayMode attribute ID: 0x000f
					, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult;
			}
		}
		IZclFrame zclFrame = readAttribute(15, context);//HolidayMode attribute ID: 0x000f
		Boolean v = ZclDataTypeBoolean.zclParse(zclFrame);
		setCachedAttributeObject(15, v);
		return v;
	}

	@Override
	public boolean getIceParty(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Boolean objectResult = null;
			objectResult = ((Boolean) getValidCachedAttributeObject(20 //IceParty attribute ID: 0x0014
					, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult;
			}
		}
		IZclFrame zclFrame = readAttribute(20, context);//IceParty attribute ID: 0x0014
		Boolean v = ZclDataTypeBoolean.zclParse(zclFrame);
		setCachedAttributeObject(20, v);
		return v;
	}

	@Override
	public boolean getSuperCoolMode(IEndPointRequestContext context) throws ApplianceException, ServiceClusterException {
		if (context != null) {
			Boolean objectResult = null;
			objectResult = ((Boolean) getValidCachedAttributeObject(11 //SuperCool attribute ID: 0x000b
					, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult;
			}
		}
		IZclFrame zclFrame = readAttribute(11, context); //SuperCool attribute ID: 0x000b
		Boolean v = ZclDataTypeBoolean.zclParse(zclFrame);
		setCachedAttributeObject(11, v);
		return v;
	}

	@Override
	public boolean getSuperFreezeMode(IEndPointRequestContext context) throws ApplianceException,
			ServiceClusterException {
		if (context != null) {
			Boolean objectResult = null;
			objectResult = ((Boolean) getValidCachedAttributeObject(12 //SuperFreeze attribute ID: 0x000c
					, context.getMaxAgeForAttributeValues()));
			if (objectResult != null) {
				return objectResult;
			}
		}
		IZclFrame zclFrame = readAttribute(12, context); //SuperFreeze attribute ID: 0x000c
		Boolean v = ZclDataTypeBoolean.zclParse(zclFrame);
		setCachedAttributeObject(12,v);
		return v;

	}

}
