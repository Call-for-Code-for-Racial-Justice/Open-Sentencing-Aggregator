package it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.ApiResponseMessage;
import io.swagger.model.Attorney;
import io.swagger.model.AttorneyResponse;
import org.junit.Test;

import static it.IntegrationTestConstants.ATTORNEY_RESOURCE;
import static org.junit.Assert.*;

public class AttorneyIT {

    private final AggregatorConnection conn = new AggregatorConnection();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addAttorney_withoutCase_addsAttorney() {

        ApiResponseMessage apiResponseMessage = new TestAttorney.Builder()
                .withoutCase()
                .create()
                .getApiResponseMessage();
        assertEquals(ApiResponseMessage.OK, apiResponseMessage.getCode());
        assertNotNull(apiResponseMessage.getMessage());
    }

    @Test
    public void addAttorney_withCase_addsAttorney() {

        ApiResponseMessage apiResponseMessage = new TestAttorney.Builder()
                .withCase()
                .create()
                .getApiResponseMessage();
        assertEquals(ApiResponseMessage.OK, apiResponseMessage.getCode());
        assertNotNull(apiResponseMessage.getMessage());
    }

    @Test
    public void getAllAttorneys_getsAttorney() throws Exception {

        String attorneyId = addAttorney();

        String json = conn.doGet(ATTORNEY_RESOURCE);
        AttorneyResponse attorneyResponse = objectMapper.readValue(json, AttorneyResponse.class);
        assertEquals((Integer) 200, attorneyResponse.getCode());
        assertNull(attorneyResponse.getError());
        assertNotNull(getAttorney(attorneyResponse, attorneyId));
    }

    private Attorney getAttorney(String attorneyId) {
        try {
            String json = conn.doGet(ATTORNEY_RESOURCE);
            AttorneyResponse attorneyResponse = objectMapper.readValue(json, AttorneyResponse.class);
            return getAttorney(attorneyResponse, attorneyId);
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Attorney getAttorney(AttorneyResponse attorneyResponse, String attorneyId) {

        Attorney result = null;
        for (Attorney attorney : attorneyResponse.getAttorney()) {
            if (attorney.getId().equals(attorneyId)) {
                result = attorney;
                break;
            }
        }

        return result;
    }

    @Test
    public void findAttorney_findsAttorney() throws JsonProcessingException {

        String attorneyId = addAttorney();

        String json = conn.doGet(ATTORNEY_RESOURCE + "/" + attorneyId);
        Attorney attorney = objectMapper.readValue(json, Attorney.class);
        assertEquals(attorneyId, attorney.getId());
    }

    @Test
    public void deleteAttorney_deletesAttorney() throws JsonProcessingException {

        String attorneyId = addAttorney();

        String deleteResponseJson = conn.doDelete(ATTORNEY_RESOURCE + "/" + attorneyId);
        ApiResponseMessage apiResponseMessage = objectMapper.readValue(deleteResponseJson, ApiResponseMessage.class);
        assertEquals(ApiResponseMessage.OK, apiResponseMessage.getCode());
        assertEquals(attorneyId, apiResponseMessage.getMessage());

        assertNull(getAttorney(attorneyId));
    }

    private String addAttorney() {
       return new TestAttorney.Builder()
                .withCase()
                .create()
                .getAttorneyId();
    }
}
