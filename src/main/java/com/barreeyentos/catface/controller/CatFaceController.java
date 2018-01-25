package com.barreeyentos.catface.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barreeyentos.catface.dto.CatFace;
import com.barreeyentos.catface.dto.CatFaceList;
import com.barreeyentos.catface.dto.CatFaceRequest;
import com.barreeyentos.catface.exception.InvalidRequestException;
import com.barreeyentos.catface.service.CatFaceService;

@Controller
@RequestMapping(path = "/v1/catfaces", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CatFaceController {

    private CatFaceService service;

    @Autowired
    public CatFaceController(CatFaceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    public CatFaceList findCatFaces(@Valid @RequestBody CatFaceRequest catFaceRequest) {
        if (Objects.isNull(catFaceRequest.getImage()) && Objects.isNull(catFaceRequest.getImageUrl())) {
            throw new InvalidRequestException("an image or imageurl must be sent");
        }

        if (Objects.nonNull(catFaceRequest.getImage()) && Objects.nonNull(catFaceRequest.getImageUrl())) {
            throw new InvalidRequestException("an image and imageurl was  sent");
        }

        List<CatFace> catFaces = service.findCatFaces(catFaceRequest);

        return new CatFaceList(catFaces);
    }
}
