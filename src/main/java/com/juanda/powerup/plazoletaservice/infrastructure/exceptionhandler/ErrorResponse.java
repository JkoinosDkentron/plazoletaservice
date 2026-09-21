package com.juanda.powerup.plazoletaservice.infrastructure.exceptionhandler;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, int status, String error, String message) {
}
