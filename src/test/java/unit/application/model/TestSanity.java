package application.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.mock;


// This class just ensures that our project can run unit tests safely with Junit & Mockito
public class TestSanity {

    private class innerMock {
        private String id;

        innerMock(String id) {
            this.id = id;
        }

        String getId() {
            return this.id;
        }
    }

    @Test
    public void testJunitAndMockito() throws Exception {
        innerMock mockedInstance = mock(innerMock.class);
        assertTrue(mockedInstance.getId() == null);
        innerMock nonMockedInstance = new innerMock("0");
        assertEquals(nonMockedInstance.getId(), "0");
    }

}