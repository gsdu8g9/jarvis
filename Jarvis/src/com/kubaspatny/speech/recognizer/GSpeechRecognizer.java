package com.kubaspatny.speech.recognizer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

/*
 * Copyright 2013 Jakub Spatny
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class GSpeechRecognizer implements SpeechRecognizer {

    private static final String url = "https://www.google.com/speech-api/v1/recognize?xjerr=1&client=speech2text";
    private int profanityFilter;
    private String language;
    private int maxResults;
    private String requestURL;

    private final static String GRecognizeURL = "https://www.google.com/speech-api/v1/recognize?xjerr=1&client=speech2text";

    private GSpeechRecognizer() {
        this.profanityFilter = 1;
        this.language = "en-US";
        this.maxResults = 1;
    }

    @Override
    public GResponse recognizeWavFile(File wavFile) {
        return null;
    } // not done

    @Override
    public GResponse recognizeWavFile(String wavFile) {
        return null;
    } // not done

    @Override
    public GResponse recognizeFlacFile(File flacFile) {

        try {
            URL url = new URL(this.requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "audio/x-flac; rate=16000");
            connection.setRequestProperty("User-Agent", "speech2text");
            connection.setConnectTimeout(60000);
            connection.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(Files.readAllBytes(flacFile.toPath()));
            wr.flush();
            wr.close();
            connection.disconnect();

            // flacFile.delete();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            in.close();

            return parseResponse(response);

        } catch (Exception e) {
            System.err.println("EXCEPTION in recognizeFlacFile():");
            e.printStackTrace();
        }

        return parseResponse("");
    }


    @Override
    public GResponse recognizeFlacFile(String flacFile) {
        File file = new File(flacFile);
        return recognizeFlacFile(file);
    }

    private GResponse parseResponse(String response) {
        GResponse gr = new GResponse();

        if (!response.contains("utterance")) {
            gr.setStatusOK(false);
            return gr;
        }

        int i = response.indexOf("[");
        int j = response.indexOf("]");
        String utterances = response.substring(i + 1, j);
        String[] ut = utterances.split(",");

        String answer = ut[0].split(":")[1].replaceAll("\"", "");
        String confidence = ut[1].split(":")[1].replaceAll("}", "");

        gr.setAnswer(answer);
        gr.setConfidence(Float.parseFloat(confidence));
        gr.setStatusOK(true);

        return gr;

    }

    protected void setProfanityFilter(boolean profanityFilter) {
        if (!profanityFilter) this.profanityFilter = 0;
    }

    protected void setLanguage(String language) {
        this.language = language;
    }

    protected void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    protected void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }


    public static class GResponse implements Response {

        private String answer = "";
        private float confidence = 0;
        private boolean statusOK = false;

        private GResponse() {
        }

        @Override
        public String getAnswer() {
            return answer;
        }

        @Override
        public float getConfidence() {
            return confidence;
        }

        @Override
        public boolean statusOK() {
            return statusOK;
        }

        private void setAnswer(String answer) {
            this.answer = answer;
        }

        private void setConfidence(float confidence) {
            this.confidence = confidence;
        }

        private void setStatusOK(boolean statusOK) {
            this.statusOK = statusOK;
        }

        private static GResponse getResponse(String responseFromGoogle) {

            GResponse response = new GResponse();
            if (!responseFromGoogle.contains("utterance")) {
                response.setStatusOK(false);
            } else {

            }

            return response;

        }
    } // GResponse

    public static class Builder implements RecognizerBuilder {

        private GSpeechRecognizer gsr;

        public Builder() {
            gsr = new GSpeechRecognizer();
        }

        @Override
        public RecognizerBuilder setProfanityFilter(boolean profanityFilter) {
            gsr.setProfanityFilter(profanityFilter);
            return this;
        }

        @Override
        public RecognizerBuilder setLanguage(String language) {
            String lang;
            switch (language.toLowerCase()) {
                case "en":
                    lang = "en-US";
                    break;
                case "us":
                    lang = "en-US";
                    break;
                case "uk":
                    lang = "en-UK";
                    break;
                case "cs":
                    lang = "cs";
                    break;
                default:
                    lang = "en-US";
            }

            gsr.setLanguage(lang);
            return this;
        }

        @Override
        public RecognizerBuilder setMaxResults(int maxResults) {
            gsr.setMaxResults(maxResults);
            return this;
        }

        @Override
        public SpeechRecognizer build() {

            StringBuilder sb = new StringBuilder().append(GRecognizeURL);
            sb.append("&lang=").append(this.gsr.language);
            sb.append("&pfilter=").append(this.gsr.profanityFilter);
            sb.append("&maxreuslts=").append(this.gsr.maxResults);
            this.gsr.setRequestURL(sb.toString());
            return this.gsr;
        }
    }


} // end of GSpeechRecognizer class

