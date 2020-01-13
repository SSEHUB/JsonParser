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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A list of {@link JsonElement}s.
 * 
 * @author Adam
 */
public class JsonList extends JsonElement implements Iterable<JsonElement> {

    private List<JsonElement> elements;
    
    /**
     * Creates an empty list.
     */
    public JsonList() {
        this.elements = new ArrayList<>();
    }
    
    /**
     * Adds an element to the end of the list.
     * 
     * @param element The element to add. Not <code>null</code>.
     */
    public void addElement(JsonElement element) {
        elements.add(Objects.requireNonNull(element));
    }
    
    /**
     * Replaces the given element in the list.
     * 
     * @param index The index to replace.
     * @param element The new element value. Not <code>null</code>.
     * 
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public void setElement(int index, JsonElement element) throws IndexOutOfBoundsException {
        elements.set(index, Objects.requireNonNull(element));
    }
    
    /**
     * Removes an element from the list. The following elements will move one to the left.
     * 
     * @param index The index to remove.
     * 
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public void removeElement(int index) throws IndexOutOfBoundsException {
        elements.remove(index);
    }
    
    /**
     * Returns an element from the list.
     * 
     * @param index The index of the element.
     * 
     * @return The element at the specified index.
     * 
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public JsonElement getElement(int index) throws IndexOutOfBoundsException {
        return elements.get(index);
    }
    
    /**
     * Returns the number of elements in this list.
     * 
     * @return The size of this list.
     */
    public int getSize() {
        return elements.size();
    }
    
    @Override
    public Iterator<JsonElement> iterator() {
        return elements.iterator();
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        if (elements.isEmpty()) {
            result.append("[]");
            
        } else {
            result.append("[ ");
            
            for (int i = 0; i < elements.size(); i++) {
                result.append(elements.get(i).toString());
                if (i != elements.size() - 1) {
                    result.append(", ");
                }
            }
            
            result.append(" ]");
        }
        
        return result.toString();
    }

    @Override
    public <T> T accept(JsonVisitor<T> visitor) {
        return visitor.visitList(this);
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;
        if (other instanceof JsonList) {
            JsonList o = (JsonList) other;
            equal = this.elements.equals(o.elements);
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }
    
}
