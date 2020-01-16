/*
 * Copyright 2017-2019 University of Hildesheim, Software Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ssehub.json;

import java.util.Map;

/**
 * A visitor for printing out JSON with proper line breaks and indentation.
 * 
 * @author Adam
 */
public class JsonPrettyPrinter implements JsonVisitor<String> {

    @Override
    public String visitObject(JsonObject object) {
        StringBuilder result = new StringBuilder();
        
        if (object.getSize() == 0) {
            result.append("{}");
            
        } else {
            result.append("{\n");
            for (Map.Entry<String, JsonElement> element : object) {
                
                result.append("\t").append(new JsonString(element.getKey()).accept(this)).append(": ");
                String[] lines = element.getValue().accept(this).split("\n");
                
                boolean first = true;
                for (String line : lines) {
                    if (first) {
                        first = false;
                    } else {
                        result.append("\t");
                    }
                    result.append(line).append("\n");
                }
                result.delete(result.length() - 1, result.length()); // remove trailing "\n"
                
                result.append(",\n");
                
            }
            result.delete(result.length() - 2, result.length()); // remove trailing ",\n"
            result.append("\n}");
        }
        
        
        return result.toString();
    }

    @Override
    public String visitList(JsonList list) {
        StringBuilder result = new StringBuilder();
        
        if (list.getSize() == 0) {
            result.append("[]");
            
        } else {
            result.append("[\n");
            for (JsonElement element : list) {
                
                String[] lines = element.accept(this).split("\n");
                
                for (String line : lines) {
                    result.append("\t").append(line).append("\n");
                }
                result.delete(result.length() - 1, result.length()); // remove trailing "\n"
                
                result.append(",\n");
                
            }
            result.delete(result.length() - 2, result.length()); // remove trailing ",\n"
            result.append("\n]");
        }
        
        
        return result.toString();
    }

    @Override
    public String visitBoolean(JsonBoolean bool) {
        return String.valueOf(bool.getValue());
    }

    @Override
    public String visitNumber(JsonNumber number) {
        return JsonNumber.jsonEscape(number.getValue());
    }

    @Override
    public String visitString(JsonString string) {
        return '"' + JsonString.jsonEscape(string.getValue()) + '"';
    }

    @Override
    public String visitNull(JsonNull nall) {
        return "null";
    }

}
