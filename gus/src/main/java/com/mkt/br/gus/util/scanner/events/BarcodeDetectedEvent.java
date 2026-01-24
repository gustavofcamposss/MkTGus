package com.mkt.br.gus.util.scanner.events;

//É como um DTO imutávelo; Menos código, mais clareza; Idela para evento
public record BarcodeDetectedEvent(
        String ean) {
}
