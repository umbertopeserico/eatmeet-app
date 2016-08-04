package com.example.eatmeet.entities.errors;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by umberto on 04/08/16.
 */
public class FieldErrorsDeserializer implements JsonDeserializer<ErrorsMap> {
    @Override
    public ErrorsMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ErrorsMap errorsMap = new ErrorsMap();
        System.out.println(json.toString());
        JsonObject errors = json.getAsJsonObject().get("errors").getAsJsonObject();

        for (Object o : errors.entrySet()) {
            Map.Entry field = (Map.Entry) o;
            String fieldName = field.getKey().toString();
            JsonArray jsonArray = (JsonArray) field.getValue();
            List<String> errorsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                errorsList.add(jsonArray.get(i).getAsString());
            }
            errorsMap.put(fieldName, errorsList);
        }

        return errorsMap;
    }
}
