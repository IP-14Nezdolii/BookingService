package com.example.lab2.controller.specifications;

import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.lab2.entity.post.Housing.HousingType;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Фільтр для оголошень")
public class PostFilter {

    @Schema(description = "Мінімальна ціна за день")
    private Float minCost = null;
    @Schema(description = "Максимальна ціна за день")
    private Float maxCost = null;

    @Schema(description = "Мінімальна площа")
    private Short minArea = null;
    @Schema(description = "Максимальна площа")
    private Short maxArea = null;

    @Schema(description = "Тип житла, див. Housing.type")
    private HousingType type = null;

    @Schema(description = "Назва міста, див. Housing")
    private String city = null;

    @Schema(description = "Мінімальна дата початку оренди")
    private LocalDate minDate;
    @Schema(description = "Максимальна дата початку оренди")
    private LocalDate maxDate;

    @Schema(description = 
        "Тип сортування, якщо буде обрано {ascending, descending, rate}"+
        "то список оголошень буде відсортовано за зростанням, спаданням ціни та рейтингом відповідно")
    private String sortingType = null;

    public Pageable getPageable(int pageNumber, int pageSize) {
        return switch (sortingType) {
            case "ascending" -> PageRequest.of(pageNumber, pageSize, Sort.by("costPerDay").ascending());
            case "descending"-> PageRequest.of(pageNumber, pageSize, Sort.by("costPerDay").descending());
            case "rate"-> PageRequest.of(pageNumber, pageSize, Sort.by("score").descending());
            default -> PageRequest.of(pageNumber, pageSize);
        };
    }
}
