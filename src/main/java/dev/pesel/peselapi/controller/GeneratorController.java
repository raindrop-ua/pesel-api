package dev.pesel.peselapi.controller;

import dev.pesel.peselapi.exception.InvalidBirthDateException;
import dev.pesel.peselapi.model.Sex;
import dev.pesel.peselapi.service.PeselGeneratorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/generator", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeneratorController {

    private static final DateTimeFormatter DOB_FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final PeselGeneratorService service;

    public GeneratorController(PeselGeneratorService service) {
        this.service = service;
    }

    @GetMapping
    public List<String> generate(
            @RequestParam(name = "sex", required = false) String sexStr,
            @RequestParam(name = "quantity", required = false) Integer quantity,
            @RequestParam(name = "dob", required = false) String dobStr
    ) {
        Sex sex = (sexStr == null) ? null : Sex.fromString(sexStr);

        LocalDate dob = null;
        if (dobStr != null && !dobStr.isBlank()) {
            try {
                dob = LocalDate.parse(dobStr.trim(), DOB_FMT);
            } catch (DateTimeParseException e) {
                throw new InvalidBirthDateException("dob must be in format dd.MM.yyyy");
            }
        }

        int q = service.normalizeQuantity(quantity);
        return service.generateUnique(q, dob, sex);
    }
}
