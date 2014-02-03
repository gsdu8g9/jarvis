package test;

import com.kubaspatny.speech.recognizer.GSpeechRecognizer;
import com.kubaspatny.speech.texttospeech.Language;
import com.kubaspatny.speech.texttospeech.SpeakUtil;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Kuba
 * Date: 21.7.13
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {

        try {

            File f = new File("src/out.flac");

            GSpeechRecognizer gsr = (GSpeechRecognizer) new GSpeechRecognizer.Builder().setLanguage("en").setMaxResults(10).setProfanityFilter(false).build();
            GSpeechRecognizer.GResponse g = gsr.recognizeFlacFile(f);

            boolean status = g.statusOK();
            String answer = g.getAnswer();
            float confidence = g.getConfidence();

            System.out.println("You said: " + answer);


            SpeakUtil.say("You said: " + g.getAnswer(), Language.getInstance("en"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
