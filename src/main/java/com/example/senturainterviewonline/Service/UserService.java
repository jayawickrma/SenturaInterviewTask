package com.example.senturainterviewonline.Service;

import com.example.senturainterviewonline.DTO.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private OkHttpClient client;

    @Autowired
    private ObjectMapper mapper;

    @Value("${weavy.api.url}")
    private String APIURL;

    @Value("${weavy.api.key}")
    private String APIKEY;

    public UserDTO saveUser(UserDTO userDTO) {
        String endpoint = APIURL + "/api/users";
        try {

            String jsonBody = mapper.writeValueAsString(userDTO);

            RequestBody body = okhttp3.RequestBody.create(
                    jsonBody,
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(endpoint)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + APIKEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return mapper.readValue(responseBody, UserDTO.class);
                } else {
                    logger.error("Failed to save user: {}", response.message());
                    throw new RuntimeException("Failed to save user: " + response);
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("Error converting user to JSON", e);
            e.printStackTrace();
            throw new RuntimeException("Error converting user to JSON", e);
        } catch (IOException e) {
            logger.error("Error making HTTP request to Weavy API", e);
            e.printStackTrace();
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }

    public UserDTO getUser(String uid) {
        String endpoint = APIURL + "/api/users/" + uid;


        Request request = new Request.Builder()
                .url(endpoint)
                .get()
                .addHeader("Authorization", "Bearer " + APIKEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return mapper.readValue(responseBody, UserDTO.class);
            } else {
                throw new RuntimeException("Failed to get user: " + response.message());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting response to UserDTO", e);
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }

    public UserDTO updateUser(String userId, UserDTO userDTO) {
        String endpoint = APIURL + "/api/users/" + userId;

        try {
            String jsonBody = mapper.writeValueAsString(userDTO);
            RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url(endpoint)
                    .put(body)
                    .addHeader("Authorization", "Bearer " + APIKEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return mapper.readValue(responseBody, UserDTO.class);
                } else {
                    throw new RuntimeException("Failed to update user: " + response.message());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting user to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }


    public void deleteUser(String userId) {
        String endpoint = APIURL + "/api/users/" + userId+"/trash";

        Request request = new Request.Builder()
                .url(endpoint)
                .post(RequestBody.create(new byte[0]))
                .addHeader("Authorization", "Bearer " + APIKEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to delete user: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }
}