package com.acs.Test.commons.constant;

import java.util.Set;

public final class AppConstants {
        private AppConstants() {}

        public static final Set<String> ALLOWED_STATUSES = Set.of(
                "Draft", "Created", "Partially Received", "Received", "Cancelled", "Closed"
        );

        public static final String SUPPLIER_STATUS_ACTIVE = "Active";
        public static final String SUPPLIER_STATUS_INACTIVE = "Inactive";

        public static final String ADDRESS_TYPE_SUPPLIER = "supplier";
        public static final String ADDRESS_TYPE_BILLING = "billing";
        public static final String ADDRESS_TYPE_SHIPPING = "shipping";

        public static final String PRODUCT_TYPE_PRODUCT = "Product";
        public static final String PRODUCT_TYPE_KIT = "Kit";
        public static final String PRODUCT_TYPE_BUNDLE = "Bundle";

        public static final String PO_TYPE_STOCK = "Stock";
        public static final String PO_TYPE_DIRECT = "Direct";
        public static final String PO_TYPE_DUMMY = "Dummy";

        public static final String PO_TEMPUR = "TP";
        public static final String PO_SEALY = "S";
        public static final String PO_STERNS_FOSTER = "SF";
        public static final String PO_TEMPUR_RETAILER = "TRS";

        public static final String PO_SHIP_TO_FC = "FC";
        public static final String PO_SHIP_TO_OTHER = "Other";

        public static final String PO_STATUS_DRAFT = "Draft";
        public static final String PO_STATUS_CREATED = "Created";
        public static final String PO_STATUS_CANCELLED = "Cancelled";
        public static final String PO_STATUS_RECEIVED = "Received";
        public static final String PO_STATUS_PARTIALLY_RECEIVED = "Partially Received";
        public static final String PO_STATUS_PENDING = "Pending";
        public static final String PO_STATUS_CLOSED = "Closed";

        public static final String ACTION_ACTIVATE = "activate";
        public static final String ACTION_INACTIVATE = "inactivate";
        public static final String ACTION_DELETE = "delete";

        public static final String SUPPLIER = "SUPPLIER";
        public static final String SUPPLIER_PRODUCT_MAPPING= "SUPPLIER_PRODUCT_MAPPING";
        public static final String PURCHASE_ORDER_VERSION= "PURCHASE_ORDER_VERSION";
        public static final String PURCHASE_ORDER= "PURCHASE_ORDER";
        public static final String ACS_PURCHASE_ORDER= "ACS_PURCHASE_ORDER";
}
