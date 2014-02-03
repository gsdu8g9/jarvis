package com.kubaspatny.speech.texttospeech;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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

public final class SpeakUtil {

    private static final String G_TRANSLATE_AUDIO = "http://translate.google.com/translate_tts?";

    private SpeakUtil(){

    }

    public static void say(String text, Language language){

        try {

            String urlAddress = new StringBuilder(G_TRANSLATE_AUDIO).append("q=").append(text.replace(" ", "%20")).append("&tl=").append(language.getLanguageCode()).toString();
            URL url = new URL(urlAddress);

            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            InputStream in = urlConnection.getInputStream();
            BufferedInputStream inBuf = new BufferedInputStream(in);

            Player p = new Player(inBuf);
            p.play();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

}
