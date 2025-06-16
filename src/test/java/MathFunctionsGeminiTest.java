import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MathFunctionsGeminiTest {
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private static final String API_KEY = ""; // Replace with your key
    private static final String SAVED_RESPONSE_FILE = "saved_gemini_response.json";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Random random = new Random();

    @Test
    public void testAllOperations() throws IOException {
        // Get test cases from Gemini or saved file
        JSONArray testCases = loadSavedResponse();
        
        if (testCases == null || testCases.length() == 0) {
            testCases = generateTestCases();
            saveResponse(testCases.toString());
        }
        
        // Run the tests
        runTestCases(testCases);
    }

    private void runTestCases(JSONArray testCases) {
        for (int i = 0; i < testCases.length(); i++) {
            JSONObject testCase = testCases.getJSONObject(i);
            String operation = testCase.getString("operation");
            int a = testCase.getInt("a");
            int b = testCase.getInt("b");
            double expected = testCase.getDouble("expected");

            switch (operation) {
                case "add":
                    assertEquals(expected, MathFunctions.add(a, b), 
                        String.format("Addition failed for %d + %d", a, b));
                    break;
                case "subtract":
                    assertEquals(expected, MathFunctions.subtract(a, b),
                        String.format("Subtraction failed for %d - %d", a, b));
                    break;
                case "multiply":
                    assertEquals(expected, MathFunctions.multiply(a, b),
                        String.format("Multiplication failed for %d * %d", a, b));
                    break;
                case "divide":
                    assertEquals(expected, MathFunctions.divide(a, b), 0.001,
                        String.format("Division failed for %d / %d", a, b));
                    break;
                default:
                    fail("Unknown operation: " + operation);
            }
        }
    }

    private JSONArray loadSavedResponse() {
        try {
            if (!Files.exists(Paths.get(SAVED_RESPONSE_FILE))) {
                return null;
            }
            String content = new String(Files.readAllBytes(Paths.get(SAVED_RESPONSE_FILE)));
            return new JSONArray(content);
        } catch (Exception e) {
            System.err.println("Warning: Failed to load saved response: " + e.getMessage());
            return null;
        }
    }

    private void saveResponse(String response) throws IOException {
        try {
            Files.write(
                Paths.get(SAVED_RESPONSE_FILE),
                response.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
            System.out.println("Successfully saved Gemini response to: " + SAVED_RESPONSE_FILE);
        } catch (IOException e) {
            System.err.println("Failed to save Gemini response: " + e.getMessage());
            throw e;
        }
    }

    private JSONArray generateTestCases() throws IOException {
        String prompt = "Generate exactly 5 test cases for basic math operations (addition, subtraction, " +
                      "multiplication, division) in JSON array format. Include both positive and " +
                      "negative numbers. For division, ensure divisors aren't zero. " +
                      "Return ONLY the JSON array with this structure:\n" +
                      "[\n" +
                      "    {\"operation\": \"add\", \"a\": 5, \"b\": 3, \"expected\": 8},\n" +
                      "    {\"operation\": \"subtract\", \"a\": 10, \"b\": 7, \"expected\": 3},\n" +
                      "    {\"operation\": \"multiply\", \"a\": 4, \"b\": -2, \"expected\": -8},\n" +
                      "    {\"operation\": \"divide\", \"a\": 10, \"b\": 2, \"expected\": 5},\n" +
                      "    {\"operation\": \"add\", \"a\": -5, \"b\": -3, \"expected\": -8}\n" +
                      "]\n" +
                      "Important: Return ONLY the JSON array, no other text or explanation.";

        String response = callGeminiAPI(prompt);
        
        try {
            String jsonArrayStr = extractJsonArray(response);
            JSONArray testCases = new JSONArray(jsonArrayStr);
            validateTestCases(testCases);
            return testCases;
        } catch (Exception e) {
            throw new IOException("Failed to parse test cases from Gemini: " + e.getMessage() + 
                                "\nResponse was: " + response, e);
        }
    }

    private String extractJsonArray(String response) {
        int start = response.indexOf('[');
        int end = response.lastIndexOf(']');
        
        if (start == -1 || end == -1 || start >= end) {
            throw new IllegalArgumentException("No JSON array found in response");
        }
        
        return response.substring(start, end + 1);
    }

    private void validateTestCases(JSONArray testCases) {
        if (testCases.length() != 5) {
            throw new AssertionError("Expected exactly 5 test cases, got " + testCases.length());
        }
        
        for (int i = 0; i < testCases.length(); i++) {
            JSONObject testCase = testCases.getJSONObject(i);
            assertTrue(testCase.has("operation"), "Missing operation in test case " + i);
            assertTrue(testCase.has("a"), "Missing 'a' in test case " + i);
            assertTrue(testCase.has("b"), "Missing 'b' in test case " + i);
            assertTrue(testCase.has("expected"), "Missing expected in test case " + i);
            
            String operation = testCase.getString("operation");
            if (!operation.matches("add|subtract|multiply|divide")) {
                throw new AssertionError("Invalid operation in test case " + i + ": " + operation);
            }
            
            if (operation.equals("divide")) {
                assertNotEquals(0, testCase.getInt("b"), "Division by zero in test case " + i);
            }
        }
    }

    private String callGeminiAPI(String prompt) throws IOException {
        JSONObject requestBody = new JSONObject();
        JSONArray contents = new JSONArray();
        JSONObject content = new JSONObject();
        JSONArray parts = new JSONArray();
        JSONObject part = new JSONObject();
        
        part.put("text", prompt);
        parts.put(part);
        content.put("parts", parts);
        contents.put(content);
        requestBody.put("contents", contents);
        
        JSONArray safetySettings = new JSONArray();
        JSONObject setting = new JSONObject();
        setting.put("category", "HARM_CATEGORY_DANGEROUS_CONTENT");
        setting.put("threshold", "BLOCK_ONLY_HIGH");
        safetySettings.put(setting);
        requestBody.put("safetySettings", safetySettings);
        
        JSONObject generationConfig = new JSONObject();
        generationConfig.put("temperature", 0.5);
        generationConfig.put("topP", 1);
        generationConfig.put("maxOutputTokens", 2000);
        requestBody.put("generationConfig", generationConfig);
        
        RequestBody body = RequestBody.create(
            requestBody.toString(), 
            MediaType.parse("application/json; charset=utf-8")
        );
        
        Request request = new Request.Builder()
            .url(GEMINI_API_URL + "?key=" + API_KEY)
            .post(body)
            .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API call failed: " + response.code() + 
                    " - " + response.body().string());
            }
            
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            
            return extractResponseText(jsonResponse);
        }
    }
    
    private String extractResponseText(JSONObject response) throws IOException {
        try {
            JSONArray candidates = response.getJSONArray("candidates");
            JSONObject firstCandidate = candidates.getJSONObject(0);
            JSONObject content = firstCandidate.getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");
            return parts.getJSONObject(0).getString("text");
        } catch (Exception e) {
            throw new IOException("Failed to extract text from response: " + e.getMessage() + 
                               "\nResponse: " + response.toString());
        }
    }
}
