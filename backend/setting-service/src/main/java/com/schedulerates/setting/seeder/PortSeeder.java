package com.schedulerates.setting.seeder;

import com.schedulerates.setting.model.port.entity.PortEntity;
import com.schedulerates.setting.repository.PortRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PortSeeder {

    private final PortRepository portRepository;

    @PostConstruct
    public void seedPorts() {
        try (
                InputStream is = Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("data/ports.xlsx")) {
            if (is == null) {
                throw new IllegalStateException("❌ ports.xlsx not found in classpath");
            }

            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<PortEntity> ports = new ArrayList<>();

            if (rows.hasNext())
                rows.next(); // skip header

            String lastIso3 = "";
            String lastCountry = "";

            while (rows.hasNext()) {
                Row row = rows.next();

                String iso3 = getCellValue(row.getCell(0));
                String country = getCellValue(row.getCell(1));
                String portName = getCellValue(row.getCell(2));
                String portCode = getCellValue(row.getCell(3));
                String latStr = getCellValue(row.getCell(4));
                String lngStr = getCellValue(row.getCell(5));

                if (!iso3.isBlank())
                    lastIso3 = iso3;
                if (!country.isBlank())
                    lastCountry = country;

                if (lastIso3.isBlank() || portName.isBlank() || portCode.isBlank() || lastCountry.isBlank()) {
                    continue;
                }

                PortEntity port = PortEntity.builder()
                        .countryNameAbbreviation(lastIso3)
                        .countryName(lastCountry)
                        .portName(portName)
                        .portCode(portCode)
                        .portLatitude(parseDouble(latStr))
                        .portLongitude(parseDouble(lngStr))
                        .portLogo(generatePortLogoName(lastCountry)) // preserves original case
                        .obs(null) // obs set to null
                        .build();

                ports.add(port);
            }

            // Commented out: no data will be saved
            // portRepository.deleteAllInBatch();
            // portRepository.flush();
            // portRepository.saveAll(ports);
            // System.out.println("✅ Ports seeded successfully: " + ports.size());

        } catch (Exception e) {
            System.err.println("❌ Failed to seed ports: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Port seeding failed", e);
        }
    }

    private String generatePortLogoName(String countryName) {
        return countryName.trim()
                .replaceAll("[^a-zA-Z0-9]", "") // keep letters and digits, original case preserved
                .concat(".png");
    }

    private String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> cell.getNumericCellValue() % 1 == 0 ? String.valueOf((int) cell.getNumericCellValue())
                    : String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> getFormulaCellValue(cell);
            default -> "";
        };
    }

    private String getFormulaCellValue(Cell cell) {
        try {
            return cell.getRichStringCellValue().getString();
        } catch (Exception e) {
            try {
                return String.valueOf(cell.getNumericCellValue());
            } catch (Exception ex) {
                return "";
            }
        }
    }

    private Double parseDouble(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(str.replaceAll("[^0-9.-]", "").trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
