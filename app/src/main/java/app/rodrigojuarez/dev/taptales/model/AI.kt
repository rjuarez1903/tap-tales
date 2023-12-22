package app.rodrigojuarez.dev.taptales.model

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import java.time.LocalDateTime

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
                  "slug": "<your-imaginative-title>",
                  "title": "<Your imaginative title>",
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
                children. Each story is custom-made for a specific child, considering their age.
                Please process the following data to craft your story:
                
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
                - Originality: The story must be original. 
                
                Structure your story in this JSON format:
                
                $template
                
                Focus on creating a magical and immersive narrative that sparks imagination and joy 
                in a young child's mind.
                
                Important: do not include any text or comments outside the given JSON structure.
                If for any reason, you're unable to create a story, just return "false".
            """.trimIndent()
        val response = generativeModel.generateContent(prompt)
        Log.d("AI","AI response: ${response.text}")
        val tale = if (response.text == "false") {
            null
        } else {
            Gson().fromJson(response.text, Tale::class.java).apply {
                words = calculateTotalWords(paragraphs)
                date = LocalDateTime.now()
            }
        }
        return tale
    }
}

private fun calculateTotalWords(paragraphs: List<String>): String {
    return paragraphs.sumOf { paragraph ->
        paragraph.trim().split("\\s+".toRegex()).size
    }.toString()
}