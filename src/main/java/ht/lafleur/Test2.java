package ht.lafleur;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.output.TokenUsage;

import java.util.List;
import java.util.Locale;

public class Test2 {

    private static final String QUESTION = "Quel est le capital d'Haiti ?";
    private static final double INPUT_PRICE_PER_MILLION_TOKENS = 0.30;
    private static final double OUTPUT_PRICE_PER_MILLION_TOKENS = 2.50;

    public static void main(String[] args) {
        String geminiKey = System.getenv("GEMINI_KEY");
        if (geminiKey == null || geminiKey.isBlank()) {
            System.err.println("Environment variable GEMINI_KEY is missing.");
            return;
        }

        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(geminiKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();

        ChatRequest request = ChatRequest.builder()
                .messages(List.of(UserMessage.from(QUESTION)))
                .build();

        ChatResponse response = model.chat(request);

        System.out.println("Question: " + QUESTION);
        System.out.println("Réponse: " + response.aiMessage().text());

        TokenUsage tokenUsage = response.tokenUsage();
        if (tokenUsage == null) {
            System.out.println("Usage tokens: indisponible pour cette réponse.");
            return;
        }

        long inputTokens = tokenUsage.inputTokenCount();
        long outputTokens = tokenUsage.outputTokenCount();
        long totalTokens = tokenUsage.totalTokenCount();

        double inputCost = inputTokens * INPUT_PRICE_PER_MILLION_TOKENS / 1_000_000.0;
        double outputCost = outputTokens * OUTPUT_PRICE_PER_MILLION_TOKENS / 1_000_000.0;
        double totalCost = inputCost + outputCost;
        double requestsForOneDollar = totalCost == 0.0 ? Double.POSITIVE_INFINITY : 1.0 / totalCost;

        System.out.println("Tokens entrée: " + inputTokens);
        System.out.println("Tokens sortie: " + outputTokens);
        System.out.println("Tokens totaux: " + totalTokens);
        System.out.printf(Locale.US, "Coût entrée: $%.8f%n", inputCost);
        System.out.printf(Locale.US, "Coût sortie: $%.8f%n", outputCost);
        System.out.printf(Locale.US, "Coût total: $%.8f%n", totalCost);
        System.out.printf(Locale.US, "Requêtes similaires pour dépenser 1$: %.2f%n", requestsForOneDollar);
    }
}
