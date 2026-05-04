package ht.lafleur;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class Test1 {
    public static void main(String[] args) {
        String geminiKey = System.getenv("GEMINI_KEY");

        if (geminiKey == null || geminiKey.isBlank()) {
            System.err.println("Environment variable GEMINI_KEY is not set. Set it and retry.");
            System.exit(1);
        }

        ChatModel model = GoogleAiGeminiChatModel.builder()
                    .apiKey(geminiKey)
                    .modelName("gemini-2.5-flash")
                    .temperature(0.7)
                    .build();

        // Test 1 : posez une question simple.
        String question1 = "Quel est le capital d'Haiti ?";
        String response1 = model.chat(question1);

        System.out.println(question1);
        System.out.println(response1);

        // Test 2 : demandez l'heure qu'il est
        String question2 = "Quelle heure il est ?";
        String response2 = model.chat(question2);

        System.out.println(question2);
        System.out.println(response2);

        /* Test 3 :
                1. Dans une première "question", dites "Bonjour" et présentez-vous en donnant votre nom.
                2. Dans une deuxième question, demandez votre nom.
        */
        String question3_1 = "Bonjour, je suis Stanley LAFLEUR. Developpeur Java et passionné d'IA.";
        String response3_1 = model.chat(question3_1);

        String question3_2 = "Quel est mon nom ?";
        String response3_2 = model.chat(question3_2);

        System.out.println(question3_1);
        System.out.println(response3_1);
        System.out.println(question3_2);
        System.out.println(response3_2);
    }
}
