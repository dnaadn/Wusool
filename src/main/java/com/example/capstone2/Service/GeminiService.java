package com.example.capstone2.Service;

import com.example.capstone2.Model.Place;
import com.example.capstone2.Model.PlaceAccessibility;
import com.example.capstone2.Repository.PlaceAccessibilityRepository;
import com.example.capstone2.Repository.PlaceRepository;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final Client client;
    private final PlaceRepository placeRepository;
    private final PlaceAccessibilityRepository placeAccessibilityRepository;

    public String analyzeAccessibilityRequest(String userRequest) {

        List<Place> places = placeRepository.findAll();

        String placesContext = places.stream()
                .map(p -> {
                    List<PlaceAccessibility> features =
                            placeAccessibilityRepository.findAll().stream()
                                    .filter(a -> a.getPlaceId().equals(p.getId()))
                                    .collect(Collectors.toList());

                    String featuresText = features.stream()
                            .map(PlaceAccessibility::getAccessibilityType)
                            .collect(Collectors.joining(", "));

                    return "- " + p.getName() + " (" + p.getCity() + "): "
                            + (featuresText.isEmpty() ? "No accessibility info" : featuresText);
                })
                .collect(Collectors.joining("\n"));

        String prompt = """
                You are an accessibility assistant for an application called Wusool.

                Analyze the user's request and identify the accessibility
                requirements they are asking for.

                User request:
                %s

                Return the requirements clearly.
                """.formatted(userRequest);

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-flash-latest",
                        prompt,
                        null
                );

        return response.text();
    }
}
