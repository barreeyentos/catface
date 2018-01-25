package com.barreeyentos.catface.controller;

import static org.assertj.core.api.Assertions.assertThat;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CatFaceControllerValidationTest {

    private static final String CAT_FACE_URL = "/v1/catfaces";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testEmptyImageUrl() {

        CatFaceRequest request = new CatFaceRequest();

        ResponseEntity<CatFaceList> result = restTemplate.postForEntity(CAT_FACE_URL, request, CatFaceList.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testNegativeThreshold() {

        CatFaceRequest request = new CatFaceRequest();
        request.setImageUrl("file://catimage.txt");
        request.setConfidenceThreshold(-0.10f);

        ResponseEntity<CatFaceList> result = restTemplate.postForEntity(CAT_FACE_URL, request, CatFaceList.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testTooLargeThreshold() {

        CatFaceRequest request = new CatFaceRequest();
        request.setImageUrl("file://catimage.txt");
        request.setConfidenceThreshold(1.10f);

        ResponseEntity<CatFaceList> result = restTemplate.postForEntity(CAT_FACE_URL, request, CatFaceList.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }
}
