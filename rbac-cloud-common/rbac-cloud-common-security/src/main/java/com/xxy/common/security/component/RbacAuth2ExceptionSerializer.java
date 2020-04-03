package com.xxy.common.security.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.xxy.common.core.constants.CommonConstants;
import com.xxy.common.security.exception.RbacAuth2Exception;
import lombok.SneakyThrows;

/**
 * @author xxy
 * <p>
 * OAuth2 异常格式化
 */
public class RbacAuth2ExceptionSerializer extends StdSerializer<RbacAuth2Exception> {
	public RbacAuth2ExceptionSerializer() {
		super(RbacAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(RbacAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", CommonConstants.FAIL);
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeEndObject();
	}
}
