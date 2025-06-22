import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class MathFunctionsGeminiTest {
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private static final String API_KEY = "AIzaSyCRBdJEi8p7ZezUWmNg70Q6_LzmczcsMqI"; // Replace with your key
    private static final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build();

    @Test
    public void testAddition() throws IOException {
        testOperation("add");
    }

    @Test
    public void testSubtraction() throws IOException {
        testOperation("subtract");
    }

    @Test
    public void testMultiplication() throws IOException {
        testOperation("multiply");
    }

    @Test
    public void testDivision() throws IOException {
        testOperation("divide");
    }

    @Test
    public void testEdgeCases() throws IOException {
        JSONArray edgeCases = loadSavedResponse("edge_cases.json");
        
        if (edgeCases == null || edgeCases.length() == 0) {
            edgeCases = generateEdgeCases();
            saveResponse(edgeCases.toString(), "edge_cases.json");
        }
        
        runEdgeCases(edgeCases);
    }

    private void testOperation(String operation) throws IOException {
        JSONArray testCases = loadSavedResponse(operation + "_tests.json");
        
        if (testCases == null || testCases.length() == 0) {
            testCases = generateOperationTestCases(operation);
            saveResponse(testCases.toString(), operation + "_tests.json");
        }
        
        runTestCases(testCases);
    }

    private void runTestCases(JSONArray testCases) {
        for (int i = 0; i < testCases.length(); i++) {
            JSONObject testCase = testCases.getJSONObject(i);
            String operation = testCase.getString("operation");
            int a = testCase.getInt("a");
            int b = testCase.getInt("b");
            double expected = testCase.getDouble("expected");
            String description = testCase.optString("description", 
                String.format("%s(%d, %d)", operation, a, b));

            try {
                double actual;
                switch (operation) {
                    case "add":
                        actual = MathFunctions.add(a, b);
                        break;
                    case "subtract":
                        actual = MathFunctions.subtract(a, b);
                        break;
                    case "multiply":
                        actual = MathFunctions.multiply(a, b);
                        break;
                    case "divide":
                        actual = MathFunctions.divide(a, b);
                        break;
                    default:
                        fail("Unknown operation: " + operation);
                        return;
                }

                if (operation.equals("divide")) {
                    assertEquals(expected, actual, 0.001, description);
                } else {
                    assertEquals((int)expected, (int)actual, description);
                }
            } catch (ArithmeticException e) {
                if (!testCase.optBoolean("expectException", false)) {
                    fail(description + " threw unexpected exception: " + e.getMessage());
                }
                // If expectException is true, the test passes
            }
        }
    }

    private void runEdgeCases(JSONArray edgeCases) {
        for (int i = 0; i < edgeCases.length(); i++) {
            JSONObject testCase = edgeCases.getJSONObject(i);
            String operation = testCase.getString("operation");
            if (!operation.equals("add") && !operation.equals("subtract") && 
                !operation.equals("multiply") && !operation.equals("divide")) {
                continue; // Skip unknown operations
            }
            
            int a = testCase.getInt("a");
            int b = testCase.getInt("b");
            String description = testCase.optString("description", 
                String.format("%s(%d, %d)", operation, a, b));
            boolean expectException = testCase.optBoolean("expectException", false);

            try {
                switch (operation) {
                    case "add":
                        MathFunctions.add(a, b);
                        break;
                    case "subtract":
                        MathFunctions.subtract(a, b);
                        break;
                    case "multiply":
                        MathFunctions.multiply(a, b);
                        break;
                    case "divide":
                        MathFunctions.divide(a, b);
                        break;
                }
                
                if (expectException) {
                    fail(description + " should have thrown exception but didn't");
                }
            } catch (ArithmeticException e) {
                if (!expectException) {
                    fail(description + " threw unexpected exception: " + e.getMessage());
                }
                // If expectException is true, the test passes
            }
        }
    }

    private JSONArray loadSavedResponse(String filename) {
        try {
            if (!Files.exists(Paths.get(filename))) {
                return null;
            }
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            return new JSONArray(content);
        } catch (Exception e) {
            System.err.println("Warning: Failed to load saved response: " + e.getMessage());
            return null;
        }
    }

    private void saveResponse(String response, String filename) throws IOException {
        try {
            Files.write(
                Paths.get(filename),
                response.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
            System.out.println("Successfully saved response to: " + filename);
        } catch (IOException e) {
            System.err.println("Failed to save response: " + e.getMessage());
            throw e;
        }
    }

    private JSONArray generateOperationTestCases(String operation) throws IOException {
        String prompt = String.format(
            "Generate exactly 10 random test cases for %s operation in JSON array format. " +
            "Include positive, negative, and edge cases. Follow these rules:\n" +
            "1. For addition/subtraction/multiplication, use integer results only\n" +
            "2. For division, use double results (e.g., 7/3=2.333...)\n" +
            "3. For division, never use b=0 unless expectException=true\n" +
            "4. For addition/multiplication, include cases that would overflow (with expectException=true)\n" +
            "5. For subtraction, include cases with large numbers and negative results\n" +
            "Return ONLY the JSON array with this structure:\n" +
            "[\n" +
            "    {\"operation\": \"%s\", \"a\": 5, \"b\": 3, \"expected\": 8, \"description\": \"normal case\"},\n" +
            "    {\"operation\": \"%s\", \"a\": 2147483647, \"b\": 1, \"expected\": 0, \"expectException\": true, \"description\": \"integer overflow\"},\n" +
            "    {\"operation\": \"%s\", \"a\": 7, \"b\": 3, \"expected\": 2.3333333333333335, \"description\": \"fractional division\"}\n" +
            "]\n" +
            "Important: Return ONLY the JSON array, no other text or explanations.",
            operation, operation, operation, operation);

        String response = callGeminiAPI(prompt);
        
        try {
            String jsonArrayStr = extractJsonArray(response);
            JSONArray testCases = new JSONArray(jsonArrayStr);
            validateOperationTestCases(testCases, operation);
            return testCases;
        } catch (Exception e) {
            throw new IOException("Failed to parse test cases from Gemini: " + e.getMessage(), e);
        }
    }

    private JSONArray generateEdgeCases() throws IOException {
        // First try with local fallback cases
        JSONArray fallbackCases = createLocalEdgeCases();
        try {
            String prompt = "Generate edge case test cases for basic math operations (add, subtract, multiply, divide) in JSON array format. " +
                          "Include cases like:\n" +
                          "1. MAX_VALUE and MIN_VALUE operations\n" +
                          "2. Overflow cases (mark with expectException=true)\n" +
                          "3. Division by zero (mark with expectException=true)\n" +
                          "4. Large number subtractions\n" +
                          "Structure should be:\n" +
                          "[\n" +
                          "    {\"operation\": \"add\", \"a\": 2147483647, \"b\": 1, \"expectException\": true, \"description\": \"integer overflow\"},\n" +
                          "    {\"operation\": \"divide\", \"a\": 1, \"b\": 0, \"expectException\": true, \"description\": \"division by zero\"}\n" +
                          "]\n" +
                          "Important: Only include add, subtract, multiply, divide operations. " +
                          "Do NOT include 'expected' field for edge cases that expect exceptions.";

            String response = callGeminiAPI(prompt);
            
            String jsonArrayStr = extractJsonArray(response);
            JSONArray testCases = new JSONArray(jsonArrayStr);
            validateEdgeCases(testCases);
            return testCases;
        } catch (Exception e) {
            System.err.println("Warning: Failed to generate edge cases from API, using local fallback: " + e.getMessage());
            return fallbackCases;
        }
    }

private JSONArray createLocalEdgeCases() {
    return new JSONArray()
        .put(new JSONObject()
            .put("operation", "add")
            .put("a", Integer.MAX_VALUE)
            .put("b", 1)
            .put("expectException", true)
            .put("description", "integer overflow (MAX_VALUE + 1)"))
        .put(new JSONObject()
            .put("operation", "add")
            .put("a", Integer.MIN_VALUE)
            .put("b", -1)
            .put("expectException", true)
            .put("description", "integer overflow negative (MIN_VALUE + -1)"))
        .put(new JSONObject()
            .put("operation", "subtract")
            .put("a", Integer.MIN_VALUE)
            .put("b", 1)
            .put("expectException", true)
            .put("description", "integer underflow (MIN_VALUE - 1)"))
        .put(new JSONObject()
            .put("operation", "multiply")
            .put("a", Integer.MIN_VALUE)
            .put("b", 2)
            .put("expectException", true)
            .put("description", "integer overflow negative (MIN_VALUE * 2)"))
        .put(new JSONObject()
            .put("operation", "divide")
            .put("a", 1)
            .put("b", 0)
            .put("expectException", true)
            .put("description", "division by zero"))
        .put(new JSONObject()
            .put("operation", "divide")
            .put("a", Integer.MIN_VALUE)
            .put("b", -1)
            .put("expectException", true)
            .put("description", "Divide min int by -1 (overflow)"))
        .put(new JSONObject()
            .put("operation", "add")
            .put("a", Integer.MIN_VALUE)
            .put("b", Integer.MAX_VALUE)
            .put("expected", -1)
            .put("description", "MIN_VALUE + MAX_VALUE"));
}


    private void validateOperationTestCases(JSONArray testCases, String operation) {
        if (testCases.length() != 10) {
            throw new AssertionError("Expected exactly 10 test cases, got " + testCases.length());
        }
        
        for (int i = 0; i < testCases.length(); i++) {
            JSONObject testCase = testCases.getJSONObject(i);
            assertEquals(operation, testCase.getString("operation"), 
                "Wrong operation in test case " + i);
            assertTrue(testCase.has("a"), "Missing 'a' in test case " + i);
            assertTrue(testCase.has("b"), "Missing 'b' in test case " + i);
            
            if (!testCase.optBoolean("expectException", false)) {
                assertTrue(testCase.has("expected"), "Missing expected in test case " + i);
                if (operation.equals("divide")) {
                    assertNotEquals(0, testCase.getInt("b"), "Division by zero in test case " + i);
                }
            }
        }
    }

    private void validateEdgeCases(JSONArray testCases) {
        if (testCases.length() == 0) {
            throw new AssertionError("Expected at least one edge case");
        }
        
        for (int i = 0; i < testCases.length(); i++) {
            JSONObject testCase = testCases.getJSONObject(i);
            assertTrue(testCase.has("operation"), "Missing operation in edge case " + i);
            String operation = testCase.getString("operation");
            if (!operation.equals("add") && !operation.equals("subtract") && 
                !operation.equals("multiply") && !operation.equals("divide")) {
                throw new AssertionError("Invalid operation in edge case: " + operation);
            }
            
            assertTrue(testCase.has("a"), "Missing 'a' in edge case " + i);
            assertTrue(testCase.has("b"), "Missing 'b' in edge case " + i);
            
            int b = testCase.getInt("b");
            
            if (operation.equals("divide") && b == 0) {
                assertTrue(testCase.optBoolean("expectException", false), 
                    "Division by zero should expect exception in test case " + i);
            }
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
        generationConfig.put("temperature", 0.7);
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
            throw new IOException("Failed to extract text from response: " + e.getMessage());
        }
    }
}
