package com.powercn.grentechdriver.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.powercn.grentechdriver.entity.CallOrderStatusEnum;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CallOrderStatusEnumSerializer implements JsonDeserializer<CallOrderStatusEnum>,JsonSerializer<CallOrderStatusEnum> {

    @Override
    public CallOrderStatusEnum deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if(jsonElement.getAsJsonObject().get("code").getAsInt()<CallOrderStatusEnum.values().length)
            return CallOrderStatusEnum.values()[jsonElement.getAsJsonObject().get("code").getAsInt()];
        return null;
    }

    @Override
    public JsonElement serialize(CallOrderStatusEnum callOrderStatusEnum, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(callOrderStatusEnum.ordinal());
    }
}
