package com.example.lab2.controller.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.lab2.entity.post.Housing;
import com.example.lab2.entity.post.Post;
import com.example.lab2.entity.post.Housing.HousingType;
import com.example.lab2.entity.post.Post.PostStatus;

@SuppressWarnings("unused")
public class PostSpecifications {
    
    public static Specification<Post> fromFilter(PostFilter filter) {
        return Specification
                .where(hasType(filter.getType()))
                .and(locatesIn(filter.getCity()))

                .and(hasMinCost(filter.getMinCost()))
                .and(hasMaxCost(filter.getMaxCost()))

                .and(hasMinArea(filter.getMinArea()))
                .and(hasMaxArea(filter.getMaxArea()))
                
                .and(hasStartDateBefore(filter.getMinDate()))
                .and(hasEndDateAfter(filter.getMaxDate()))

                .and(hasStatus(PostStatus.AVAILABLE));
    }

    public static Specification<Post> hasType(HousingType type) {
        return (root, query, builder) -> 
            type != null 
                ? builder.equal(root.get("housing").get("type"), type)
                : null;
    }

    public static Specification<Post> locatesIn(String city) {
        return (root, query, builder) -> 
            city != null 
                ? builder.equal(root
                    .get("housing")
                    .get("address")
                    .get("city"), city)
                : null;
    }

    public static Specification<Post> hasMinCost(Float costPerDay) {
        return (root, query, builder) -> 
            costPerDay != null 
                ? builder.greaterThanOrEqualTo(root.get("costPerDay"), costPerDay)
                : null;
    }

    public static Specification<Post> hasMaxCost(Float costPerDay) {
        return (root, query, builder) -> 
            costPerDay != null 
                ? builder.lessThanOrEqualTo(root.get("costPerDay"), costPerDay)
                : null;
    }

    public static Specification<Post> hasMinArea(Short area) {
        return (root, query, builder) -> 
            area != null 
                ? builder.greaterThanOrEqualTo(root.get("housing").get("totalArea"), area)
                : null;
    }

    public static Specification<Post> hasMaxArea(Short area) {
        return (root, query, builder) -> 
            area != null 
                ? builder.lessThanOrEqualTo(root.get("housing").get("totalArea"), area)
                : null;
    }

    public static Specification<Post> isAvailable(Boolean avaliable) {
        return (root, query, builder) -> 
            avaliable != null 
                    ? builder.equal(root.get("available"), avaliable)
                    : null;
    }

    public static Specification<Post> hasStatus(PostStatus status) {
        return (root, query, builder) -> 
            status != null 
                    ? builder.equal(root.get("postStatus"), status)
                    : null;
    }

    public static Specification<Post> hasStartDateBefore(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> 
            startDate != null
                    ? criteriaBuilder.lessThanOrEqualTo(root.get("minDate"), startDate)
                    : null;
    }

    public static Specification<Post> hasEndDateAfter(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> 
            endDate != null
                    ? criteriaBuilder.greaterThanOrEqualTo(root.get("maxDate"), endDate)
                    : null;
    }
}
