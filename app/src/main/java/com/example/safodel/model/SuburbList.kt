package com.example.safodel.model

import java.util.*

class SuburbList {

    /*-- init the LGA list --*/
    companion object{
        fun init():List<String>{
            val list =  Arrays.asList("NARRE WARREN","OCEAN GROVE","LAKES ENTRANCE","FRANKSTON NORTH",
                "MAFFRA","HAMPTON PARK","BAXTER","MORWELL","LORNE","GEELONG","DROMANA","ROSEBUD","MELTON",
                "ST KILDA","NOBLE PARK NORTH","SALE","ALTONA","WERRIBEE","CRANBOURNE","TORQUAY","BRIGHTON",
                "DANDENONG","RICHMOND","HOPPERS CROSSING","HAMPTON EAST","PORTARLINGTON","LANGWARRIN","MACLEOD",
                "ALTONA NORTH","PORT MELBOURNE","CORIO","BEAUMARIS","MULGRAVE","FAIRFIELD","CAMPBELLFIELD",
                "FITZROY","SOLDIERS HILL","LARA","BURNLEY","BEECHWORTH","GLENROY","PATTERSON LAKES",
                "CLIFTON HILL","MANIFOLD HEIGHTS","MELBOURNE","FITZROY NORTH","ELWOOD","CARRUM DOWNS",
                "COLAC","COLLINGWOOD","BENDIGO","HAMPTON","SOUTHBANK","WINDSOR","NORTH MELBOURNE",
                "ST KILDA EAST","WERRIBEE SOUTH","NARRE WARREN NORTH","MOOLAP","ECHUCA","FRANKSTON",
                "CARLTON NORTH","CLAYTON","BONBEACH","MENTONE","OAKLEIGH","CARLTON","ASCOT VALE",
                "HIGHETT","CRAIGIEBURN","EPPING","MOUNT CLEAR","BENALLA","PAKENHAM","GEELONG WEST",
                "SWAN HILL","BURNSIDE","MIDDLE PARK","FOOTSCRAY","GLEN WAVERLEY","DONVALE","BLACKBURN",
                "PRAHRAN","KEW","SHEPPARTON EAST","BRUNSWICK","ST KILDA WEST","CAULFIELD EAST","FLEMINGTON",
                "MAINDAMPLE","MANSFIELD","ALBERT PARK","MORNINGTON","SOUTH MELBOURNE","MAIDSTONE","MARIBYRNONG",
                "INVERMAY PARK","TOORAK","HORSHAM","SUNSHINE WEST","UPPER FERNTREE GULLY","ROWVILLE",
                "GROVEDALE","WHITTLESEA","DOCKLANDS","PARKDALE","BRUNSWICK EAST","EAST MELBOURNE","SELBY",
                "CAMBERWELL","KALORAMA","COBURG NORTH","DIAMOND CREEK","BOX HILL SOUTH","TEMPLESTOWE LOWER",
                "HAMILTON","MOUNT MARTHA","BLACK HILL","BUNDOORA","PASCOE VALE","DANDENONG SOUTH",
                "MOOROOPNA","THORNBURY","BANKSIA PENINSULA","GLEN IRIS","HUNTINGDALE","BALLARAT CENTRAL",
                "SHEPPARTON","YARRAVILLE","COBURG","BAYSWATER","HAWTHORN","BOX HILL NORTH",
                "MILL PARK","SPRINGVALE SOUTH","WALPA","MOUNT ELIZA","KANGAROO FLAT","SOUTH YARRA",
                "TOOTGAROOK","HASTINGS","BENTLEIGH EAST","WENDOUREE","KENSINGTON","BRAYBROOK","QUARRY HILL",
                "ESSENDON","KEW EAST","SUNSHINE","RINGWOOD NORTH","MOONEE PONDS","MOOROOLBARK",
                "NOBLE PARK","HERNE HILL","TRARALGON","MILDURA","RINGWOOD","TAYLORS LAKES","FERNTREE GULLY",
                "BROWN HILL","NORTHCOTE","PRESTON","CURLEWIS","DELACOMBE","CHELSEA HEIGHTS","MOUNT WAVERLEY",
                "ROSANNA","ECHUCA VILLAGE","WANGARATTA","CHILTERN","WODONGA","BORONIA","BERWICK","SOUTH MORANG",
                "NUNAWADING","CHELTENHAM","ALPHINGTON","OLINDA","BRAESIDE","BRIGHTON EAST","HEIDELBERG",
                "ABBOTSFORD","TREMONT","HAWTHORN EAST","WARRNAMBOOL","FERNY CREEK","MONT ALBERT","SUNBURY",
                "CARRUM","BENTLEIGH","ELTHAM","CAULFIELD NORTH","BLACK ROCK","BRUNSWICK WEST","ELLIMINYT",
                "TRAVANCORE","RESERVOIR","ARMADALE","NEWTOWN","HURSTBRIDGE","SEABROOK","CHELSEA","NEWCOMB",
                "WANGOOM","MALVERN","EAGLEHAWK","SASSAFRAS","THOMASTOWN","IRONBARK","HEIDELBERG HEIGHTS",
                "CAULFIELD","WILLIAMSTOWN","ARARAT","CANTERBURY","MURRUMBEENA","PARKVILLE","ST ALBANS","HAZELWOOD",
                "DEER PARK","RINGWOOD EAST","KANGAROO GROUND","CONGUPNA","BALWYN","ELSTERNWICK","RUTHERGLEN","YEA",
                "FRANKSTON SOUTH","FLORA HILL","MELTON SOUTH","PAYNESVILLE","BONEO","WEST MELBOURNE","SURREY HILLS",
                "ALTONA MEADOWS","ORMOND","GRAHAMVALE","IVANHOE","STRATHDALE","HALLAM","DRYSDALE","WALLAN",
                "WEST WODONGA","KILSYTH","LONG GULLY","WANTIRNA SOUTH","GARDENVALE","BURWOOD","BALWYN NORTH",
                "SANDRINGHAM","LILYDALE","NEWINGTON","DROUIN","BOWENVALE","INVERMAY","HEIDELBERG WEST","CROYDON",
                "SPRINGVALE","CAMPERDOWN","ABERFELDIE","STRATHFIELDSAYE","POINT LONSDALE","CREMORNE","FAWKNER",
                "PUCKAPUNYAL","CLAYTON SOUTH","MARYVALE","CAULFIELD SOUTH","DANDENONG NORTH","WHEELERS HILL",
                "EDEN PARK","LETHBRIDGE","MALVERN EAST","LALOR","VERMONT","CRANBOURNE NORTH","BROADMEADOWS",
                "WONGA PARK","ROXBURGH PARK","DIXONS CREEK","SORRENTO","KENNINGTON","MARONG","CLARINDA",
                "LAKE WENDOUREE","WAHGUNYAH","CARNEGIE","NEWBOROUGH","KERANG","MITCHAM","GOLDEN GULLY",
                "MOORABOOL","SEAHOLME","BUNYIP","MORDIALLOC","SCORESBY","WANGARATTA SOUTH","CROYDON SOUTH",
                "MCKINNON","BOX HILL","SPOTSWOOD","KEALBA","GREENSBOROUGH","BURWOOD EAST","CROYDON HILLS",
                "BALACLAVA","GOLDEN SQUARE","BUNINYONG","PORTSEA","BELLBRAE","NORTH WARRANDYTE","COROP",
                "KIALLA","BARNAWARTHA NORTH","INDENTED HEAD","HUGHESDALE","MAIDEN GULLY","EAST BENDIGO",
                "PORTLAND","OAKLEIGH EAST","KALLISTA","ASPENDALE","SEBASTOPOL","BAYSWATER NORTH","BLACKBURN SOUTH",
                "RYE","TAMBO UPPER","DENNINGTON","SEAFORD","BELMONT","POINT WILSON","NEWPORT","FINGAL",
                "WHITE HILLS","HADFIELD","ALFREDTON","DOVETON","BAKERY HILL","DINGLEY VILLAGE","WARRENHEIP",
                "NORTH BENDIGO","NARRE WARREN SOUTH","MYRTLEFORD","TIMBOON","NEW GISBORNE","MYRNIONG",
                "BELL POST HILL","SEDDON","BACCHUS MARSH","LOY YANG","HAMLYN HEIGHTS","LEONGATHA",
                "KINGSBURY","HEATHMONT","MOORABBIN","MERTON","IRYMPLE","RESEARCH","COBRAM","KINGSVILLE",
                "OAKLEIGH SOUTH","COHUNA","MALLACOOTA","MOUNT EVELYN","CALIFORNIA GULLY","CAPE WOOLAMAI",
                "TRAFALGAR","BURNSIDE HEIGHTS","QUEENSCLIFF","DONCASTER","ECHUCA WEST","MARLO","TATURA",
                "TEMPLESTOWE","TINAMBA WEST","MARYSVILLE","CAROLINE SPRINGS","INVERLOCH","SUNDAY CREEK",
                "VERMONT SOUTH","HEALESVILLE","CAPEL SOUND","PASCOE VALE SOUTH","GIRGARRE","CORRYONG",
                "KORUMBURRA","LYNBROOK","BAIRNSDALE","MERNDA","BREAKWATER","FOREST HILL","AIRPORT WEST",
                "MOUNT DUNEED","KOOYONG","YUROKE","ASHBURTON","SMITHS GULLY","CAIRNLEA","BALLARAT EAST",
                "NORTH GEELONG","WARRANWOOD","MONTMORENCY","SOMERVILLE","POINT COOK","DONCASTER EAST",
                "TULLAMARINE","BEECH FOREST","LAUNCHING PLACE","KNOXFIELD","MEENIYAN","KYNETON","BARWON HEADS",
                "BELGRAVE","CHADSTONE","WATTLE BANK","WATSONIA NORTH","SHERBROOKE","KINGLAKE",
                "TOOLERN VALE","SYDENHAM","TRUGANINA","AVONDALE HEIGHTS","BALNARRING","MOE","DONALD"
                ,"CONNEWARRE","KURUNJANG","ST ARNAUD","ST LEONARDS","WONTHAGGI","WARRAGUL","KOONWARRA",
                "BALLARAT NORTH","WESTMEADOWS","STRATFORD","WANTIRNA","KOO WEE RUP","NHILL","HAZELWOOD NORTH",
                "WANDANA HEIGHTS","LOVELY BANKS","SOUTH GEELONG","ASHWOOD","KALIMNA WEST","NICHOLS POINT",
                "HESKET","BULLEEN","TRARALGON EAST","WATSONIA","KEILOR EAST","IVANHOE EAST","DARLEY","THE BASIN"
                ,"MERBEIN","SEYMOUR","BLAIRGOWRIE","CASTLEMAINE","WOODEND","ESSENDON WEST","WHITTINGTON",
                "GLADSTONE PARK","MOUNT BEAUTY","TARNEIT","PARAPARAP","LEOPOLD","LYSTERFIELD","VENTNOR",
                "WARRANDYTE","PEARCEDALE","SPRING GULLY","TECOMA","APSLEY","NOTTING HILL","EDITHVALE",
                "NARRACAN","EASTWOOD","MENZIES CREEK","TYERS","VIEWBANK","DALLAS","RIPPONLEA",
                "BUCKLEY SWAMP","EAST GEELONG","LANCEFIELD","BRIAGOLONG","PINE LODGE","HEATHCOTE"
                ,"DEEPDENE","EMERALD","FAIRHAVEN","HMAS CERBERUS","CANADIAN","NUMURKAH","AIREYS INLET"
                ,"HEATHERTON","GEMBROOK","SAFETY BEACH","FALLS CREEK","BITTERN","GISBORNE",
                "EAGLEMONT","JACKASS FLAT","GREENVALE","DIGGERS REST","HIGHTON","SUNSHINE NORTH",
                "WONGARRA","COBAINS","MARYBOROUGH","ELTHAM NORTH","ORBOST","HARCOURT","MANOR LAKES"
                ,"BIG HILL","WEST FOOTSCRAY","TABILK","SMYTHES CREEK","ARTHURS CREEK","LYNDHURST",
                "YARRAWONGA","OVENS","YALLOURN NORTH","CRANBOURNE WEST","TAYLORS HILL","COCKATOO",
                "NORLANE","EPSOM","KEILOR DOWNS","MARSHALL","KEYSBOROUGH","ALBION","PRINCES HILL",
                "KILSYTH SOUTH","ASPENDALE GARDENS","BLACKBURN NORTH","JAN JUC","THOMSON","SKYE",
                "KEILOR","TERANG","CAPE PATERSON","BEAUFORT","COWES","MIRBOO NORTH","ATTWOOD",
                "KYABRAM","SAILORS GULLY","STRATHMORE","MONBULK","YARRAGON","RIDDELLS CREEK"
                ,"NORTH WONTHAGGI","CRESWICK","JUNORTOUN","NARRE WARREN EAST","BROOKLYN",
                "ST ANDREWS","LAVERTON","TIDAL RIVER","GOLDEN POINT","SURF BEACH","CLUNES",
                "BRIGHT","GLEN HUNTLY","MONTROSE","TARRAWINGEE","BAMAWM EXTENSION",
                "MONT ALBERT NORTH","WALLINGTON","GATEWAY ISLAND","AXE CREEK","MCKENZIE HILL",
                "WYNDHAM VALE","YALLAMBIE","BORUNG","ORRVALE","GOWANBRAE","MYRTLEBANK","BELL PARK"
                ,"GELLIBRAND LOWER","ENDEAVOUR HILLS","HILLSIDE","WATERWAYS","MELBOURNE AIRPORT"
                ,"TYLDEN","WANDIN NORTH","GOORAMADDA","BUXTON","LAKE BOGA","CRIB POINT","HARKAWAY"
                ,"ANGLESEA","KALIMNA","NATHALIA","NERRINA","WILDWOOD","GUNBOWER","CAMPBELLS CREEK",
                "GUNDOWRING","PORT FAIRY","CHURCHILL","HUMEVALE","LOWER PLENTY","DAYLESFORD","SILVAN"
                ,"COLAC WEST","PARK ORCHARDS","MOUNT PLEASANT","KINGS PARK","SARSFIELD","REDAN",
                "CLYDEBANK","LAVERTON NORTH","ROSEDALE","BULLA","COOLAROO","WATTLE FLAT","HARRIETVILLE"
                ,"NIDDRIE","WILLIAMS LANDING","EUMEMMERRING","BALOOK","TRENTHAM","KOROIT",
                "TOTTENHAM","SWAN REACH","CROYDON NORTH","BALMORAL","CASTERTON","ZEERUST","GOON NURE"
                ,"BRIDGEWATER ON LODDON","NAR NAR GOON","WARRACKNABEAL","MELTON WEST","BUCKLAND",
                "ARDMONA","MEPUNGA WEST","TOOLAMBA","LURG","CLOVERLEA","YAN YEAN","OFFICER"
                ,"MOUNT HELEN","DERRIMUT","CARDIGAN","MALDON","LITTLE RIVER","WINTER VALLEY",
                "NAYOOK","WARBURTON","TUNGAMAH","LAL LAL","CRANBOURNE EAST","DOOKIE","CLEMATIS",
                "SOUTH KINGSVILLE","NEWRY","CRANBOURNE SOUTH","COWWARR","QUANDONG","ALLANSFORD",
                "ARGYLE","LONGFORD","WILLIAMSTOWN NORTH","GLENORCHY","BEACONSFIELD","TYAAK","MACEDON"
                ,"HARKNESS","TYABB","GARFIELD NORTH","STAWELL","DELAHEY","HOWQUA INLET","BARNADOWN",
                "WOODVALE","GENTLE ANNIE","NEWBURY","BUNG BONG","TYNONG","POREPUNKAH","ARAPILES"
                ,"COSGROVE","MOORABBIN AIRPORT","BANDIANA","BELGRAVE SOUTH","MOGGS CREEK","NAGAMBIE"
                ,"MYALL","GLENGARRY","TUERONG","YENDON","DRUMCONDRA","CALULU","SANDHURST","COLDSTREAM",
                "BARFOLD","FORGE CREEK","MOUNT MACEDON","MICKLEHAM","GLENROWAN","COGHILLS CREEK","BOORT"
                ,"MERRICKS","AVENEL","HEYFIELD","QUAMBATOOK","BASS","MCCRAE","ALBANVALE","DRUMMOND NORTH",
                "ALEXANDRA","TARNAGULLA","AXEDALE","OXLEY","NEWHAM","ARTHURS SEAT","MOOROODUC","GHERINGHAP",
                "EUROA","JAMIESON","BOLWARRA","KEILOR PARK","SHADY CREEK","PYALONG","KILMORE","WHITEHEADS CREEK",
                "GUYS HILL","MOUNT DANDENONG","PARWAN","CATHCART","BATESFORD","TOOLANGI","BARRAMUNGA","YALLOURN",
                "MEADOW HEIGHTS","STRATHEWEN","BROADFORD","SMITHS BEACH","AVOCA","VIOLET TOWN","DALYSTON","BUSHFIELD"
                ,"METUNG","SILVERLEAVES","MOUNT BUFFALO","EASTERN VIEW","CLIFTON SPRINGS","MINERS REST",
                "WINGAN RIVER","FYANSFORD","NORTH WANGARATTA","NATIMUK","OAK PARK","ZUMSTEINS",
                "BEVERIDGE","YARRA JUNCTION","MOUNT BULLER","UPWEY","HALLS GAP","KIALLA EAST",
                "GREAT WESTERN","COTTLES BRIDGE","DRIFFIELD","RIPPLEBROOK","BROOKFIELD","TALLAROOK",
                "NARRAWONG","DOREEN","DIMBOOLA","AGNES","MURROON","BELLFIELD","BOCHARA","EILDON"
                ,"PLUMPTON","ASCOT","LAKE GARDENS","BOISDALE","APOLLO BAY","RED HILL","KELVIN VIEW",
                "YARRA GLEN","ST HELENA","VERVALE","AVALON","WYE RIVER","ONDIT","NEWBRIDGE",
                "SEPARATION CREEK","OUYEN","TARRINGTON","PYRAMID HILL","YACKANDANDAH","VECTIS"
                ,"GORDON","WAL WAL","CLYDE","WHITFIELD","MOUNT WALLACE","GONN CROSSING","CHRISTMAS HILLS",
                "TOWER HILL","ST CLAIR","POOWONG","MAIN RIDGE","BARWITE","EYNESBURY","MAGPIE"
                ,"RHYLL","DEANSIDE","TALLANGATTA VALLEY","TARWIN LOWER","GERMANTOWN","BUNGAREE",
                "CALDER PARK","BARANDUDA","BUFFALO RIVER","MITCHELL PARK","BOGONG","JEERALANG JUNCTION"
                ,"DEVON NORTH","DARNUM","TANYBRYN","FRYERSTOWN","DEANS MARSH","RAVENHALL",
                "BELLARINE","MIRIMBAH","ESSENDON NORTH","ALLANS FLAT","CHEWTON","ANAKIE","COBBLEBANK",
                "WOODSTOCK","ROMSEY","FOSTER","COCOROC","DAWSON","MUSK","RAVENSWOOD",
                "LOWER NORTON","WANDILIGONG","MORANDING","WATTLE GLEN","MILAWA","FLYNN","GERANGAMETE"
                ,"HARSTON","OAKLANDS JUNCTION","SHELFORD","HEPBURN SPRINGS","WILSONS PROMONTORY",
                "NORTH SHORE","MYERS FLAT","CAMBARVILLE","YUULONG","BETE BOLONG","SEDGWICK",
                "DARRAWEIT GUIM","STRATHMORE HEIGHTS","KIALLA WEST","BADGER CREEK","RUSSELLS BRIDGE"
                ,"GLEN PARK","WELLSFORD","LENEVA","CARDINIA","SULKY","BALLIANG","REDESDALE","ARDEER",
                "CONCONGELLA","YARRAMBAT","WINDERMERE","BANGHOLME","GRANTVILLE","COBDEN","ST ALBANS PARK"
                ,"LINDENOW","SEA LAKE","ANDERSON","MADDINGLEY","WOORAGEE","MOUNT ROWAN",
                "HAWKESDALE","BARONGAROOK","ARMSTRONG CREEK","ILLOWA","CHIRNSIDE PARK","THE PATCH",
                "CORA LYNN","YANNATHAN","GLENORMISTON NORTH","MERRICKS NORTH","BUCKLEY","MANDURANG"
                ,"HORDERN VALE","GLENGARRY WEST","BERRIWILLOCK","KENNETT RIVER","SOMERS","BUFFALO"
                ,"WAREEK","TOORADIN","LEARMONTH","MURRAYDALE","WILSONS HILL","STRATH CREEK",
                "RAVENSWOOD SOUTH","TOONGABBIE","TRARALGON SOUTH","BENWERRIN","CAPE SCHANCK",
                "ROBINVALE","LAKE MUNDI","SWANPOOL","WAURN PONDS","SEVILLE","LITCHFIELD"
                ,"GLENDONALD","CARLSRUHE","WEST BENDIGO","WOODEND NORTH","PHEASANT CREEK",
                "FERNSHAW","GRANITE ROCK","RIPPLESIDE","CHARLEMONT","CABBAGE TREE","MURCHISON EAST"
                ,"WINCHELSEA","BONNIE DOON","BRIAR HILL","WALMER","MERRIMU","KEILOR LODGE",
                "PANMURE","SHOREHAM","EGANSTOWN")
            return list
        }
    }
}

