package com.itacademy.web.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilPath {

    public static final String BASE_URL = "http://localhost:8080/";
    public static final String REDIRECT = "redirect:/";
    public static final String LOGIN = "login";
    public static final String SINGIN = "singin";
    public static final String HOME = "home";
    public static final String USER_LIST = "user-list";
    public static final String USER_CHANGE_ROLE = "user-changeRole";
    public static final String USER_CHANGE_DETAIL = "user-detail";
    public static final String PART_LIST = "part-list";
    public static final String CABINET = "cabinet";
    public static final String PART_CREATE = "part-create";
    public static final String PART_EDIT = "part-edit";
    public static final String DOCUM_LIST = "document-list";
    public static final String DOCUM_CREATE = "document-create";
    public static final String DOCUM_EDIT = "document-edit";
    public static final String DOCUM_DELETE = "document-delete";
    public static final String DOCPART_DETAIL = "docpart-detail";
    public static final String DOCPART_DELETE = "docpart-delete";
    public static final String DOCPART_EDIT = "docpart-edit";
    public static final String DOCPART_ADD = "docpart-add";

    public String pathUrl(String url) {
        return url.replace(BASE_URL, "").trim();
    }

    public boolean checkUrl(String url, String wordFirst, String wordSecond) {
        return url.contains(wordFirst) || url.contains(wordSecond);
    }
}