package com.mtjin.lolrankduo.utils.extensions

fun String.toRankNum(rank: String): Int {
    when (rank) {
        "Iron 4" -> return 0
        "Iron 3" -> return 1
        "Iron 2" -> return 2
        "Iron 1" -> return 3
        "Bronze 4" -> return 4
        "Bronze 3" -> return 5
        "Bronze 2" -> return 6
        "Bronze 1" -> return 7
        "Silver 4" -> return 8
        "Silver 3" -> return 9
        "Silver 2" -> return 10
        "Silver 1" -> return 11
        "Gold 4" -> return 12
        "Gold 3" -> return 13
        "Gold 2" -> return 14
        "Gold 1" -> return 15
        "Platinum 4" -> return 16
        "Platinum 3" -> return 17
        "Platinum 2" -> return 18
        "Platinum 1" -> return 19
        "Diamond 4" -> return 20
        "Diamond 3" -> return 21
        "Diamond 2" -> return 22
        "Diamond 1" -> return 23
        "Master" -> return 24
        "GrandMaster" -> return 25
        "Challenger" -> return 26
    }
    return 0
}

fun Int.toRankName(rankNum: Int): String {
    when (rankNum) {
        0 -> return "Iron 4"
        1 -> return "Iron 3"
        2 -> return "Iron 2"
        3 -> return "Iron 1"
        4 -> return "Bronze 4"
        5 -> return "Bronze 3"
        6 -> return "Bronze 2"
        7 -> return "Bronze 1"
        8 -> return "Silver 4"
        9 -> return "Silver 3"
        10 -> return "Silver 2"
        11 -> return "Silver 1"
        12 -> return "Gold 4"
        13 -> return "Gold 3"
        14 -> return "Gold 2"
        15 -> return "Gold 1"
        16 -> return "Platinum 4"
        17 -> return "Platinum 3"
        18 -> return "Platinum 2"
        19 -> return "Platinum 1"
        20 -> return "Diamond 4"
        21 -> return "Diamond 3"
        22 -> return "Diamond 2"
        23 -> return "Diamond 1"
        24 -> return "Master"
        25 -> return "GrandMaster"
        26 -> return "Challenger"
    }
    return "Iron 4"
}


/*<item>Iron 4</item>
<item>Iron 3</item>
<item>Iron 2</item>
<item>Iron 1</item>
<item>Bronze 4</item>
<item>Bronze 3</item>
<item>Bronze 2</item>
<item>Bronze 1</item>
<item>Silver 4</item>
<item>Silver 3</item>
<item>Silver 2</item>
<item>Silver 1</item>
<item>Gold 4</item>
<item>Gold 3</item>
<item>Gold 2</item>
<item>Gold 1</item>
<item>Platinum 4</item>
<item>Platinum 3</item>
<item>Platinum 2</item>
<item>Platinum 1</item>
<item>Diamond 4</item>
<item>Diamond 3</item>
<item>Diamond 2</item>
<item>Diamond 1</item>
<item>Master</item>
<item>GrandMaster</item>
<item>Challenger</item>*/
