package com.mega.component.workflow.serialization;

public interface Serializer
{
    /**
     * Serialize the given object into bytes
     *
     * @param obj the object
     * @return byte representation
     */
    <T> byte[] serialize(T obj);

    /**
     * Deserialize the given bytes into an object
     *
     * @param data bytes
     * @param clazz type of object
     * @return object
     */
    <T> T deserialize(byte[] data, Class<T> clazz);
}
