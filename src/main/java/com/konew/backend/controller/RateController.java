package com.konew.backend.controller;

import com.konew.backend.model.BookRateModel;
import com.konew.backend.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rates")
public class RateController
{
    RateService rateService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping
    public void addRate(@Valid @RequestBody BookRateModel bookRateModel)
    {
        rateService.saveOrUpdateRate(bookRateModel);
    }
}
