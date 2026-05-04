package ht.lafleur;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

public class Test3 {
        public static void main(String[] args) {

            String geminiKey = System.getenv("GEMINI_KEY");
            if (geminiKey == null || geminiKey.isBlank()) {
                System.err.println("Environment variable GEMINI_KEY is not set. Set it and retry.");
                System.exit(1);
            }

            String templateText = """
                    Traduis le texte suivant en anglais : {{it}}
                    """;

            ChatModel model = GoogleAiGeminiChatModel.builder()
                    .apiKey(geminiKey)
                    .modelName("gemini-2.5-flash")
                    .temperature(0.7)
                    .build();



            PromptTemplate template = PromptTemplate.from(templateText);

            Prompt prompt = template.apply("Bonjour, comment ça va ?");

            String reponse = model.chat(prompt.text());

            System.out.println("Prompt : " + prompt.text());
            System.out.println("Réponse : " + reponse);
        }
}
