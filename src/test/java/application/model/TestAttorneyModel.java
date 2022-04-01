package application.model;

import com.ibm.cloud.cloudant.v1.model.*;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.http.ServiceCall;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.ibm.cloud.cloudant.v1.Cloudant;
import io.swagger.model.Attorney;
import org.mockito.Mockito;
import java.util.HashMap;


public class TestAttorneyModel {

    Attorney testAttorney = new Attorney();

    DocumentResult testGoodResponse = mock(DocumentResult.class);
    DocumentResult testBadResponse = mock(DocumentResult.class);
    Cloudant fakeService = mock(Cloudant.class);
    ModelHelper modelHelper = mock(ModelHelper.class);
    PostDocumentOptions testGoodCreateDocumentOptions = mock(PostDocumentOptions.class);
    DeleteDocumentOptions testGoodDeleteDocumentOptions = mock(DeleteDocumentOptions.class);
    GetDocumentOptions testGoodGetDocumentOptions = mock(GetDocumentOptions.class);
    GetDocumentOptions testBadGetDocumentOptions = mock(GetDocumentOptions.class);
    ServiceCall goodServiceCall = mock(ServiceCall.class);
    ServiceCall badServiceCall = mock(ServiceCall.class);
    ServiceCall <DocumentResult> goodCreateServiceCall = mock(ServiceCall.class);
    ServiceCall <DocumentResult> goodDeleteServiceCall = mock(ServiceCall.class);

    Response goodResponse = mock(Response.class);
    Response badResponse = mock(Response.class);
    Response<DocumentResult> goodCreateResponse = mock(Response.class);
    Response<DocumentResult> badCreateResponse = mock(Response.class);
    Response<DocumentResult> goodDeleteResponse = mock(Response.class);

    AttorneyModel testAttorneyModel = null;
    String databaseName = "outcarcerate-attorney";

    @Before
    public void initTestAttorneyModel() {       
        // Setting a test Attorney, with no cases
        testAttorney.setId("0");
        testAttorney.setRev("Test Revision");
        testAttorney.setUsername("johndoe");
        testAttorney.setCases(null);
        testAttorneyModel = new AttorneyModel(fakeService, modelHelper);

        when(testGoodResponse.isOk() ).thenReturn(Boolean.TRUE);
        when(testBadResponse.isOk()).thenReturn(Boolean.FALSE);
        when(goodCreateResponse.getStatusCode()).thenReturn(200);
        when(badCreateResponse.getStatusCode()).thenReturn(500);
        when(modelHelper.getPostDocumentOptions(any(),any())).thenReturn(testGoodCreateDocumentOptions);
        when(fakeService.postDocument(testGoodCreateDocumentOptions)).thenReturn(goodServiceCall);
        when(modelHelper.getGetDocumentOptions(Mockito.eq("0"),any())).thenReturn(testGoodGetDocumentOptions);
        when(modelHelper.getGetDocumentOptions(Mockito.eq("1"),any())).thenReturn(testBadGetDocumentOptions);
        when(fakeService.getDocument(testGoodGetDocumentOptions)).thenReturn(goodServiceCall);
        when(fakeService.getDocument(testBadGetDocumentOptions)).thenReturn(badServiceCall);
        when(goodServiceCall.execute()).thenReturn(goodResponse);
        when(badServiceCall.execute()).thenReturn(badResponse);
        when(goodResponse.getResult()).thenReturn(getTestDocument("0", "testRev", "johndoe"));
        when(badResponse.getResult()).thenReturn(null);
        //test saving document
        when(modelHelper.getPostDocumentOptions(any(Document.class),any())).thenReturn(testGoodCreateDocumentOptions);
        when(fakeService.postDocument(testGoodCreateDocumentOptions)).thenReturn(goodCreateServiceCall);
        when(goodCreateServiceCall.execute()).thenReturn(goodCreateResponse);
        when(goodCreateResponse.getResult()).thenReturn(testGoodResponse);

        //test Delete
        when(modelHelper.getDeleteDocumentOptions(Mockito.eq("0"),any(),any())).thenReturn(testGoodDeleteDocumentOptions);
        when(fakeService.deleteDocument(testGoodDeleteDocumentOptions)).thenReturn(goodCreateServiceCall);
        when(goodDeleteServiceCall.execute()).thenReturn(goodDeleteResponse);
        when(goodDeleteResponse.getResult()).thenReturn(testGoodResponse);
    }

    @Test
    public void testGetAttorney() throws Exception {
        // We do have an attorney with id = 0, so this should return our test attorney
        Attorney attorney = testAttorneyModel.getAttorney("0");
        assertTrue(attorney != null);
        assertEquals(attorney.getUsername(), "johndoe");
        assertEquals(attorney.getId(),"0");
        // We don't have attorney with id = 1, should return null
        attorney = testAttorneyModel.getAttorney("1");
        assertNull(attorney);
    }

    @Test
    public void testSave() throws Exception {
        // Trying to save our testAttorney, which should returns 200
        assertEquals(testAttorneyModel.save(testAttorney).isOk(), Boolean.TRUE);
        // Trying to save a null or bad Attorney value, which should returns 0 (made up error call)
        assertNull(testAttorneyModel.save(null));
    }

    @Test
    public void testDelete() throws Exception {
        // Trying to save our testAttorney, which should returns 200
        assertEquals(testAttorneyModel.save(testAttorney).isOk(), Boolean.TRUE);
        // Trying to delete our testAttorney, which should returns 200
        when(modelHelper.getDeleteDocumentOptions("0","Test Revision",databaseName))
                           .thenReturn(testGoodDeleteDocumentOptions);
        assertEquals(testAttorneyModel.delete("0").isOk(), Boolean.TRUE);
    }

    private Document getTestDocument(String id, String rev, String username){
        Document doc = new Document();
        doc.setId(id);
        doc.setRev(rev);
        doc.setProperties(new HashMap<String, Object>() {{
            put("username",username);
        }});

        return doc;
    }

    // TODO: add test for addCaseToAttorney
    // TODO: add test for deleteCaseFromAttorney
    // TODO: add test for getCasesForAttorney
    // TODO: add test for getCasesByIdForAttorney
    // TODO: add test for getAll
}