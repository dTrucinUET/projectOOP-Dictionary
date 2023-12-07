package com.dictionary.api;

import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class DictionaryApi {
    public static void main(String[] args) {
        try {
            // Thay thế "www.google.com" bằng địa chỉ IP hoặc tên miền của máy chủ bạn muốn kiểm tra
            String host = "www.google.com";

            InetAddress inetAddress = InetAddress.getByName(host);

            // Kiểm tra xem có thể kết nối đến máy chủ hay không
            if (inetAddress.isReachable(5000)) {
                System.out.println("Connected to the network");

                // Gọi API ở đây nếu bạn đã kết nối mạng thành công
                try {
                    // Thay thế URL bằng đường link API bạn muốn gọi
                    String word = "hello";
                    String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;

                    // Tạo URL cho yêu cầu GET
                    URL url = new URL(apiUrl);

                    // Mở kết nối HTTP
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    // Lấy phản hồi từ API
                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    // Đọc dữ liệu từ API
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    // In kết quả
                    System.out.println("API Response: " + response.toString());

                    String jsonString = response.toString();
                    // Chuyển đổi chuỗi JSON thành đối tượng JSONArray
                    JSONArray jsonArray = new JSONArray(jsonString);

                    // Lặp qua các phần tử trong mảng JSON
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // Lấy thông tin từ đối tượng JSON
                        String h = jsonObject.getString("word");
                        JSONArray phoneticsArray = jsonObject.getJSONArray("phonetics");
                        JSONArray meaningsArray = jsonObject.getJSONArray("meanings");

                        // In ra thông tin
                        System.out.println("Word: " + h);

                        // In ra thông tin về phonetics
                        System.out.println("Phonetics:");
                        for (int j = 0; j < phoneticsArray.length(); j++) {
                            JSONObject phoneticObject = phoneticsArray.getJSONObject(j);
                            String audio = phoneticObject.optString("audio");
                            String text = phoneticObject.optString("text");
                            // In ra thông tin về phonetics
                            System.out.println("  Audio: " + audio);
                            System.out.println("  Text: " + text);
                        }

                        // In ra thông tin về meanings
                        System.out.println("Meanings:");
                        for (int k = 0; k < meaningsArray.length(); k++) {
                            JSONObject meaningObject = meaningsArray.getJSONObject(k);
                            String partOfSpeech = meaningObject.optString("partOfSpeech");
                            // In ra thông tin về meanings
                            System.out.println("  Part of Speech: " + partOfSpeech);
                            // Bạn có thể tiếp tục lấy thông tin khác tùy thuộc vào cấu trúc của JSON
                        }

                        // In ra các thông tin khác tùy thuộc vào cấu trúc JSON của bạn
                    }

                    // Đóng kết nối
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Not connected to the network");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
