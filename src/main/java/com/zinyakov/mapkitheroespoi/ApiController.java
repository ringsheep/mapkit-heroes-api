package com.zinyakov.mapkitheroespoi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonRawValue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @JsonRawValue
    @GetMapping(value = "/pois", produces = "application/json")
    public String getPois() {
        return search(55.791370, 37.452973,
                55.806133, 37.496961);
    }

    private String endpoint = "http://overpass-api.de/api/interpreter?data=";

    private String search(Double southernLat, Double westernLon, Double nothernLat, Double easternLon) {
//        OverpassQuery query = new OverpassQuery()
//                .format(JSON)
//                .timeout(30)
//                .filterQuery()
//                .node()
//                .tag("amenity")
//                .boundingBox(
//                        southernLat,
//                        westernLon,
//                        nothernLat,
//                        easternLon
//                )
//                .end()
//                .output(100)
//                ;
//        String requestUrl = endpoint + query.build().replace("\"", "");
//        System.out.println(requestUrl);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(requestUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();

        String output = "";
        try {
            JsonNode node = mapper.readTree(new File("/Users/georgezinyakov/Downloads/mapkit-heroes-poi/src/main/java/com/zinyakov/mapkitheroespoi/data.json"));
            JsonNode arrNode = node.findValue("elements");
            List<PointOfInterest> pois = new ArrayList<>();
            if (arrNode.isArray()) {
                for (final JsonNode objNode : arrNode) {
                    double latitude = objNode.get("lat").asDouble();
                    double longitude = objNode.get("lon").asDouble();
                    GeoPosition position = new GeoPosition(latitude,longitude);

                    String osmId = objNode.get("id").asText();
                    String type = objNode.get("tags").get("amenity").asText();
                    pois.add(new PointOfInterest(osmId, position, type));
                }
            }
            output = mapper.writeValueAsString(pois);
        }
        catch (IOException ex) {
            return ex.getMessage();
        }

        return output;
    }

}
