package it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import io.swagger.api.ApiResponseMessage;
import io.swagger.model.CaseResponse;
import io.swagger.model.ModelCase;
import org.junit.Test;

import static it.IntegrationTestConstants.CASE_RESOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CaseIT {

    private final AggregatorConnection conn = new AggregatorConnection();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addCase_addsCase() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);

        ApiResponseMessage apiResponseMessage = addCase(attorneyId, clientId);

        assertEquals(ApiResponseMessage.OK, apiResponseMessage.getCode());
        assertNotNull(apiResponseMessage.getMessage());
    }

    @Test
    public void getCases_getsCasesByAttorney() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);
        addCase(attorneyId, clientId);

        String json = conn.doGet(CASE_RESOURCE + "/" + attorneyId);
        CaseResponse caseResponse = objectMapper.readValue(json, CaseResponse.class);
        assertEquals(1, caseResponse.getClients().size());
        ModelCase modelCase = Iterables.getOnlyElement(caseResponse.getClients());
        assertEquals(attorneyId, modelCase.getAttorneyId());
        assertEquals(clientId, modelCase.getClientId());
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
                "  \"_id\": \"string\",\n" +
                "  \"_rev\": null,\n" +
                "  \"client_id\": \"" + clientId + "\",\n" +
                "  \"attorney_id\": \"" + attorneyId +"\",\n" +
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
