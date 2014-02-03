package com.kubaspatny.speech.recognizer;

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

public class UtilTest {

    public static void main(String[] args) {

        String s = "{\"status\":0,\"id\":\"8c784b5667acfa094d45849e5e51e7e6-1\",\"hypotheses\":[{\"utterance\":\"jessica\",\"confidence\":0.79475987},{\"utterance\":\"giffgaff\"},{\"utterance\":\"yes\"},{\"utterance\":\"giff gaff\"},{\"utterance\":\"yes car\"},{\"utterance\":\"gift\"},{\"utterance\":\"get car\"}]}";
        //System.out.println(s);
        getUtterances(s);

    }

    public static void getUtterances(String response){

        int i = response.indexOf("[");
        int j = response.indexOf("]");
        String utterances = response.substring(i+1,j);
        String[] ut = utterances.split(",");

        String answer = ut[0].split(":")[1].replaceAll("\"","");
        System.out.println(answer);
        String confidence = ut[1].split(":")[1].replaceAll("}","");
        System.out.println(confidence);
        Float f = Float.parseFloat(confidence);

    }


}
