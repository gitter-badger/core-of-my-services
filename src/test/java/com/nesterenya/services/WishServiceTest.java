package com.nesterenya.services;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.*;

import com.nesterenya.modal.Wish;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;


public class WishServiceTest {

    @Test
    public void iterator_will_return_hello_world() {
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello").thenReturn("World");
        String result = i.next() + " " + i.next();

        assertEquals("Hello World", result);
    }

    @Test
    public void test_query_count() {
        Wish w1 = new Wish();
        w1.setId(ObjectId.get());
        w1.setDate(new Date());
        w1.setDislikes(5);
        w1.setLikes(7);
        w1.setText("bla bla");

        Wish w2 = new Wish();
        w2.setId(ObjectId.get());
        w2.setDate(new Date());
        w2.setDislikes(1);
        w2.setLikes(7);
        w2.setText("bla bla bla");

        Datastore datastore = mock(Datastore.class);
        Query<Wish> query = mock(Query.class);
        when(query.asList()).thenReturn( new ArrayList<Wish>(Arrays.asList(w1,w2)));
        when(datastore.createQuery(Wish.class)).thenReturn(query);
        when(query.order("-date")).thenReturn(query);

        WishService wishService = new WishService(datastore);
        assertEquals(2,wishService.getAll().size());
    }


}
