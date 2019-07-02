package util;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


public class utils {

    public static String toDateToStr(Date date) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");// 完整的时间
        return sdf.format(date);
    }

    public static String toformatDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 完整的时间
        return sdf.format(date);
    }

//    public static String getDbTypeChar(DataBaseType baseType) {
//        String c = ":";
//        switch (baseType) {
//            case oracle:
//                c = ":";
//                break;
//            case mysql:
//                c = "@";
//                break;
//            case mssql:
//                c = "?";
//                break;
//            default:
//                break;
//        }
//        return c;
//    }

    public static Date GetAddDate(Date startDate, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, n);
        return c.getTime();
    }

    public static Date GetAddMonth(Date startDate, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.MONTH, +n);
        return c.getTime();
    }

    public static Date GetAddMonth(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, +n);
        return c.getTime();
    }

    public static Date GetAddDate(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, +n);
        return c.getTime();
    }

    public static Date GetMaxDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, +1000);
        return c.getTime();
    }

    public static String toAutoid(String c, Long id) {
        return String.format(c + "%020d", id);
    }

    public static String toAutoid(String c, int n, int id) {
        return String.format(c + "%0" + n + "d", id);
    }
    public static String toAutoid(String c, int n, long id) {
        return String.format(c + "%0" + n + "d", id);
    }

    public static String toAutoid(String c, int id) {
        return String.format(c + "%010d", id);
    }

//    public static FileInfo addFileInfo(String filename, String uuid, String suffix, Integer userid) throws Exception {
//        FileInfo file = new FileInfo();
//        int slash = filename.lastIndexOf("/");
//        if (slash == -1) {
//            slash = filename.lastIndexOf("\\");
//        }
//        if (slash > -1) {
//            filename = filename.substring(slash + 1, filename.length());
//        }
//        file.publisher_id = userid;
//        file.filename = filename;// 原来的文件名称
//        file.uuid = uuid;// uuid
//        file.suffix = suffix;
//        file.description = "普通上传";
//
//        return file;
//    }

    private static String getTypeFolder(int type) {
        String name = "temp";
        switch (type) {
            case 1:
                name = "Goods";
                break;
            case 2:
                name = "User";
                break;
            case 3:
                name = "Blog";
                break;
            case 4:
                name = "Information";
                break;
            case 5:
                name = "Customer";
                break;
            case 6:
                name = "workflow";
                break;
            default:
                break;
        }
        return name;
    }

    public static String getUploadPath(Integer comid, int type, Integer entityid) {
        String comidAutoid = toAutoid("C", comid);
        // /C:/BT_HOME/image/upload/C0000000004/Goods/2/232.jpg
        String typename = getTypeFolder(type);
        String ftpPath = "upload";
        String path = System.getenv("BT_HOME") + "/image/" + ftpPath + "/" + comidAutoid + "/" + typename + "/" + entityid.toString() + "/";
        File file2 = new File(path);
        if (!file2.exists() && !file2.isDirectory()) {
            file2.mkdirs();
        }
        return path;
    }

    public static String getFlowCaseFileUploadPath(Integer comid, int type, Integer entityid, Integer flowid, Integer caseid, Integer actid, Integer userid) {
        String comidAutoid = toAutoid("C", comid);
        // /C:/BT_HOME/image/upload/C0000000004/Goods/2/232.jpg
        String typename = getTypeFolder(type);
        String ftpPath = "upload";
        String path = System.getenv("BT_HOME") + "/image/" + ftpPath + "/{comidAutoid}/{typename}/{flowid}/{caseid}/{actid}/{userid}/{entiryid}/";

        path = path.replace("comidAutoid", comidAutoid).replace("typename", typename).replace("flowid", flowid.toString())
                .replace("caseid", caseid.toString()).replace("actid", actid.toString()).replace("userid", userid.toString()).replace("entityid", entityid.toString());
        File file2 = new File(path);
        if (!file2.exists() && !file2.isDirectory()) {
            file2.mkdirs();
        }
        return path;
    }

    @SuppressWarnings("rawtypes")
    public static List<HashMap<String, Object>> getFieldMap(List<String> plist, List list) {
        Iterator iterator = list.iterator();
        List<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
        while (iterator.hasNext()) {
            Object[] objs = (Object[]) iterator.next();

            HashMap<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < objs.length; i++) {
                String str = plist.get(i);
                str = str.replace(".", "_");
                map.put(str, objs[i]);
            }
            maps.add(map);
        }
        return maps;
    }

    public static Object getScale(Double v, int len) {
        BigDecimal bd = new BigDecimal(v);
        return bd.setScale(len, BigDecimal.ROUND_HALF_UP);
    }

    public static List<Integer> toIntegers(String ids) {
        String[] delArrIds = ids.split(",");
        List<Integer> delList = new ArrayList<Integer>();
        for (String s : delArrIds) {
            delList.add(Integer.parseInt(s));
        }
        return delList;
    }

    public static List<Integer> toPerent(long s, long f) {
        List<Integer> list = new ArrayList<Integer>();
        double key = Double.valueOf(s) / (Double.valueOf(s) + Double.valueOf(f));
        Object sp = utils.getScale(key, 2);
        Double ap = Double.valueOf(sp.toString()) * 100;
        Integer a = ap.intValue();
        double key2 = Double.valueOf(f) / (Double.valueOf(s) + Double.valueOf(f));
        Object fp = utils.getScale(key2, 2);
        Double bp = Double.valueOf(fp.toString()) * 100;
        int b = bp.intValue();

        list.add(a);
        list.add(b);
        return list;
    }

    public static float toFixed(float v, int len) {
        BigDecimal b = new BigDecimal(v);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    public static List<Map<String,Object>> addZero(List<Map<String,Object>> maps){
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            boolean isHas=false;
            for (Iterator<Map<String, Object>> iterator = maps.iterator(); iterator.hasNext(); ) {
                Map<String, Object> _map = iterator.next();
                if (Integer.valueOf(_map.get("HourSplit").toString())==i) {
                    list.add(_map);
                    isHas=true;
                    break;
                }
            }
            if (!isHas) {
                Map<String, Object> _mapZero = new HashMap<>();
                _mapZero.put("result", 0);
                _mapZero.put("total", 0);
                _mapZero.put("HourSplit", i);
                list.add(_mapZero);
            }
        }
        return list;
    }
}
