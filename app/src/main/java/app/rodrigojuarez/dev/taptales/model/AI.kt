package app.rodrigojuarez.dev.taptales.model

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson

object AI {
    private val generativeModel = GenerativeModel(
        // Use a model that's applicable for your use case (see "Implement basic use cases" below)
        modelName = "gemini-pro", // "models/gemini-pro"
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = ""
    )

    suspend fun requestTale(childName: String, age: String, language: String): Tale? {
        val template = """
                {
                  "slug": "your-imaginative-title",
                  "title": "<Your imaginative title>",
                  "readingTime": "<minutes to read the story>",
                  "paragraphs": [
                      "<Introduction of the story and setting>",
                      "<Development of the plot>",
                      "<Continuation of the child's adventure>",
                      "<A twist or a new discovery>",
                      "<The climax of the story>",
                      "<The unfolding of events leading to the end>",
                      "<A heartwarming conclusion>"
                    ]
                }
            """.trimIndent()
        val prompt = """
                You are an imaginative writer tasked with creating personalized short stories for 
                children. Each story is custom-made for a specific child, based on dynamic input data.
                Please process the following dynamic input data to craft your story:
                
                ```
                { 
                 "name": $childName, 
                 "age": $age, 
                 "language": $language 
                }
                ```
                
                Guidelines for your story:
                
                - Title: Create a captivating title within a five-word limit.
                - Reading Time: The story should be readable in 3 to 5 minutes.
                - Story Content: Compose a narrative that is both engaging and matches the 
                requested language. The story should contain around 7 paragraphs, with a total 
                character count of 2000 to 2500 characters.
                - Originality: The story must be original and not a translation. 
                It should be specifically crafted for the child, considering their age.
                
                Structure your story in this JSON format:
                
                $template
                
                Focus on creating a magical and immersive narrative that sparks imagination and joy in a young child's mind.
                
                Important: do not include any text or comments outside the given JSON structure.
            """.trimIndent()
        val response = generativeModel.generateContent(prompt)
        println("Respuesta de la IA: ${response.text}")
        return if (response.text == "false") {
            null
        } else {
            val tale = Gson().fromJson(response.text, Tale::class.java)
            tale
        }
    }
}