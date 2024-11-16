package com.example.tipocambio_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TipoCambioController {
    private final SoapClient soapClient;

    @Autowired
    public TipoCambioController(SoapClient soapClient) {
        this.soapClient = soapClient;
    }

    @GetMapping("/tipo-cambio")
    public String getTipoCambio() {
        return soapClient.getTipoCambio();
    }
}