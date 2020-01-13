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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A JSON object. Basically a {@code Map<String, JsonElement>}. Keeps insertion order when
 * iterating using {@link #iterator()}.
 * 
 * @author Adam
 */
public class JsonObject extends JsonElement implements Iterable<Map.Entry<String, JsonElement>> {

    private Map<String, JsonElement> elements;
    
    /**
     * Creates an empty {@link JsonObject}.
     */
    public JsonObject() {
        this.elements = new LinkedHashMap<>();
    }
    
    /**
     * Puts an element into the map.
     * 
     * @param key The key of the element. Not <code>null</code>.
     * @param element The element to add. Not <code>null</code>.
     */
    public void putElement(String key, JsonElement element) {
        elements.put(Objects.requireNonNull(key), Objects.requireNonNull(element));
    }
    
    /**
     * Returns an element from this map.
     * 
     * @param key The key of the element. Not <code>null</code>.
     * 
     * @return The element, or <code>null</code> if no element with the given key is present.
     */
    public JsonElement getElement(String key) {
        return elements.get(Objects.requireNonNull(key));
    }
    
    /**
     * Helper method for reading a {@link JsonValue} with a specified type.
     * 
     * @param key The key of the element in this map. Not <code>null</code>.
     * @param type The expected type of {@link JsonValue}. Not <code>null</code>.
     * 
     * @param <T> The type of value to return.
     * 
     * @return The value of the {@link JsonValue} with the specified key.
     * 
     * @throws NoSuchElementException If no element with such a key exists, or has an invalid type.
     * 
     */
    @SuppressWarnings("unchecked")
    private <T> T getValue(String key, Class<? extends JsonValue<T>> type) throws NoSuchElementException {
        JsonElement element = getElement(key);
        if (element == null) {
            throw new NoSuchElementException("No element with key \"" + key + "\"");
        }
        
        if (!(type.isAssignableFrom(element.getClass()))) {
            throw new NoSuchElementException("Expected key \"" + key + "\" with type " + type.getSimpleName()
                    + ", but got " + element.getClass().getSimpleName());
        }
        
        return ((JsonValue<T>) element).getValue();
    }
    
    /**
     * Convenience method for reading a string value.
     * 
     * @param key The key of the element to read. Not <code>null</code>.
     * 
     * @return The string value of this element. Never <code>null</code>.
     * 
     * @throws NoSuchElementException If no such element exists, or the element is not a string value.
     */
    public String getString(String key) throws NoSuchElementException {
        return getValue(key, JsonString.class);
    }
    
    /**
     * Convenience method for reading a boolean value.
     * 
     * @param key The key of the element to read. Not <code>null</code>.
     * 
     * @return The boolean value of this element.
     * 
     * @throws NoSuchElementException If no such element exists, or the element is not a boolean value.
     */
    public boolean getBoolean(String key) throws NoSuchElementException {
        return getValue(key, JsonBoolean.class);
    }
    
    /**
     * Convenience method for reading an integer value.
     * 
     * @param key The key of the element to read. Not <code>null</code>.
     * 
     * @return The integer value of this element.
     * 
     * @throws NoSuchElementException If no such element exists, or the element is not an integer value.
     */
    public int getInt(String key) throws NoSuchElementException {
        Number value = getValue(key, JsonNumber.class);
        if (!(value instanceof Integer)) {
            throw new NoSuchElementException("Expected key \"" + key + "\" with type integer, but got "
                    + value.getClass().getSimpleName());
        }
        return (int) value;
    }
    
    /**
     * Convenience method for reading a long value.
     * 
     * @param key The key of the element to read. Not <code>null</code>.
     * 
     * @return The long value of this element.
     * 
     * @throws NoSuchElementException If no such element exists, or the element is not a long value.
     */
    public long getLong(String key) throws NoSuchElementException {
        Number value = getValue(key, JsonNumber.class);
        if (!(value instanceof Integer) && !(value instanceof Long)) {
            throw new NoSuchElementException("Expected key \"" + key + "\" with type long, but got "
                    + value.getClass().getSimpleName());
        }
        return value.longValue();
    }
    
    /**
     * Convenience method for reading a double value.
     * 
     * @param key The key of the element to read. Not <code>null</code>.
     * 
     * @return The double value of this element.
     * 
     * @throws NoSuchElementException If no such element exists, or the element is not a double value.
     */
    public double getDouble(String key) throws NoSuchElementException {
        Number value = getValue(key, JsonNumber.class);
        if (!(value instanceof Double)) {
            throw new NoSuchElementException("Expected key \"" + key + "\" with type double, but got "
                    + value.getClass().getSimpleName());
        }
        return (double) value;
    }
    
    /**
     * Convenience method for reading a list value.
     * 
     * @param key The key of the element to read. Not <code>null</code>.
     * 
     * @return The list stored under the given key. Never <code>null</code>.
     * 
     * @throws NoSuchElementException If no such element exists, or the element is not a list.
     */
    public JsonList getList(String key) throws NoSuchElementException {
        JsonElement element = getElement(key);
        if (element == null) {
            throw new NoSuchElementException("No element with key \"" + key + "\"");
        }
        
        if (!(element instanceof JsonList)) {
            throw new NoSuchElementException("Expected key \"" + key + "\" with type JsonList, but got "
                    + element.getClass().getSimpleName());
        }
        
        return (JsonList) element;
    }
    
    /**
     * Convenience method for reading an object value.
     * 
     * @param key The key of the element to read. Not <code>null</code>.
     * 
     * @return The object stored under the given key. Never <code>null</code>.
     * 
     * @throws NoSuchElementException If no such element exists, or the element is not an object.
     */
    public JsonObject getObject(String key) throws NoSuchElementException {
        JsonElement element = getElement(key);
        if (element == null) {
            throw new NoSuchElementException("No element with key \"" + key + "\"");
        }
        
        if (!(element instanceof JsonObject)) {
            throw new NoSuchElementException("Expected key \"" + key + "\" with type JsonObject, but got "
                    + element.getClass().getSimpleName());
        }
        
        return (JsonObject) element;
    }
    
    /**
     * Removes an element from this map.
     * 
     * @param key The key of the element. Not <code>null</code>.
     */
    public void removeElement(String key) {
        elements.remove(key);
    }
    
    /**
     * Returns the number of elements in this object.
     * 
     * @return The size of this map.
     */
    public int getSize() {
        return elements.size();
    }
    
    @Override
    public Iterator<Map.Entry<String, JsonElement>> iterator() {
        return elements.entrySet().iterator();
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        
        if (!elements.isEmpty()) {
            result.append("{ ");
            
            for (Map.Entry<String, JsonElement> entry : this) {
                result.append('"')
                        .append(entry.getKey())
                        .append("\": ")
                        .append(entry.getValue().toString())
                        .append(", ");
            }
            result.delete(result.length() - 2, result.length()); // remvoe trailing ", "
            
            result.append(" }");
            
        } else {
            result.append("{}");
        }
        
        return result.toString();
    }

    @Override
    public <T> T accept(JsonVisitor<T> visitor) {
        return visitor.visitObject(this);
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;
        if (other instanceof JsonObject) {
            JsonObject o = (JsonObject) other;
            equal = this.elements.equals(o.elements);
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }
    
}
