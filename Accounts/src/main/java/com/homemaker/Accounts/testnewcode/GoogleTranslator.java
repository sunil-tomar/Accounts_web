package com.homemaker.Accounts.testnewcode;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class GoogleTranslator {

    public static void main(String[] args) {
        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        // The text to translate
        String text = "Hello, how are you?";

        // Translates the text into Hindi
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("hi"));

        // Prints the translation
        System.out.println("English: " + text);
        System.out.println("Hindi: " + translation.getTranslatedText());
    }
}