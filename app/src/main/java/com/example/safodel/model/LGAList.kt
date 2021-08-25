package com.example.safodel.model


class LGAList {
    companion object{
        fun init():Array<String>{
            val list =  listOf("(FALLS CREEK)","(MOUNT BULLER)","ALPINE",
            "ARARAT","BALLARAT","BANYULE","BASS COAST","BAW BAW","BAYSIDE"
            ,"BENALLA","BENDIGO","BOROONDARA","BRIMBANK","BULOKE","CAMPASPE"
            ,"CARDINIA","CASEY","CENTRAL GOLDFIELDS","COLAC OTWAY","CORANGAMITE",
            "DANDENONG","DAREBIN","EAST GIPPSLAND","FRANKSTON","GANNAWARRA",
            "GEELONG","GLEN EIRA","GLENELG","GOLDEN PLAINS","HEPBURN","HOBSONS BAY",
            "HORSHAM","HUME","INDIGO","KINGSTON","KNOX","LATROBE","MACEDON RANGES",
            "MANNINGHAM","MANSFIELD","MARIBYRNONG","MAROONDAH","MELBOURNE","MELTON",
            "MILDURA","MITCHELL","MOIRA","MONASH","MOONEE VALLEY")
            return list.toTypedArray()
        }
    }
}