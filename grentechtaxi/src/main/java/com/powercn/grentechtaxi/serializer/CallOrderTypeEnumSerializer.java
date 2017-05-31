package com.powercn.grentechtaxi.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.powercn.grentechtaxi.entity.CallOrderTypeEnum;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CallOrderTypeEnumSerializer implements JsonDeserializer<CallOrderTypeEnum> ,JsonSerializer<CallOrderTypeEnum> {


    @Override
    public CallOrderTypeEnum deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if(jsonElement.getAsInt()<CallOrderTypeEnum.values().length)
        return CallOrderTypeEnum.values()[jsonElement.getAsInt()];
        return null;
    }

    @Override
    public JsonElement serialize(CallOrderTypeEnum callOrderTypeEnum, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(callOrderTypeEnum.ordinal());
    }
}
