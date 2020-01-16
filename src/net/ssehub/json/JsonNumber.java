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

import java.util.Objects;

/**
 * A number value of JSON.
 * 
 * @author Adam
 */
public class JsonNumber extends JsonValue<Number> {

    private Number value;
    
    /**
     * Creates a number.
     * 
     * @param value The value of this number. Not <code>null</code>.
     */
    public JsonNumber(Number value) {
        this.value = Objects.requireNonNull(value);
    }
    
    @Override
    public Number getValue() {
        return value;
    }
    
    /**
     * "Escapes" the given number so that it is JSON compatible. This method handles Infinity and NaN by returning
     * a string containing <code>null</code>.
     * 
     * @param number The number to "escape". Not <code>null</code>.
     * 
     * @return The properly "escaped" number. Never <code>null</code>.
     */
    public static String jsonEscape(Number number) {
        String result;
        
        double dValue = number.doubleValue();
        if (Double.isInfinite(dValue) || Double.isNaN(dValue)) {
            // infinity and NaN are not allowed in JSON
            result = "null";
        } else {
            result = number.toString();
        }
        
        return result;
    }
    
    @Override
    public String toString() {
        return jsonEscape(value);
    }

    @Override
    public <T> T accept(JsonVisitor<T> visitor) {
        return visitor.visitNumber(this);
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;
        if (other instanceof JsonNumber) {
            JsonNumber o = (JsonNumber) other;
            equal = this.value.equals(o.value);
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
}
