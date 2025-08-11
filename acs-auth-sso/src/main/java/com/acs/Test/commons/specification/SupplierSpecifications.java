package com.acs.Test.commons.specification;

import com.acs.Test.dto.supplier.SupplierSearchRequest;
import com.acs.Test.pojo.Supplier;
import com.acs.Test.pojo.SupplierAddress;
import com.acs.Test.pojo.SupplierContact;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

public class SupplierSpecifications {

    public static Specification<Supplier> buildSpecification(SupplierSearchRequest request) {
        return (root, query, cb) -> {
            query.distinct(true);

            // Use reusable joins once - block removal - search field not applicable
            Join<Supplier, SupplierAddress> addressJoin = root.joinSet("addresses", JoinType.LEFT);
            Join<Supplier, SupplierContact> contactJoin = root.joinSet("contacts", JoinType.LEFT);

            // New joins for SKU/Product filtering
            // Join<Supplier, SupplierProductMapping> mappingJoin = root.joinSet("supplierProductMappings", JoinType.LEFT);
            //Join<SupplierProductMapping, Product> productJoin = mappingJoin.join("product", JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            // Individual filters - criteria query builder
            if (request.getSupplierName() != null && !request.getSupplierName().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("supplierName")),
                                "%" + request.getSupplierName().toLowerCase() + "%"));
            }

            if (request.getSupplierCode() != null && !request.getSupplierCode().isEmpty()) {
                // predicate condition - for complete string literal equality comparison
                /* predicate = cb.and(predicate,
                        cb.equal(cb.lower(root.get("supplierCode")), request.supplierCode().toLowerCase())); */
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("supplierCode")),
                                "%" + request.getSupplierCode().toLowerCase() + "%"));
            }

            if (request.getStatus() != null && !request.getStatus().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.equal(cb.lower(root.get("status")), request.getStatus().toLowerCase()));
            }

            if (request.getCountry() != null && !request.getCountry().isEmpty()) {
                CriteriaBuilder.In<String> inClause = cb.in(cb.lower(addressJoin.get("country")));
                request.getCountry().forEach(c -> inClause.value(c.toLowerCase()));
                predicate = cb.and(predicate, inClause);
            }

            if (request.getState() != null && !request.getState().isEmpty()) {
                CriteriaBuilder.In<String> inClause = cb.in(cb.lower(addressJoin.get("state")));
                request.getState().forEach(s -> inClause.value(s.toLowerCase()));
                predicate = cb.and(predicate, inClause);
            }

            if (request.getCity() != null && !request.getCity().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(addressJoin.get("city")),
                                "%" + request.getCity().toLowerCase() + "%"));
            }

            if (request.getContactName() != null && !request.getContactName().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(contactJoin.get("name")),
                                "%" + request.getContactName().toLowerCase() + "%"));
            }

            if (request.getContactEmail() != null && !request.getContactEmail().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(contactJoin.get("email")),
                                "%" + request.getContactEmail().toLowerCase() + "%"));
            }

            if (request.getContactPhone() != null && !request.getContactPhone().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(contactJoin.get("phone")),
                                "%" + request.getContactPhone().toLowerCase() + "%"));
            }

            // Product filter would need join to a mapping entity which is not present yet

            // Only join product mapping if SKU or productName is being filtered
/*
            if ((request.sku() != null && !request.sku().isEmpty()) ||
                    (request.productName() != null && !request.productName().isEmpty())) {

                Join<Supplier, SupplierProductMapping> mappingJoin =root.joinSet("supplierProductMappings", JoinType.LEFT);
                Join<SupplierProductMapping, Product> productJoin = mappingJoin.join("product", JoinType.LEFT);

                if (request.sku() != null && !request.sku().isEmpty()) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(productJoin.get("sku")), "%" + request.sku().toLowerCase() + "%"));
                }

                if (request.productName() != null && !request.productName().isEmpty()) {
                    predicate = cb.and(predicate,
                            cb.like(cb.lower(productJoin.get("productName")), "%" + request.productName().toLowerCase() + "%"));
                }
            }
*/

            return predicate;
        };
    }
}

