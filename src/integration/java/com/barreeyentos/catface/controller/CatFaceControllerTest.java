package com.barreeyentos.catface.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.barreeyentos.catface.dto.CatFaceList;
import com.barreeyentos.catface.dto.CatFaceRequest;
import com.barreeyentos.catface.dto.Position;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CatFaceControllerTest {

    private static final String CAT_FACE_URL = "/v1/catfaces";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testPerfectCat() {
        CatFaceRequest request = new CatFaceRequest();
        request.setImage(CatFacesImages.PERFECT_CAT);
        request.setConfidenceThreshold(0.7f);

        ResponseEntity<CatFaceList> result = restTemplate.postForEntity(CAT_FACE_URL, request, CatFaceList.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        CatFaceList faces = result.getBody();
        assertThat(faces.getCatFaces()).hasSize(1);
        assertThat(faces.getCatFaces().get(0).getConfidence()).isCloseTo(1.00, within(0.001));
        assertThat(faces.getCatFaces().get(0).getPosition()).isEqualTo(Position.of(0, 0));
    }

    @Test
    public void testZeroCatFacesWithEmptySpaces() {
        CatFaceRequest request = new CatFaceRequest();
        request.setImage(CatFacesImages.EMPTY_CAT);
        request.setConfidenceThreshold(0.7f);

        ResponseEntity<CatFaceList> result = restTemplate.postForEntity(CAT_FACE_URL, request, CatFaceList.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        CatFaceList faces = result.getBody();
        assertThat(faces.getCatFaces()).hasSize(0);

    }

    @Test
    public void testZeroCatFacesWithPluses() {
        CatFaceRequest request = new CatFaceRequest();
        request.setImage(CatFacesImages.ALL_PLUS_CAT);
        request.setConfidenceThreshold(0.7f);

        ResponseEntity<CatFaceList> result = restTemplate.postForEntity(CAT_FACE_URL, request, CatFaceList.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        CatFaceList faces = result.getBody();
        assertThat(faces.getCatFaces()).hasSize(0);

    }

    @Test
    public void testFuzzyCat() {
        CatFaceRequest request = new CatFaceRequest();
        request.setImage(CatFacesImages.NOISY_CAT);
        request.setConfidenceThreshold(0.7f);

        ResponseEntity<CatFaceList> result = restTemplate.postForEntity(CAT_FACE_URL, request, CatFaceList.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        CatFaceList faces = result.getBody();
        assertThat(faces.getCatFaces()).hasSize(1);
        assertThat(faces.getCatFaces().get(0).getConfidence()).isCloseTo(.999, within(0.001));
        assertThat(faces.getCatFaces().get(0).getPosition()).isEqualTo(Position.of(0, 0));
    }

}
