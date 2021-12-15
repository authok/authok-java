package com.authok.net;

import com.authok.exception.AuthokException;
import org.junit.Test;
import static org.junit.Assert.assertThrows;

public class RequestTest {

    @Test
    public void defaultImplementationShouldThrow() {
        assertThrows("executeAsync",
            UnsupportedOperationException.class,
            new Request<String>() {
                @Override
                public String execute() throws AuthokException {
                    return null;
                }
            }::executeAsync);
    }
}
