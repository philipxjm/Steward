package edu.steward.Sentiment;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

/**
 * Created by Philip on 4/16/17.
 */
public class SentimentAnalyzer {
  public SentimentAnalyzer() {

  }

  public int findSentiment(String line) {

    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    int mainSentiment = 0;
    if (line != null && line.length() > 0) {
      int longest = 0;
      Annotation annotation = pipeline.process(line);
      for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
        Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
        int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
        String partText = sentence.toString();
        if (partText.length() > longest) {
          mainSentiment = sentiment;
          longest = partText.length();
        }
      }
    }
    if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
      return -1;
    }
    return mainSentiment;
  }
}
