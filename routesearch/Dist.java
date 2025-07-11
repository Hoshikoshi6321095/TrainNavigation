package routesearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import routesearch.data.javafile.Station;
import routesearch.data.javafile.StationBranch;

public class Dist {
    private static int countHue = 0;
    private static int heu_mode = 1;  //0: hubeny, 1: haversine, 2: pythagoras
    private static Station start;
    private static Station goal;
    public static List<Node> open_list = new ArrayList<>();
    public static List<Node> close_list = new ArrayList<>();

    // 駅間の営業キロを返す.営業キロが求められなかったら-1
    public static float distance(Station start, Station goal) {
        if (start == null || goal == null) {
            return (float) -1;// 営業キロが求められなかった
        } else if (start == goal) {
            return (float) 0;// 同じ駅同士の距離は0
        }
        Dist.start = start;
        Dist.goal = goal;
        Node min_node = null;
        // float min_distance = 0;
        boolean searching = true;

        countHue = 0;
        open_list.clear();
        close_list.clear();

        open_list.add(new Node(start, 0, 0, null));

        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(Node nodeA, Node nodeB) {
                return Float.valueOf(nodeA.getF()).compareTo(Float.valueOf(nodeB.getF()));
            }
        };

        while (searching) {
            if (open_list.size() == 0) {
                return (float) -1;// 営業キロが求められなかった
            }
            min_node = Collections.min(open_list, comparator);

            if (min_node.getS().getName().equals(goal.getName())) {
                searching = false;
                // printRoot(min_node);
            } else {
                // System.out.println("展開開始！ ");
                // System.out.println(min_node.getS().getName());
                min_node.open(goal);
            }
        }
        // System.out.println(((float)Math.round(min_node.getG() * 10) / 10)+"km");
        return ((float) Math.round(min_node.getG() * 10) / 10);
    }

    public static int fare(Station start, Station goal) {
        List<String> sg = new ArrayList<>(Arrays.asList(start.getName(), goal.getName()));
        float distance = (float) distance(start, goal);
        if (distance < 0) {
            return -1;
        }
        int fare = 0;

        // 山手線の駅名を配列に格納
        String[] yamanoteStations = {
                "品川", "大崎", "五反田", "目黒", "恵比寿", "渋谷", "原宿", "代々木", "新宿",
                "新大久保", "高田馬場", "目白", "池袋", "大塚", "巣鴨", "駒込", "田端",
                "西日暮里", "日暮里", "鶯谷", "上野", "御徒町", "秋葉原", "神田", "東京",
                "有楽町", "新橋", "浜松町", "田町", "品川",

                "御茶ノ水", "水道橋", "飯田橋", "市ヶ谷", "四ツ谷", "信濃町", "千駄ヶ谷"
        };

        String[] tokyou_nishihunabashi = {
                "東京", "八丁堀", "越中島", "潮見", "新木場", "葛西臨海公園", "舞浜", "新浦安", "市川塩浜", "西船橋"
        };
        String[] shinjuku_takao = {
                "新宿", "中野", "高円寺", "阿佐ヶ谷", "荻窪", "西荻窪", "吉祥寺", "三鷹", "武蔵境", "東小金井", "武蔵小金井", "国分寺", "西国分寺", "国立", "立川",
                "日野", "豊田", "八王子", "西八王子", "高尾"
        };
        String[] shinjuku_hachiouji = {
                "新宿", "中野", "高円寺", "阿佐ヶ谷", "荻窪", "西荻窪", "吉祥寺", "三鷹", "武蔵境", "東小金井", "武蔵小金井", "国分寺", "西国分寺", "国立", "立川",
                "日野", "豊田", "八王子"
        };
        String[] shinjuku_haijima = {
                "新宿", "中野", "高円寺", "阿佐ヶ谷", "荻窪", "西荻窪", "吉祥寺", "三鷹", "武蔵境", "東小金井", "武蔵小金井", "国分寺", "西国分寺", "国立", "立川",
                "西立川", "東中神", "中神", "昭島", "拝島"
        };
        String[] shibuya_sakuragichou = {
                "渋谷", "恵比寿", "大崎", "西大井", "武蔵小杉", "新川崎", "横浜", "桜木町"
        };
        String[] shibuya_yokohama = {
                "渋谷", "恵比寿", "大崎", "西大井", "武蔵小杉", "新川崎", "横浜"
        };
        String[] shibuya_kichijouji = {
                "渋谷", "原宿", "代々木", "新宿", "中野", "高円寺", "阿佐ヶ谷", "荻窪", "西荻窪", "吉祥寺"
        };
        String[] shinbashi_kurihama = {
                "新橋", "品川", "西大井", "武蔵小杉", "新川崎", "横浜", "保土ヶ谷", "東戸塚", "戸塚", "大船", "北鎌倉", "鎌倉", "逗子", "東逗子", "田浦","横須賀", "衣笠", "久里浜",
                "大井町","大森","蒲田","川崎","鶴見","新子安","東神奈川"
        };
        String[] shinbashi_taura = {
                "新橋", "品川", "西大井", "武蔵小杉", "新川崎", "横浜", "保土ヶ谷", "東戸塚", "戸塚", "大船", "北鎌倉", "鎌倉", "逗子", "東逗子", "田浦",
                "大井町","大森","蒲田","川崎","鶴見","新子安","東神奈川"
        };
        String[] hamamatsuchou_yokosuka = {
                "浜松町", "田町", "高輪ゲートウェイ", "品川", "西大井", "武蔵小杉", "新川崎", "横浜", "保土ヶ谷", "東戸塚", "戸塚", "大船", "北鎌倉", "鎌倉", "逗子","東逗子", "田浦", "横須賀",
        };
        String[] shinagawa_kinugasa = {
                "品川", "西大井", "武蔵小杉", "新川崎", "横浜", "保土ヶ谷", "東戸塚", "戸塚", "大船", "北鎌倉", "鎌倉", "逗子", "東逗子", "田浦", "横須賀", "衣笠",
                "大井町","大森","蒲田","川崎","鶴見","新子安","東神奈川"
        };
        String[] shinagawa_zushi = {
                "品川", "西大井", "武蔵小杉", "新川崎", "横浜", "保土ヶ谷", "東戸塚", "戸塚", "大船", "北鎌倉", "鎌倉", "逗子",
                "大井町","大森","蒲田","川崎","鶴見","新子安","東神奈川"
        };
        String[] shinagawa_yokohama = {
                "品川", "西大井", "武蔵小杉", "新川崎", "横浜",
                "大井町","大森","蒲田","川崎","鶴見","新子安","東神奈川"
        };
        String[] yokohama_taura = {
                "横浜", "保土ヶ谷", "東戸塚", "戸塚", "大船", "北鎌倉", "鎌倉", "逗子", "東逗子", "田浦"
        };
        String[] yokohama_zushi = {
                "横浜", "保土ヶ谷", "東戸塚", "戸塚", "大船", "北鎌倉", "鎌倉", "逗子"
        };

        boolean is_yamanote = false;
        if (Arrays.asList(yamanoteStations).containsAll(sg)) {
            is_yamanote = true;
        }

        // 運賃の場合分け
        if (distance <= 3) {
            fare = 146;
        } else if (distance <= 6) {
            fare = 167;
        } else if (distance <= 10) {
            fare = 178;
        } else if (distance <= 15) {
            if (is_yamanote) {
                fare = 208;
            } else {
                fare = 230;
            }
        } else if (distance <= 20) {
            if (is_yamanote) {
                fare = 274;
            } else {
                fare = 318;
            }
        } else if (distance <= 25) {
            if (is_yamanote) {
                fare = 351;
            } else {
                fare = 406;
            }
        } else if (distance <= 30) {
            if (is_yamanote) {
                fare = 428;
            } else {
                fare = 483;
            }
        } else if (distance <= 35) {
            if (is_yamanote) {
                fare = 494;
            } else {
                fare = 571;
            }
        } else if (distance <= 40) {
            if (is_yamanote) {
                fare = 571;
            } else {
                fare = 659;
            }
        } else if (distance <= 45) {
            if (is_yamanote) {
                fare = 637;
            } else {
                fare = 736;
            }
        } else if (distance <= 50) {
            fare = 824;
        } else if (distance <= 60) {
            fare = 945;
        } else if (distance <= 70) {
            fare = 1100;
        } else if (distance <= 80) {
            fare = 1275;
        } else if (distance <= 90) {
            fare = 1451;
        } else if (distance <= 100) {
            fare = 1616;
        } else if (distance <= 120) {
            fare = 1880;
        } else if (distance <= 140) {
            fare = 2210;
        } else if (distance <= 160) {
            fare = 2540;
        } else if (distance <= 180) {
            fare = 2870;
        } else if (distance <= 200) {
            fare = 3200;
        } else if (distance <= 220) {
            fare = 3530;
        } else if (distance <= 240) {
            fare = 3860;
        } else if (distance <= 260) {
            fare = 4190;
        } else if (distance <= 280) {
            fare = 4520;
        } else if (distance <= 300) {
            fare = 4850;
        } else if (distance <= 320) {
            fare = 5180;
        } else if (distance <= 340) {
            fare = 5510;
        } else if (distance <= 360) {
            fare = 5730;
        } else if (distance <= 380) {
            fare = 5940;
        } else if (distance <= 400) {
            fare = 6270;
        }

        else {
            // それ以上の距離の場合
            fare = -1;
        }
        
        if ((fare > 230) && Arrays.asList(shibuya_kichijouji).containsAll(sg) ) {
            fare = 230;
            // return fare;
        }
        if ((fare > 303) && Arrays.asList(shinagawa_yokohama).containsAll(sg) ) {
            fare = 303;
            // return fare;
        }
        if ((fare > 318) && Arrays.asList(tokyou_nishihunabashi).containsAll(sg)) {
            fare = 318;
            // return fare;
        }
        if ((fare > 356) && Arrays.asList(yokohama_zushi).containsAll(sg)) {
            fare = 356;
            // return fare;
        }
        if ((fare > 406) && Arrays.asList(shibuya_yokohama).containsAll(sg)) {
            fare = 406;
            // return fare;
        }
        if ((fare > 483) && Arrays.asList(shinjuku_haijima).containsAll(sg)) {
            fare = 483;
            // return fare;
        }
        if ((fare > 483) && Arrays.asList(shibuya_sakuragichou).containsAll(sg)) {
            fare = 483;
            // return fare;
        }
        if ((fare > 483) && Arrays.asList(yokohama_taura).containsAll(sg)) {
            fare = 483;
            // return fare;
        }
        if ((fare > 492) && Arrays.asList(shinjuku_hachiouji).containsAll(sg)) {
            fare = 492;
            // return fare;
        }
        if ((fare > 571) && Arrays.asList(shinjuku_takao).containsAll(sg)) {
            fare = 571;
            // return fare;
        }
        if ((fare > 736) && Arrays.asList(shinagawa_zushi).containsAll(sg)) {
            fare = 736;
            // return fare;
        }
        if ((fare > 824) && Arrays.asList(shinbashi_taura).containsAll(sg)) {
            fare = 824;
            // return fare;
        }
        if ((fare > 824) && Arrays.asList(hamamatsuchou_yokosuka).containsAll(sg)) {
            fare = 824;
            // return fare;
        }
        if ((fare > 824) && Arrays.asList(shinagawa_kinugasa).containsAll(sg)) {
            fare = 824;
            // return fare;
        }
        if ((fare > 945) && Arrays.asList(shinbashi_kurihama).containsAll(sg)) {
            fare = 945;
            // return fare;
        }
        //System.out.println(countHue);
        return fare;
    }
    /*
     * //https://qiita.com/Yuzu2yan/items/0f312954feeb3c83c70e
     * //球面三角法 GeographyLibライブラリ 三平方の定理などと比較。open関数が呼ばれる回数をカウントしてアルゴリズムの無駄を確認する。
     * public static float heuristic(Station st_a, Station st_b) {
     * //double POLE_RADIUS = 6356752; // 極半径(短半径) Ry
     * double EQUATOR_RADIUS = 6378137; // 赤道半径(長半径) Rx
     * //double E = 0.081819191042815790; // 離心率
     * double E2= 0.006694380022900788; // 離心率の２乗
     * 
     * double lat_radius_a = Math.toRadians(st_a.getY());
     * double lon_radius_a = Math.toRadians(st_a.getX());
     * double lat_radius_b = Math.toRadians(st_b.getY());
     * double lon_radius_b = Math.toRadians(st_b.getX());
     * 
     * double dy = (lat_radius_a - lat_radius_b);
     * double dx = (lon_radius_a - lon_radius_b);
     * double p = (lat_radius_a + lat_radius_b) / 2;
     * 
     * double w = Math.sqrt(1-E2*Math.pow(Math.sin(p), 2));
     * double m = EQUATOR_RADIUS*(1-E2) / Math.pow(w, 3); // 子午線曲率半径
     * double n = EQUATOR_RADIUS / w; //卯酉線曲率半径
     * double d = Math.sqrt(Math.pow(dy*m, 2) + Math.pow(dx*n*Math.cos(p), 2));
     * 
     * return (float) (Math.round(d/100) / 10);
     * }
     */

    // https://qiita.com/Yuzu2yan/items/0f312954feeb3c83c70e
    // 球面三角法 GeographyLibライブラリ 三平方の定理などと比較。open関数が呼ばれる回数をカウントしてアルゴリズムの無駄を確認する。

    public static float heuristic(Station a, Station b) {
        float distance = 0;
        countHue++;

        switch (heu_mode) {
            case 0:
                distance = hubeny(a, b);
                break;
            case 1:
                distance = haversine(a, b);
                break;
            default:
                distance = pythagoras(a, b);
                break;
        }

        return distance;
    }

    public static float hubeny(Station a, Station b) { // 地球を回転楕円体と見立てる。
        // double POLE_RADIUS = 6356752; // 極半径(短半径) Ry
        double EQUATOR_RADIUS = 6378137; // 赤道半径(長半径) Rx
        // double E = 0.081819191042815790; // 離心率
        double E2 = 0.006694380022900788; // 離心率の２乗

        double lat_radius_a = Math.toRadians(a.getY());
        double lon_radius_a = Math.toRadians(a.getX());
        double lat_radius_b = Math.toRadians(b.getY());
        double lon_radius_b = Math.toRadians(b.getX());

        double dy = (lat_radius_a - lat_radius_b);
        double dx = (lon_radius_a - lon_radius_b);
        double p = (lat_radius_a + lat_radius_b) / 2;

        double w = Math.sqrt(1 - E2 * Math.pow(Math.sin(p), 2));
        double m = EQUATOR_RADIUS * (1 - E2) / Math.pow(w, 3); // 子午線曲率半径
        double n = EQUATOR_RADIUS / w; // 卯酉線曲率半径
        double d = Math.sqrt(Math.pow(dy * m, 2) + Math.pow(dx * n * Math.cos(p), 2));

        return (float) (Math.round(d / 100) / 10);
    }

    public static float haversine(Station a, Station b) { // 球面三角法（地球を球に見立てる）
        double EQUATOR_RADIUS = 6378.137; // km

        double lat_radius_a = Math.toRadians(a.getY());
        double lon_radius_a = Math.toRadians(a.getX());
        double lat_radius_b = Math.toRadians(b.getY());
        double lon_radius_b = Math.toRadians(b.getX());

        return (float) (EQUATOR_RADIUS * Math.acos(Math.sin(lat_radius_a) * Math.sin(lat_radius_b)
                + Math.cos(lat_radius_a) * Math.cos(lat_radius_b) * Math.cos(lon_radius_b - lon_radius_a)));
    }

    public static float pythagoras(Station a, Station b) { // 一旦ただの三平方の定理で回す
        double h = Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
        return (float) h;
    }

    public static void showList() {
        System.out.print("open [");
        for (Node op : Dist.open_list) {
            System.out.print(op.getS().getName() + "(" + op.getF() + ") ");
        }
        System.out.println("]");
        System.out.print("close [");
        for (Node cl : Dist.close_list) {
            System.out.print(cl.getS().getName() + "(" + cl.getF() + ") ");
        }
        System.out.println("]");
        System.out.println("");
    }

    public static void printRoot(Node goal) {
        Node tmp = goal;
        while (true) {
            if (tmp.getPreNode() != null) {
                System.out.print(tmp.getS().getName() + "\n ↑\n");
                tmp = tmp.getPreNode();
            } else {
                System.out.println(tmp.getS().getName());
                return;
            }
        }
    }

    public static void setHueMode(int mode) {
        Dist.heu_mode = mode;
        return;
    }
    static class Node {
        private Station s;
        private float f;
        private float g;
        private Node preNode;

        public Node(Station s, float f, float g, Node preNode) {
            this.s = s;
            this.f = f;
            this.g = g;
            this.preNode = preNode;
        }

        public void open(Station goal) {
            for (StationBranch b : this.s.getBranch()) {
                float new_g = this.g + b.getDistance();
                Station next_st = b.nextStation(this.s);
                new Node(next_st, new_g + heuristic(next_st, goal), new_g, this).updateList();
            }

            open_list.remove(this);
            close_list.add(this);

            // System.out.println("展開終了！");
            // Dist.showList();
        }

        @Override
        public boolean equals(Object obj) {

            if (obj instanceof Node) {
                Node n = (Node) obj;
                // return this.s.getName().equals(n.s.getName()); 何故かできそうな雰囲気がある
                return this.s.getName().equals(n.getS().getName());
            }
            /*
             * else if(obj instanceof Station) {
             * Station st = (Station) obj;
             * return this.s.getName().equals(st.getName());
             * }
             */
            return false;
        }
        // 通常、equals関数をオーバーライドする場合は、hashCode関数もオーバーライドする必要があるが、
        // 今回のコードでは、hashCodeが関係するコレクション等を特に使用していないため、省略する。

        public void updateList() {
            for (Node op : open_list) {
                if (this.equals(op)) {
                    if (this.f < op.getF()) {
                        open_list.remove(op);
                        break;
                    } else {
                        // Dist.showList();
                        return;
                    }
                }
            }

            for (Node cl : close_list) {
                if (this.equals(cl)) {
                    if (this.f < cl.getF()) {
                        close_list.remove(cl);
                        break;
                    } else {
                        // Dist.showList();
                        return;
                    }
                }
            }

            open_list.add(this);
            // Dist.showList();
        }

        public float getF() {
            return this.f;
        }

        public Station getS() {
            return this.s;
        }

        public float getG() {
            return this.g;
        }

        public Node getPreNode() {
            return preNode;
        }
    }
}