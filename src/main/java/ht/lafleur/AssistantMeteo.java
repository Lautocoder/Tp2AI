package ht.lafleur;

import dev.langchain4j.service.SystemMessage;

public interface AssistantMeteo {

    @SystemMessage("Tu es un assistant de voyage. Tu peux répondre à toutes les questions, " +
            "notamment sur la météo en utilisant tes outils, mais aussi sur les attractions, " +
            "les musées, les conseils de voyage, etc.")
    String chat(String question);
}
