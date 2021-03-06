package com.coelhocaique.finance.api.helper

object Messages {
    const val DEFAULT_ERROR_MESSAGE = "Internal error, please try again."
    const val NO_PARAMETERS = "No parameters informed."
    const val MISSING_HEADERS = "Not authorized to perform the request."
    const val NOT_NULL = "%s must not be null."
    const val INVALID_REQUEST = "Invalid request body."
    const val INVALID_ID = "Invalid id."
    const val INVALID_REF_CODE = "Invalid reference_code."
}

object Fields {
    const val ID = "id"
    const val AUTHORIZATION = "Authorization"
    const val REF_DATE = "reference_date"
    const val REF_CODE = "reference_code"
    const val DATE_FROM = "date_from"
    const val DATE_TO = "date_to"
    const val AMOUNT = "amount"
    const val DESCRIPTION = "description"
    const val DEBT_DATE = "debt_date"
    const val INSTALLMENTS = "installments"
    const val TAG = "tag"
    const val TYPE = "type"
    const val VALUE = "value"
    const val NAME = "name"
    const val PROPERTY_NAME = "property_name"
    const val RECEIPT_DATE = "receipt_date"
    const val GROSS_AMOUNT = "gross_amount"
    const val SOURCE_NAME = "source_name"
}