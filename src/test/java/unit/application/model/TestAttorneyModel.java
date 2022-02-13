package application.model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.Database;

import io.swagger.model.Attorney;
import application.model.AttorneyModel;

public class TestAttorneyModel {

    Attorney testAttorney = new Attorney();
    Response testGoodResponse = mock(com.cloudant.client.api.model.Response.class);
    Response testBadResponse = mock(com.cloudant.client.api.model.Response.class);
    Response testNotFoundResponse = mock(com.cloudant.client.api.model.Response.class);
    Database fakeDatabase = mock(Database.class);
    AttorneyModel testAttorneyModel = new AttorneyModel(fakeDatabase);

    @Before
    public void initTestAttorneyModel() {       
        // Setting a test Attorney, with no cases
        testAttorney.setId("0");
        testAttorney.setRev("Test Revision");
        testAttorney.setUsername("johndoe");
        testAttorney.setCases(null);

        when(testGoodResponse.getStatusCode()).thenReturn(200);
        when(testBadResponse.getStatusCode()).thenReturn(0);
        when(testNotFoundResponse.getStatusCode()).thenReturn(404);

        when(fakeDatabase.find(Attorney.class, "0")).thenReturn(testAttorney);
        when(fakeDatabase.save(testAttorney)).thenReturn(testGoodResponse);
        when(fakeDatabase.save(null)).thenReturn(testBadResponse);
        when(fakeDatabase.remove(null)).thenReturn(testBadResponse);
    }

    @Test
    public void testRead() throws Exception {
        // We do not have an attorney with id = 1, so this should return "null"
        assertTrue(testAttorneyModel.read("1") == null);
        // We do have an attorney with id = 0, so this should return our test attorney
        assertTrue(testAttorneyModel.read("0") != null);
        // Checking that the username is matching johndoe
        assertEquals(testAttorneyModel.read("0").getUsername(), "johndoe");
    }

    @Test
    public void testSave() throws Exception {
        // Trying to save our testAttorney, which should returns 200
        assertEquals(testAttorneyModel.save(testAttorney).getStatusCode(), 200);

        // Trying to save a null or bad Attorney value, which should returns 0 (made up error call)
        assertEquals(testAttorneyModel.save(null).getStatusCode(), 0);
    }

    @Test
    public void testDelete() throws Exception {
        // Trying to save our testAttorney, which should returns 200
        assertEquals(testAttorneyModel.save(testAttorney).getStatusCode(), 200);

        // Trying to delete our testAttorney, which should returns 200
        when(fakeDatabase.remove(testAttorney)).thenReturn(testGoodResponse);
        assertEquals(testAttorneyModel.delete("0").getStatusCode(), 200);

        // A second delete call should return 404, since it's already deleted
        when(fakeDatabase.remove(testAttorney)).thenReturn(testNotFoundResponse);
        assertEquals(testAttorneyModel.delete("0").getStatusCode(), 404);

        // Trying to delete a null or bad Attorney value, which should returns 0 (made up error call)
        assertEquals(testAttorneyModel.delete(null).getStatusCode(), 0);
    }

    // TODO: add test for addCaseToAttorney
    // TODO: add test for deleteCaseFromAttorney
    // TODO: add test for getCasesForAttorney
    // TODO: add test for getCasesByIdForAttorney
    // TODO: add test for getAll
}