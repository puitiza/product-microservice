package com.anthony.product.model.templateDto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Swagger api documentation requires these classes to display json models of the responses.
 * Each class's javadoc is what Swagger will translate them into on the ui.
 */
public class templateDto {

   /* /**
     * {
     *   "timestampMillis": 0,
     *   "metadata": {},
     *   "dimensions": {},
     *   "payload": "base64 representation of bytes"
     * }

    @Schema
    static class EventModel {
        @Schema(description = "Timestamp of the event", required = true)
        private long timestampMillis;
        @Schema(description = "Metadata - string to string key/value pairs")
        private Map<String, String> metadata;
        @Schema(description = "Dimensions - string to double key/value pairs")
        private Map<String, Double> dimensions;
        @Schema(description = "Base64 encoded string payload", example = "QmFzZTY0IGVuY29kZWQ=")
        private String payload;


    }*/

    /**
     * { "size": 0 }
     */
    @Schema
    public static class SizeResponse {
        @Schema(description = "total number of elements found")
        public int getSize() {
            return 0;
        }
    }
}
