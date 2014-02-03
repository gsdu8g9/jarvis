package com.kubaspatny.speech.texttospeech;

import java.util.HashSet;

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

public final class Language {

    private String language;

    private static HashSet<String> languages = null;

    public static final String ENGLISH = "en";
    public static final String CZECH = "cs";

    private Language(){

    }

    private Language(String language){
        this.language = language;
    }

    private static void initLanguages(){
        if(languages == null){
            languages = new HashSet<>();
            languages.add(ENGLISH);
            languages.add(CZECH);
        }
    }

    private static String checkLang(String language){
        initLanguages();
        if(languages.contains(language)){
            return language;
        }

        // add detecting language here
        return Language.ENGLISH;
    }

    public static Language getInstance(String language){
        return new Language(checkLang(language));
    }

    public String getLanguageCode(){
        return this.language;
    }


}
