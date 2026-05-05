package ht.lafleur;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

public class Test4 {

    public static void main(String[] args) {

        String geminiKey = System.getenv("GEMINI_KEY");

        if (geminiKey == null || geminiKey.isBlank()) {
            System.err.println("Environment variable GEMINI_KEY is not set. Set it and retry.");
            System.exit(1);
        }

        // Création du modèle d'embeddings avec le builder
        EmbeddingModel model = GoogleAiEmbeddingModel.builder()
                .apiKey(geminiKey)
                .modelName("gemini-embedding-2")
                .taskType(GoogleAiEmbeddingModel.TaskType.SEMANTIC_SIMILARITY)
                .outputDimensionality(300)
                .timeout(Duration.ofSeconds(20))
                .build();

        // Définition des couples de phrases
        String[][] couples = {
            {"Le chat dort sur le canapé.", "Le félin se repose sur le sofa."}, // Ces phrases sont similaires en contenu
            {"J'aime la programmation en Java.", "Le football est mon sport préféré."}, // Ces phrases sont différentes en contenu
            {"Il fait beau aujourd'hui.", "Le soleil brille ce matin."}, // Ces phrases sont liées par le thème de la météo
            {"La voiture roule vite.", "L'avion décolle de l'aéroport."} // Ces phrases sont différentes en contenu mais partagent le thème du transport
        };

        System.out.println("=== Test 4 : Similarité cosinus entre phrases ===\n");

        for (String[] couple : couples) {
            String phrase1 = couple[0];
            String phrase2 = couple[1];

            // Génération des embeddings
            Response<Embedding> response1 = model.embed(phrase1);
            Response<Embedding> response2 = model.embed(phrase2);

            // Récupération des vecteurs via content()
            Embedding embedding1 = response1.content();
            Embedding embedding2 = response2.content();

            // Calcul de la similarité cosinus
            double similarite = CosineSimilarity.between(embedding1, embedding2);

            System.out.println("Phrase 1 : " + phrase1);
            System.out.println("Phrase 2 : " + phrase2);
            System.out.printf("Similarité cosinus : %.4f%n", similarite);
            System.out.println("--------------------------------------------------");
        }
    }
}
