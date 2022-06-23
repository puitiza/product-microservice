package com.anthony.product.model.templateDto;

/**
 * Swagger api documentation requires these classes to display json models of the responses.
 * Each class's javadoc is what Swagger will translate them into on the ui.
 */
public class ExampleResponse {

    public static final String NOT_FOUND =
            """
                    {
                        "status": 404,
                        "timestamp": "20-06-2022 11:17:09 PET",
                        "errorCode": "PC-0001",
                        "message": "Item with id {0} not found",
                        "stackTrace": [
                            "com.anthony.product.service.ProductService.lambda$getProduct$0(ProductService.java:24)",
                            "java.base/java.util.Optional.orElseThrow(Optional.java:403)",
                            "com.anthony.product.service.ProductService.getProduct(ProductService.java:22)",
                            "com.anthony.product.controller.ProductController.getProduct(ProductController.java:38)"
                        ],
                        "errors": null,
                        "debugMessage": null
                    }""";
}
