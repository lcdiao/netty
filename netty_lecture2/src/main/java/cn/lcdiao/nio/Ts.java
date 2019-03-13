package cn.lcdiao.nio;

/**
 * Created by diao on 2019/3/13
 */
public class Ts {

    public static void main(String[] args) {
        String source = "source";
        String target = "rced";

        System.out.println(strStr(source,target));
    }

    /**
     * @param source:
     * @param target:
     * @return: return the index
     */
    public static int strStr(String source, String target) {
        if(source.equals(target)){
            return 0;
        }
        if ("".equals(target)){
            return 0;
        }
        int sl = source.length();
        int tl = target.length();
        for(int i = 0; i < sl-tl+1; i++) {
            if(source.charAt(i) == target.charAt(0)){
                String s = source.substring(i,tl+i);
                if(s.equals(target)){
                    return i;
                }
            }
        }
        return -1;
    }
}
