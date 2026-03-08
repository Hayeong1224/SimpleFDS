package com.example.core.fds.model;

    /*
    record: enum과 같은 특별한 형태의 class
    간단하게 데이터를 저장하고 옮기는 역할
    일반 class와 다르게 생성자, getter, hashCode(), equals(), toString() 제공
     */

import java.util.List;

public record DetectionResult(
        int score,
        List<String> reasons
) {
    public static DetectionResult ok() {
        return new DetectionResult(0, List.of()); // 불변 리스트 생성
    }

}
