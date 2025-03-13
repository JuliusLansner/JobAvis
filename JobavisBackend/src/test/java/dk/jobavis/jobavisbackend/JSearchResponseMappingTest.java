package dk.jobavis.jobavisbackend;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import org.junit.jupiter.api.Test;

public class JSearchResponseMappingTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testDeserializeJsearchResponse() throws Exception{
        String sampleJson = "{\n" +
                "  \"status\": \"OK\",\n" +
                "  \"request_id\": \"some-id\",\n" +
                "  \"parameters\": {\n" +
                "    \"query\": \"developer\",\n" +
                "    \"page\": 1,\n" +
                "    \"num_pages\": 1,\n" +
                "    \"date_posted\": \"all\",\n" +
                "    \"country\": \"dk\",\n" +
                "    \"language\": \"da\"\n" +
                "  },\n" +
                "  \"data\": [\n" +
                "    { \"job_id\": \"123\", \"job_title\": \"Backend Developer\", \"employer_name\": \"Novicell Denmark\" }\n" +
                "  ]\n" +
                "}";
        JSearchResponse response = mapper.readValue(sampleJson,JSearchResponse.class);
        assertEquals("OK",response.getStatus());
        assertNotNull(response.getParameters());
        assertEquals("developer",response.getParameters().getQuery());
        assertEquals(1,response.getData().size());
        assertEquals("123",response.getData().get(0).getJob_id());

    }


}
