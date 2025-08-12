package com.acs.Test.commons.specification;

import com.acs.Test.dto.request.product.ProductSearchRequest;
import com.acs.Test.pojo.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {

    public static Specification<Product> buildSpecification(ProductSearchRequest filters) {
        return (root, query, cb) -> {

            // If all fields are empty, return no filter (match all)
            boolean allEmpty =
                    (filters == null)
                            || (isBlank(filters.getSku()) && isBlank(filters.getProductName()) && isBlank(filters.getUom()) &&
                            isEmpty(filters.getProductType()) && isEmpty(filters.getTempStorageType()) && isEmpty(filters.getCategory()));

            if (allEmpty) {
                return cb.conjunction(); // means always true, i.e., fetch all
            }

            List<Predicate> predicates = new ArrayList<>();

            if (!isBlank(filters.getSku())) {
                predicates.add(cb.like(cb.lower(root.get("sku")), "%" + filters.getSku().toLowerCase() + "%"));
            }

            if (!isBlank(filters.getProductName())) {
                predicates.add(cb.like(cb.lower(root.get("productName")), "%" + filters.getProductName().toLowerCase() + "%"));
            }

            if (!isBlank(filters.getUom())) {
                predicates.add(cb.like(cb.lower(root.get("uom")), "%" + filters.getUom().toLowerCase() + "%"));
            }

            if (!isEmpty(filters.getProductType())) {
                List<Predicate> typePreds = new ArrayList<>();
                for (String val : filters.getProductType()) {
                    typePreds.add(cb.like(cb.lower(root.get("productType")), "%" + val.toLowerCase() + "%"));
                }
                predicates.add(cb.or(typePreds.toArray(new Predicate[0])));
            }

            if (!isEmpty(filters.getTempStorageType())) {
                List<Predicate> tempPreds = new ArrayList<>();
                for (String val : filters.getTempStorageType()) {
                    tempPreds.add(cb.like(cb.lower(root.get("tempStorageType")), "%" + val.toLowerCase() + "%"));
                }
                predicates.add(cb.or(tempPreds.toArray(new Predicate[0])));
            }

            if (!isEmpty(filters.getCategory())) {
                List<Predicate> catPreds = new ArrayList<>();
                for (String val : filters.getCategory()) {
                    catPreds.add(cb.like(cb.lower(root.get("category")), "%" + val.toLowerCase() + "%"));
                }
                predicates.add(cb.or(catPreds.toArray(new Predicate[0])));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    private static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
