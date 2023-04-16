import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.CoreDocument;

import java.util.Properties;

import java.io.FileWriter;
import java.io.IOException;


class SentimentAnalysis {
    private static int positiveCount = 0;
    private static int negativeCount = 0;
    private static int neutralCount = 0;

    public static void analyzeSentiment(String review){
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        props.setProperty("tokenize.language", "en");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        CoreDocument document = new CoreDocument(review);
        pipeline.annotate(document);

        for (CoreSentence sentence : document.sentences()){
            String sentiment = sentence.sentiment();
            System.out.println("Sentiment of " + sentence.text() + " is " + sentiment);
            if (sentiment.equals("Positive")) {
                System.out.println("The review is positive.");
                positiveCount++;
                saveSentimentToFile(sentence.text(), "positive3.csv");
            } else if (sentiment.equals("Negative")) {
                System.out.println("The review is negative.");
                negativeCount++;
                saveSentimentToFile(sentence.text(), "negative3.csv");
            } else {
                System.out.println("The sentiment of the review is neutral.");
                neutralCount++;
                saveSentimentToFile(sentence.text(), "neutral3.csv");
            }
        }
    }

    public static void saveSentimentToFile(String text, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.append(text);
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving sentiment to file: " + e.getMessage());
        }
    }

    public static void printSentimentCounts() {
        System.out.println("Positive Count: " + positiveCount);
        System.out.println("Negative Count: " + negativeCount);
        System.out.println("Neutral Count: " + neutralCount);
    }

    public static void main(String[] args) {
        String text = "sample text";
        String[] splittedData = text.split("\\+");
        for (String s : splittedData) {
            analyzeSentiment(s);
        }
        printSentimentCounts();
    }
}
