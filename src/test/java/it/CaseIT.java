package it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import io.swagger.api.ApiResponseMessage;
import io.swagger.model.CaseResponse;
import io.swagger.model.ModelCase;
import org.junit.Test;

import static it.IntegrationTestConstants.CASE_RESOURCE;
import static org.junit.Assert.*;

public class CaseIT {

    private final AggregatorConnection conn = new AggregatorConnection();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addCase_addsCase() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);

        ApiResponseMessage apiResponseMessage = addCase(attorneyId, clientId);

        assertEquals(ApiResponseMessage.OK, apiResponseMessage.getCode());
        assertEquals(attorneyId, apiResponseMessage.getMessage());
    }

    @Test
    public void getAllCasesByAttorney_getsAllCasesByAttorney() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);
        addCase(attorneyId, clientId);
        addCase(attorneyId, clientId);

        CaseResponse caseResponse = getCasesByAttorney(attorneyId);
        assertEquals(2, caseResponse.getClients().size());
        for (ModelCase modelCase : caseResponse.getClients()) {
            assertEquals(attorneyId, modelCase.getAttorneyId());
            assertEquals(clientId, modelCase.getClientId());
            assertNotNull(modelCase.getId());
        }
    }

    @Test
    public void getCaseByAttorney_getsCaseByAttorneyAndId() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);
        addCase(attorneyId, clientId);

        CaseResponse caseResponseByAttorney = getCasesByAttorney(attorneyId);
        assertEquals(1, caseResponseByAttorney.getClients().size());
        String caseId = Iterables.getOnlyElement(caseResponseByAttorney.getClients()).getId();

        CaseResponse caseResponseById = getCaseByCaseId(attorneyId, caseId);
        assertEquals(1, caseResponseById.getClients().size());
        assertEquals(caseId, Iterables.getOnlyElement(caseResponseById.getClients()).getId());
    }

    @Test
    public void deleteCaseById_deletesCase() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);
        addCase(attorneyId, clientId);

        CaseResponse caseResponseByAttorney = getCasesByAttorney(attorneyId);
        assertEquals(1, caseResponseByAttorney.getClients().size());
        String caseId = Iterables.getOnlyElement(caseResponseByAttorney.getClients()).getId();

        String jsonCaseById = conn.doDelete(CASE_RESOURCE + "/" + attorneyId + "/" + caseId);
        ApiResponseMessage apiResponseMessage = objectMapper.readValue(jsonCaseById, ApiResponseMessage.class);
        assertEquals(ApiResponseMessage.OK, apiResponseMessage.getCode());
        assertEquals(attorneyId, apiResponseMessage.getMessage());

        CaseResponse caseResponseAfterDelete = getCasesByAttorney(attorneyId);
        assertEquals(0, caseResponseAfterDelete.getClients().size());
    }

    @Test
    public void getReportForCase_getsReport() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);
        addCase(attorneyId, clientId);

        CaseResponse caseResponseByAttorney = getCasesByAttorney(attorneyId);
        assertEquals(1, caseResponseByAttorney.getClients().size());
        String caseId = Iterables.getOnlyElement(caseResponseByAttorney.getClients()).getId();

        String jsonReport = conn.doGet(CASE_RESOURCE + "/report/" + attorneyId + "/" + caseId);
        assertTrue(jsonReport.contains("magic")); // Not implemented yet
    }

    private CaseResponse getCasesByAttorney(String attorneyId) throws JsonProcessingException {
        String json = conn.doGet(CASE_RESOURCE + "/" + attorneyId);
        return objectMapper.readValue(json, CaseResponse.class);
    }

    private CaseResponse getCaseByCaseId(String attorneyId, String caseId) throws JsonProcessingException {
        String jsonCaseById = conn.doGet(CASE_RESOURCE + "/" + attorneyId + "/" + caseId);
        return objectMapper.readValue(jsonCaseById, CaseResponse.class);
    }

    private String addAttorney() {

        TestAttorney testAttorney = new TestAttorney.Builder()
                .withoutCase()
                .create();
        return testAttorney.getAttorneyId();
    }

    private String addClient(String attorneyId) {
        return new TestClient.Builder(attorneyId)
                .create()
                .getClientId();
    }

    private ApiResponseMessage addCase(String attorneyId, String clientId) throws JsonProcessingException {

        String json = conn.doPost(CASE_RESOURCE + "?attorneyId=" + attorneyId, getNewCaseJson(attorneyId, clientId));
        return objectMapper.readValue(json, ApiResponseMessage.class);
    }

    private String getNewCaseJson(String attorneyId, String clientId) {
        return "{\n" +
                "  \"_id\": null,\n" +
                "  \"_rev\": null,\n" +
                "  \"client_id\": \"" + clientId + "\",\n" +
                "  \"attorney_id\": \"" + attorneyId + "\",\n" +
                "  \"possible_charges\": [\n" +
                "    {\n" +
                "      \"_id\": null,\n" +
                "      \"_rev\": null,\n" +
                "      \"trial_type\": \"Guilty plea\",\n" +
                "      \"disposition_charged_class\": \"string\",\n" +
                "      \"charge_code\": \"Administration of Justice\",\n" +
                "      \"attempted\": true,\n" +
                "      \"primary\": true,\n" +
                "      \"controlled_substance_quantity_level\": 17,\n" +
                "      \"possible_sentences\": [\n" +
                "        {\n" +
                "          \"_id\": null,\n" +
                "          \"_rev\": null,\n" +
                "          \"chargeDisposition\": \"BFW\",\n" +
                "          \"phase\": \"string\",\n" +
                "          \"sentence_type\": \"Prison Only\",\n" +
                "          \"commitmentTerm\": 0,\n" +
                "          \"commitmentUnit\": \"string\",\n" +
                "          \"commitmentType\": \"string\",\n" +
                "          \"minimum_incarceration_months\": 0,\n" +
                "          \"maximum_incarceration_months\": 0,\n" +
                "          \"probation_term_months\": 0,\n" +
                "          \"minimum_probation_months\": 0,\n" +
                "          \"maximum_probation_months\": 0,\n" +
                "          \"fine_dollars\": 0,\n" +
                "          \"minimum_fine_dollars\": 0,\n" +
                "          \"maximum_fine_dollars\": 0,\n" +
                "          \"community_service_hours\": 0,\n" +
                "          \"restitution\": \"string\",\n" +
                "          \"alternative_sentence\": \"string\",\n" +
                "          \"suspended_sentence\": true\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
