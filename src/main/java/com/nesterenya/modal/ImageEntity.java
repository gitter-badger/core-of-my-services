package com.nesterenya.modal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nesterenya.helpers.ObjectIdDeserializer;
import com.nesterenya.helpers.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;


public class ImageEntity {

    @JsonDeserialize(using = ObjectIdDeserializer.class)
    @JsonSerialize(using = ObjectIdSerializer.class)
    @Id
    private ObjectId id;
    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}
