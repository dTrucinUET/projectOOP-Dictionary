package com.dictionary.api;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslateAPI {
    private final String langFirst;
    private final String langSecond;

    public TranslateAPI(String langFirst, String langSecond) {
        this.langFirst = langFirst;
        this.langSecond = langSecond;
    }

    public String translateText(String input) {
        try {
            String url = String.format(
                    "https://translate.googleapis.com/translate_a/single?client=gtx&sl=%s&tl=%s&dt=t&q=%s",
                    langFirst, langSecond, URLEncoder.encode(input, StandardCharsets.UTF_8));

            HttpURLConnection con = (HttpURLConnection) new URI(url).toURL().openConnection();
            String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);

            // Xử lý kết quả JSON để lấy văn bản dịch
            JSONArray jsonArray = new JSONArray(result);
            JSONArray translationLine = jsonArray.getJSONArray(0);

            StringBuilder translation = new StringBuilder();
            for (int i = 0; i < translationLine.length(); i++) {
                JSONArray translationLineString = translationLine.getJSONArray(i);
                translation.append(" ").append(translationLineString.getString(0));
            }

            if (translation.length() > 1) {
                return translation.substring(1);
            }

        } catch (IOException | URISyntaxException | JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        // Tạo đối tượng TranslateAPI với ngôn ngữ đầu và ngôn ngữ đích
        TranslateAPI translator = new TranslateAPI("en", "vi");
        TranslateAPI translator1 = new TranslateAPI("vi", "en");

        // Văn bản cần dịch
        String inputText = "Hello, how are you? i'm fine, thank you";
        String inputText1 = "xin chào tôi là luân";

        // Gọi hàm dịch và hiển thị kết quả
        String translatedText = translator.translateText(inputText);
        String translatedText1 = translator1.translateText(inputText1);
        System.out.println("Input Text: " + inputText);
        System.out.println("Translated Text: " + translatedText);
        System.out.println("Input Text: " + inputText1);
        System.out.println("Translated Text: " + translatedText1);
    }
}