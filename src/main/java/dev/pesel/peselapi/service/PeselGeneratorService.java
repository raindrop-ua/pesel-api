package dev.pesel.peselapi.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import dev.pesel.peselapi.exception.InvalidParamException;
import dev.pesel.peselapi.model.Sex;
import dev.pesel.peselapi.util.PeselUtils;
import org.springframework.stereotype.Service;

@Service
public class PeselGeneratorService {

    private final Random rnd = new SecureRandom();

    public String generate(LocalDate dob, Sex sex) {
        PeselUtils.ensureValidDate(dob);

        int yy = dob.getYear() % 100;
        int mmCode = PeselUtils.monthWithCentury(dob);
        int dd = dob.getDayOfMonth();

        String datePart = String.format("%02d%02d%02d", yy, mmCode, dd);

        StringBuilder serial = new StringBuilder();
        for (int i = 0; i < 3; i++) serial.append(rnd.nextInt(10));

        int sexDigit;
        if (sex == null) {
            sexDigit = rnd.nextInt(10);
        } else {
            sexDigit = (sex == Sex.FEMALE) ? (rnd.nextInt(5) * 2) : (rnd.nextInt(5) * 2 + 1);
        }

        String firstTen = datePart + serial + sexDigit;
        int checksum = PeselUtils.checksumDigit(firstTen);

        return firstTen + checksum;
    }

    public String generateRandom(Sex sex) {
        LocalDate today = LocalDate.now();
        int maxYear = Math.min(PeselUtils.MAX_YEAR, today.getYear());
        int minYear = Math.max(PeselUtils.MIN_YEAR, today.getYear() - 100);

        while (true) {
            int y = rnd.nextInt(maxYear - minYear + 1) + minYear;
            int m = rnd.nextInt(12) + 1;
            int dMax = switch (m) {
                case 1,3,5,7,8,10,12 -> 31;
                case 4,6,9,11 -> 30;
                case 2 -> PeselUtils.isLeap(y) ? 29 : 28;
                default -> 28;
            };
            int d = rnd.nextInt(dMax) + 1;
            if (PeselUtils.isValidDate(y, m, d)) {
                return generate(LocalDate.of(y, m, d), sex);
            }
        }
    }

    public int maxUniquePossible(LocalDate dob, Sex sex) {
        if (dob != null && sex != null) return 1000 * 5;
        if (dob != null)              return 1000 * 10;
        return Integer.MAX_VALUE;
    }

    public List<String> generateUnique(int quantity, LocalDate dob, Sex sex) {
        int maxPossible = maxUniquePossible(dob, sex);
        if (quantity > maxPossible) {
            throw new InvalidParamException(
                    "Requested quantity=" + quantity +
                    " exceeds the maximum unique combinations (" + maxPossible +
                    ") for the given constraints."
            );
        }

        var set = new LinkedHashSet<String>(Math.min(quantity * 2, 10_000));
        int attempts = 0;
        int maxAttempts = Math.max(quantity * 20, 200);

        while (set.size() < quantity && attempts < maxAttempts) {
            String pesel = (dob != null) ? generate(dob, sex) : generateRandom(sex);
            set.add(pesel);
            attempts++;
        }

        if (set.size() < quantity) {
            throw new InvalidParamException(
                    "Could not generate the requested number of unique PESELs. " +
                    "Try lowering 'quantity' or relaxing constraints."
            );
        }

        return List.copyOf(set);
    }

    public int normalizeQuantity(Integer quantity) {
        int q = (quantity == null) ? 1 : quantity;
        if (q < 1) throw new InvalidParamException("quantity must be >= 1");
        if (q > 10000) throw new InvalidParamException("quantity must be <= 10000");
        return q;
    }
}