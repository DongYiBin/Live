package com.mz.live.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jsonGenerator.writeString(sdf.format(value));
	}
}
