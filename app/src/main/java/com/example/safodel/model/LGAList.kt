package com.example.safodel.model

import okhttp3.internal.toImmutableList
import java.util.*


class LGAList {

    /*-- init the LGA list --*/
    companion object{
        fun init():List<String>{
            val list =  Arrays.asList("BALLARAT","BANYULE","BASS COAST",
            "BAW BAW","BAYSIDE","BENALLA","BENDIGO","BOROONDARA","BRIMBANK"
            ,"BULOKE","CAMPASPE","CARDINIA","CASEY","CENTRAL GOLDFIELDS","COLAC OTWAY"
            ,"CORANGAMITE","DANDENONG","DAREBIN","EAST GIPPSLAND","FRANKSTON",
            "GANNAWARRA","GEELONG","GLEN EIRA","GLENELG","HEPBURN",
            "HOBSONS BAY","HORSHAM","HUME","KINGSTON","KNOX","LATROBE",
            "MACEDON RANGES","MANNINGHAM","MANSFIELD","MARIBYRNONG","MAROONDAH","MELBOURNE","MELTON",
            "MILDURA","MITCHELL","MOIRA","MONASH","MOONEE VALLEY","MOORABOOL",
            "MORELAND","MORNINGTON PENINSULA","MOUNT ALEXANDER","MOYNE","MURRINDINDI","NILLUMBIK",
            "NORTHERN GRAMPIANS","PORT PHILLIP","PYRENEES","QUEENSCLIFFE","SHEPPARTON","SOUTH GIPPSLAND",
             "SOUTHERN GRAMPIANS","STONNINGTON","SURF COAST","SWAN HILL","WANGARATTA",
            "WARRNAMBOOL","WELLINGTON","WHITEHORSE","WHITTLESEA","WODONGA","WYNDHAM","YARRA","YARRA RANGES")
            return list
        }
    }
}