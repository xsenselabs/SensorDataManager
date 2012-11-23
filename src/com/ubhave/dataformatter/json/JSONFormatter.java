/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

This library was developed as part of the EPSRC Ubhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.dataformatter.json;

import org.json.simple.JSONObject;

import com.ubhave.dataformatter.DataFormatter;
import com.ubhave.sensormanager.ESException;
import com.ubhave.sensormanager.config.SensorConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public abstract class JSONFormatter extends DataFormatter
{
	
	private final static String SENSOR_TYPE = "sensorType";
	private final static String SENSE_TIME = "senseStartTime";
	private final static String UNKNOWN_SENSOR = "unknownSensor";
	
	public JSONObject toJSON(final SensorData data)
	{
		JSONObject json = new JSONObject();
		if (data != null)
		{
			addGenericData(json, data);
			addSensorSpecificData(json, data);
			
			SensorConfig config = data.getSensorConfig();
			addGenericConfig(json, config);
			addSensorSpecificConfig(json, config);
		}
		return json;
	}
	
	@Override
	public String toString(final SensorData data)
	{
		return toJSON(data).toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	protected void addGenericData(JSONObject json, SensorData data)
	{
		json.put(SENSE_TIME, data.getTimestamp());
		try
		{
			json.put(SENSOR_TYPE, SensorUtils.getSensorName(data.getSensorType()));
		}
		catch (ESException e)
		{
			json.put(SENSOR_TYPE, UNKNOWN_SENSOR);
		}
	}
	
	protected abstract void addGenericConfig(JSONObject json, SensorConfig config);
	
	protected abstract void addSensorSpecificData(JSONObject json, SensorData data);
	
	protected abstract void addSensorSpecificConfig(JSONObject json, SensorConfig config);
}
