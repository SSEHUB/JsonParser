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

/**
 * Abstract super-class of all JSON elements.
 * 
 * @author Adam
 */
public abstract class JsonElement {

    /**
     * Creates a valid JSON string of this element.
     */
    @Override
    public abstract String toString();
    
    /**
     * Accepts the given visitor.
     * 
     * @param visitor The visitor to accept. Not <code>null</code>.
     * 
     * @return The return value of the visitor.
     * 
     * @param <T> The return type of the visitor.
     */
    public abstract <T> T accept(JsonVisitor<T> visitor);
    
    @Override
    public abstract boolean equals(Object other);
    
    @Override
    public abstract int hashCode();
    
}
