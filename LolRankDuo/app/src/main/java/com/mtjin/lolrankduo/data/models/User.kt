package com.mtjin.lolrankduo.data.models

// 데이터 용량이 아닌 읽기/쓰기로 비용이 나가므로 편의상 String 타입 데이터로 한다.
data class User(
    var id: String = "", // 고유값 (카카오로그인 이메일로 할듯)
    var gameId: String = "", // 게임 아이디
    var profileImage: String = "", // 프로필 이미지 url
    var positionList: List<String> = ArrayList(), // 내 포지션
    var sex: String = "", // 성별
    var tear: String = "",  // 티어
    var age: String = "", // 나이
    var introduce: String = "", // 소개글
    var lastLoginTimestamp: Long = 0, //마지막 접속 시간
    var teamPositionList: List<String> = ArrayList(), //내가 원하는 팀원 포지션
    var voice: Boolean = false, //보이스 가능 여부
    var fcm: String = "", // FCM 토큰
    var historyIdList: List<String> = ArrayList(), // 이미 나와 이어진 아이디들 (재매칭안되게 하기위해)
    var recommend: Int = 0
)
