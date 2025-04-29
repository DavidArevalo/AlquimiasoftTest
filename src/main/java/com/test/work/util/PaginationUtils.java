package com.test.work.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

public class PaginationUtils {

    public static <T> HttpHeaders generatePaginationHttpHeaders(UriComponentsBuilder uriBuilder, Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

        String baseUrl = uriBuilder.toUriString();

        int pageNumber = page.getNumber();
        int pageSize = page.getSize();
        int totalPages = page.getTotalPages();

        StringBuilder link = new StringBuilder();

        if ((pageNumber + 1) < totalPages) {
            link.append(buildLink(baseUrl, pageNumber + 1, pageSize, "next")).append(",");
        }
        if (pageNumber > 0) {
            link.append(buildLink(baseUrl, pageNumber - 1, pageSize, "prev")).append(",");
        }
        link.append(buildLink(baseUrl, totalPages - 1, pageSize, "last")).append(",");
        link.append(buildLink(baseUrl, 0, pageSize, "first"));

        headers.add(HttpHeaders.LINK, link.toString());
        return headers;
    }

    private static String buildLink(String baseUrl, int page, int size, String rel) {
        return "<" + baseUrl + "?page=" + page + "&size=" + size + ">; rel=\"" + rel + "\"";
    }
}
